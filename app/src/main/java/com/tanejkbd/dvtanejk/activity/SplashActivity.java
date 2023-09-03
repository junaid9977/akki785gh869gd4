package com.tanejkbd.dvtanejk.activity;

import static com.google.android.play.core.install.model.AppUpdateType.IMMEDIATE;

import android.content.Intent;
import android.content.IntentSender;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.tanejkbd.dvtanejk.App;
import com.tanejkbd.dvtanejk.BuildConfig;
import com.tanejkbd.dvtanejk.R;
import com.tanejkbd.dvtanejk.models.GameModel;
import com.tanejkbd.dvtanejk.models.PaymentModel;
import com.tanejkbd.dvtanejk.models.User;
import com.tanejkbd.dvtanejk.models.WebsiteModel;
import com.tanejkbd.dvtanejk.utils.Constant;
import com.tanejkbd.dvtanejk.utils.CustomVolleyJsonRequest;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.OnFailureListener;
import com.google.android.play.core.tasks.OnSuccessListener;
import com.google.android.play.core.tasks.Task;
import com.google.gson.Gson;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.NetworkInterface;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class SplashActivity extends AppCompatActivity {
    boolean LOGIN = false;
    private AppUpdateManager appUpdateManager;
    public static final int RC_APP_UPDATE = 101;
    SplashActivity activity;
    String user_name = null;
    private int retry_settings = 0, retry_details = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        activity = this;


        String is_login = Constant.getString(activity, Constant.IS_LOGIN);
        if (is_login.equals("true")) {
            LOGIN = true;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Log.e("TAG", "onCreate:if part activarte ");
            appUpdateManager = AppUpdateManagerFactory.create(this);
            UpdateApp();
        } else {
            Log.e("TAG", "onCreate:else part activarte ");
            onInit();
        }
    }


    private void onInit() {
        if (Constant.isNetworkAvailable(activity)) {
            if (LOGIN) {
                try {
                    String tag_json_obj = "json_login_req";
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("get_login", "any");
                    params.put("email", Constant.getString(activity, Constant.USER_EMAIL));
                    params.put("password", Constant.getString(activity, Constant.USER_PASSWORD));
                    CustomVolleyJsonRequest jsonObjReq = new CustomVolleyJsonRequest(Request.Method.POST,
                            new String(Base64.decode(BuildConfig.ApiKey, Base64.DEFAULT)) + BuildConfig.LoginKey, params, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("TAG", response.toString());

                            try {
                                boolean status = response.getBoolean("status");
                                if (status) {
                                    JSONObject jsonObject = response.getJSONObject("0");
                                    Constant.setString(activity, Constant.USER_ID, jsonObject.getString("id"));
                                    final User user = new User(jsonObject.getString("name"), jsonObject.getString("number"), jsonObject.getString("email"), jsonObject.getString("device"), jsonObject.getString("points"), jsonObject.getString("referraled_with"), jsonObject.getString("status"), jsonObject.getString("referral_code"));

                                    if (response.has("date")) {
                                        Constant.setString(activity, Constant.TODAY_DATE, response.getString("date"));
                                    } else {
                                        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                                        Constant.setString(activity, Constant.TODAY_DATE, currentDate);
                                    }

                                    if (jsonObject.has("daily_check_in")) {
                                        Constant.setString(activity, Constant.LAST_DATE, jsonObject.getString("daily_check_in"));
                                    }
                                    if (jsonObject.has("last_date_watch")) {
                                        Constant.setString(activity, Constant.LAST_DATE_WATCH, jsonObject.getString("last_date_watch"));
                                    }
                                    if (jsonObject.has("watched1_date")) {
                                        Constant.setString(activity, Constant.WATCHED1_DATE, jsonObject.getString("watched1_date"));
                                    }
                                    if (jsonObject.has("watched2_date")) {
                                        Constant.setString(activity, Constant.WATCHED2_DATE, jsonObject.getString("watched2_date"));
                                    }
                                    if (jsonObject.has("watched3_date")) {
                                        Constant.setString(activity, Constant.WATCHED3_DATE, jsonObject.getString("watched3_date"));
                                    }
                                    if (jsonObject.has("watched4_date")) {
                                        Constant.setString(activity, Constant.WATCHED4_DATE, jsonObject.getString("watched4_date"));
                                    }
                                    if (jsonObject.has("watched5_date")) {
                                        Constant.setString(activity, Constant.WATCHED5_DATE, jsonObject.getString("watched5_date"));
                                    }
                                    if (jsonObject.has("watched6_date")) {
                                        Constant.setString(activity, Constant.WATCHED6_DATE, jsonObject.getString("watched6_date"));
                                    }
                                    if (jsonObject.has("watched7_date")) {
                                        Constant.setString(activity, Constant.WATCHED7_DATE, jsonObject.getString("watched7_date"));
                                    }
                                    if (jsonObject.has("watched8_date")) {
                                        Constant.setString(activity, Constant.WATCHED8_DATE, jsonObject.getString("watched8_date"));
                                    }
                                    if (jsonObject.has("watched9_date")) {
                                        Constant.setString(activity, Constant.WATCHED9_DATE, jsonObject.getString("watched9_date"));
                                    }
                                    if (jsonObject.has("watched10_date")) {
                                        Constant.setString(activity, Constant.WATCHED10_DATE, jsonObject.getString("watched10_date"));
                                    }
                                    if (jsonObject.has("visited1_date")) {
                                        Constant.setString(activity, Constant.VISITED1_DATE, jsonObject.getString("visited1_date"));
                                    }
                                    if (jsonObject.has("visited2_date")) {
                                        Constant.setString(activity, Constant.VISITED2_DATE, jsonObject.getString("visited2_date"));
                                    }
                                    if (jsonObject.has("visited3_date")) {
                                        Constant.setString(activity, Constant.VISITED3_DATE, jsonObject.getString("visited3_date"));
                                    }
                                    if (jsonObject.has("visited4_date")) {
                                        Constant.setString(activity, Constant.VISITED4_DATE, jsonObject.getString("visited4_date"));
                                    }
                                    if (jsonObject.has("visited5_date")) {
                                        Constant.setString(activity, Constant.VISITED5_DATE, jsonObject.getString("visited5_date"));
                                    }
                                    if (jsonObject.has("visited6_date")) {
                                        Constant.setString(activity, Constant.VISITED6_DATE, jsonObject.getString("visited6_date"));
                                    }
                                    if (jsonObject.has("visited7_date")) {
                                        Constant.setString(activity, Constant.VISITED7_DATE, jsonObject.getString("visited7_date"));
                                    }
                                    if (jsonObject.has("visited8_date")) {
                                        Constant.setString(activity, Constant.VISITED8_DATE, jsonObject.getString("visited8_date"));
                                    }
                                    if (jsonObject.has("visited9_date")) {
                                        Constant.setString(activity, Constant.VISITED9_DATE, jsonObject.getString("visited9_date"));
                                    }
                                    if (jsonObject.has("visited10_date")) {
                                        Constant.setString(activity, Constant.VISITED10_DATE, jsonObject.getString("visited10_date"));
                                    }
                                    if (jsonObject.has("last_date_invalid")) {
                                        Constant.setString(activity, Constant.LAST_DATE_INVALID, jsonObject.getString("last_date_invalid"));
                                    }
                                    if (jsonObject.has("scratch_date")) {
                                        Constant.setString(activity, Constant.LAST_DATE_SCRATCH, jsonObject.getString("scratch_date"));
                                    }
                                    if (jsonObject.has("scratch_count")) {
                                        Constant.setString(activity, Constant.SCRATCH_COUNT, jsonObject.getString("scratch_count"));
                                    }

                                    if (jsonObject.has("spin_date")) {
                                        Constant.setString(activity, Constant.LAST_DATE_SPIN, jsonObject.getString("spin_date"));
                                    }
                                    if (jsonObject.has("spin_count")) {
                                        Constant.setString(activity, Constant.SPIN_COUNT, jsonObject.getString("spin_count"));
                                    }
                                    if (jsonObject.has("captcha_count")) {
                                        Constant.setString(activity, Constant.CAPTCHA_COUNT, jsonObject.getString("captcha_count"));
                                    }
                                    if (jsonObject.has("captcha_date")) {
                                        Constant.setString(activity, Constant.LAST_DATE_CAPTCHA, jsonObject.getString("captcha_date"));
                                    }
                                    if (jsonObject.has("singup_bounus_date")) {
                                        Constant.setString(activity, Constant.SIGNUP_BOUNUS_DATE, jsonObject.getString("singup_bounus_date"));
                                    }
                                    if (jsonObject.has("quiz_count")) {
                                        Constant.setString(activity, Constant.QUIZ_COUNT, jsonObject.getString("quiz_count"));
                                    }
                                    if (jsonObject.has("quiz_date")) {
                                        Constant.setString(activity, Constant.LAST_DATE_QUIZ, jsonObject.getString("quiz_date"));
                                    }
                                    if (jsonObject.has("game_count")) {
                                        Constant.setString(activity, Constant.GAME_COUNT, jsonObject.getString("game_count"));
                                    }
                                    if (jsonObject.has("game_date")) {
                                        Constant.setString(activity, Constant.LAST_DATE_GAME, jsonObject.getString("game_date"));
                                    }
                                    if (user.getName() != null) {
                                        Constant.setString(activity, Constant.USER_NAME, user.getName());
                                        Log.e("TAG", "onDataChange: " + user.getName());
                                    }
                                    if (user.getNumber() != null) {
                                        Constant.setString(activity, Constant.USER_NUMBER, user.getNumber());
                                        Log.e("TAG", "onDataChange: " + user.getNumber());
                                    }
                                    if (user.getEmail() != null) {
                                        Constant.setString(activity, Constant.USER_EMAIL, user.getEmail());
                                        Log.e("TAG", "onDataChange: " + user.getEmail());
                                    }
                                    if (user.getDevice() != null) {
                                        Constant.setString(activity, Constant.USER_DEVICE, user.getDevice());
                                        Log.e("TAG", "onDataChange: " + user.getDevice());
                                    }
                                    if (user.getPoints() != null) {
                                        Constant.setString(activity, Constant.USER_POINTS, user.getPoints());
                                    }
                                    if (user.getReferCode() != null) {
                                        Constant.setString(activity, Constant.REFER_CODE, user.getReferCode());
                                        Log.e("TAG", "onDataChange: " + user.getReferCode());
                                    }
                                    if (user.getIsBLocked() != null) {
                                        Constant.setString(activity, Constant.USER_BLOCKED, user.getIsBLocked());
                                        Log.e("TAG", "onDataChange: " + user.getIsBLocked());
                                    }
                                    if (user.getUserReferCode() != null) {
                                        Constant.setString(activity, Constant.USER_REFFER_CODE, user.getUserReferCode());
                                        Log.e("TAG", "onDataChange: " + user.getUserReferCode());
                                    }

                                    if (Constant.getString(activity, Constant.USER_BLOCKED).equals("0")) {
                                        Constant.showBlockedDialog(activity, activity, getResources().getString(R.string.you_are_blocked));
                                    } else {
                                        Log.e("TAG", "onInit: login pART");
                                        getSettingsFromAdminPannel();
                                        getGameSettingsFromAdminPannel();
                                        getRedeemSettingsFromAdminPannel();
                                        getAppsSettingsFromAdminPannel();
                                        getVideoSettingsFromAdminPannel();
                                        getVisitSettingsFromAdminPannel();
                                    }
                                } else {
                                    // here get ads beetween and daily scratch limit daily spin limit, daily check in points, coin to rupee,captcha count
                                    getSettingsFromAdminPannel();
                                    getGameSettingsFromAdminPannel();
                                    getRedeemSettingsFromAdminPannel();
                                    getAppsSettingsFromAdminPannel();
                                    getVideoSettingsFromAdminPannel();
                                    getVisitSettingsFromAdminPannel();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                            VolleyLog.d("TAG", "Error: " + error.getMessage());
                            if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                                Constant.showToastMessage(activity, getResources().getString(R.string.slow_internet_connection));
                            }
                        }
                    });
                    jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
                            Constant.GET_SETTINGS_FROM_ADMIN_PANNEL_TIMEOUT,
                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                    // Adding request to request queue
                    App.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);

                } catch (Exception e) {
                    Log.e("TAG", "onInit: excption " + e.getMessage().toString());
                }
            } else {
                if (Constant.getString(activity, Constant.USER_BLOCKED).equals("0")) {
                    Constant.showBlockedDialog(activity, activity, getResources().getString(R.string.you_are_blocked));
                    return;
                }
                getSettingsFromAdminPannel();
                getGameSettingsFromAdminPannel();
                getRedeemSettingsFromAdminPannel();
                getAppsSettingsFromAdminPannel();
                getVideoSettingsFromAdminPannel();
                getVisitSettingsFromAdminPannel();
                // here get ads beetween and daily scratch limit daily spin limit, daily check in points, coin to rupee,captcha count
            }
        } else {
            Constant.showInternetErrorDialog(activity, "Please Check your Internet Connection");
        }
    }

    private void getSettingsFromAdminPannel() {
        if (Constant.isNetworkAvailable(activity)) {
            try {
                String tag_json_obj = "json_login_req";
                Map<String, String> map = new HashMap<>();
                map.put("get_settings", "any");
                CustomVolleyJsonRequest customVolleyJsonRequest = new CustomVolleyJsonRequest(Request.Method.POST,
                        new String(Base64.decode(BuildConfig.ApiKey, Base64.DEFAULT)) + BuildConfig.ASettingsKey, map, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            boolean status = response.getBoolean("status");
                            if (status) {
                                JSONObject jb = response.getJSONObject("0");
                                Constant.setString(activity, Constant.ADS_BEETWEEN, jb.getString("ads_between"));
                                Constant.setString(activity, Constant.DAILY_SCRATCH_COUNT, jb.getString("daily_scratch_limit"));
                                Constant.setString(activity, Constant.DAILY_SPIN_COUNT, jb.getString("daily_spin_limit"));
                                Constant.setString(activity, Constant.DAILY_CHECK_IN_POINTS, jb.getString("daily_check_in_points"));
                                Constant.setString(activity, Constant.ADS_CLICK_COINS, jb.getString("ads_click_coins"));
                                Constant.setString(activity, Constant.ADS_CLICK_AFTER_X_CLICK, jb.getString("ads_click_after_x_click"));
                                Constant.setString(activity, Constant.ADS_CLICK_TIME, jb.getString("ads_click_time"));
                                Constant.setString(activity, Constant.DAILY_WATCH_POINTS, jb.getString("daily_watch_points"));
                                Constant.setString(activity, Constant.COIN_TO_RUPEE, jb.getString("coin_to_rupee_text"));
                                Constant.setString(activity, Constant.DAILY_CAPTCHA_COUNT, jb.getString("daily_captcha_limit"));
                                Constant.setString(activity, Constant.DAILY_QUIZ_COUNT, jb.getString("daily_quiz_limit"));
                                Constant.setString(activity, Constant.MINIMUM_REDEEM_POINTS, jb.getString("minimum_redeem_points"));
                                Constant.setString(activity, Constant.AD_TYPE, jb.getString("ad_type"));
                                Constant.setString(activity, Constant.APPLOVIN_BANNER_ID, jb.getString("applovin_banner_id"));
                                Constant.setString(activity, Constant.APPLOVIN_INTERSTITAL_ID, jb.getString("applovin_interstital_id"));
                                Constant.setString(activity, Constant.APPLOVIN_REWARDED_ID, jb.getString("applovin_rewarded_id"));

                                Constant.setString(activity, Constant.UNITY_BANNER_ID, jb.getString("unity_banner_id"));
                                Constant.setString(activity, Constant.UNITY_INTERSTITAL_ID, jb.getString("unity_interstital_id"));
                                Constant.setString(activity, Constant.UNITY_REWARDED_ID, jb.getString("unity_rewarded_id"));

                                Constant.setString(activity, Constant.STARTAPP_BANNER_ID, jb.getString("startapp_banner_id"));
                                Constant.setString(activity, Constant.STARTAPP_INTERSTITAL_ID, jb.getString("startapp_interstital_id"));
                                Constant.setString(activity, Constant.VPN_ENABLE, jb.getString("is_vpn_enable"));
                                Constant.setString(activity, Constant.VPN_LOGO, jb.getString("vpn_logo"));
                                Constant.setString(activity, Constant.VPN_MSG, jb.getString("vpn_msg"));
                                Constant.setString(activity, Constant.VPN_LINK, jb.getString("vpn_link"));
                                Constant.setString(activity, Constant.IS_P_UPDATE_ENABLED, jb.getString("is_p_update_enabled"));
                                Constant.setString(activity, Constant.REFER_TEXT, jb.getString("refer_text"));


                                Constant.setString(activity, Constant.SPIN_PRICE_COIN, jb.getString("spin_price_coins"));
                                Constant.setString(activity, Constant.SCRATCH_PRICE_COIN, jb.getString("scratch_price_coins"));
                                Constant.setString(activity, Constant.CAPTCHA_PRICE_COIN, jb.getString("captcha_price_coins"));
                                Constant.setString(activity, Constant.SIGNUP_BOUNUS, jb.getString("signup_points"));
                                Constant.setString(activity, Constant.UNITY_GAME_ID, jb.getString("unity_game_id"));
                                Constant.setString(activity, Constant.STARTAPP_APP_ID, jb.getString("startapp_app_id"));
                                Constant.setString(activity, Constant.SHARE_TEXT, jb.getString("share_text"));
                                Constant.setString(activity, Constant.IS_PROMOTION_DIALOG_ENABLE, jb.getString("is_promotion_dialog_enable"));
                                Constant.setString(activity, Constant.PROMOTION_TEXT, jb.getString("promotion_text"));
                                Constant.setString(activity, Constant.PROMOTION_LINK, jb.getString("promotion_link"));
                                Constant.setString(activity, Constant.SPIN_POINT_1, jb.getString("spin_point_1"));
                                Constant.setString(activity, Constant.SPIN_POINT_2, jb.getString("spin_point_2"));
                                Constant.setString(activity, Constant.SPIN_POINT_3, jb.getString("spin_point_3"));
                                Constant.setString(activity, Constant.SPIN_POINT_4, jb.getString("spin_point_4"));
                                Constant.setString(activity, Constant.SPIN_POINT_5, jb.getString("spin_point_5"));
                                Constant.setString(activity, Constant.SPIN_POINT_6, jb.getString("spin_point_6"));
                                Constant.setString(activity, Constant.SPIN_POINT_7, jb.getString("spin_point_7"));
                                Constant.setString(activity, Constant.SPIN_POINT_8, jb.getString("spin_point_8"));
                                Constant.setString(activity, Constant.SPIN_POINT_9, jb.getString("spin_point_9"));
                                Constant.setString(activity, Constant.SPIN_POINT_10, jb.getString("spin_point_10"));
                                Constant.setString(activity, Constant.IS_P1_ENABLED, jb.getString("is_p1_enable"));
                                Constant.setString(activity, Constant.P1_IMAGE, jb.getString("p1_image"));
                                Constant.setString(activity, Constant.P1_LINK, jb.getString("p1_link"));
                                Constant.setString(activity, Constant.IS_INAPP_OPEN1, jb.getString("is_inapp_open1"));
                                Constant.setString(activity, Constant.IS_P2_ENABLED, jb.getString("is_p2_enable"));
                                Constant.setString(activity, Constant.P2_IMAGE, jb.getString("p2_image"));
                                Constant.setString(activity, Constant.P2_LINK, jb.getString("p2_link"));
                                Constant.setString(activity, Constant.IS_INAPP_OPEN2, jb.getString("is_inapp_open2"));
                                Constant.setString(activity, Constant.IS_P3_ENABLED, jb.getString("is_p3_enable"));
                                Constant.setString(activity, Constant.P3_IMAGE, jb.getString("p3_image"));
                                Constant.setString(activity, Constant.P3_LINK, jb.getString("p3_link"));
                                Constant.setString(activity, Constant.IS_INAPP_OPEN3, jb.getString("is_inapp_open3"));
                                Constant.setString(activity, Constant.IS_P4_ENABLED, jb.getString("is_p4_enable"));
                                Constant.setString(activity, Constant.P4_IMAGE, jb.getString("p4_image"));
                                Constant.setString(activity, Constant.P4_LINK, jb.getString("p4_link"));
                                Constant.setString(activity, Constant.IS_INAPP_OPEN4, jb.getString("is_inapp_open4"));
                                Constant.setString(activity, Constant.P_BANNER_HEIGHT, jb.getString("p_banner_height"));
                                Constant.setString(activity, Constant.QUIZ_PLAY_TIME, jb.getString("quiz_play_time"));
                                Constant.setString(activity, Constant.PER_QUIZ_COIN, jb.getString("per_quiz_coin"));
                                Constant.setString(activity, Constant.QUIZ_QUESTION_LIMIT, jb.getString("quiz_question_limit"));
                                Constant.setString(activity, Constant.INVALID_CLICK_COUNT, jb.getString("invalid_click_count"));
                                Constant.setString(activity, Constant.USE_IN_ROOTED_DEVICE, jb.getString("use_in_rooted_device"));
                                Constant.setString(activity, Constant.IS_USE_MULTIPLE_ACCOUNT, jb.getString("is_use_multiple_account"));

                                    if (jb.getString("is_vpn_enable").equalsIgnoreCase("true")) {
                                        getVpn();
                                    } else {
                                        if (jb.getString("is_vpn_use").equalsIgnoreCase("true")) {
                                            gotoLoginActivity();
                                        } else {
                                            if (isVpnConnectionActive()) {
                                                Log.i("VpnCheck", "VPN is Connected");
                                                vpnNotUsDialog();
                                            } else {
                                                Log.i("VpnCheck", "VPN is not Connected");
                                                gotoLoginActivity();
                                            }
                                        }
                                    }

                            } else {
                                Constant.showToastMessage(activity, "No Settings Found In Admin Pannel");
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            Constant.showToastMessage(activity, "Something Went Wrong Try Again");
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                            Constant.showToastMessage(activity, getResources().getString(R.string.slow_internet_connection));
                        }
                    }
                });
                customVolleyJsonRequest.setRetryPolicy(new DefaultRetryPolicy(
                        Constant.GET_SETTINGS_FROM_ADMIN_PANNEL_TIMEOUT,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                // Adding request to request queue
                App.getInstance().addToRequestQueue(customVolleyJsonRequest, tag_json_obj);

            } catch (Exception e) {
                Log.e("TAG", "Admin Settings: excption " + e.getMessage().toString());
            }
        } else {
            Constant.showInternetErrorDialog(activity, "Please Check your Internet Connection");
        }
    }


    private void getGameSettingsFromAdminPannel() {
        if (Constant.isNetworkAvailable(activity)) {
            try {
                String tag_json_obj = "json_login_req";
                Map<String, String> map = new HashMap<>();
                map.put("get_game_settings", "any");
                CustomVolleyJsonRequest customVolleyJsonRequest = new CustomVolleyJsonRequest(Request.Method.POST,
                        new String(Base64.decode(BuildConfig.ApiKey, Base64.DEFAULT)) + BuildConfig.GSettingsKey, map, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            boolean status = response.getBoolean("status");
                            if (status) {
                                JSONObject jb = response.getJSONObject("0");
                                Constant.setString(activity, Constant.DAILY_GAME_COUNT, jb.getString("daily_game_limit"));
                                Constant.setString(activity, Constant.GAME_PRICE_COINS, jb.getString("game_price_coins"));
                                Constant.setString(activity, Constant.GAME_PLAY_TIME, jb.getString("game_play_time"));
                                Constant.setString(activity, Constant.GAME_WARNING_MSG, jb.getString("game_warning_msg"));
                                Constant.setString(activity, Constant.REDIRECT_TO_ACTIVITY, jb.getString("redirect_to_activity"));
                                Constant.setString(activity, Constant.PG_BANNER_HEIGHT, jb.getString("pg_banner_height"));
                                Constant.setString(activity, Constant.HOME_GAME_BANNER, jb.getString("home_game_banner"));
                                Constant.setString(activity, Constant.GAME_HOME_BANNER_HIDE, jb.getString("game_home_banner_hide"));
                                Constant.setString(activity, Constant.GAME_HOME_BANNER_GAME_ACTIVITY, jb.getString("game_home_banner_game_activity"));
                                Constant.setString(activity, Constant.GAME_HOME_BANNER_LINK, jb.getString("game_home_banner_link"));
                                Constant.setString(activity, Constant.IS_PG1_ENABLED, jb.getString("is_pg1_enable"));
                                Constant.setString(activity, Constant.PG1_IMAGE, jb.getString("pg1_image"));
                                Constant.setString(activity, Constant.PG1_LINK, jb.getString("pg1_link"));
                                Constant.setString(activity, Constant.PG1_OPEN_WITH, jb.getString("pg1_open_with"));
                                Constant.setString(activity, Constant.IS_PG2_ENABLED, jb.getString("is_pg2_enable"));
                                Constant.setString(activity, Constant.PG2_IMAGE, jb.getString("pg2_image"));
                                Constant.setString(activity, Constant.PG2_LINK, jb.getString("pg2_link"));
                                Constant.setString(activity, Constant.PG2_OPEN_WITH, jb.getString("pg2_open_with"));
                                Constant.setString(activity, Constant.IS_PG3_ENABLED, jb.getString("is_pg3_enable"));
                                Constant.setString(activity, Constant.PG3_IMAGE, jb.getString("pg3_image"));
                                Constant.setString(activity, Constant.PG3_LINK, jb.getString("pg3_link"));
                                Constant.setString(activity, Constant.PG3_OPEN_WITH, jb.getString("pg3_open_with"));
                                Constant.setString(activity, Constant.IS_PG4_ENABLED, jb.getString("is_pg4_enable"));
                                Constant.setString(activity, Constant.PG4_IMAGE, jb.getString("pg4_image"));
                                Constant.setString(activity, Constant.PG4_LINK, jb.getString("pg4_link"));
                                Constant.setString(activity, Constant.PG4_OPEN_WITH, jb.getString("pg4_open_with"));
                                if (response.has("data")) {
                                    JSONArray array = response.getJSONArray("data");
                                    ArrayList<GameModel> gameModelArrayList = new ArrayList<>();
                                    for (int i = 0; i < array.length(); i++) {
                                        JSONObject object = array.getJSONObject(i);
                                        if (object.getString("is_enable").equalsIgnoreCase("true")) {
                                            GameModel gameModel = new GameModel(
                                                    object.getString("id"),
                                                    object.getString("is_enable"),
                                                    object.getString("image"),
                                                    object.getString("title"),
                                                    object.getString("link")
                                            );
                                            gameModelArrayList.add(gameModel);
                                        }
                                    }
                                    if (!gameModelArrayList.isEmpty()) {
                                        Gson gson = new Gson();
                                        // getting data from gson and storing it in a string.
                                        String json = gson.toJson(gameModelArrayList);
                                        Constant.setString(activity, Constant.GAME_LIST, json);
                                    }else {
                                        Constant.setString(activity, Constant.GAME_LIST, "");
                                    }
                                }
                            } else {
                                Constant.showToastMessage(activity, "No Settings Found In Admin Pannel");
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            Constant.showToastMessage(activity, "Something Went Wrong From Game Settings");
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                            Constant.showToastMessage(activity, getResources().getString(R.string.slow_internet_connection));
                        }
                    }
                });
                customVolleyJsonRequest.setRetryPolicy(new DefaultRetryPolicy(
                        Constant.GET_SETTINGS_FROM_ADMIN_PANNEL_TIMEOUT,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                // Adding request to request queue
                App.getInstance().addToRequestQueue(customVolleyJsonRequest, tag_json_obj);

            } catch (Exception e) {
                Log.e("TAG", "Game Settings: excption " + e.getMessage().toString());
            }
        } else {
            Constant.showInternetErrorDialog(activity, "Please Check your Internet Connection");
        }
    }


    private void getRedeemSettingsFromAdminPannel() {
        if (Constant.isNetworkAvailable(activity)) {
            try {
                String tag_json_obj = "json_login_req";
                Map<String, String> map = new HashMap<>();
                map.put("get_redeem_settings", "any");
                CustomVolleyJsonRequest customVolleyJsonRequest = new CustomVolleyJsonRequest(Request.Method.POST,
                        new String(Base64.decode(BuildConfig.ApiKey, Base64.DEFAULT)) + BuildConfig.RSettingsKey, map, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            boolean status = response.getBoolean("status");
                            if (status) {
                                ArrayList<PaymentModel> websiteModelArrayList = new ArrayList<>();
                                JSONArray jb = response.getJSONArray("data");
                                for (int i = 0; i < jb.length(); i++) {
                                    JSONObject visitObject = jb.getJSONObject(i);
                                    if (visitObject.getString("payment_btn").equalsIgnoreCase("true")) {
                                        PaymentModel websiteModel = new PaymentModel(
                                                visitObject.getString("id"),
                                                visitObject.getString("payment_btn"),
                                                visitObject.getString("payment_btn_type"),
                                                visitObject.getString("payment_btn_name"),
                                                visitObject.getString("payment_btn_logo"),
                                                visitObject.getString("payment_btn_desc"),
                                                visitObject.getString("payment_btn_coins")
                                        );
                                        websiteModelArrayList.add(websiteModel);
                                    }
                                }
                                if (!websiteModelArrayList.isEmpty()) {
                                    Gson gson = new Gson();
                                    // getting data from gson and storing it in a string.
                                    String json = gson.toJson(websiteModelArrayList);
                                    Constant.setString(activity, Constant.PAYMENT_LIST, json);
                                }else {
                                    Constant.setString(activity, Constant.PAYMENT_LIST, "");
                                }
                            } else {
                                Constant.showToastMessage(activity, "No Settings Found In Admin Pannel");
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            Constant.showToastMessage(activity, "Something Went Wrong From Withdraw Settings");
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                            Constant.showToastMessage(activity, getResources().getString(R.string.slow_internet_connection));
                        }
                    }
                });
                customVolleyJsonRequest.setRetryPolicy(new DefaultRetryPolicy(
                        Constant.GET_SETTINGS_FROM_ADMIN_PANNEL_TIMEOUT,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                // Adding request to request queue
                App.getInstance().addToRequestQueue(customVolleyJsonRequest, tag_json_obj);

            } catch (Exception e) {
                Log.e("TAG", "Withdraw Settings: excption " + e.getMessage().toString());
            }
        } else {
            Constant.showInternetErrorDialog(activity, "Please Check your Internet Connection");
        }
    }


    private void getAppsSettingsFromAdminPannel() {
        if (Constant.isNetworkAvailable(activity)) {
            try {
                String tag_json_obj = "json_login_req";
                Map<String, String> map = new HashMap<>();
                map.put("get_apps_settings", "any");
                CustomVolleyJsonRequest customVolleyJsonRequest = new CustomVolleyJsonRequest(Request.Method.POST,
                        new String(Base64.decode(BuildConfig.ApiKey, Base64.DEFAULT)) + BuildConfig.AppsSettingKey, map, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            boolean status = response.getBoolean("status");
                            if (status) {
                                ArrayList<WebsiteModel> websiteModelArrayList = new ArrayList<>();
                                JSONArray jb = response.getJSONArray("data");
                                for (int i = 0; i < jb.length(); i++) {
                                    JSONObject visitObject = jb.getJSONObject(i);
                                    if (visitObject.getString("is_enable").equalsIgnoreCase("true")) {
                                        WebsiteModel websiteModel = new WebsiteModel(
                                                visitObject.getString("id"),
                                                visitObject.getString("is_enable"),
                                                visitObject.getString("title"),
                                                visitObject.getString("link"),
                                                visitObject.getString("coin"),
                                                visitObject.getString("timer"),
                                                null,
                                                visitObject.getString("_desc"),
                                                visitObject.getString("logo"),
                                                visitObject.getString("pkg")
                                        );
                                        websiteModelArrayList.add(websiteModel);
                                    }
                                }
                                if (!websiteModelArrayList.isEmpty()) {
                                    Gson gson = new Gson();
                                    // getting data from gson and storing it in a string.
                                    String json = gson.toJson(websiteModelArrayList);
                                    Constant.setString(activity, Constant.APPS_LIST, json);
                                } else {
                                    Constant.setString(activity, Constant.APPS_LIST, "");
                                }
                            } else {
                                Constant.showToastMessage(activity, "No Settings Found In Admin Pannel");
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            Constant.showToastMessage(activity, "Something Went Wrong From Apps Settings");
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                            Constant.showToastMessage(activity, getResources().getString(R.string.slow_internet_connection));
                        }
                    }
                });
                customVolleyJsonRequest.setRetryPolicy(new DefaultRetryPolicy(
                        Constant.GET_SETTINGS_FROM_ADMIN_PANNEL_TIMEOUT,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                // Adding request to request queue
                App.getInstance().addToRequestQueue(customVolleyJsonRequest, tag_json_obj);

            } catch (Exception e) {
                Log.e("TAG", "Withdraw Settings: excption " + e.getMessage().toString());
            }
        } else {
            Constant.showInternetErrorDialog(activity, "Please Check your Internet Connection");
        }
    }

    private void getVideoSettingsFromAdminPannel() {
        if (Constant.isNetworkAvailable(activity)) {
            try {
                String tag_json_obj = "json_login_req";
                Map<String, String> map = new HashMap<>();
                map.put("get_video_settings", "any");
                CustomVolleyJsonRequest customVolleyJsonRequest = new CustomVolleyJsonRequest(Request.Method.POST,
                        new String(Base64.decode(BuildConfig.ApiKey, Base64.DEFAULT)) + BuildConfig.VideoSettingKey, map, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            boolean status = response.getBoolean("status");
                            if (status) {
                                ArrayList<WebsiteModel> websiteModelArrayList = new ArrayList<>();
                                JSONArray jb = response.getJSONArray("data");
                                for (int i = 0; i < jb.length(); i++) {
                                    JSONObject visitObject = jb.getJSONObject(i);
                                    if (visitObject.getString("is_enable").equalsIgnoreCase("true")) {
                                        WebsiteModel websiteModel = new WebsiteModel(
                                                visitObject.getString("id"),
                                                visitObject.getString("is_enable"),
                                                visitObject.getString("title"),
                                                visitObject.getString("link"),
                                                visitObject.getString("coin"),
                                                visitObject.getString("timer"),
                                                visitObject.getString("browser"),
                                                null,
                                                null,
                                                null
                                        );
                                        websiteModelArrayList.add(websiteModel);
                                    }
                                }
                                if (!websiteModelArrayList.isEmpty()) {
                                    Gson gson = new Gson();
                                    // getting data from gson and storing it in a string.
                                    String json = gson.toJson(websiteModelArrayList);
                                    Constant.setString(activity, Constant.VIDEO_LIST, json);
                                }else {
                                    Constant.setString(activity, Constant.VIDEO_LIST, "");
                                }
                            } else {
                                Constant.showToastMessage(activity, "No Settings Found In Admin Pannel");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Constant.showToastMessage(activity, "Something Went Wrong From Apps Settings");
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                            Constant.showToastMessage(activity, getResources().getString(R.string.slow_internet_connection));
                        }
                    }
                });
                customVolleyJsonRequest.setRetryPolicy(new DefaultRetryPolicy(
                        Constant.GET_SETTINGS_FROM_ADMIN_PANNEL_TIMEOUT,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                // Adding request to request queue
                App.getInstance().addToRequestQueue(customVolleyJsonRequest, tag_json_obj);

            } catch (Exception e) {
                Log.e("TAG", "Withdraw Settings: excption " + e.getMessage().toString());
            }
        } else {
            Constant.showInternetErrorDialog(activity, "Please Check your Internet Connection");
        }
    }


    private void getVisitSettingsFromAdminPannel() {
        if (Constant.isNetworkAvailable(activity)) {
            try {
                String tag_json_obj = "json_login_req";
                Map<String, String> map = new HashMap<>();
                map.put("get_visit_settings", "any");
                CustomVolleyJsonRequest customVolleyJsonRequest = new CustomVolleyJsonRequest(Request.Method.POST,
                        new String(Base64.decode(BuildConfig.ApiKey, Base64.DEFAULT)) + BuildConfig.VisitSettingKey, map, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            boolean status = response.getBoolean("status");
                            if (status) {
                                ArrayList<WebsiteModel> websiteModelArrayList = new ArrayList<>();
                                JSONArray jb = response.getJSONArray("data");
                                for (int i = 0; i < jb.length(); i++) {
                                    JSONObject visitObject = jb.getJSONObject(i);
                                    if (visitObject.getString("is_visit_enable").equalsIgnoreCase("true")) {
                                        WebsiteModel websiteModel = new WebsiteModel(
                                                visitObject.getString("id"),
                                                visitObject.getString("is_visit_enable"),
                                                visitObject.getString("visit_title"),
                                                visitObject.getString("visit_link"),
                                                visitObject.getString("visit_coin"),
                                                visitObject.getString("visit_timer"),
                                                visitObject.getString("browser"),
                                                null,
                                                null,
                                                null
                                        );
                                        websiteModelArrayList.add(websiteModel);
                                    }
                                }
                                if (!websiteModelArrayList.isEmpty()) {
                                    Gson gson = new Gson();

                                    // getting data from gson and storing it in a string.
                                    String json = gson.toJson(websiteModelArrayList);
                                    Constant.setString(activity, Constant.WEBSITE_LIST, json);
                                }else {
                                    Constant.setString(activity, Constant.WEBSITE_LIST, "");
                                }
                            } else {
                                Constant.showToastMessage(activity, "No Settings Found In Admin Pannel");
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            Constant.showToastMessage(activity, "Something Went Wrong From Apps Settings");
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                            Constant.showToastMessage(activity, getResources().getString(R.string.slow_internet_connection));
                        }
                    }
                });
                customVolleyJsonRequest.setRetryPolicy(new DefaultRetryPolicy(
                        Constant.GET_SETTINGS_FROM_ADMIN_PANNEL_TIMEOUT,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                // Adding request to request queue
                App.getInstance().addToRequestQueue(customVolleyJsonRequest, tag_json_obj);

            } catch (Exception e) {
                Log.e("TAG", "Withdraw Settings: excption " + e.getMessage().toString());
            }
        } else {
            Constant.showInternetErrorDialog(activity, "Please Check your Internet Connection");
        }
    }


    private void gotoLoginActivity() {
        App.initAds();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (LOGIN) {
                    Constant.GotoNextActivity(activity, MainActivity.class, "");
                } else {
                    String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                    Constant.setString(activity, Constant.TODAY_DATE, currentDate);
                    Log.e("TAG", "onInit: else part of no login");
                    Constant.GotoNextActivity(activity, LoginActivity.class, "");
                }
                overridePendingTransition(R.anim.enter, R.anim.exit);
                finish();
            }
        }, 1000);
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

    private void vpnUseDialog() {
        SweetAlertDialog sweetAlertDialog;
        sweetAlertDialog = new SweetAlertDialog(activity, SweetAlertDialog.WARNING_TYPE);
        sweetAlertDialog.setCancelable(false);
        sweetAlertDialog.setCanceledOnTouchOutside(false);
        sweetAlertDialog.setTitle("VPN ALERT");
        sweetAlertDialog.setContentText(Constant.getString(activity, Constant.VPN_MSG));
        sweetAlertDialog.setConfirmText("Download");
        sweetAlertDialog.setCancelText("Cancel");
        sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Constant.getString(activity, Constant.VPN_LINK))));
                finish();
            }
        });
        sweetAlertDialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.dismiss();
                finish();
            }
        }).show();
    }

    //VPN Dialog 2
    private void vpnNotUsDialog() {
        SweetAlertDialog sweetAlertDialog;
        sweetAlertDialog = new SweetAlertDialog(activity, SweetAlertDialog.ERROR_TYPE);
        sweetAlertDialog.setCancelable(false);
        sweetAlertDialog.setCanceledOnTouchOutside(false);
        sweetAlertDialog.setTitle("VPN ALERT");
        sweetAlertDialog.setContentText("Please Disconnect the VPN and use this app.");
        sweetAlertDialog.setConfirmText("Ok");
        sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.dismiss();
                finish();
            }
        }).show();
    }

    public void getVpn() {
        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(activity);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, "http://ip-api.com/json", null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String country = response.getString("countryCode");
                            if (country.equalsIgnoreCase("US") || country.equalsIgnoreCase("GB") || country.equalsIgnoreCase("CA")) {
                                gotoLoginActivity();
                            } else {
                                vpnUseDialog();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Constant.showToastMessage(activity, "Something went wrong...");
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    public void UpdateApp() {
        try {
            Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();
            appUpdateInfoTask.addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>() {
                @Override
                public void onSuccess(AppUpdateInfo appUpdateInfo) {
                    if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                            && appUpdateInfo.isUpdateTypeAllowed(IMMEDIATE)) {
                        try {
                            appUpdateManager.startUpdateFlowForResult(
                                    appUpdateInfo, IMMEDIATE, activity, RC_APP_UPDATE);
                            Log.e("TAG", "onCreate:startUpdateFlowForResult part activarte ");
                        } catch (IntentSender.SendIntentException e) {
                            e.printStackTrace();
                        }
                    } else if (appUpdateInfo.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                        try {
                            appUpdateManager.startUpdateFlowForResult(
                                    appUpdateInfo, IMMEDIATE, activity, RC_APP_UPDATE);
                            Log.e("TAG", "onCreate:startUpdateFlowForResult part activarte ");
                        } catch (IntentSender.SendIntentException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Log.e("TAG", "onCreate:startUpdateFlowForResult else part activarte ");
                        activity.onInit();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(Exception e) {
                    e.printStackTrace();
                    Log.e("TAG", "onCreate:addOnFailureListener else part activarte ");
                    activity.onInit();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_APP_UPDATE) {
            if (resultCode == RESULT_OK) {
                onInit();
            } else if (resultCode == RESULT_CANCELED) {
                UpdateApp();
            }
        }
    }
}