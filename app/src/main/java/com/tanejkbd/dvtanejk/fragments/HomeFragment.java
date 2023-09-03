package com.tanejkbd.dvtanejk.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.tanejkbd.dvtanejk.App;
import com.tanejkbd.dvtanejk.R;
import com.tanejkbd.dvtanejk.activity.AppsActivity;
import com.tanejkbd.dvtanejk.activity.CaptchaActivity;
import com.tanejkbd.dvtanejk.activity.GameActivity;
import com.tanejkbd.dvtanejk.activity.GameLoader;
import com.tanejkbd.dvtanejk.activity.GiftCardActivity;
import com.tanejkbd.dvtanejk.activity.LoginActivity;
import com.tanejkbd.dvtanejk.activity.QuizActivity;
import com.tanejkbd.dvtanejk.activity.ScratchActivity;
import com.tanejkbd.dvtanejk.activity.SpinActivity;
import com.tanejkbd.dvtanejk.activity.VideoActivity;
import com.tanejkbd.dvtanejk.activity.VisitActivity;
import com.tanejkbd.dvtanejk.activity.WatchAndEarnActivity;
import com.tanejkbd.dvtanejk.utils.Constant;
import com.bumptech.glide.Glide;
import com.scottyab.rootbeer.RootBeer;
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

import java.net.NetworkInterface;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements IUnityAdsInitializationListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Context mContext;
    boolean isAdsShow = true;

    LinearLayout promotion_height, appTask, readTask, videoTask, gameTask, game_layout, taskLayout, followLayout, notioffBtn;
    RelativeLayout reward_btn,
            telegram, instagram;
    CardView check_in_layout, watch_ads, spinLayout, scratchLayout, quizLayout, captchaLayout;
    private TextView user_name_text_view;
    private TextView user_points_text_view;
    private ImageView game_banner, promotion_1, promotion_2, promotion_3, promotion_4;
    private ProgressDialog alertDialog;
    String isGhbNotExternalOpen;
    String currentDateInvalid, last_date_invalid;
    private MediaPlayer popupSound;
    private MediaPlayer collectSound;
    LinearLayout notiBtn;
    int adCount = 0;
    StartAppAd startAppAd;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        Constant.setString(mContext, Constant.LAST_TIME_ADD_TO_SERVER, "");
        user_name_text_view = view.findViewById(R.id.user_name_text_view);
        user_points_text_view = view.findViewById(R.id.user_points_text_view);
        check_in_layout = view.findViewById(R.id.daily_check_in_layout);
        appTask = view.findViewById(R.id.appTask);
        videoTask = view.findViewById(R.id.videoTask);
        notiBtn = view.findViewById(R.id.notiBtn);
        notioffBtn = view.findViewById(R.id.notioffBtn);
        gameTask = view.findViewById(R.id.gameTask);
        readTask = view.findViewById(R.id.readTask);
        reward_btn = view.findViewById(R.id.reward_btn);
        spinLayout = view.findViewById(R.id.spinLayout);
        scratchLayout = view.findViewById(R.id.scratchLayout);
        watch_ads = view.findViewById(R.id.watch_ads);
        captchaLayout = view.findViewById(R.id.captchaLayout);
        quizLayout = view.findViewById(R.id.quizLayout);
        game_banner = view.findViewById(R.id.game_banner);
        taskLayout = view.findViewById(R.id.taskLayout);
        followLayout = view.findViewById(R.id.followLayout);
        game_layout = view.findViewById(R.id.game_layout);
        promotion_height = view.findViewById(R.id.promotion_height);
        promotion_1 = view.findViewById(R.id.promotion_1);
        promotion_2 = view.findViewById(R.id.promotion_2);
        promotion_3 = view.findViewById(R.id.promotion_3);
        promotion_4 = view.findViewById(R.id.promotion_4);
        telegram = view.findViewById(R.id.telegram);
        instagram = view.findViewById(R.id.instagram);

        isGhbNotExternalOpen = Constant.getString(mContext, Constant.GAME_HOME_BANNER_GAME_ACTIVITY);
        ViewGroup.LayoutParams params = promotion_height.getLayoutParams();

        popupSound = MediaPlayer.create(mContext, R.raw.popup);
        collectSound = MediaPlayer.create(mContext, R.raw.collect);

        if (Constant.isNetworkAvailable(getActivity()) && Constant.isOnline(getActivity())) {
            String adType = Constant.getString(getActivity(), Constant.AD_TYPE);
            if (adType.equalsIgnoreCase("startapp")) {
                LoadStartAppInterstital();
            } else if (adType.equalsIgnoreCase("unity")) {
                UnityAds.load(Constant.getString(getActivity(), Constant.UNITY_INTERSTITAL_ID), loadListener);
            }
        } else {
            Constant.showInternetErrorDialog(getActivity(), "Please Check your Internet Connection");
        }

        notiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                followLayout.setVisibility(View.VISIBLE);
                taskLayout.setVisibility(View.GONE);
                notiBtn.setVisibility(View.GONE);
                notioffBtn.setVisibility(View.VISIBLE);
            }
        });
        notioffBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                followLayout.setVisibility(View.GONE);
                taskLayout.setVisibility(View.VISIBLE);
                notiBtn.setVisibility(View.VISIBLE);
                notioffBtn.setVisibility(View.GONE);
            }
        });
        telegram.setOnClickListener(v -> {
            downloadAction(getResources().getString(R.string.telegram_url));
        });
        instagram.setOnClickListener(v -> {
            downloadAction(getResources().getString(R.string.instagram_url));
        });


        try {
            params.height = Integer.parseInt(Constant.getString(mContext, Constant.P_BANNER_HEIGHT));
        } catch (NumberFormatException ex) {
            Constant.showToastMessage(mContext, "Invalid Format");
        }
        promotion_height.setLayoutParams(params);
        promotion1();
        promotion2();
        promotion3();
        promotion4();
        gameBanner();

        if (Constant.getString(requireActivity(), Constant.USE_IN_ROOTED_DEVICE).equalsIgnoreCase("false")) {
            RootBeer rootBeer = new RootBeer(mContext);
            if (rootBeer.isRooted()) {
                Constant.appsCheckerDialog(mContext, getActivity(), "You can't use this app because your device is rooted.");
            }
        }
        onClick();
        Constant.loadInvalidCounter(mContext);

        return view;
    }


    private void gameBanner() {
        Glide.with(mContext)
                .load(Constant.getString(mContext, Constant.HOME_GAME_BANNER))
                .into(game_banner);
        String isGameBannerShow = Constant.getString(mContext, Constant.GAME_HOME_BANNER_HIDE);
        if (!isGameBannerShow.equalsIgnoreCase("true")) {
            game_layout.setVisibility(View.GONE);
        }
    }

    private void promotion1() {
        String isPromotion1True = Constant.getString(mContext, Constant.IS_P1_ENABLED);
        String isInAppOpen1 = Constant.getString(mContext, Constant.IS_INAPP_OPEN1);

        if (isPromotion1True.equalsIgnoreCase("true")) {
            Glide.with(mContext)
                    .load(Constant.getString(mContext, Constant.P1_IMAGE))
                    .into(promotion_1);
            promotion_1.setOnClickListener(v -> {
                if (isInAppOpen1.equalsIgnoreCase("chrome_tab")) {
                    CustomTabsIntent.Builder customIntent = new CustomTabsIntent.Builder();
                    customIntent.setToolbarColor(ContextCompat.getColor(mContext, R.color.colorPrimaryDark));
                    openCustomTab(getActivity(), customIntent.build(), Uri.parse(Constant.getString(mContext, Constant.P1_LINK)));
                } else if (isInAppOpen1.equalsIgnoreCase("chrome_earning_tab")) {
                    inAppAction(Constant.getString(mContext, Constant.P1_LINK));
                } else if (isInAppOpen1.equalsIgnoreCase("external_browser")) {
                    downloadAction(Constant.getString(mContext, Constant.P1_LINK));
                } else if (isInAppOpen1.equalsIgnoreCase("game_activity")) {
                    Constant.GotoNextActivity(mContext, GameActivity.class, "");
                }
            });
        } else {
            promotion_1.setVisibility(View.GONE);
        }
    }

    private void promotion2() {
        String isPromotion2True = Constant.getString(mContext, Constant.IS_P2_ENABLED);
        String isInAppOpen2 = Constant.getString(mContext, Constant.IS_INAPP_OPEN2);

        if (isPromotion2True.equalsIgnoreCase("true")) {
            Glide.with(mContext)
                    .load(Constant.getString(mContext, Constant.P2_IMAGE))
                    .into(promotion_2);
            promotion_2.setOnClickListener(v -> {
                if (isInAppOpen2.equalsIgnoreCase("chrome_tab")) {
                    CustomTabsIntent.Builder customIntent = new CustomTabsIntent.Builder();
                    customIntent.setToolbarColor(ContextCompat.getColor(mContext, R.color.colorPrimaryDark));
                    openCustomTab(getActivity(), customIntent.build(), Uri.parse(Constant.getString(mContext, Constant.P2_LINK)));
                } else if (isInAppOpen2.equalsIgnoreCase("chrome_earning_tab")) {
                    inAppAction(Constant.getString(mContext, Constant.P2_LINK));
                } else if (isInAppOpen2.equalsIgnoreCase("external_browser")) {
                    downloadAction(Constant.getString(mContext, Constant.P2_LINK));
                } else if (isInAppOpen2.equalsIgnoreCase("game_activity")) {
                    Constant.GotoNextActivity(mContext, GameActivity.class, "");
                }
            });
        } else {
            promotion_2.setVisibility(View.GONE);
        }
    }

    private void promotion3() {
        String isPromotion3True = Constant.getString(mContext, Constant.IS_P3_ENABLED);
        String isInAppOpen3 = Constant.getString(mContext, Constant.IS_INAPP_OPEN3);
        if (isPromotion3True.equalsIgnoreCase("true")) {
            Glide.with(mContext)
                    .load(Constant.getString(mContext, Constant.P3_IMAGE))
                    .into(promotion_3);
            promotion_3.setOnClickListener(v -> {
                if (isInAppOpen3.equalsIgnoreCase("chrome_tab")) {
                    CustomTabsIntent.Builder customIntent = new CustomTabsIntent.Builder();
                    customIntent.setToolbarColor(ContextCompat.getColor(mContext, R.color.colorPrimaryDark));
                    openCustomTab(getActivity(), customIntent.build(), Uri.parse(Constant.getString(mContext, Constant.P3_LINK)));
                } else if (isInAppOpen3.equalsIgnoreCase("chrome_earning_tab")) {
                    inAppAction(Constant.getString(mContext, Constant.P3_LINK));
                } else if (isInAppOpen3.equalsIgnoreCase("external_browser")) {
                    downloadAction(Constant.getString(mContext, Constant.P3_LINK));
                } else if (isInAppOpen3.equalsIgnoreCase("game_activity")) {
                    Constant.GotoNextActivity(mContext, GameActivity.class, "");
                }
            });
        } else {
            promotion_3.setVisibility(View.GONE);
        }
    }

    private void promotion4() {
        String isPromotion4True = Constant.getString(mContext, Constant.IS_P4_ENABLED);
        String isInAppOpen4 = Constant.getString(mContext, Constant.IS_INAPP_OPEN4);
        if (isPromotion4True.equalsIgnoreCase("true")) {
            Glide.with(mContext)
                    .load(Constant.getString(mContext, Constant.P4_IMAGE))
                    .into(promotion_4);
            promotion_4.setOnClickListener(v -> {
                if (isInAppOpen4.equalsIgnoreCase("chrome_tab")) {
                    CustomTabsIntent.Builder customIntent = new CustomTabsIntent.Builder();
                    customIntent.setToolbarColor(ContextCompat.getColor(mContext, R.color.colorPrimaryDark));
                    openCustomTab(getActivity(), customIntent.build(), Uri.parse(Constant.getString(mContext, Constant.P4_LINK)));
                } else if (isInAppOpen4.equalsIgnoreCase("chrome_earning_tab")) {
                    inAppAction(Constant.getString(mContext, Constant.P4_LINK));
                } else if (isInAppOpen4.equalsIgnoreCase("external_browser")) {
                    downloadAction(Constant.getString(mContext, Constant.P4_LINK));
                } else if (isInAppOpen4.equalsIgnoreCase("game_activity")) {
                    Constant.GotoNextActivity(mContext, GameActivity.class, "");
                }
            });
        } else {
            promotion_4.setVisibility(View.GONE);
        }
    }

    public static void openCustomTab(Activity activity, CustomTabsIntent customTabsIntent, Uri uri) {
        String packageName = "com.android.chrome";
        if (packageName != null) {
            customTabsIntent.intent.setPackage(packageName);
            customTabsIntent.launchUrl(activity, uri);
        } else {
            activity.startActivity(new Intent(Intent.ACTION_VIEW, uri));
        }
    }


    //Download dialog
    private void downloadDialog(String msg) {
        popupSound.start();
        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(mContext, SweetAlertDialog.NORMAL_TYPE);
        sweetAlertDialog.setCancelable(false);
        sweetAlertDialog.setCanceledOnTouchOutside(false);
        sweetAlertDialog.setTitle("Download");
        sweetAlertDialog.setContentText(msg);
        sweetAlertDialog.setConfirmText("Ok");
        sweetAlertDialog.setCancelText("No");
        sweetAlertDialog.setConfirmClickListener(sweetAlertDialog12 -> {
            downloadAction(Constant.getString(mContext, Constant.PROMOTION_LINK));
        }).setCancelClickListener(Dialog::dismiss);
    }

    public void downloadAction(String url) {
        try {
            Uri webpage = Uri.parse(url);
            Intent myIntent = new Intent(Intent.ACTION_VIEW, webpage);
            startActivity(myIntent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getActivity(), "Failed to load.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    public void inAppAction(String url) {
        Intent intent = new Intent(mContext, GameLoader.class);
        intent.putExtra("GAME_PASSING", url);
        startActivity(intent);
    }

    private void onClick() {
        checkIsBlocked();
        //rating_layout.setOnClickListener(view -> Constant.ratingApp(mContext));
        check_in_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Constant.isNetworkAvailable(mContext) && Constant.isOnline(mContext)) {
                    String currentDate = Constant.getString(mContext, Constant.TODAY_DATE);
                    Log.e("TAG", "onClick: Current Date" + currentDate);
                    String last_date = Constant.getString(mContext, Constant.LAST_DATE);
                    if (last_date.equalsIgnoreCase("0")) {
                        last_date = "";
                    }
                    Log.e("TAG", "onClick: last_date Date" + last_date);
                    if (last_date.equals("")) {
                        Constant.setString(mContext, Constant.LAST_DATE, currentDate);
                        Constant.addPoints(mContext, Integer.parseInt(Constant.getString(mContext, Constant.DAILY_CHECK_IN_POINTS)), 0, "daily", currentDate);
                        user_points_text_view.setText(Constant.getString(mContext, Constant.USER_POINTS));
                        showDialogOfPoints(Integer.parseInt(Constant.getString(mContext, Constant.DAILY_CHECK_IN_POINTS)));
                    } else {
                        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                        try {
                            Date pastDAte = sdf.parse(last_date);
                            Date currentDAte = sdf.parse(currentDate);
                            long diff = currentDAte.getTime() - pastDAte.getTime();
                            long difference_In_Days = (diff / (1000 * 60 * 60 * 24)) % 365;
                            Log.e("TAG", "onClick: Days Diffrernce" + difference_In_Days);
                            if (difference_In_Days > 0) {
                                Constant.setString(mContext, Constant.LAST_DATE, currentDate);
                                Constant.addPoints(mContext, Integer.parseInt(Constant.getString(mContext, Constant.DAILY_CHECK_IN_POINTS)), 0, "daily", currentDate);
                                user_points_text_view.setText(Constant.getString(mContext, Constant.USER_POINTS));
                                showDialogOfPoints(Integer.parseInt(Constant.getString(mContext, Constant.DAILY_CHECK_IN_POINTS)));
                            } else {
                                showDialogOfPoints(0);
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    Constant.showInternetErrorDialog(getActivity(), "Please Check your Internet Connection");
                }
            }
        });
        scratchLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Constant.GotoNextActivity(getActivity(), ScratchActivity.class, "");
                requireActivity().overridePendingTransition(R.anim.enter, R.anim.exit);
            }
        });
        spinLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Constant.GotoNextActivity(getActivity(), SpinActivity.class, "");
                requireActivity().overridePendingTransition(R.anim.enter, R.anim.exit);
            }
        });
        captchaLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Constant.GotoNextActivity(mContext, CaptchaActivity.class, "");
                requireActivity().overridePendingTransition(R.anim.enter, R.anim.exit);
            }
        });
        quizLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Constant.GotoNextActivity(mContext, QuizActivity.class, "");
                requireActivity().overridePendingTransition(R.anim.enter, R.anim.exit);
            }
        });
        game_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isGhbNotExternalOpen.equalsIgnoreCase("external")) {
                    downloadAction(Constant.getString(mContext, Constant.GAME_HOME_BANNER_LINK));
                } else if (isGhbNotExternalOpen.equalsIgnoreCase("inapp")) {
                    inAppAction(Constant.getString(mContext, Constant.GAME_HOME_BANNER_LINK));
                } else {
                    Constant.GotoNextActivity(mContext, GameActivity.class, "");
                    requireActivity().overridePendingTransition(R.anim.enter, R.anim.exit);
                }
            }
        });
        videoTask.setOnClickListener(view -> {
            Constant.GotoNextActivity(mContext, VideoActivity.class, "");
            requireActivity().overridePendingTransition(R.anim.enter, R.anim.exit);
        });
        gameTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Constant.GotoNextActivity(mContext, GameActivity.class, "");
                requireActivity().overridePendingTransition(R.anim.enter, R.anim.exit);
            }
        });
        reward_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Constant.GotoNextActivity(mContext, GiftCardActivity.class, "");
                requireActivity().overridePendingTransition(R.anim.enter, R.anim.exit);
            }
        });
        appTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Constant.GotoNextActivity(mContext, AppsActivity.class, "");
                requireActivity().overridePendingTransition(R.anim.enter, R.anim.exit);
            }
        });
        readTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Constant.GotoNextActivity(mContext, VisitActivity.class, "");
                requireActivity().overridePendingTransition(R.anim.enter, R.anim.exit);
            }
        });
        watch_ads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constant.GotoNextActivity(mContext, WatchAndEarnActivity.class, "");
                requireActivity().overridePendingTransition(R.anim.enter, R.anim.exit);
            }
        });
    }

    private void gotoProfile() {
        if (Constant.getString(mContext, Constant.IS_LOGIN).equals("true")) {
            Constant.GotoNextActivity(mContext, ProfileFragment.class, "msg");
            requireActivity().overridePendingTransition(R.anim.enter, R.anim.exit);
        } else {
            Constant.showToastMessage(mContext, getResources().getString(R.string.login_first));
            Constant.GotoNextActivity(mContext, LoginActivity.class, "msg");
            requireActivity().overridePendingTransition(R.anim.enter, R.anim.exit);
            requireActivity().finish();
        }
    }

    private void setUSerName() {
        user_name_text_view.setText(Constant.getString(mContext, Constant.USER_NAME));
        String user_points = Constant.getString(mContext, Constant.USER_POINTS);
        if (user_points.equals("")) {
            user_points_text_view.setText("0");
        } else {
            user_points_text_view.setText(Constant.getString(mContext, Constant.USER_POINTS));
        }
    }

    private void checkIsBlocked() {
        if (Constant.getString(mContext, Constant.USER_BLOCKED).equals("0")) {
            Constant.showBlockedDialog(mContext, getActivity(), getResources().getString(R.string.you_are_blocked));
            return;
        }
        checkUserInfo();
        setUSerName();
    }

    private void checkUserInfo() {
        String user_refer_code = Constant.getString(mContext, Constant.USER_REFFER_CODE);
        String user_name = Constant.getString(mContext, Constant.USER_NAME);
        String user_number = Constant.getString(mContext, Constant.USER_NUMBER);
        if (user_refer_code.equals("") || user_name.equals("") || user_number.equals("")) {
            showUpdateProfileDialog();
        }
    }

    private void showUpdateProfileDialog() {
        popupSound.start();
        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(mContext, SweetAlertDialog.WARNING_TYPE);
        sweetAlertDialog.setCancelable(false);
        sweetAlertDialog.setCanceledOnTouchOutside(false);
        sweetAlertDialog.setTitle(getResources().getString(R.string.incomplite_profile));
        sweetAlertDialog.setConfirmText(getResources().getString(R.string.update_profile));
        sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.dismiss();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        gotoProfile();
                    }
                }, 1000);
            }
        }).show();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Constant.invalidClickCount >= Integer.parseInt(Constant.getString(mContext, Constant.INVALID_CLICK_COUNT))) {
            putInvalidClickDate();
            Constant.deleteInvalidCounter(mContext);
            Log.i("checkInvalid", "Total Invalid Click is: " + Constant.invalidClickCount);
        }
        checkInvalidBlocked();
    }

    @Override
    public void onPause() {
        super.onPause();
        adCount = 0;
        Constant.saveInvalidCounter(mContext);
    }

    private void checkInvalidBlocked() {
        if (Constant.getString(mContext, Constant.LAST_DATE_INVALID).equals(Constant.getString(mContext, Constant.TODAY_DATE))) {
            Constant.showBlockedDialog(mContext, getActivity(), "You are Blocked for today! Reason is: Invalid Clicks");
        } else {
            Constant.blockedHackingApps(mContext, getActivity());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Constant.setString(mContext, "exit", "exit");
        if (isAdsShow) {
            allInterstitialShow();
        }
        user_points_text_view.setText(Constant.getString(mContext, Constant.USER_POINTS));
        checkIsBlocked();

        String isPromotionDialogShow = Constant.getString(mContext, Constant.IS_PROMOTION_DIALOG_ENABLE);

        if (isPromotionDialogShow.equalsIgnoreCase("true")) {
            downloadDialog(Constant.getString(mContext, Constant.PROMOTION_TEXT));
        }

        String last_date = Constant.getString(mContext, Constant.SIGNUP_BOUNUS_DATE);
        if (last_date.equalsIgnoreCase("0")) {
            collectBonusDialog();
        }
    }

    private void putInvalidClickDate() {
        if (Constant.isNetworkAvailable(mContext) && Constant.isOnline(mContext)) {
            currentDateInvalid = Constant.getString(mContext, Constant.TODAY_DATE);
            Log.e("TAG", "onClick: Current Date" + currentDateInvalid);
            last_date_invalid = Constant.getString(mContext, Constant.LAST_DATE_INVALID);
            if (last_date_invalid.equalsIgnoreCase("0")) {
                last_date_invalid = "";
            }
            Log.e("TAG", "onClick: last_date Date" + last_date_invalid);
            if (last_date_invalid.equals("")) {
                Constant.setString(mContext, Constant.LAST_DATE_INVALID, currentDateInvalid);
                Constant.addPoints(mContext, 0, 0, "invalid", currentDateInvalid);
            } else {
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                try {
                    Date pastDAte = sdf.parse(last_date_invalid);
                    Date currentDAte = sdf.parse(currentDateInvalid);
                    long diff = currentDAte.getTime() - pastDAte.getTime();
                    long difference_In_Days = (diff / (1000 * 60 * 60 * 24)) % 365;
                    Log.e("TAG", "onClick: Days Diffrernce" + difference_In_Days);
                    if (difference_In_Days > 0) {
                        Constant.setString(mContext, Constant.LAST_DATE_INVALID, currentDateInvalid);
                        Constant.addPoints(mContext, 0, 0, "invalid", currentDateInvalid);
                    } else {
                        //showDialogOfPoints(0);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        } else {
            Constant.showInternetErrorDialog(getActivity(), "Please Check your Internet Connection");
        }
    }


    // Check VPN Connected or not
    public static boolean isVpnConnectionActive() {
        List<String> networkList = new ArrayList<>();
        try {
            for (NetworkInterface networkInterface : Collections.list(NetworkInterface.getNetworkInterfaces())) {
                if (networkInterface.isUp())
                    networkList.add(networkInterface.getName());
            }
        } catch (Exception ex) {

        }

        return networkList.contains("tun0");
    }

    public void showDialogOfPoints(int points) {
        popupSound.start();
        if (points == Integer.parseInt(Constant.getString(mContext, Constant.DAILY_CHECK_IN_POINTS))) {
            new SweetAlertDialog(mContext, SweetAlertDialog.SUCCESS_TYPE)
                    .setTitleText("Congratulation!")
                    .setContentText("You won " + points + " " + "coins")
                    .show();
            allInterstitialShow();
        } else {
            new SweetAlertDialog(mContext, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Oops...")
                    .setContentText("Today Chance is Over!")
                    .show();
            allInterstitialShow();
        }
    }

    private void collectBonusDialog() {
        SweetAlertDialog sweetAlertDialog;
        sweetAlertDialog = new SweetAlertDialog(mContext, SweetAlertDialog.SUCCESS_TYPE);
        sweetAlertDialog.setCancelable(false);
        sweetAlertDialog.setCanceledOnTouchOutside(false);
        sweetAlertDialog.setTitle("Sign Up Bonus!");
        sweetAlertDialog.setContentText("Collect Your Sign Up Bonus.");
        sweetAlertDialog.setConfirmText("Ok");
        sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                allInterstitialShow();
                sweetAlertDialog.dismiss();
                if (Constant.isNetworkAvailable(mContext) && Constant.isOnline(mContext)) {
                    String currentDate = Constant.getString(mContext, Constant.TODAY_DATE);
                    Log.e("TAG", "onClick: Current Date" + currentDate);
                    String last_date = Constant.getString(mContext, Constant.SIGNUP_BOUNUS_DATE);
                    if (last_date.equalsIgnoreCase("0")) {
                        last_date = "";
                    }
                    Log.e("TAG", "onClick: last_date Date" + last_date);
                    if (last_date.equals("")) {
                        Constant.setString(mContext, Constant.SIGNUP_BOUNUS_DATE, currentDate);
                        Constant.addPoints(mContext, Integer.parseInt(Constant.getString(mContext, Constant.SIGNUP_BOUNUS)), 0, "bonus", currentDate);
                        user_points_text_view.setText(Constant.getString(mContext, Constant.USER_POINTS));
                    } else {
                        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                        try {
                            Date pastDAte = sdf.parse(last_date);
                            Date currentDAte = sdf.parse(currentDate);
                            long diff = currentDAte.getTime() - pastDAte.getTime();
                            long difference_In_Days = (diff / (1000 * 60 * 60 * 24)) % 365;
                            Log.e("TAG", "onClick: Days Diffrernce" + difference_In_Days);
                            if (difference_In_Days > 0) {
                                Constant.setString(mContext, Constant.SIGNUP_BOUNUS_DATE, currentDate);
                                Constant.addPoints(mContext, Integer.parseInt(Constant.getString(mContext, Constant.SIGNUP_BOUNUS)), 0, "bonus", currentDate);
                                user_points_text_view.setText(Constant.getString(mContext, Constant.USER_POINTS));
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    Constant.showInternetErrorDialog(getActivity(), "Please Check your Internet Connection");
                }
            }
        }).show();
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

                }

                @Override
                public void adDisplayed(Ad ad) {
                    isAdsShow = false;
                }

                @Override
                public void adClicked(Ad ad) {

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
        String adType = Constant.getString(mContext, Constant.AD_TYPE);
        if (adType.equalsIgnoreCase("startapp")) {
            ShowStartappInterstital();
        } else if (adType.equalsIgnoreCase("unity")) {
            unityInterstitialAd();
        }
    }


    private void LoadStartAppBanner(LinearLayout layout) {
        Banner banner = new Banner(mContext, new BannerListener() {
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


    private IUnityAdsLoadListener loadListener = new IUnityAdsLoadListener() {
        @Override
        public void onUnityAdsAdLoaded(String placementId) {
            UnityAds.show(getActivity(), Constant.getString(mContext, Constant.UNITY_INTERSTITAL_ID), new UnityAdsShowOptions(), showListener);
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
            isAdsShow = false;
        }

        @Override
        public void onUnityAdsShowClick(String placementId) {
            Log.v("UnityAdsExample", "onUnityAdsShowClick: " + placementId);
        }

        @Override
        public void onUnityAdsShowComplete(String placementId, UnityAds.UnityAdsShowCompletionState state) {
            Log.v("UnityAdsExample", "onUnityAdsShowComplete: " + placementId);
            isAdsShow = false;
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
        UnityAds.show(getActivity(), Constant.getString(getActivity(), Constant.UNITY_INTERSTITAL_ID), new UnityAdsShowOptions(), showListener);
    }

}