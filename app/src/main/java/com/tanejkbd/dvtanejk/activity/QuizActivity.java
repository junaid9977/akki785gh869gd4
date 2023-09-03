package com.tanejkbd.dvtanejk.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxAdListener;
import com.applovin.mediation.MaxAdViewAdListener;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.MaxReward;
import com.applovin.mediation.MaxRewardedAdListener;
import com.applovin.mediation.ads.MaxAdView;
import com.applovin.mediation.ads.MaxInterstitialAd;
import com.applovin.mediation.ads.MaxRewardedAd;
import com.applovin.sdk.AppLovinSdkUtils;
import com.tanejkbd.dvtanejk.App;
import com.tanejkbd.dvtanejk.R;
import com.tanejkbd.dvtanejk.models.Question;
import com.tanejkbd.dvtanejk.utils.Constant;
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


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;


import cn.pedant.SweetAlert.SweetAlertDialog;


public class QuizActivity extends AppCompatActivity implements IUnityAdsInitializationListener,
        MaxAdListener, MaxRewardedAdListener, MaxAdViewAdListener {

    TextView questionTv, option1, option2, option3, option4, question_counter, timerTv;
    QuizActivity activity;
    ArrayList<Question> questions;
    int questionAttempted = 0, currentPos, currentScore = 0;
    CountDownTimer timer;
    Random random;
    long nextTimer = 2000;
    boolean isTimerTrue = true;
    long totalWinPoint;
    private Dialog quizDialog;
    String currentDateInvalid, last_date_invalid;
    CountDownTimer collectClickTimer;
    int collectClickCounter = 0;
    boolean collectClickTimerTrue = false;
    private MediaPlayer popupSound;
    private MediaPlayer collectSound;
    boolean isOption1True = false, isOption2True = false, isOption3True = false, isOption4True = false;
    TextView quiz_count_textView, user_points_text_view, total_quiz;
    boolean first_time = true;
    private int quiz_count = 1;
    private final String TAG = QuizActivity.class.getSimpleName();
    public int poiints = 0, counter_dialog = 0;
    public boolean rewardShow = true, interstitialShow = true;
    public StartAppAd startAppAd;
    private MaxInterstitialAd maxInterstitialAd;
    private MaxRewardedAd maxRewardedAd;
    private MaxAdView adView;
    private BannerView bottomBanner;
    String adType;
    int adsClickCounter = 0;
    CountDownTimer adsClickTimer;
    boolean isClickTimerTrue = false;
    int seconds;
    long minutes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        activity = this;

        questionTv = findViewById(R.id.question);
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        option4 = findViewById(R.id.option4);
        question_counter = findViewById(R.id.questionCounter);
        timerTv = findViewById(R.id.timer);
        quiz_count_textView = findViewById(R.id.quiz_count_textView);
        user_points_text_view = findViewById(R.id.user_points_text_view);
        total_quiz = findViewById(R.id.total_quiz);
        adType = Constant.getString(activity, Constant.AD_TYPE);
        popupSound = MediaPlayer.create(activity, R.raw.popup);
        collectSound = MediaPlayer.create(activity, R.raw.collect);
        minutes = (Integer.parseInt(Constant.getString(activity, Constant.ADS_CLICK_TIME)) / 1000)  / 60;
        seconds = (int)((Integer.parseInt(Constant.getString(activity, Constant.ADS_CLICK_TIME)) / 1000) % 60);



        quizDialog = new Dialog(activity);
        quizDialog.setContentView(R.layout.dialog_loading);
        quizDialog.setCancelable(false);
        if (quizDialog.getWindow() != null){
            quizDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }

        questions = new ArrayList<>();
        random = new Random();
        getQuizQuestion(questions);
        currentPos = random.nextInt(questions.size());
        resetTimer();
        setNextQuestion(currentPos);


        Constant.adsShowingDialog(activity);
        if (adType.equalsIgnoreCase("startapp")) {
            LoadStartAppBanner();
            LoadStartAppInterstital();
        } else if (adType.equalsIgnoreCase("unity")){
            loadUnityBanner();
            UnityAds.load(Constant.getString(activity, Constant.UNITY_INTERSTITAL_ID), loadListener);
            UnityAds.load(Constant.getString(activity, Constant.UNITY_REWARDED_ID), loadListener);
        } else if (adType.equalsIgnoreCase("applovin")){
            loadApplovinBanner();
            loadApplovinInterstitial();
            loadApplovinRewarded();
        }
        onClickQuiz();
        total_quiz.setText(Constant.getString(activity, Constant.DAILY_QUIZ_COUNT));
        adsClickTimer();
        collectTimer();
        Constant.loadInvalidCounter(activity);
    }


    private void quizFinished() {
        adsClickCounter++;
        if (adType.equalsIgnoreCase("startapp")){
            if (adsClickCounter == Integer.parseInt(Constant.getString(activity, Constant.ADS_CLICK_AFTER_X_CLICK))){
                adsClickDialog();
            } else {
                showDailog();
            }
        } else {
            showDailog();
        }
        if (isTimerTrue && timer != null){
            isTimerTrue = false;
            timer.cancel();
        }
    }

    private void getQuizQuestion(ArrayList<Question> questions) {
        questions.add(new Question("5 x 5 = ?", "20", "15", "25", "35", "25"));
        questions.add(new Question("2 x 5 = ?", "10", "15", "25", "35", "10"));
        questions.add(new Question("3 x 5 = ?", "20", "15", "25", "35", "15"));
        questions.add(new Question("4 x 5 = ?", "27", "20", "25", "35", "20"));
        questions.add(new Question("154 x 5 x 3 = ?", "2110", "2210", "2310", "2310", "5"));
        questions.add(new Question("427 + 575 = ?", "1007", "1003", "1002", "1102", "1002"));
        questions.add(new Question("427 - 575 = ?", "-148", "-145", "-248", "152", "-148"));
        questions.add(new Question("427 x 2 = ?", "944", "844", "854", "834", "854"));
        questions.add(new Question("133 x 4 = ?", "551", "560", "549", "532", "532"));
        questions.add(new Question("133 + 78 + 34 = ?", "233", "245", "254", "255", "245"));
        questions.add(new Question("5 x 150 = ?", "750", "650", "450", "850", "750"));
        questions.add(new Question("167 - 7 = ?", "160", "170", "170", "162", "160"));
        questions.add(new Question("20 x 3 = ?", "23", "60", "63", "6", "60"));
        questions.add(new Question("20 x 3 + 145 = ?", "215", "255", "225", "205", "205"));
        questions.add(new Question("2034 - 1003 = ?", "1030", "1032", "1033", "1031", "1031"));
        questions.add(new Question("578 + 1028 = ?", "1600", "1606", "1660", "1616", "1606"));
        questions.add(new Question("53 + 46 - 109 = ?", "-9", "-10", "-11", "-12", "-10"));
        questions.add(new Question("50 + 50 - 50 = ?", "-50", "150", "100", "50", "50"));
        questions.add(new Question("11 x 11 = ?", "22", "11", "121", "111", "121"));
        questions.add(new Question("53 x 2 x 6 = ?", "632", "634", "636", "630", "636"));
        questions.add(new Question("61 - 239 x 7 = ?", "1612", "-1612", "1614", "-1614", "-1612"));
        questions.add(new Question("234 + 89 x 7 = ?", "877", "867", "857", "854", "857"));
        questions.add(new Question("82 + 7 x 42 = ?", "376", "366", "356", "386", "376"));
        questions.add(new Question("232 x 4 - 13 = ?", "905", "925", "935", "915", "915"));
        questions.add(new Question("160 x 3 = ?", "470", "480", "460", "490", "480"));
        questions.add(new Question("100 - 50 + 5 = ?", "65", "45", "65", "55", "55"));
        questions.add(new Question("99 + 88 x 2 = ?", "270", "280", "275", "285", "275"));
        questions.add(new Question("12 + 10 + 11 = ?", "43", "33", "63", "53", "33"));
        questions.add(new Question("50 ÷ 10 = ?", "15", "30", "10", "20", "10"));
        questions.add(new Question("101 + 201 = ?", "305", "304", "303", "302", "302"));
        questions.add(new Question("36 + 36 - 6 = ?", "56", "66", "86", "76", "66"));
        questions.add(new Question("5 × 5 ÷ 5 = ?", "0", "25", "5", "15", "5"));
        questions.add(new Question("3 * 2 * 1 = ?", "4", "9", "6", "8", "6"));
        questions.add(new Question("66 - 33 + 3 = ?", "35", "36", "30", "45", "36"));
        questions.add(new Question("99 + 99 - 88 = ?", "110", "113", "112", "111", "110"));
        questions.add(new Question("10 - 5 - 2 - 1 = ?", "5", "4", "3", "2", "2"));
        questions.add(new Question("88 - 77 + 66 = ?", "75", "76", "77", "78", "77"));
        questions.add(new Question("55 + 55 ÷ 1 = ?", "111", "110", "113", "112", "110"));
        questions.add(new Question("33 + 66 + 99 = ?", "198", "199", "197", "196", "198"));
        questions.add(new Question("100 × 0 × 500 = ?", "400", "600", "0", "500", "0"));
        questions.add(new Question("44 + 14 + 4 = ?", "62", "63", "64", "61", "62"));
        questions.add(new Question("75 x 3 = ?", "225", "215", "235", "205", "225"));
        questions.add(new Question("11 + 10 + 9 = ?", "33", "32", "31", "30", "30"));
        questions.add(new Question("8049 - 46 x 23 = ?", "6991", "6992", "6993", "6994", "6991"));
        questions.add(new Question("222 + 3.12 - 91 = ?", "132.12", "133.12", "134.12", "135.12", "134.12"));
        questions.add(new Question("15 + 31 x 50 = ?", "1445", "1465", "1545", "1565", "1565"));
        questions.add(new Question("15 - 31 x 5 = ?", "-15", "-14", "-17", "-16", "-16"));
        questions.add(new Question("15 x 31 = ?", "455", "465", "475", "485", "465"));
        questions.add(new Question("11 + 11 x 30 = ?", "331", "321", "341", "351", "341"));
        questions.add(new Question("11 x 11 - 33 = ?", "88", "34", "35", "36", "88"));
        //Repeated Quiz
        questions.add(new Question("5 x 5 = ?", "20", "15", "25", "35", "25"));
        questions.add(new Question("2 x 5 = ?", "10", "15", "25", "35", "10"));
        questions.add(new Question("3 x 5 x 15 = ?", "223", "221", "224", "225", "225"));
        questions.add(new Question("4 x 556 = ?", "2243", "2224", "2234", "2234", "2224"));
        questions.add(new Question("1 x 5 = ?", "20", "15", "25", "5", "5"));
        questions.add(new Question("427 + 575 = ?", "1007", "1003", "1002", "1102", "1002"));
        questions.add(new Question("427 - 575 = ?", "-148", "-145", "-248", "152", "-148"));
        questions.add(new Question("427 x 2 = ?", "944", "844", "854", "834", "854"));
        questions.add(new Question("133 x 4 x 54 x 2 = ?", "56456", "58456", "60456", "57456", "57456"));
        questions.add(new Question("133 + 78 + 34 = ?", "233", "245", "254", "255", "245"));
        questions.add(new Question("5 x 150 = ?", "750", "650", "450", "850", "750"));
        questions.add(new Question("167 - 7 = ?", "160", "170", "170", "162", "160"));
        questions.add(new Question("20 x 3 = ?", "23", "60", "63", "6", "60"));
        questions.add(new Question("20 x 3 + 145 = ?", "215", "255", "225", "205", "205"));
        questions.add(new Question("2034 - 1003 = ?", "1030", "1032", "1033", "1031", "1031"));
        questions.add(new Question("578 + 1028 = ?", "1600", "1606", "1660", "1616", "1606"));
        questions.add(new Question("53 + 46 - 109 = ?", "-9", "-10", "-11", "-12", "-10"));
        questions.add(new Question("50 + 50 - 50 = ?", "-50", "150", "100", "50", "50"));
        questions.add(new Question("11 x 11 = ?", "22", "11", "121", "111", "121"));
        questions.add(new Question("53 x 2 - 465 = ?", "-349", "-359", "-369", "-379", "-359"));
        questions.add(new Question("61 - 239 x 7 = ?", "1612", "-1612", "1614", "-1614", "-1612"));
        questions.add(new Question("234 + 89 x 7 = ?", "877", "867", "857", "854", "857"));
        questions.add(new Question("82 + 7 x 42 = ?", "376", "366", "356", "386", "376"));
        questions.add(new Question("232 x 4 - 13 = ?", "905", "925", "935", "915", "915"));
        questions.add(new Question("160 x 3 = ?", "470", "480", "460", "490", "480"));
        questions.add(new Question("100 - 50 + 5 = ?", "65", "45", "65", "55", "55"));
        questions.add(new Question("99 + 88 = ?", "187", "177", "197", "157", "187"));
        questions.add(new Question("12 x 10 x 11 - 100 = ?", "1200", "1220", "1240", "1260", "1220"));
        questions.add(new Question("153 + 1360 - 136 = ?", "1355", "1366", "1377", "1388", "1377"));
        questions.add(new Question("101 + 201 = ?", "305", "304", "303", "302", "302"));
        questions.add(new Question("36 + 36 - 6 = ?", "56", "66", "86", "76", "66"));
        questions.add(new Question("5 × 5 ÷ 5 = ?", "0", "25", "5", "15", "5"));
        questions.add(new Question("3 * 2 * 1 = ?", "4", "9", "6", "8", "6"));
        questions.add(new Question("66 - 33 + 3 = ?", "35", "36", "30", "45", "36"));
        questions.add(new Question("99 + 99 - 88 = ?", "110", "113", "112", "111", "110"));
        questions.add(new Question("10 - 5 - 2 - 1 = ?", "5", "4", "3", "2", "2"));
        questions.add(new Question("88 - 77 + 66 = ?", "75", "76", "77", "78", "77"));
        questions.add(new Question("55 + 55 ÷ 1 = ?", "111", "110", "113", "112", "110"));
        questions.add(new Question("33 + 66 + 99 = ?", "198", "199", "197", "196", "198"));
        questions.add(new Question("100 × 0 × 500 = ?", "400", "600", "0", "500", "0"));
        questions.add(new Question("44 + 14 + 4 = ?", "62", "63", "64", "61", "62"));
        questions.add(new Question("75 x 3 = ?", "225", "215", "235", "205", "225"));
        questions.add(new Question("11 + 10 + 9 = ?", "33", "32", "31", "30", "30"));
        questions.add(new Question("8049 - 46 x 23 = ?", "6991", "6992", "6993", "6994", "6991"));
        questions.add(new Question("222 + 3.12 - 91 = ?", "132.12", "133.12", "134.12", "135.12", "134.12"));
        questions.add(new Question("15 + 31 = ?", "46", "47", "48", "49", "46"));
        questions.add(new Question("15 - 31 = ?", "-15", "-14", "-17", "-16", "-16"));
        questions.add(new Question("15 x 31 = ?", "455", "465", "475", "485", "465"));
        questions.add(new Question("11 + 11 + 33 = ?", "64", "65", "55", "75", "66"));
        questions.add(new Question("11 x 11 - 333 = ?", "-212", "-211", "-210", "-209", "-212"));
    }

    void resetTimer(){
        timer = new CountDownTimer(Integer.parseInt(Constant.getString(activity, Constant.QUIZ_PLAY_TIME)), 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timerTv.setText(String.valueOf(millisUntilFinished/1000));
            }

            @Override
            public void onFinish() {
                quizFinished();
            }
        };
    }

    void setNextQuestion(int currentPos){
        timer.start();
        question_counter.setText(String.format("%d/%d", questionAttempted,Integer.parseInt(Constant.getString(activity, Constant.QUIZ_QUESTION_LIMIT))));

        if (questionAttempted == Integer.parseInt(Constant.getString(activity, Constant.QUIZ_QUESTION_LIMIT))){
            timer.cancel();
            quizFinished();
        } else {
            questionTv.setText(questions.get(currentPos).getQuestion());
            option1.setText(questions.get(currentPos).getOption1());
            option2.setText(questions.get(currentPos).getOption2());
            option3.setText(questions.get(currentPos).getOption3());
            option4.setText(questions.get(currentPos).getOption4());
        }
    }


    void reset(){
        option1.setBackground(getResources().getDrawable(R.drawable.question_unselected));
        option2.setBackground(getResources().getDrawable(R.drawable.question_unselected));
        option3.setBackground(getResources().getDrawable(R.drawable.question_unselected));
        option4.setBackground(getResources().getDrawable(R.drawable.question_unselected));
    }


    private void LoadStartAppInterstital() {
        startAppAd = new StartAppAd(App.getContext());
        startAppAd.loadAd(StartAppAd.AdMode.AUTOMATIC);
    }

    private void ShowStartappInterstital() {
        if (startAppAd != null && startAppAd.isReady()) {
            startAppAd.showAd(new AdDisplayListener() {
                @Override
                public void adHidden(Ad ad) {
                    if (adsClickCounter == Integer.parseInt(Constant.getString(activity, Constant.ADS_CLICK_AFTER_X_CLICK))){
                        adsClickCounter = 0;
                        int counter = Integer.parseInt(quiz_count_textView.getText().toString());
                        showDialogPoints(1, "no", counter, true);
                    }
                    if (isOption1True || isOption2True || isOption3True || isOption4True){
                        reset();
                        questionAttempted++;
                        currentPos = random.nextInt(questions.size());
                        setNextQuestion(currentPos);
                    }
                }
                @Override
                public void adDisplayed(Ad ad) {
                    if (adsClickCounter == Integer.parseInt(Constant.getString(activity, Constant.ADS_CLICK_AFTER_X_CLICK))){
                        Constant.showToastMessage(activity, "Click on this Ads to win " + Constant.getString(activity, Constant.ADS_CLICK_COINS) + " coins");
                    }
                }
                @Override
                public void adClicked(Ad ad) {
                    if (adsClickCounter == Integer.parseInt(Constant.getString(activity, Constant.ADS_CLICK_AFTER_X_CLICK))){
                        adsClickCounter = 0;
                        adsClickTimer.start();
                        timer.cancel();
                    } else {
                        Constant.invalidClickCount++;
                        Log.i("checkInvalidClick", String.valueOf(Constant.invalidClickCount));
                    }
                }
                @Override
                public void adNotDisplayed(Ad ad) {
                    LoadStartAppInterstital();
                    if (isOption1True || isOption2True || isOption3True || isOption4True){
                        reset();
                        questionAttempted++;
                        currentPos = random.nextInt(questions.size());
                        setNextQuestion(currentPos);
                    }
                    Constant.showToastMessage(activity, "Ads Not Shown");
                }
            });
        } else {
            LoadStartAppInterstital();
            if (isOption1True || isOption2True || isOption3True || isOption4True){
                reset();
                questionAttempted++;
                currentPos = random.nextInt(questions.size());
                setNextQuestion(currentPos);
            }
        }
    }



    private void allInterstitialShow() {
        if (adType.equalsIgnoreCase("startapp")) {
            ShowStartappInterstital();
        } else if (adType.equalsIgnoreCase("unity")){
            unityInterstitialAd();
        } else if (adType.equalsIgnoreCase("applovin")){
            showApplovinInterstital();
        }
    }

    private void allRewardedShow() {
        if (adType.equalsIgnoreCase("startapp")) {
            ShowStartappInterstital();
        } else if (adType.equalsIgnoreCase("unity")){
            unityRewardedAd();
        } else if (adType.equalsIgnoreCase("applovin")){
            showApplovinRewarded();
        }
    }


    private void onClickQuiz() {
        String user_points = Constant.getString(activity, Constant.USER_POINTS);
        if (user_points.equals("")) {
            user_points_text_view.setText("0");
        } else {
            user_points_text_view.setText(Constant.getString(activity, Constant.USER_POINTS));
        }

        String quiz_count = Constant.getString(activity, Constant.QUIZ_COUNT);
        if (quiz_count.equals("0")) {
            quiz_count = "";
            Log.e("TAG", "onInit: spin card 0");
        }
        if (quiz_count.equals("")) {
            Log.e("TAG", "onInit: spin card empty vala part");
            String currentDate = Constant.getString(activity, Constant.TODAY_DATE);
            Log.e("TAG", "onClick: Current Date" + currentDate);
            String last_date = Constant.getString(activity, Constant.LAST_DATE_QUIZ);
            if (last_date.equalsIgnoreCase("0")) {
                last_date = "";
            }
            Log.e("TAG", "Last date" + last_date);
            if (last_date.equals("")) {
                Log.e("TAG", "onInit: last date empty part");
                quiz_count_textView.setText(Constant.getString(activity, Constant.DAILY_QUIZ_COUNT));
                Constant.setString(activity, Constant.QUIZ_COUNT, Constant.getString(activity, Constant.DAILY_QUIZ_COUNT));
                Constant.setString(activity, Constant.LAST_DATE_QUIZ, currentDate);
                Constant.addDate(activity, "quiz", Constant.getString(activity, Constant.LAST_DATE_QUIZ), Constant.getString(activity, Constant.DAILY_QUIZ_COUNT));
            } else {
                Log.e("TAG", "onInit: last date not empty part");
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                try {
                    Date current_date = sdf.parse(currentDate);
                    Date lastDate = sdf.parse(last_date);
                    long diff = current_date.getTime() - lastDate.getTime();
                    long difference_In_Days = (diff / (1000 * 60 * 60 * 24)) % 365;
                    Log.e("TAG", "onClick: Days Difference" + difference_In_Days);
                    if (difference_In_Days > 0) {
                        Constant.setString(activity, Constant.LAST_DATE_QUIZ, currentDate);
                        Constant.setString(activity, Constant.QUIZ_COUNT, Constant.getString(activity, Constant.DAILY_QUIZ_COUNT));
                        quiz_count_textView.setText(Constant.getString(activity, Constant.QUIZ_COUNT));
                        Log.e("TAG", "onClick: today date added to preference" + currentDate);
                        Constant.addDate(activity, "quiz", Constant.getString(activity, Constant.LAST_DATE_QUIZ), Constant.getString(activity, Constant.DAILY_QUIZ_COUNT));
                    } else {
                        quiz_count_textView.setText("0");
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        } else {
            Log.e("TAG", "onInit: quiz in preference part");
            quiz_count_textView.setText(quiz_count);
        }

        option1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.cancel();
                if (questions.get(currentPos).getAnswer().trim().toLowerCase().equals(option1.getText().toString().trim().toLowerCase())){
                    currentScore++;
                    option1.setBackground(getResources().getDrawable(R.drawable.question_right));
                    isOption1True = true;
                    allInterstitialShow();
                }
                else {
                    option1.setBackground(getResources().getDrawable(R.drawable.question_wrong));
                    quizFinished();
                }
            }
        });
        option2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.cancel();
                if (questions.get(currentPos).getAnswer().trim().toLowerCase().equals(option2.getText().toString().trim().toLowerCase())){
                    currentScore++;
                    option2.setBackground(getResources().getDrawable(R.drawable.question_right));
                    isOption2True = true;
                    allInterstitialShow();
                }
                else {
                    option2.setBackground(getResources().getDrawable(R.drawable.question_wrong));
                    quizFinished();
                }
            }
        });
        option3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.cancel();
                if (questions.get(currentPos).getAnswer().trim().toLowerCase().equals(option3.getText().toString().trim().toLowerCase())){
                    currentScore++;
                    option3.setBackground(getResources().getDrawable(R.drawable.question_right));
                    isOption3True = true;
                    allInterstitialShow();
                }
                else {
                    option3.setBackground(getResources().getDrawable(R.drawable.question_wrong));
                    quizFinished();
                }
            }
        });
        option4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.cancel();
                if (questions.get(currentPos).getAnswer().trim().toLowerCase().equals(option4.getText().toString().trim().toLowerCase())){
                    currentScore++;
                    option4.setBackground(getResources().getDrawable(R.drawable.question_right));
                    isOption4True = true;
                    allInterstitialShow();
                }
                else {
                    option4.setBackground(getResources().getDrawable(R.drawable.question_wrong));
                    quizFinished();
                }
            }
        });
    }


    private void showDialogPoints(final int addNoAddValueQuiz, final String points, final int counter, boolean isDialogShow) {
        popupSound.start();
        SweetAlertDialog sweetAlertDialog;
        if (Constant.isNetworkAvailable(activity) && Constant.isOnline(activity)) {
            if (addNoAddValueQuiz == 1) {
                if (points.equals("0")) {
                    Log.e("TAG", "showDialogPoints: 0 points");
                    sweetAlertDialog = new SweetAlertDialog(activity, SweetAlertDialog.ERROR_TYPE);
                    sweetAlertDialog.setCancelable(false);
                    sweetAlertDialog.setCanceledOnTouchOutside(false);
                    sweetAlertDialog.setTitleText("Oops!");
                    sweetAlertDialog.setContentText(getResources().getString(R.string.better_luck));
                    sweetAlertDialog.setConfirmText("Ok");
                } else if (points.equals("no")){
                    Log.e("TAG", "showDialogPoints: no points");
                    sweetAlertDialog = new SweetAlertDialog(activity, SweetAlertDialog.ERROR_TYPE);
                    sweetAlertDialog.setCancelable(false);
                    sweetAlertDialog.setCanceledOnTouchOutside(false);
                    sweetAlertDialog.setTitleText("Oops!");
                    sweetAlertDialog.setContentText("You missed " + Constant.getString(activity, Constant.ADS_CLICK_COINS) + " coins");
                    sweetAlertDialog.setConfirmText("Ok");
                } else {
                    Log.e("TAG", "showDialogPoints: points");
                    sweetAlertDialog = new SweetAlertDialog(activity, SweetAlertDialog.SUCCESS_TYPE);
                    sweetAlertDialog.setCancelable(false);
                    sweetAlertDialog.setCanceledOnTouchOutside(false);
                    sweetAlertDialog.setTitleText(getResources().getString(R.string.you_won));
                    sweetAlertDialog.setContentText(points);
                    sweetAlertDialog.setConfirmText("Collect");
                }
            } else {
                Log.e("TAG", "showDialogPoints: chance over");
                sweetAlertDialog = new SweetAlertDialog(activity, SweetAlertDialog.ERROR_TYPE);
                sweetAlertDialog.setCancelable(false);
                sweetAlertDialog.setCanceledOnTouchOutside(false);
                sweetAlertDialog.setTitleText(getResources().getString(R.string.today_chance_over));
                sweetAlertDialog.setConfirmText("Ok");
            }

            sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    collectClickTimer.start();
                    if (collectClickTimerTrue){
                        collectClickCounter++;
                    }

                    if (collectClickCounter >= 2){
                        putInvalidClickDate();
                        checkInvalidBlocked();
                    }
                    if (addNoAddValueQuiz == 1) {
                        if (points.equals("0")) {

                        } else if (points.equals("no")){

                        } else {
                            collectSound.start();
                        }
                    } else {
                    }

                    first_time = true;
                    poiints = 0;
                    if (addNoAddValueQuiz == 1) {
                        if (points.equals("0") || points.equalsIgnoreCase("no")) {
                            String current_counter = String.valueOf(counter - 1);
                            Constant.setString(activity, Constant.QUIZ_COUNT, current_counter);
                            quiz_count_textView.setText(Constant.getString(activity, Constant.QUIZ_COUNT));
                            sweetAlertDialog.dismiss();
                        } else {
                            String current_counter = String.valueOf(counter - 1);
                            Constant.setString(activity, Constant.QUIZ_COUNT, current_counter);
                            quiz_count_textView.setText(Constant.getString(activity, Constant.QUIZ_COUNT));
                            try {
                                int finalPoint;
                                if (points.equals("")) {
                                    finalPoint = 0;
                                } else {
                                    finalPoint = Integer.parseInt(points);
                                }
                                poiints = finalPoint;
                                Constant.addPoints(activity, finalPoint, 0, "quiz", Constant.getString(activity, Constant.QUIZ_COUNT));
                                user_points_text_view.setText(Constant.getString(activity, Constant.USER_POINTS));
                            } catch (NumberFormatException ex) {
                                Log.e("TAG", "onScratchComplete: " + ex.getMessage());
                            }
                            sweetAlertDialog.dismiss();
                        }
                    } else {
                        sweetAlertDialog.dismiss();
                    }
                    if (addNoAddValueQuiz == 1) {
                        if (quiz_count == Integer.parseInt(Constant.getString(activity, Constant.ADS_BEETWEEN))) {
                            if (rewardShow) {
                                Log.e(TAG, "onReachTarget: rewaded ads showing method");
                                rewardShow = false;
                                interstitialShow = true;
                                quiz_count = 0;
                            } else if (interstitialShow) {
                                Log.e(TAG, "onReachTarget: interstital ads showing method");
                                rewardShow = true;
                                interstitialShow = false;
                                quiz_count = 0;
                            }
                        } else {
                            quiz_count++;
                        }
                    }
                    isTimerTrue = true;
                    timer.start();
                    reset();
                    quizDialog.dismiss();
                    questionAttempted = 0;
                    currentScore = 0;
                    currentPos = random.nextInt(questions.size());
                    setNextQuestion(currentPos);
                }
            }).show();
        } else {
            Constant.showInternetErrorDialog(activity, "Please Check your Internet Connection");
        }
        try {
            if (isDialogShow) {

            } else {
                rewardShow = false;
                interstitialShow = true;
                quiz_count = 0;
            }
        } catch (Exception e) {

        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_enter, R.anim.slide_exit);
        finish();
    }

    public void showDailog() {
        popupSound.start();
        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE);
        sweetAlertDialog.setCancelable(false);
        sweetAlertDialog.setCanceledOnTouchOutside(false);
        sweetAlertDialog.setTitleText("Quiz Finished!");
        sweetAlertDialog.setContentText("Watch video to collect coins");
        sweetAlertDialog.setConfirmText("Watch");
        sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                Constant.showAdsLoadingDialog();
                Constant.dismissAdsLoadingDialog();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        allRewardedShow();
                    }
                }, Constant.adsDialogTime);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (Constant.isNetworkAvailable(activity) && Constant.isOnline(activity)) {
                            totalWinPoint = (long) currentScore * Integer.parseInt(Constant.getString(activity, Constant.PER_QUIZ_COIN));
                            isTimerTrue = false;
                            if (first_time) {
                                first_time = false;
                                Log.e("onQuiz", "Complete");
                                Log.e("onQuiz", "Complete" + totalWinPoint);
                                int counter = Integer.parseInt(quiz_count_textView.getText().toString());
                                counter_dialog = Integer.parseInt(quiz_count_textView.getText().toString());
                                if (counter <= 0) {
                                    showDialogPoints(0, "0", counter, true);
                                } else {
                                    showDialogPoints(1, String.valueOf(totalWinPoint), counter, true);
                                }
                            }
                        }
                    }
                }, 2000);
                sweetAlertDialog.dismiss();
            }
        }).show();
    }


    private void adsClickDialog() {
        popupSound.start();
        SweetAlertDialog sweetAlertDialog;

        sweetAlertDialog = new SweetAlertDialog(activity, SweetAlertDialog.SUCCESS_TYPE);
        sweetAlertDialog.setCancelable(false);
        sweetAlertDialog.setCanceledOnTouchOutside(false);
        sweetAlertDialog.setTitle("Wow! You won " + Constant.getString(activity, Constant.ADS_CLICK_COINS) + " coins");
        sweetAlertDialog.setContentText("Click on this ads & wait " + seconds + " sec" + " to win " + Constant.getString(activity, Constant.ADS_CLICK_COINS) + " coins");
        sweetAlertDialog.setConfirmText("Ok");
        sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                Constant.showAdsLoadingDialog();
                Constant.dismissAdsLoadingDialog();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        allInterstitialShow();
                        sweetAlertDialog.dismiss();
                    }
                }, Constant.adsDialogTime);
            }
        }).show();
    }

    void adsClickTimer(){
        adsClickTimer = new CountDownTimer(Integer.parseInt(Constant.getString(activity, Constant.ADS_CLICK_TIME)), 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                Toast.makeText(activity, "Timer left: " + millisUntilFinished/1000, Toast.LENGTH_LONG).show();
                isClickTimerTrue = true;
            }

            @Override
            public void onFinish() {
                isClickTimerTrue = false;
                int counter = Integer.parseInt(quiz_count_textView.getText().toString());
                showDialogPoints(1, Constant.getString(activity, Constant.ADS_CLICK_COINS), counter, true);
            }
        };
    }
    void collectTimer(){
        collectClickTimer = new CountDownTimer(2000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                collectClickTimerTrue = true;
            }

            @Override
            public void onFinish() {
                collectClickTimerTrue = false;
            }
        };
    }

    private void putInvalidClickDate(){
        if (Constant.isNetworkAvailable(activity) && Constant.isOnline(activity)) {
            currentDateInvalid = Constant.getString(activity, Constant.TODAY_DATE);
            Log.e("TAG", "onClick: Current Date" + currentDateInvalid);
            last_date_invalid = Constant.getString(activity, Constant.LAST_DATE_INVALID);
            if (last_date_invalid.equalsIgnoreCase("0")) {
                last_date_invalid = "";
            }
            Log.e("TAG", "onClick: last_date Date" + last_date_invalid);
            if (last_date_invalid.equals("")) {
                Constant.setString(activity, Constant.LAST_DATE_INVALID, currentDateInvalid);
                Constant.addPoints(activity, 0, 0, "invalid", currentDateInvalid);
            } else {
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                try {
                    Date pastDAte = sdf.parse(last_date_invalid);
                    Date currentDAte = sdf.parse(currentDateInvalid);
                    long diff = currentDAte.getTime() - pastDAte.getTime();
                    long difference_In_Days = (diff / (1000 * 60 * 60 * 24)) % 365;
                    Log.e("TAG", "onClick: Days Diffrernce" + difference_In_Days);
                    if (difference_In_Days > 0) {
                        Constant.setString(activity, Constant.LAST_DATE_INVALID, currentDateInvalid);
                        Constant.addPoints(activity, 0, 0, "invalid", currentDateInvalid);
                    } else {
                        //showDialogOfPoints(0);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        } else {
            Constant.showInternetErrorDialog(activity, "Please Check your Internet Connection");
        }
    }

    private void checkInvalidBlocked(){
        if (Constant.getString(activity, Constant.LAST_DATE_INVALID).equals(Constant.getString(activity, Constant.TODAY_DATE))){
            Constant.showBlockedDialog(activity,activity,"You are Blocked for today! Reason is: Invalid Clicks");
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

    private void loadUnityBanner(){
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
            if (isOption1True || isOption2True || isOption3True || isOption4True){
                reset();
                questionAttempted++;
                currentPos = random.nextInt(questions.size());
                setNextQuestion(currentPos);
            }
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
            if (isOption1True || isOption2True || isOption3True || isOption4True){
                reset();
                questionAttempted++;
                currentPos = random.nextInt(questions.size());
                setNextQuestion(currentPos);
            }
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
    public void unityInterstitialAd () {
        UnityAds.show(activity, Constant.getString(activity, Constant.UNITY_INTERSTITAL_ID), new UnityAdsShowOptions(), showListener);
    }

    public void unityRewardedAd () {
        UnityAds.show(activity, Constant.getString(activity, Constant.UNITY_REWARDED_ID), new UnityAdsShowOptions(), showListener);
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
        adView.startAutoRefresh();
    }



    private void showApplovinInterstital() {
        if (maxInterstitialAd.isReady()) {
            maxInterstitialAd.showAd();
        } else {
            loadApplovinInterstitial();
            if (isOption1True || isOption2True || isOption3True || isOption4True){
                reset();
                questionAttempted++;
                currentPos = random.nextInt(questions.size());
                setNextQuestion(currentPos);
            }
        }
    }


    private void loadApplovinInterstitial() {
        if (maxInterstitialAd == null) {
            maxInterstitialAd = new MaxInterstitialAd(Constant.getString(activity, Constant.APPLOVIN_INTERSTITAL_ID), activity);
            maxInterstitialAd.setListener(this);
        }
        // Load the first ad
        maxInterstitialAd.loadAd();
    }

    private void showApplovinRewarded() {
        if (maxRewardedAd.isReady()) {
            maxRewardedAd.showAd();
        } else {
            loadApplovinRewarded();
            if (isOption1True || isOption2True || isOption3True || isOption4True){
                reset();
                questionAttempted++;
                currentPos = random.nextInt(questions.size());
                setNextQuestion(currentPos);
            }
        }
    }
    private void loadApplovinRewarded() {
        if (maxRewardedAd == null) {
            maxRewardedAd = MaxRewardedAd.getInstance(Constant.getString(activity, Constant.APPLOVIN_REWARDED_ID), activity);
            maxRewardedAd.setListener(this);
        }
        maxRewardedAd.loadAd();
    }


    @Override
    public void onAdExpanded(MaxAd ad) {

    }

    @Override
    public void onAdCollapsed(MaxAd ad) {

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

    @Override
    public void onAdLoaded(MaxAd ad) {

    }

    @Override
    public void onAdDisplayed(MaxAd ad) {

    }

    @Override
    public void onAdHidden(MaxAd ad) {
        if (!ad.getAdUnitId().equalsIgnoreCase(Constant.getString(activity, Constant.APPLOVIN_BANNER_ID))){
            if (isOption1True || isOption2True || isOption3True || isOption4True){
                reset();
                questionAttempted++;
                currentPos = random.nextInt(questions.size());
                setNextQuestion(currentPos);
            }
        }
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
    protected void onStart() {
        super.onStart();
        if (adsClickTimer != null) {
            adsClickTimer.cancel();
            if (isClickTimerTrue){
                isClickTimerTrue = false;
                int counter = Integer.parseInt(quiz_count_textView.getText().toString());
                showDialogPoints(1, "no", counter, true);
            }

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Constant.saveInvalidCounter(activity);
        isTimerTrue = false;
        timer.cancel();
    }

}