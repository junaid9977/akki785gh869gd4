package com.tanejkbd.dvtanejk.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.tanejkbd.dvtanejk.R
import com.tanejkbd.dvtanejk.models.WebsiteModel
import com.tanejkbd.dvtanejk.utils.Constant
import com.tanejkbd.dvtanejk.utils.Constant.ONE_THOUSAND_MILISECOND


class VisitLinksActivity : AppCompatActivity() {
    private lateinit var timerTextView: TextView
    private lateinit var webView: WebView
    private lateinit var activity: VisitLinksActivity
    private lateinit var visitType: String
    private var isNull: Boolean = false
    private var isCountdownFinish: Boolean = false
    private var isCountDownStart: Boolean = false
    private var countDownTimer: CountDownTimer? = null
    private var time: Long = 0
    private lateinit var websiteModel: WebsiteModel
    private var url: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_visit_links)
        activity = this
        timerTextView = findViewById(R.id.time_text)
        webView = findViewById(R.id.webView)
        intent?.let {
            with(it) {
                visitType = getStringExtra("type") as String
                Log.e("TYPE", "onCreate: $visitType")
                if (visitType.equals("website", ignoreCase = true)
                    || visitType.equals("video", ignoreCase = true)
                ) {
                    websiteModel = getSerializableExtra("websiteModal") as WebsiteModel
                    setDataToActivity(
                        link = websiteModel.visit_link,
                        time_visit = websiteModel.visit_timer
                    )
                } else {
                    unableToGetIntent()
                }
            }
            setUpWebViewAndLoadUrl()
        } ?: kotlin.run {
            unableToGetIntent()
            return
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setUpWebViewAndLoadUrl() {
        url?.let {
            webView.webViewClient = MyBrowser()
            webView.settings.loadsImagesAutomatically = true
            webView.settings.javaScriptEnabled = true
            webView.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
            webView.loadUrl(it)
            webView.visibility = View.VISIBLE
        } ?: kotlin.run {
            unableToGetIntent()
        }
    }

    private fun setDataToActivity(link: String?, time_visit: String?) {
        link?.let {
            url = it
            if (!it.contains("http://") && !it.contains("https://")) {
                url = "http://$url"
            }
            time_visit?.let { t ->
                time = t.toLong() * 60
                timerTextView.text = "$time s"
            }
        } ?: kotlin.run {
            unableToGetIntent()
        }
    }

    inner class MyBrowser : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            view.loadUrl(url)
            return true
        }

        override fun onPageFinished(view: WebView, url: String) {
            if (countDownTimer == null) {
                if (!isCountdownFinish) {
                    isCountDownStart = true
                    startCountdownTimer()
                }
            }
            super.onPageFinished(view, url)
        }
    }

    override fun onResume() {
        if (isCountDownStart) {
            startCountdownTimer()
        }
        super.onResume()
    }

    override fun onPause() {
        stopTimer()
        super.onPause()
    }

    private fun unableToGetIntent() {
        Constant.showToastMessage(activity, "Something went wrong try again")
        isNull = true
        onBackPressed()
    }

    private fun startCountdownTimer() {
        if (countDownTimer != null) {
            return
        }
        countDownTimer = object : CountDownTimer(
            time * ONE_THOUSAND_MILISECOND,
            ONE_THOUSAND_MILISECOND.toLong()
        ) {
            override fun onTick(millisUntilFinished: Long) {
                val t = millisUntilFinished / 1000
                time = t
                timerTextView.text = "$t s"
            }

            override fun onFinish() {
                isCountdownFinish = true
                stopTimer()
                setResultToActivity(RESULT_OK)
            }
        }
        countDownTimer?.start()
    }


    private fun stopTimer() {
        countDownTimer?.cancel().also {
            countDownTimer = null
        }
    }


    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
            return
        }
        if (isNull) {
            stopTimer()
            setResultToActivity(RESULT_CANCELED)
            return
        }
        if (countDownTimer != null) {
            AlertDialog.Builder(activity)
                .setTitle("Are You Sure?")
                .setMessage("If You back then no points will credited to your wallet")
                .setPositiveButton("Yes") { dialog, _ ->
                    dialog.dismiss()
                    stopTimer()
                    setResultToActivity(RESULT_CANCELED)
                }.setNegativeButton(
                    "No"
                ) { dialog, _ -> dialog.dismiss() }.show()
        } else {
            stopTimer()
            setResultToActivity(RESULT_CANCELED)
        }
    }

    private fun setResultToActivity(result: Int) {
        setResult(result, Intent())
        finish()
    }
}