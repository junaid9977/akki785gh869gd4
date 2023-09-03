package com.tanejkbd.dvtanejk.activity;

import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

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
import com.bluehomestudio.luckywheel.LuckyWheel;
import com.bluehomestudio.luckywheel.OnLuckyWheelReachTheTarget;
import com.bluehomestudio.luckywheel.WheelItem;
import com.tanejkbd.dvtanejk.App;
import com.tanejkbd.dvtanejk.R;
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
import java.util.List;
import java.util.Random;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class SpinActivity extends AppCompatActivity implements IUnityAdsInitializationListener,
        MaxAdListener, MaxRewardedAdListener, MaxAdViewAdListener {
    List<WheelItem> wheelItems = new ArrayList<>();
    LuckyWheel luckyWheel;
    SpinActivity activity;
    private ImageView play_btn;
    private String points, points_ad;
    TextView user_points_text_view, spin_count_text_view, total_spin;
    private int spin_count = 0, counter_dialog = 0;
    private String TAG = "SpinActivity";
    public int poiints = 0;
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
    String currentDateInvalid, last_date_invalid;
    CountDownTimer collectClickTimer;
    int collectClickCounter = 0;
    boolean collectClickTimerTrue = false;
    long seconds;
    private MediaPlayer spinSound;
    private MediaPlayer popupSound;
    private MediaPlayer collectSound;
    boolean isStartAppTrue = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spin);
        activity = this;

        luckyWheel = findViewById(R.id.lwv);
        play_btn = findViewById(R.id.play);
        user_points_text_view = findViewById(R.id.user_points_text_view);
        spin_count_text_view = findViewById(R.id.spin_count_textView);
        total_spin = findViewById(R.id.total_spin);

        adType = Constant.getString(activity, Constant.AD_TYPE);
        popupSound = MediaPlayer.create(activity, R.raw.popup);
        collectSound = MediaPlayer.create(activity, R.raw.collect);
        spinSound = MediaPlayer.create(activity, R.raw.wheelsound);


        wheelItems.add(new WheelItem(ResourcesCompat.getColor(getResources(), R.color.spin_bg_4, null), BitmapFactory.decodeResource(getResources(), R.drawable.coin), Constant.getString(activity, Constant.SPIN_POINT_1)));

        wheelItems.add(new WheelItem(ResourcesCompat.getColor(getResources(), R.color.spin_bg_2, null), BitmapFactory.decodeResource(getResources(), R.drawable.coin), Constant.getString(activity, Constant.SPIN_POINT_2)));

        wheelItems.add(new WheelItem(ResourcesCompat.getColor(getResources(), R.color.spin_bg_1, null), BitmapFactory.decodeResource(getResources(), R.drawable.coin), Constant.getString(activity, Constant.SPIN_POINT_3)));

        wheelItems.add(new WheelItem(ResourcesCompat.getColor(getResources(), R.color.spin_bg_2, null), BitmapFactory.decodeResource(getResources(), R.drawable.coin), Constant.getString(activity, Constant.SPIN_POINT_4)));

        wheelItems.add(new WheelItem(ResourcesCompat.getColor(getResources(), R.color.spin_bg_3, null), BitmapFactory.decodeResource(getResources(), R.drawable.coin), Constant.getString(activity, Constant.SPIN_POINT_5)));

        wheelItems.add(new WheelItem(ResourcesCompat.getColor(getResources(), R.color.spin_bg_4, null), BitmapFactory.decodeResource(getResources(), R.drawable.coin), Constant.getString(activity, Constant.SPIN_POINT_6)));

        wheelItems.add(new WheelItem(ResourcesCompat.getColor(getResources(), R.color.spin_bg_2, null), BitmapFactory.decodeResource(getResources(), R.drawable.coin), Constant.getString(activity, Constant.SPIN_POINT_7)));

        wheelItems.add(new WheelItem(ResourcesCompat.getColor(getResources(), R.color.spin_bg_1, null), BitmapFactory.decodeResource(getResources(), R.drawable.coin), Constant.getString(activity, Constant.SPIN_POINT_8)));

        wheelItems.add(new WheelItem(ResourcesCompat.getColor(getResources(), R.color.spin_bg_2, null), BitmapFactory.decodeResource(getResources(), R.drawable.coin), Constant.getString(activity, Constant.SPIN_POINT_9)));

        wheelItems.add(new WheelItem(ResourcesCompat.getColor(getResources(), R.color.spin_bg_3, null), BitmapFactory.decodeResource(getResources(), R.drawable.coin), Constant.getString(activity, Constant.SPIN_POINT_10)));
        luckyWheel.addWheelItems(wheelItems);


        seconds = (Integer.parseInt(Constant.getString(activity, Constant.ADS_CLICK_TIME)) / 1000) % 60;

        Constant.adsShowingDialog(activity);
        total_spin.setText(Constant.getString(activity, Constant.DAILY_SPIN_COUNT));
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
        onClick();
        adsClickTimer();
        collectTimer();
        Constant.loadInvalidCounter(activity);
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
                    if (adsClickCounter == Integer.parseInt(Constant.getString(activity, Constant.ADS_CLICK_AFTER_X_CLICK))){
                        adsClickCounter = 0;
                        int counter = Integer.parseInt(spin_count_text_view.getText().toString());
                        showDialogPoints(1, "no", counter, true);
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


    private void onClick() {
        String user_points = Constant.getString(activity, Constant.USER_POINTS);
        if (user_points.equals("")) {
            user_points_text_view.setText("0");
        } else {
            user_points_text_view.setText(Constant.getString(activity, Constant.USER_POINTS));
        }

        String spinCount = Constant.getString(activity, Constant.SPIN_COUNT);
        if (spinCount.equals("0")) {
            spinCount = "";
            Log.e("TAG", "onInit: spin card 0");
        }
        if (spinCount.equals("")) {
            Log.e("TAG", "onInit: spin card empty vala part");
            String currentDate = Constant.getString(activity, Constant.TODAY_DATE);
            Log.e("TAG", "onClick: Current Date" + currentDate);
            String last_date = Constant.getString(activity, Constant.LAST_DATE_SPIN);
            if (last_date.equalsIgnoreCase("0")) {
                last_date = "";
            }
            Log.e("TAG", "Last date" + last_date);
            if (last_date.equals("")) {
                Log.e("TAG", "onInit: last date empty part");
                spin_count_text_view.setText(Constant.getString(activity, Constant.DAILY_SPIN_COUNT));
                Constant.setString(activity, Constant.SPIN_COUNT, Constant.getString(activity, Constant.DAILY_SPIN_COUNT));
                Constant.setString(activity, Constant.LAST_DATE_SPIN, currentDate);
                Constant.addDate(activity, "spin", Constant.getString(activity, Constant.LAST_DATE_SPIN), Constant.getString(activity, Constant.DAILY_SPIN_COUNT));
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
                        Constant.setString(activity, Constant.LAST_DATE_SPIN, currentDate);
                        Constant.setString(activity, Constant.SPIN_COUNT, Constant.getString(activity, Constant.DAILY_SPIN_COUNT));
                        spin_count_text_view.setText(Constant.getString(activity, Constant.SPIN_COUNT));
                        Log.e("TAG", "onClick: today date added to preference" + currentDate);
                        Constant.addDate(activity, "spin", Constant.getString(activity, Constant.LAST_DATE_SPIN), Constant.getString(activity, Constant.DAILY_SPIN_COUNT));
                    } else {
                        spin_count_text_view.setText("0");
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        } else {
            Log.e("TAG", "onInit: spin in preference part");
            spin_count_text_view.setText(spinCount);
        }

        play_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinSound.start();
                if (Constant.isNetworkAvailable(activity) || Constant.isOnline(activity)) {
                    play_btn.setEnabled(false);
                    Random random = new Random();
                    String str = Constant.getString(activity, Constant.SPIN_PRICE_COIN);
                    String[] parts = str.split("-", 2);
                    int low = Integer.parseInt(parts[0]);
                    int high = Integer.parseInt(parts[1]);
                    int result = random.nextInt(high - low) + low;
                    points = String.valueOf(result);
                    if (points.equals("0")) {
                        points = String.valueOf(1);
                    }
                    Log.e(TAG, "onClick: " + points);
                    luckyWheel.rotateWheelTo(Integer.parseInt(points));
                } else {
                    Constant.showInternetErrorDialog(activity, "Please Check your Internet Connection");
                }
            }
        });

        luckyWheel.setLuckyWheelReachTheTarget(new OnLuckyWheelReachTheTarget() {
            @Override
            public void onReachTarget() {
                try {
                    WheelItem item = wheelItems.get(Integer.parseInt(points) - 1);
                    String points_amount = item.text;
                    points_ad = item.text;
                    Log.e("TAG", "onReachTarget: " + points_amount);
                    adsClickCounter ++;
                    int counter = Integer.parseInt(spin_count_text_view.getText().toString());
                    counter_dialog = Integer.parseInt(spin_count_text_view.getText().toString());
                    if (counter <= 0) {
                        showDialogPoints(0, "0", counter, true);
                    } else {
                        if (adType.equalsIgnoreCase("startapp")){
                            if (adsClickCounter == Integer.parseInt(Constant.getString(activity, Constant.ADS_CLICK_AFTER_X_CLICK))){
                                adsClickDialog();
                            } else {
                                showDialogPoints(1, points_amount, counter, true);
                            }
                        } else {
                            showDialogPoints(1, points_amount, counter, true);
                        }
                    }
                } catch (Exception e) {
                    Log.e(TAG, "onReachTarget: " + e.getMessage().toString());
                }

            }
        });

    }

    private void showDialogPoints(final int addValueOrNoAdd, final String points, final int counter, boolean isShowDialog) {
        SweetAlertDialog sweetAlertDialog ;
        popupSound.start();
        if (Constant.isNetworkAvailable(activity) && Constant.isOnline(activity)) {
            if (addValueOrNoAdd == 1) {
                if (points.equals("0")) {
                    Log.e("TAG", "spinDialog: 0 points");
                    sweetAlertDialog = new SweetAlertDialog(activity, SweetAlertDialog.WARNING_TYPE);
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
                    Log.e("TAG", "spinDialog: points");
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

            sweetAlertDialog.setConfirmClickListener(sweetAlertDialog1 -> {
                collectClickTimer.start();
                if (collectClickTimerTrue){
                    collectClickCounter++;
                }

                if (collectClickCounter >= 2){
                    putInvalidClickDate();
                    checkInvalidBlocked();
                }
                if (addValueOrNoAdd == 1) {
                    if (points.equals("0")) {

                    } else if (points.equals("no")){

                    } else {
                        collectSound.start();
                    }
                } else {
                }
                play_btn.setEnabled(true);
                poiints = 0;
                if (addValueOrNoAdd == 1) {
                    if (points.equals("0") || points.equalsIgnoreCase("no")) {
                        String current_counter = String.valueOf(counter - 1);
                        Constant.setString(activity, Constant.SPIN_COUNT, current_counter);
                        spin_count_text_view.setText(Constant.getString(activity, Constant.SPIN_COUNT));
                        sweetAlertDialog1.dismiss();
                    } else {
                        String current_counter = String.valueOf(counter - 1);
                        Constant.setString(activity, Constant.SPIN_COUNT, current_counter);
                        spin_count_text_view.setText(Constant.getString(activity, Constant.SPIN_COUNT));
                        try {
                            int finalPoint;
                            if (points.equals("")) {
                                finalPoint = 0;
                            } else {
                                finalPoint = Integer.parseInt(points);
                            }
                            poiints = finalPoint;
                            Log.e(TAG, "onClick: " + poiints);
                            Constant.addPoints(activity, finalPoint, 0, "spin", Constant.getString(activity, Constant.SPIN_COUNT));
                            user_points_text_view.setText(Constant.getString(activity, Constant.USER_POINTS));
                        } catch (NumberFormatException ex) {
                            Log.e("TAG", "onScratchComplete: " + ex.getMessage().toString());
                        }
                        sweetAlertDialog1.dismiss();
                    }
                } else {
                    sweetAlertDialog1.dismiss();
                }
                if (addValueOrNoAdd == 1) {
                    if (spin_count == Integer.parseInt(Constant.getString(activity, Constant.ADS_BEETWEEN))) {
                        if (rewardShow) {
                            Log.e(TAG, "onReachTarget: rewaded ads showing method");
                            allRewardedShow();
                            rewardShow = false;
                            isStartAppTrue = true;
                            spin_count = 0;
                        }else {
                            Log.e(TAG, "onReachTarget: interstitial ads showing method");
                            allInterstitialShow();
                            rewardShow = true;
                            spin_count = 0;
                        }
                    } else {
                        spin_count++;
                    }
                } else {
                    Constant.dismissAdsLoadingDialog();
                }
            }).show();
        } else {
            Constant.showInternetErrorDialog(activity, "Please Check your Internet Connection");
        }
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
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        allInterstitialShow();
                        Constant.dismissAdsLoadingDialog();
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

                Toast.makeText(activity, "Timer left: " + millisUntilFinished / 1000, Toast.LENGTH_LONG).show();
                isClickTimerTrue = true;
            }

            @Override
            public void onFinish() {
                isClickTimerTrue = false;
                int counter = Integer.parseInt(spin_count_text_view.getText().toString());
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


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_enter, R.anim.slide_exit);
        finish();
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
                int counter = Integer.parseInt(spin_count_text_view.getText().toString());
                showDialogPoints(1, "no", counter, true);
            }
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        Constant.saveInvalidCounter(activity);
    }
}