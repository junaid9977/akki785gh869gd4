package com.tanejkbd.dvtanejk.activity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.tanejkbd.dvtanejk.App;
import com.tanejkbd.dvtanejk.R;
import com.tanejkbd.dvtanejk.utils.Constant;
import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxAdViewAdListener;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.MaxReward;
import com.applovin.mediation.MaxRewardedAdListener;
import com.applovin.mediation.ads.MaxAdView;
import com.applovin.mediation.ads.MaxRewardedAd;
import com.applovin.sdk.AppLovinSdkUtils;
import com.startapp.sdk.ads.banner.Banner;
import com.startapp.sdk.ads.banner.BannerListener;
import com.startapp.sdk.adsbase.Ad;
import com.startapp.sdk.adsbase.StartAppAd;
import com.startapp.sdk.adsbase.adlisteners.AdDisplayListener;
import com.unity3d.ads.IUnityAdsInitializationListener;
import com.unity3d.ads.IUnityAdsLoadListener;
import com.unity3d.ads.IUnityAdsShowListener;
import com.unity3d.ads.UnityAds;
import com.unity3d.ads.UnityAdsShowOptions;
import com.unity3d.services.banners.BannerErrorInfo;
import com.unity3d.services.banners.BannerView;
import com.unity3d.services.banners.UnityBannerSize;

import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class WatchAndEarnActivity extends AppCompatActivity implements IUnityAdsInitializationListener,
        MaxAdViewAdListener,
        MaxRewardedAdListener {

    WatchAndEarnActivity activity;
    RelativeLayout watch_btn1, watch_btn2, watch_btn3, watch_btn4, watch_btn5, watch_btn6, watch_btn7, watch_btn8, watch_btn9, watch_btn10;
    TextView user_points_text_view, total_watch, watch_count_textView, watch_text1, watch_text2, watch_text3, watch_text4, watch_text5,
            watch_text6, watch_text7, watch_text8, watch_text9, watch_text10;
    ImageView watch_img1, watch_img2, watch_img3, watch_img4, watch_img5, watch_img6, watch_img7, watch_img8, watch_img9, watch_img10;
    int counter = 0;
    String currentDate, last_date;

    private final String TAG = WatchAndEarnActivity.class.getSimpleName();
    private StartAppAd startAppAd;
    private BannerView bottomBanner;
    String adType;
    private TextView msgText;
    CountDownTimer adsClickTimer;
    boolean isClickTimerTrue = false;
    int seconds;
    private MediaPlayer popupSound;
    private MediaPlayer collectSound;
    private MaxAdView adView;
    private MaxRewardedAd maxRewardedAd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_and_earn);

        activity = this;
        watch_count_textView = findViewById(R.id.watch_count_textView);
        msgText = findViewById(R.id.msgText);
        user_points_text_view = findViewById(R.id.user_points_text_view);
        total_watch = findViewById(R.id.total_watch);
        watch_btn1 = findViewById(R.id.watch_btn1);
        watch_btn2 = findViewById(R.id.watch_btn2);
        watch_btn3 = findViewById(R.id.watch_btn3);
        watch_btn4 = findViewById(R.id.watch_btn4);
        watch_btn5 = findViewById(R.id.watch_btn5);
        watch_btn6 = findViewById(R.id.watch_btn6);
        watch_btn7 = findViewById(R.id.watch_btn7);
        watch_btn8 = findViewById(R.id.watch_btn8);
        watch_btn9 = findViewById(R.id.watch_btn9);
        watch_btn10 = findViewById(R.id.watch_btn10);
        watch_img1 = findViewById(R.id.watch_img1);
        watch_img2 = findViewById(R.id.watch_img2);
        watch_img3 = findViewById(R.id.watch_img3);
        watch_img4 = findViewById(R.id.watch_img4);
        watch_img5 = findViewById(R.id.watch_img5);
        watch_img6 = findViewById(R.id.watch_img6);
        watch_img7 = findViewById(R.id.watch_img7);
        watch_img8 = findViewById(R.id.watch_img8);
        watch_img9 = findViewById(R.id.watch_img9);
        watch_img10 = findViewById(R.id.watch_img10);
        watch_text1 = findViewById(R.id.watch_text1);
        watch_text2 = findViewById(R.id.watch_text2);
        watch_text3 = findViewById(R.id.watch_text3);
        watch_text4 = findViewById(R.id.watch_text4);
        watch_text5 = findViewById(R.id.watch_text5);
        watch_text6 = findViewById(R.id.watch_text6);
        watch_text7 = findViewById(R.id.watch_text7);
        watch_text8 = findViewById(R.id.watch_text8);
        watch_text9 = findViewById(R.id.watch_text9);
        watch_text10 = findViewById(R.id.watch_text10);
        adType = Constant.getString(activity, Constant.AD_TYPE);
        popupSound = MediaPlayer.create(activity, R.raw.popup);
        collectSound = MediaPlayer.create(activity, R.raw.collect);

        seconds = (int) ((Integer.parseInt(Constant.getString(activity, Constant.ADS_CLICK_TIME)) / 1000) % 60);
        msgText.setText(MessageFormat.format("Watch 10 ads to win {0} coins", Constant.getString(activity, Constant.DAILY_WATCH_POINTS)));

        Constant.adsShowingDialog(activity);
        user_points_text_view.setText(Constant.getString(activity, Constant.USER_POINTS));
        watch_btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                watch_btn1.setBackgroundResource(R.drawable.btn_disabled_bg);
                watch_img1.setBackgroundResource(R.drawable.watch_icon_gray);
                watch_text1.setTextColor(getResources().getColor(R.color.gray));
                watch_btn1.setEnabled(false);
                watchDialog();
            }
        });
        watch_btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                watch_btn2.setBackgroundResource(R.drawable.btn_disabled_bg);
                watch_img2.setBackgroundResource(R.drawable.watch_icon_gray);
                watch_text2.setTextColor(getResources().getColor(R.color.gray));
                watch_btn2.setEnabled(false);
                watchDialog();
            }
        });
        watch_btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                watch_btn3.setBackgroundResource(R.drawable.btn_disabled_bg);
                watch_img3.setBackgroundResource(R.drawable.watch_icon_gray);
                watch_text3.setTextColor(getResources().getColor(R.color.gray));
                watch_btn3.setEnabled(false);
                watchDialog();
            }
        });
        watch_btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                watch_btn4.setBackgroundResource(R.drawable.btn_disabled_bg);
                watch_img4.setBackgroundResource(R.drawable.watch_icon_gray);
                watch_text4.setTextColor(getResources().getColor(R.color.gray));
                watch_btn4.setEnabled(false);
                watchDialog();
            }
        });
        watch_btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                watch_btn5.setBackgroundResource(R.drawable.btn_disabled_bg);
                watch_img5.setBackgroundResource(R.drawable.watch_icon_gray);
                watch_text5.setTextColor(getResources().getColor(R.color.gray));
                watch_btn5.setEnabled(false);
                watchDialog();
            }
        });
        watch_btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                watch_btn6.setBackgroundResource(R.drawable.btn_disabled_bg);
                watch_img6.setBackgroundResource(R.drawable.watch_icon_gray);
                watch_text6.setTextColor(getResources().getColor(R.color.gray));
                watch_btn6.setEnabled(false);
                watchDialog();
            }
        });
        watch_btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                watch_btn7.setBackgroundResource(R.drawable.btn_disabled_bg);
                watch_img7.setBackgroundResource(R.drawable.watch_icon_gray);
                watch_text7.setTextColor(getResources().getColor(R.color.gray));
                watch_btn7.setEnabled(false);
                watchDialog();
            }
        });
        watch_btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                watch_btn8.setBackgroundResource(R.drawable.btn_disabled_bg);
                watch_img8.setBackgroundResource(R.drawable.watch_icon_gray);
                watch_text8.setTextColor(getResources().getColor(R.color.gray));
                watch_btn8.setEnabled(false);
                watchDialog();
            }
        });
        watch_btn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                watch_btn9.setBackgroundResource(R.drawable.btn_disabled_bg);
                watch_img9.setBackgroundResource(R.drawable.watch_icon_gray);
                watch_text9.setTextColor(getResources().getColor(R.color.gray));
                watch_btn9.setEnabled(false);
                watchDialog();
            }
        });
        watch_btn10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                watch_btn10.setBackgroundResource(R.drawable.btn_disabled_bg);
                watch_img10.setBackgroundResource(R.drawable.watch_icon_gray);
                watch_text10.setTextColor(getResources().getColor(R.color.gray));
                watch_btn10.setEnabled(false);
                watchDialog();
            }
        });


        if (adType.equalsIgnoreCase("startapp")) {
            LoadStartAppBanner();
            LoadStartAppInterstital();
        } else if (adType.equalsIgnoreCase("unity")) {
            loadUnityBanner();
        } else if (adType.equalsIgnoreCase("applovin")) {
            loadApplovinBanner();
            loadApplovinRewarded();
        }


        if (Constant.getString(activity, Constant.LAST_DATE_WATCH).equals(Constant.getString(activity, Constant.TODAY_DATE))) {
            watch_count_textView.setText("10");
            watch_btn1.setBackgroundResource(R.drawable.btn_disabled_bg);
            watch_img1.setBackgroundResource(R.drawable.watch_icon_gray);
            watch_text1.setTextColor(getResources().getColor(R.color.gray));
            watch_btn1.setEnabled(false);
            watch_btn2.setBackgroundResource(R.drawable.btn_disabled_bg);
            watch_img2.setBackgroundResource(R.drawable.watch_icon_gray);
            watch_text2.setTextColor(getResources().getColor(R.color.gray));
            watch_btn2.setEnabled(false);
            watch_btn3.setBackgroundResource(R.drawable.btn_disabled_bg);
            watch_img3.setBackgroundResource(R.drawable.watch_icon_gray);
            watch_text3.setTextColor(getResources().getColor(R.color.gray));
            watch_btn3.setEnabled(false);
            watch_btn4.setBackgroundResource(R.drawable.btn_disabled_bg);
            watch_img4.setBackgroundResource(R.drawable.watch_icon_gray);
            watch_text4.setTextColor(getResources().getColor(R.color.gray));
            watch_btn4.setEnabled(false);
            watch_btn5.setBackgroundResource(R.drawable.btn_disabled_bg);
            watch_img5.setBackgroundResource(R.drawable.watch_icon_gray);
            watch_text5.setTextColor(getResources().getColor(R.color.gray));
            watch_btn5.setEnabled(false);
            watch_btn6.setBackgroundResource(R.drawable.btn_disabled_bg);
            watch_img6.setBackgroundResource(R.drawable.watch_icon_gray);
            watch_text6.setTextColor(getResources().getColor(R.color.gray));
            watch_btn6.setEnabled(false);
            watch_btn7.setBackgroundResource(R.drawable.btn_disabled_bg);
            watch_img7.setBackgroundResource(R.drawable.watch_icon_gray);
            watch_text7.setTextColor(getResources().getColor(R.color.gray));
            watch_btn7.setEnabled(false);
            watch_btn8.setBackgroundResource(R.drawable.btn_disabled_bg);
            watch_img8.setBackgroundResource(R.drawable.watch_icon_gray);
            watch_text8.setTextColor(getResources().getColor(R.color.gray));
            watch_btn8.setEnabled(false);
            watch_btn9.setBackgroundResource(R.drawable.btn_disabled_bg);
            watch_img9.setBackgroundResource(R.drawable.watch_icon_gray);
            watch_text9.setTextColor(getResources().getColor(R.color.gray));
            watch_btn9.setEnabled(false);
            watch_btn10.setBackgroundResource(R.drawable.btn_disabled_bg);
            watch_img10.setBackgroundResource(R.drawable.watch_icon_gray);
            watch_text10.setTextColor(getResources().getColor(R.color.gray));
            watch_btn10.setEnabled(false);
        } else {
            watch_count_textView.setText("0");
            counter = 0;
            watch_btn1.setBackgroundResource(R.drawable.btn_bg_one);
            watch_img1.setBackgroundResource(R.drawable.watch_icon2);
            watch_text1.setTextColor(getResources().getColor(R.color.watch_color1));
            watch_btn1.setEnabled(true);
            watch_btn2.setBackgroundResource(R.drawable.btn_bg_two);
            watch_img2.setBackgroundResource(R.drawable.watch_icon1);
            watch_text2.setTextColor(getResources().getColor(R.color.watch_color2));
            watch_btn2.setEnabled(true);
            watch_btn3.setBackgroundResource(R.drawable.btn_bg_three);
            watch_img3.setBackgroundResource(R.drawable.watch_icon4);
            watch_text3.setTextColor(getResources().getColor(R.color.watch_color3));
            watch_btn3.setEnabled(true);
            watch_btn4.setBackgroundResource(R.drawable.btn_bg_four);
            watch_img4.setBackgroundResource(R.drawable.watch_icon3);
            watch_text4.setTextColor(getResources().getColor(R.color.watch_color4));
            watch_btn4.setEnabled(true);
            watch_btn5.setBackgroundResource(R.drawable.btn_bg_one);
            watch_img5.setBackgroundResource(R.drawable.watch_icon2);
            watch_text5.setTextColor(getResources().getColor(R.color.watch_color1));
            watch_btn5.setEnabled(true);
            watch_btn6.setBackgroundResource(R.drawable.btn_bg_two);
            watch_img6.setBackgroundResource(R.drawable.watch_icon1);
            watch_text6.setTextColor(getResources().getColor(R.color.watch_color2));
            watch_btn6.setEnabled(true);
            watch_btn7.setBackgroundResource(R.drawable.btn_bg_three);
            watch_img7.setBackgroundResource(R.drawable.watch_icon4);
            watch_text7.setTextColor(getResources().getColor(R.color.watch_color3));
            watch_btn7.setEnabled(true);
            watch_btn8.setBackgroundResource(R.drawable.btn_bg_four);
            watch_img8.setBackgroundResource(R.drawable.watch_icon3);
            watch_text8.setTextColor(getResources().getColor(R.color.watch_color4));
            watch_btn8.setEnabled(true);
            watch_btn9.setBackgroundResource(R.drawable.btn_bg_one);
            watch_img9.setBackgroundResource(R.drawable.watch_icon2);
            watch_text9.setTextColor(getResources().getColor(R.color.watch_color1));
            watch_btn9.setEnabled(true);
            watch_btn10.setBackgroundResource(R.drawable.btn_bg_two);
            watch_img10.setBackgroundResource(R.drawable.watch_icon1);
            watch_text10.setTextColor(getResources().getColor(R.color.watch_color2));
            watch_btn10.setEnabled(true);
        }
        adsClickTimer();
        Constant.loadInvalidCounter(activity);

    }

    private void loadApplovinBanner() {
        final LinearLayout layout = findViewById(R.id.banner_container);
        adView = new MaxAdView(Constant.getString(activity, Constant.APPLOVIN_BANNER_ID), activity);
        adView.setListener(this);
        int width = ViewGroup.LayoutParams.MATCH_PARENT;
        final boolean isTablet = AppLovinSdkUtils.isTablet(activity);
        final int heightPx = AppLovinSdkUtils.dpToPx(activity, isTablet ? 90 : 50);
        adView.setLayoutParams(new LinearLayout.LayoutParams(width, heightPx));
        layout.addView(adView);

        adView.loadAd();
        adView.setVisibility(View.VISIBLE);
        adView.startAutoRefresh();
    }


    private void watchDialog() {
        popupSound.start();
        SweetAlertDialog sweetAlertDialog;
        sweetAlertDialog = new SweetAlertDialog(activity, SweetAlertDialog.SUCCESS_TYPE);
        sweetAlertDialog.setCancelable(false);
        sweetAlertDialog.setCanceledOnTouchOutside(false);
        sweetAlertDialog.setTitleText("Good Job!");
        sweetAlertDialog.setConfirmText("Ok");
        sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.dismiss();
                counter++;
                Constant.showToastMessage(activity, String.valueOf(counter));
                watch_count_textView.setText(String.valueOf(counter));
                Constant.showAdsLoadingDialog();
                Constant.dismissAdsLoadingDialog();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        unityStartAppRewardedShow();
                        if (counter == 10) {
                            adsClickDialog();
                        }
                    }
                }, Constant.adsDialogTime);
            }
        }).show();
    }


    public void showDialogOfPoints(int points) {
        popupSound.start();
        if (points == Integer.parseInt(Constant.getString(activity, Constant.DAILY_WATCH_POINTS))) {
            new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                    .setTitleText("Congratulation!")
                    .setContentText("You won " + points + " " + "coins")
                    .show();
        } else {
            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Oops...")
                    .setContentText("Today Chance is Over!")
                    .show();
        }
    }

    private void loadApplovinRewarded() {
        if (maxRewardedAd == null) {
            maxRewardedAd = MaxRewardedAd.getInstance(Constant.getString(activity, Constant.APPLOVIN_REWARDED_ID), activity);
            maxRewardedAd.setListener(this);
        }
        maxRewardedAd.loadAd();
    }

    private void LoadStartAppInterstital() {
        if (startAppAd == null) {
            startAppAd = new StartAppAd(App.getContext());
            startAppAd.loadAd(StartAppAd.AdMode.AUTOMATIC);
        } else {
            startAppAd.loadAd(StartAppAd.AdMode.AUTOMATIC);
        }
    }

    private void ShowStartappInterstital() {
        if (startAppAd != null && startAppAd.isReady()) {
            startAppAd.showAd(new AdDisplayListener() {
                @Override
                public void adHidden(Ad ad) {
                    if (counter == 11) {
                        timerNotFinishDialog();
                    }
                }

                @Override
                public void adDisplayed(Ad ad) {
                    if (counter == 11) {
                        Constant.showToastMessage(activity, "Click on this Ads to win " + Constant.getString(activity, Constant.DAILY_WATCH_POINTS) + " coins");
                    }
                }

                @Override
                public void adClicked(Ad ad) {
                    if (counter == 11) {
                        counter = 0;
                        adsClickTimer.start();
                    } else {
                        Constant.invalidClickCount++;
                        Log.i("checkInvalidClick", String.valueOf(Constant.invalidClickCount));
                    }
                }

                @Override
                public void adNotDisplayed(Ad ad) {
                    LoadStartAppInterstital();
                }
            });
        } else {
            LoadStartAppInterstital();
        }
    }

    private void LoadStartAppBanner() {
        final LinearLayout layout = findViewById(R.id.banner_container);
        Banner banner = new Banner(activity, new BannerListener() {
            @Override
            public void onReceiveAd(View view) {
                layout.addView(view);
            }

            @Override
            public void onFailedToReceiveAd(View view) {

            }

            @Override
            public void onImpression(View view) {

            }

            @Override
            public void onClick(View view) {
                Constant.invalidClickCount++;
                Log.i("checkInvalidClick", String.valueOf(Constant.invalidClickCount));
            }
        });
        banner.loadAd(300, 50);
    }


    private void adsClickDialog() {
        popupSound.start();
        SweetAlertDialog sweetAlertDialog;

        sweetAlertDialog = new SweetAlertDialog(activity, SweetAlertDialog.SUCCESS_TYPE);
        sweetAlertDialog.setCancelable(false);
        sweetAlertDialog.setCanceledOnTouchOutside(false);
        sweetAlertDialog.setTitle("Wow! You won " + Constant.getString(activity, Constant.DAILY_WATCH_POINTS) + " coins");
        sweetAlertDialog.setContentText("Click on this ads & wait " + seconds + " sec" + " to win " + Constant.getString(activity, Constant.DAILY_WATCH_POINTS) + " coins");
        sweetAlertDialog.setConfirmText("Ok");
        sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                counter++;
                Constant.showAdsLoadingDialog();
                Constant.dismissAdsLoadingDialog();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        unityStartAppInterstitialShow();
                        sweetAlertDialog.dismiss();
                    }
                }, Constant.adsDialogTime);
            }
        }).show();
    }

    void adsClickTimer() {
        adsClickTimer = new CountDownTimer(Integer.parseInt(Constant.getString(activity, Constant.ADS_CLICK_TIME)), 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                Toast.makeText(activity, "Timer left: " + millisUntilFinished / 1000, Toast.LENGTH_LONG).show();
                isClickTimerTrue = true;
            }

            @Override
            public void onFinish() {
                isClickTimerTrue = false;
                collectDialog();
            }
        };
    }

    private void collectDialog() {
        SweetAlertDialog sweetAlertDialog;
        sweetAlertDialog = new SweetAlertDialog(activity, SweetAlertDialog.WARNING_TYPE);
        sweetAlertDialog.setCancelable(false);
        sweetAlertDialog.setCanceledOnTouchOutside(false);
        sweetAlertDialog.setTitle("Are you sure you want to collect your coins?");
        sweetAlertDialog.setConfirmText("Yes");
        sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.dismiss();
                if (Constant.isNetworkAvailable(activity) && Constant.isOnline(activity)) {
                    currentDate = Constant.getString(activity, Constant.TODAY_DATE);
                    Log.e("TAG", "onClick: Current Date" + currentDate);
                    last_date = Constant.getString(activity, Constant.LAST_DATE_WATCH);
                    if (last_date.equalsIgnoreCase("0")) {
                        last_date = "";
                    }
                    Log.e("TAG", "onClick: last_date Date" + last_date);
                    if (last_date.equals("")) {
                        Constant.setString(activity, Constant.LAST_DATE_WATCH, currentDate);
                        Constant.addPoints(activity, Integer.parseInt(Constant.getString(activity, Constant.DAILY_WATCH_POINTS)), 0, "watch", currentDate);
                        user_points_text_view.setText(Constant.getString(activity, Constant.USER_POINTS));
                        showDialogOfPoints(Integer.parseInt(Constant.getString(activity, Constant.DAILY_WATCH_POINTS)));
                    } else {
                        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                        try {
                            Date pastDAte = sdf.parse(last_date);
                            Date currentDAte = sdf.parse(currentDate);
                            long diff = currentDAte.getTime() - pastDAte.getTime();
                            long difference_In_Days = (diff / (1000 * 60 * 60 * 24)) % 365;
                            Log.e("TAG", "onClick: Days Diffrernce" + difference_In_Days);
                            if (difference_In_Days > 0) {
                                Constant.setString(activity, Constant.LAST_DATE_WATCH, currentDate);
                                Constant.addPoints(activity, Integer.parseInt(Constant.getString(activity, Constant.DAILY_WATCH_POINTS)), 0, "watch", currentDate);
                                user_points_text_view.setText(Constant.getString(activity, Constant.USER_POINTS));
                                showDialogOfPoints(Integer.parseInt(Constant.getString(activity, Constant.DAILY_WATCH_POINTS)));
                            } else {
                                showDialogOfPoints(0);
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    Constant.showInternetErrorDialog(activity, "Please Check your Internet Connection");
                }
            }
        }).show();
    }


    private void unityStartAppInterstitialShow() {
        if (adType.equalsIgnoreCase("startapp")) {
            ShowStartappInterstital();
        } else if (adType.equalsIgnoreCase("unity")) {
            unityInterstitialAd();
        } else if (adType.equalsIgnoreCase("applovin")) {
            showApplovinRewarded();
        }
    }

    private void unityStartAppRewardedShow() {
        if (adType.equalsIgnoreCase("startapp")) {
            ShowStartappInterstital();
        } else if (adType.equalsIgnoreCase("unity")) {
            unityRewardedAd();
        } else if (adType.equalsIgnoreCase("applovin")) {
            showApplovinRewarded();
        }
    }

    private void showApplovinRewarded() {
        if (maxRewardedAd.isReady()) {
            maxRewardedAd.showAd();
        } else {
            loadApplovinRewarded();
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_enter, R.anim.slide_exit);
        finish();
    }

    public void timerNotFinishDialog() {
        SweetAlertDialog sweetAlertDialog;
        sweetAlertDialog = new SweetAlertDialog(activity, SweetAlertDialog.ERROR_TYPE);
        sweetAlertDialog.setCancelable(false);
        sweetAlertDialog.setCanceledOnTouchOutside(false);
        sweetAlertDialog.setTitleText("Oops!");
        sweetAlertDialog.setContentText("You missed " + Constant.getString(activity, Constant.DAILY_WATCH_POINTS) + " coins");
        sweetAlertDialog.setConfirmText("Ok");
        sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.dismiss();
            }
        }).show();
    }


    private void loadUnityBanner() {
        final LinearLayout layout = findViewById(R.id.banner_container);
        bottomBanner = new BannerView(activity, Constant.getString(activity, Constant.UNITY_BANNER_ID), new UnityBannerSize(320, 50));
        bottomBanner.setListener(bannerListener);
        bottomBanner.load();
        layout.addView(bottomBanner);
    }

    private BannerView.IListener bannerListener = new BannerView.IListener() {
        @Override
        public void onBannerLoaded(BannerView bannerAdView) {
            Log.v("unityCheck", "onBannerLoaded: " + bannerAdView.getPlacementId());
        }

        @Override
        public void onBannerFailedToLoad(BannerView bannerAdView, BannerErrorInfo errorInfo) {
            Log.e("unityCheck", "Unity Ads failed to load banner for " + bannerAdView.getPlacementId() + " with error: [" + errorInfo.errorCode + "] " + errorInfo.errorMessage);
        }

        @Override
        public void onBannerClick(BannerView bannerAdView) {
            Log.v("unityCheck", "onBannerClick: " + bannerAdView.getPlacementId());
        }

        @Override
        public void onBannerLeftApplication(BannerView bannerAdView) {
            Log.v("unityCheck", "onBannerLeftApplication: " + bannerAdView.getPlacementId());
        }
    };

    private IUnityAdsLoadListener loadListener = new IUnityAdsLoadListener() {
        @Override
        public void onUnityAdsAdLoaded(String placementId) {
        }

        @Override
        public void onUnityAdsFailedToLoad(String placementId, UnityAds.UnityAdsLoadError error, String message) {
            Log.e("UnityAdsExample", "Unity Ads failed to load ad for " + placementId + " with error: [" + error + "] " + message);
        }
    };

    private IUnityAdsShowListener showListener = new IUnityAdsShowListener() {
        @Override
        public void onUnityAdsShowFailure(String placementId, UnityAds.UnityAdsShowError error, String message) {
            Log.e("UnityAdsExample", "Unity Ads failed to show ad for " + placementId + " with error: [" + error + "] " + message);
        }

        @Override
        public void onUnityAdsShowStart(String placementId) {
            Log.v("UnityAdsExample", "onUnityAdsShowStart: " + placementId);
        }

        @Override
        public void onUnityAdsShowClick(String placementId) {
            Log.v("UnityAdsExample", "onUnityAdsShowClick: " + placementId);
        }

        @Override
        public void onUnityAdsShowComplete(String placementId, UnityAds.UnityAdsShowCompletionState state) {
            Log.v("UnityAdsExample", "onUnityAdsShowComplete: " + placementId);
        }
    };

    @Override
    public void onInitializationComplete() {

    }

    @Override
    public void onInitializationFailed(UnityAds.UnityAdsInitializationError error, String message) {
        Log.e("UnityAdsExample", "Unity Ads initialization failed with error: [" + error + "] " + message);
    }

    // Implement a function to load an interstitial ad. The ad will start to show once the ad has been loaded.
    public void unityInterstitialAd() {
        UnityAds.show(activity, Constant.getString(activity, Constant.UNITY_INTERSTITAL_ID), new UnityAdsShowOptions(), showListener);
    }

    public void unityRewardedAd() {
        UnityAds.show(activity, Constant.getString(activity, Constant.UNITY_REWARDED_ID), new UnityAdsShowOptions(), showListener);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (adsClickTimer != null) {
            adsClickTimer.cancel();
            if (isClickTimerTrue) {
                isClickTimerTrue = false;
                timerNotFinishDialog();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Constant.saveInvalidCounter(activity);
    }


    @Override
    public void onAdExpanded(MaxAd ad) {

    }

    @Override
    public void onAdCollapsed(MaxAd ad) {

    }

    @Override
    public void onAdLoaded(MaxAd ad) {

    }

    @Override
    public void onAdDisplayed(MaxAd ad) {

    }

    @Override
    public void onAdHidden(MaxAd ad) {

    }

    @Override
    public void onAdClicked(MaxAd ad) {

    }

    @Override
    public void onAdLoadFailed(String adUnitId, MaxError error) {

    }

    @Override
    public void onAdDisplayFailed(MaxAd ad, MaxError error) {

    }

    @Override
    public void onRewardedVideoStarted(MaxAd ad) {

    }

    @Override
    public void onRewardedVideoCompleted(MaxAd ad) {

    }

    @Override
    public void onUserRewarded(MaxAd ad, MaxReward reward) {

    }
}