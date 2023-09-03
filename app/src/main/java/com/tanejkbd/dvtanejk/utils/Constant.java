package com.tanejkbd.dvtanejk.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.tanejkbd.dvtanejk.R;
import com.tanejkbd.dvtanejk.services.PointsService;
import com.tanejkbd.dvtanejk.services.UpdateDateService;
import com.tanejkbd.dvtanejk.sharedPref.PrefManager;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.regex.Pattern;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Constant extends Activity {
    public static final String CHECK_VALID_PKG = "check_valid_pkg";
    public static final String GET_COUNTRY = "get_country";
    public static final String IS_LOGIN = "IsLogin";
    public static final String USER_BLOCKED = "user_blocked";
    public static final String LANGUAGE = "language";
    public static final String USER_NAME = "user_name";
    public static final String USER_NUMBER = "user_number";
    public static final String USER_EMAIL = "user_email";
    public static final String USER_DEVICE = "user_device";
    public static final String USER_POINTS = "user_points";
    public static final String IS_UPDATE = "user_points";
    public static final String USER_REFFER_CODE = "user_reffer_code";
    public static final String LAST_DATE = "Last_Date";
    public static final String LAST_DATE_WATCH = "last_date_watch";
    public static final String LAST_DATE_INVALID = "last_date_invalid";
    public static final String INVALID_CLICK_COUNT = "invalid_click_count";
    public static final String LAST_TIME_ADD_TO_SERVER = "last_time_added";
    public static final String REFER_CODE = "refer_code";
    public static final String SCRATCH_COUNT = "scratch_count";
    public static final String CAPTCHA_COUNT = "captcha_count";
    public static final String QUIZ_COUNT = "quiz_count";
    public static final String GAME_COUNT = "game_count";
    public static final String LAST_DATE_SCRATCH = "last_date_scratch";
    public static final String LAST_DATE_CAPTCHA = "last_date_captcha";
    public static final String LAST_DATE_QUIZ = "last_date_quiz";
    public static final String LAST_DATE_GAME = "last_date_game";
    public static final String SPIN_COUNT = "spin_count";
    public static final String LAST_DATE_SPIN = "last_date_spin";
    public static final String USER_ID = "user_id";
    public static final String USER_PASSWORD = "password";
    public static final String TODAY_DATE = "today_date";
    public static final String REFER_TEXT = "refer_text_from_admin";
    public static final String SPIN_PRICE_COIN = "spin_price_coins";
    public static final String SCRATCH_PRICE_COIN = "scratch_price_coins";
    public static final String CAPTCHA_PRICE_COIN = "captcha_price_coins";
    public static final String SIGNUP_BOUNUS = "singup_bounus_points";
    public static final String SIGNUP_BOUNUS_DATE = "singup_bounus_date";
    public static final String DAILY_CAPTCHA_COUNT = "daily_captcha_count";
    public static final String DAILY_QUIZ_COUNT = "daily_quiz_count";
    public static final String DAILY_GAME_COUNT = "daily_game_count";
    public static final String GAME_PRICE_COINS = "game_price_coins";
    public static final String GAME_PLAY_TIME = "game_play_time";
    public static final String GAME_WARNING_MSG = "game_warning_msg";
    public static final String REDIRECT_TO_ACTIVITY = "redirect_to_activity";
    public static final String STARTAPP_APP_ID = "start_app_app_id";
    public static final String UNITY_GAME_ID = "unity_game_id";
    public static final String PROMOTION_TEXT = "promotion_text";
    public static final String PROMOTION_LINK = "promotion_link";
    public static final String SPIN_POINT_1 = "spin_point_1";
    public static final String SPIN_POINT_2 = "spin_point_2";
    public static final String SPIN_POINT_3 = "spin_point_3";
    public static final String SPIN_POINT_4 = "spin_point_4";
    public static final String SPIN_POINT_5 = "spin_point_5";
    public static final String SPIN_POINT_6 = "spin_point_6";
    public static final String SPIN_POINT_7 = "spin_point_7";
    public static final String SPIN_POINT_8 = "spin_point_8";
    public static final String SPIN_POINT_9 = "spin_point_9";
    public static final String SPIN_POINT_10 = "spin_point_10";
    public static final String IS_P1_ENABLED = "is_p1_enable";
    public static final String P1_IMAGE = "p1_image";
    public static final String P1_LINK = "p1_link";
    public static final String IS_INAPP_OPEN1 = "is_inapp_open1";
    public static final String IS_P2_ENABLED = "is_p2_enable";
    public static final String P2_IMAGE = "p2_image";
    public static final String P2_LINK = "p2_link";
    public static final String IS_INAPP_OPEN2 = "is_inapp_open2";
    public static final String IS_P3_ENABLED = "is_p3_enable";
    public static final String P3_IMAGE = "p3_image";
    public static final String P3_LINK = "p3_link";
    public static final String IS_INAPP_OPEN3 = "is_inapp_open3";
    public static final String IS_P4_ENABLED = "is_p4_enable";
    public static final String P4_IMAGE = "p4_image";
    public static final String P4_LINK = "p4_link";
    public static final String IS_INAPP_OPEN4 = "is_inapp_open4";
    public static final String P_BANNER_HEIGHT = "p_banner_height";
    public static final String PG_BANNER_HEIGHT = "pg_banner_height";
    public static final String HOME_GAME_BANNER = "home_game_banner";
    public static final String QUIZ_PLAY_TIME = "quiz_play_time";
    public static final String PER_QUIZ_COIN = "per_quiz_coin";
    public static final String QUIZ_QUESTION_LIMIT = "quiz_question_limit";

    // Games
    public static final String IS_PG1_ENABLED = "is_pg1_enable";
    public static final String PG1_IMAGE = "pg1_image";
    public static final String PG1_LINK = "pg1_link";
    public static final String PG1_OPEN_WITH = "pg1_open_with";
    public static final String IS_PG2_ENABLED = "is_pg2_enable";
    public static final String PG2_IMAGE = "pg2_image";
    public static final String PG2_LINK = "pg2_link";
    public static final String PG2_OPEN_WITH = "pg2_open_with";
    public static final String IS_PG3_ENABLED = "is_pg3_enable";
    public static final String PG3_IMAGE = "pg3_image";
    public static final String PG3_LINK = "pg3_link";
    public static final String PG3_OPEN_WITH = "pg3_open_with";
    public static final String IS_PG4_ENABLED = "is_pg4_enable";
    public static final String PG4_IMAGE = "pg4_image";
    public static final String PG4_LINK = "pg4_link";
    public static final String PG4_OPEN_WITH = "pg4_open_with";
    public static final String IS_G1_ENABLE = "is_g1_enable";
    public static final String G1_IMAGE = "g1_image";
    public static final String G1_TITLE = "g1_title";
    public static final String G1_LINK = "g1_link";
    public static final String IS_G2_ENABLE = "is_g2_enable";
    public static final String G2_IMAGE = "g2_image";
    public static final String G2_TITLE = "g2_title";
    public static final String G2_LINK = "g2_link";
    public static final String IS_G3_ENABLE = "is_g3_enable";
    public static final String G3_IMAGE = "g3_image";
    public static final String G3_TITLE = "g3_title";
    public static final String G3_LINK = "g3_link";
    public static final String IS_G4_ENABLE = "is_g4_enable";
    public static final String G4_IMAGE = "g4_image";
    public static final String G4_TITLE = "g4_title";
    public static final String G4_LINK = "g4_link";
    public static final String IS_G5_ENABLE = "is_g5_enable";
    public static final String G5_IMAGE = "g5_image";
    public static final String G5_TITLE = "g5_title";
    public static final String G5_LINK = "g5_link";
    public static final String IS_G6_ENABLE = "is_g6_enable";
    public static final String G6_IMAGE = "g6_image";
    public static final String G6_TITLE = "g6_title";
    public static final String G6_LINK = "g6_link";
    public static final String IS_G7_ENABLE = "is_g7_enable";
    public static final String G7_IMAGE = "g7_image";
    public static final String G7_TITLE = "g7_title";
    public static final String G7_LINK = "g7_link";
    public static final String IS_G8_ENABLE = "is_g8_enable";
    public static final String G8_IMAGE = "g8_image";
    public static final String G8_TITLE = "g8_title";
    public static final String G8_LINK = "g8_link";
    public static final String IS_G9_ENABLE = "is_g9_enable";
    public static final String G9_IMAGE = "g9_image";
    public static final String G9_TITLE = "g9_title";
    public static final String G9_LINK = "g9_link";
    public static final String IS_G10_ENABLE = "is_g10_enable";
    public static final String G10_IMAGE = "g10_image";
    public static final String G10_TITLE = "g10_title";
    public static final String G10_LINK = "g10_link";
    public static final String IS_G11_ENABLE = "is_g11_enable";
    public static final String G11_IMAGE = "g11_image";
    public static final String G11_TITLE = "g11_title";
    public static final String G11_LINK = "g11_link";
    public static final String IS_G12_ENABLE = "is_g12_enable";
    public static final String G12_IMAGE = "g12_image";
    public static final String G12_TITLE = "g12_title";
    public static final String G12_LINK = "g12_link";
    public static final String IS_G13_ENABLE = "is_g13_enable";
    public static final String G13_IMAGE = "g13_image";
    public static final String G13_TITLE = "g13_title";
    public static final String G13_LINK = "g13_link";
    public static final String IS_G14_ENABLE = "is_g14_enable";
    public static final String G14_IMAGE = "g14_image";
    public static final String G14_TITLE = "g14_title";
    public static final String G14_LINK = "g14_link";
    public static final String IS_G15_ENABLE = "is_g15_enable";
    public static final String G15_IMAGE = "g15_image";
    public static final String G15_TITLE = "g15_title";
    public static final String G15_LINK = "g15_link";
    public static final String IS_G16_ENABLE = "is_g16_enable";
    public static final String G16_IMAGE = "g16_image";
    public static final String G16_TITLE = "g16_title";
    public static final String G16_LINK = "g16_link";
    public static final String IS_G17_ENABLE = "is_g17_enable";
    public static final String G17_IMAGE = "g17_image";
    public static final String G17_TITLE = "g17_title";
    public static final String G17_LINK = "g17_link";
    public static final String IS_G18_ENABLE = "is_g18_enable";
    public static final String G18_IMAGE = "g18_image";
    public static final String G18_TITLE = "g18_title";
    public static final String G18_LINK = "g18_link";
    public static final String IS_G19_ENABLE = "is_g19_enable";
    public static final String G19_IMAGE = "g19_image";
    public static final String G19_TITLE = "g19_title";
    public static final String G19_LINK = "g19_link";
    public static final String IS_G20_ENABLE = "is_g20_enable";
    public static final String G20_IMAGE = "g20_image";
    public static final String G20_TITLE = "g20_title";
    public static final String G20_LINK = "g20_link";


    //Apps Settings
    public static final String USE_IN_ROOTED_DEVICE = "use_in_rooted_device";
    public static final String IS_USE_MULTIPLE_ACCOUNT = "is_use_multiple_account";
    public static final String IS_A1_ENABLE = "is_a1_enable";
    public static final String IS_A2_ENABLE = "is_a2_enable";
    public static final String IS_A3_ENABLE = "is_a3_enable";
    public static final String IS_A4_ENABLE = "is_a4_enable";
    public static final String IS_A5_ENABLE = "is_a5_enable";
    public static final String IS_A6_ENABLE = "is_a6_enable";
    public static final String IS_A7_ENABLE = "is_a7_enable";
    public static final String IS_A8_ENABLE = "is_a8_enable";
    public static final String IS_A9_ENABLE = "is_a9_enable";
    public static final String IS_A10_ENABLE = "is_a10_enable";

    public static final String A1_LOGO = "a1_logo";
    public static final String A2_LOGO = "a2_logo";
    public static final String A3_LOGO = "a3_logo";
    public static final String A4_LOGO = "a4_logo";
    public static final String A5_LOGO = "a5_logo";
    public static final String A6_LOGO = "a6_logo";
    public static final String A7_LOGO = "a7_logo";
    public static final String A8_LOGO = "a8_logo";
    public static final String A9_LOGO = "a9_logo";
    public static final String A10_LOGO = "a10_logo";

    public static final String A1_TITLE = "a1_title";
    public static final String A2_TITLE = "a2_title";
    public static final String A3_TITLE = "a3_title";
    public static final String A4_TITLE = "a4_title";
    public static final String A5_TITLE = "a5_title";
    public static final String A6_TITLE = "a6_title";
    public static final String A7_TITLE = "a7_title";
    public static final String A8_TITLE = "a8_title";
    public static final String A9_TITLE = "a9_title";
    public static final String A10_TITLE = "a10_title";

    public static final String A1_DESC = "a1_desc";
    public static final String A2_DESC = "a2_desc";
    public static final String A3_DESC = "a3_desc";
    public static final String A4_DESC = "a4_desc";
    public static final String A5_DESC = "a5_desc";
    public static final String A6_DESC = "a6_desc";
    public static final String A7_DESC = "a7_desc";
    public static final String A8_DESC = "a8_desc";
    public static final String A9_DESC = "a8_desc";
    public static final String A10_DESC = "a10_desc";

    public static final String A1_LINK = "a1_link";
    public static final String A2_LINK = "a2_link";
    public static final String A3_LINK = "a3_link";
    public static final String A4_LINK = "a4_link";
    public static final String A5_LINK = "a5_link";
    public static final String A6_LINK = "a6_link";
    public static final String A7_LINK = "a7_link";
    public static final String A8_LINK = "a8_link";
    public static final String A9_LINK = "a8_link";
    public static final String A10_LINK = "a10_link";

    public static final String A1_PKG = "a1_pkg";
    public static final String A2_PKG = "a2_pkg";
    public static final String A3_PKG = "a3_pkg";
    public static final String A4_PKG = "a4_pkg";
    public static final String A5_PKG = "a5_pkg";
    public static final String A6_PKG = "a6_pkg";
    public static final String A7_PKG = "a7_pkg";
    public static final String A8_PKG = "a8_pkg";
    public static final String A9_PKG = "a8_pkg";
    public static final String A10_PKG = "a10_pkg";

    public static final String A1_COIN = "a1_coin";
    public static final String A2_COIN = "a2_coin";
    public static final String A3_COIN = "a3_coin";
    public static final String A4_COIN = "a4_coin";
    public static final String A5_COIN = "a5_coin";
    public static final String A6_COIN = "a6_coin";
    public static final String A7_COIN = "a7_coin";
    public static final String A8_COIN = "a8_coin";
    public static final String A9_COIN = "a8_coin";
    public static final String A10_COIN = "a10_coin";

    public static final String A1_TIMER = "a1_timer";
    public static final String A2_TIMER = "a2_timer";
    public static final String A3_TIMER = "a3_timer";
    public static final String A4_TIMER = "a4_timer";
    public static final String A5_TIMER = "a5_timer";
    public static final String A6_TIMER = "a6_timer";
    public static final String A7_TIMER = "a7_timer";
    public static final String A8_TIMER = "a8_timer";
    public static final String A9_TIMER = "a9_timer";
    public static final String A10_TIMER = "a10_timer";


    //Video Settings
    public static final String IS_V1_ENABLE = "is_v1_enable";
    public static final String IS_V2_ENABLE = "is_v2_enable";
    public static final String IS_V3_ENABLE = "is_v3_enable";
    public static final String IS_V4_ENABLE = "is_v4_enable";
    public static final String IS_V5_ENABLE = "is_v5_enable";
    public static final String IS_V6_ENABLE = "is_v6_enable";
    public static final String IS_V7_ENABLE = "is_v7_enable";
    public static final String IS_V8_ENABLE = "is_v8_enable";
    public static final String IS_V9_ENABLE = "is_v9_enable";
    public static final String IS_V10_ENABLE = "is_v10_enable";

    public static final String V1_TITLE = "v1_title";
    public static final String V2_TITLE = "v2_title";
    public static final String V3_TITLE = "v3_title";
    public static final String V4_TITLE = "v4_title";
    public static final String V5_TITLE = "v5_title";
    public static final String V6_TITLE = "v6_title";
    public static final String V7_TITLE = "v7_title";
    public static final String V8_TITLE = "v8_title";
    public static final String V9_TITLE = "v9_title";
    public static final String V10_TITLE = "v10_title";


    public static final String V1_LINK = "v1_link";
    public static final String V2_LINK = "v2_link";
    public static final String V3_LINK = "v3_link";
    public static final String V4_LINK = "v4_link";
    public static final String V5_LINK = "v5_link";
    public static final String V6_LINK = "v6_link";
    public static final String V7_LINK = "v7_link";
    public static final String V8_LINK = "v8_link";
    public static final String V9_LINK = "v8_link";
    public static final String V10_LINK = "v10_link";


    public static final String V1_COIN = "v1_coin";
    public static final String V2_COIN = "v2_coin";
    public static final String V3_COIN = "v3_coin";
    public static final String V4_COIN = "v4_coin";
    public static final String V5_COIN = "v5_coin";
    public static final String V6_COIN = "v6_coin";
    public static final String V7_COIN = "v7_coin";
    public static final String V8_COIN = "v8_coin";
    public static final String V9_COIN = "v8_coin";
    public static final String V10_COIN = "v10_coin";

    public static final String V1_TIMER = "v1_timer";
    public static final String V2_TIMER = "v2_timer";
    public static final String V3_TIMER = "v3_timer";
    public static final String V4_TIMER = "v4_timer";
    public static final String V5_TIMER = "v5_timer";
    public static final String V6_TIMER = "v6_timer";
    public static final String V7_TIMER = "v7_timer";
    public static final String V8_TIMER = "v8_timer";
    public static final String V9_TIMER = "v9_timer";
    public static final String V10_TIMER = "v10_timer";

    public static final String WATCHED1_DATE = "watched1_date";
    public static final String WATCHED2_DATE = "watched2_date";
    public static final String WATCHED3_DATE = "watched3_date";
    public static final String WATCHED4_DATE = "watched4_date";
    public static final String WATCHED5_DATE = "watched5_date";
    public static final String WATCHED6_DATE = "watched6_date";
    public static final String WATCHED7_DATE = "watched7_date";
    public static final String WATCHED8_DATE = "watched8_date";
    public static final String WATCHED9_DATE = "watched9_date";
    public static final String WATCHED10_DATE = "watched10_date";

    //Visit Settings
    public static final String IS_VISIT1_ENABLE = "is_visit1_enable";
    public static final String IS_VISIT2_ENABLE = "is_visit2_enable";
    public static final String IS_VISIT3_ENABLE = "is_visit3_enable";
    public static final String IS_VISIT4_ENABLE = "is_visit4_enable";
    public static final String IS_VISIT5_ENABLE = "is_visit5_enable";
    public static final String IS_VISIT6_ENABLE = "is_visit6_enable";
    public static final String IS_VISIT7_ENABLE = "is_visit7_enable";
    public static final String IS_VISIT8_ENABLE = "is_visit8_enable";
    public static final String IS_VISIT9_ENABLE = "is_visit9_enable";
    public static final String IS_VISIT10_ENABLE = "is_visit10_enable";

    public static final String VISIT1_TITLE = "visit1_title";
    public static final String VISIT2_TITLE = "visit2_title";
    public static final String VISIT3_TITLE = "visit3_title";
    public static final String VISIT4_TITLE = "visit4_title";
    public static final String VISIT5_TITLE = "visit5_title";
    public static final String VISIT6_TITLE = "visit6_title";
    public static final String VISIT7_TITLE = "visit7_title";
    public static final String VISIT8_TITLE = "visit8_title";
    public static final String VISIT9_TITLE = "visit9_title";
    public static final String VISIT10_TITLE = "visit10_title";


    public static final String VISIT1_LINK = "visit1_link";
    public static final String VISIT2_LINK = "visit2_link";
    public static final String VISIT3_LINK = "visit3_link";
    public static final String VISIT4_LINK = "visit4_link";
    public static final String VISIT5_LINK = "visit5_link";
    public static final String VISIT6_LINK = "visit6_link";
    public static final String VISIT7_LINK = "visit7_link";
    public static final String VISIT8_LINK = "visit8_link";
    public static final String VISIT9_LINK = "visit8_link";
    public static final String VISIT10_LINK = "visit10_link";


    public static final String VISIT1_COIN = "visit1_coin";
    public static final String VISIT2_COIN = "visit2_coin";
    public static final String VISIT3_COIN = "visit3_coin";
    public static final String VISIT4_COIN = "visit4_coin";
    public static final String VISIT5_COIN = "visit5_coin";
    public static final String VISIT6_COIN = "visit6_coin";
    public static final String VISIT7_COIN = "visit7_coin";
    public static final String VISIT8_COIN = "visit8_coin";
    public static final String VISIT9_COIN = "visit8_coin";
    public static final String VISIT10_COIN = "visit10_coin";

    public static final String VISIT1_TIMER = "visit1_timer";
    public static final String VISIT2_TIMER = "visit2_timer";
    public static final String VISIT3_TIMER = "visit3_timer";
    public static final String VISIT4_TIMER = "visit4_timer";
    public static final String VISIT5_TIMER = "visit5_timer";
    public static final String VISIT6_TIMER = "visit6_timer";
    public static final String VISIT7_TIMER = "visit7_timer";
    public static final String VISIT8_TIMER = "visit8_timer";
    public static final String VISIT9_TIMER = "visit9_timer";
    public static final String VISIT10_TIMER = "visit10_timer";

    public static final String VISITED1_DATE = "visited1_date";
    public static final String VISITED2_DATE = "visited2_date";
    public static final String VISITED3_DATE = "visited3_date";
    public static final String VISITED4_DATE = "visited4_date";
    public static final String VISITED5_DATE = "visited5_date";
    public static final String VISITED6_DATE = "visited6_date";
    public static final String VISITED7_DATE = "visited7_date";
    public static final String VISITED8_DATE = "visited8_date";
    public static final String VISITED9_DATE = "visited9_date";
    public static final String VISITED10_DATE = "visited10_date";


    // Redeem Settings
    public static final String PAYMENT_BTN_1 = "payment_btn_1";
    public static final String PAYMENT_BTN_1_TYPE = "payment_btn_1_type";
    public static final String PAYMENT_BTN_1_NAME = "payment_btn_1_name";
    public static final String PAYMENT_BTN_1_LOGO = "payment_btn_1_logo";
    public static final String PAYMENT_BTN_1_DESC = "payment_btn_1_desc";
    public static final String PAYMENT_BTN_1_COINS = "payment_btn_1_coins";

    public static final String PAYMENT_BTN_2 = "payment_btn_2";
    public static final String PAYMENT_BTN_2_TYPE = "payment_btn_2_type";
    public static final String PAYMENT_BTN_2_NAME = "payment_btn_2_name";
    public static final String PAYMENT_BTN_2_LOGO = "payment_btn_2_logo";
    public static final String PAYMENT_BTN_2_DESC = "payment_btn_2_desc";
    public static final String PAYMENT_BTN_2_COINS = "payment_btn_2_coins";

    public static final String PAYMENT_BTN_3 = "payment_btn_3";
    public static final String PAYMENT_BTN_3_TYPE = "payment_btn_3_type";
    public static final String PAYMENT_BTN_3_NAME = "payment_btn_3_name";
    public static final String PAYMENT_BTN_3_LOGO = "payment_btn_3_logo";
    public static final String PAYMENT_BTN_3_DESC = "payment_btn_3_desc";
    public static final String PAYMENT_BTN_3_COINS = "payment_btn_3_coins";

    public static final String PAYMENT_BTN_4 = "payment_btn_4";
    public static final String PAYMENT_BTN_4_TYPE = "payment_btn_4_type";
    public static final String PAYMENT_BTN_4_NAME = "payment_btn_4_name";
    public static final String PAYMENT_BTN_4_LOGO = "payment_btn_4_logo";
    public static final String PAYMENT_BTN_4_DESC = "payment_btn_4_desc";
    public static final String PAYMENT_BTN_4_COINS = "payment_btn_4_coins";

    public static final String PAYMENT_BTN_5 = "payment_btn_5";
    public static final String PAYMENT_BTN_5_TYPE = "payment_btn_5_type";
    public static final String PAYMENT_BTN_5_NAME = "payment_btn_5_name";
    public static final String PAYMENT_BTN_5_LOGO = "payment_btn_5_logo";
    public static final String PAYMENT_BTN_5_DESC = "payment_btn_5_desc";
    public static final String PAYMENT_BTN_5_COINS = "payment_btn_5_coins";

    public static final String PAYMENT_BTN_6 = "payment_btn_6";
    public static final String PAYMENT_BTN_6_TYPE = "payment_btn_6_type";
    public static final String PAYMENT_BTN_6_NAME = "payment_btn_6_name";
    public static final String PAYMENT_BTN_6_LOGO = "payment_btn_6_logo";
    public static final String PAYMENT_BTN_6_DESC = "payment_btn_6_desc";
    public static final String PAYMENT_BTN_6_COINS = "payment_btn_6_coins";

    public static final String PAYMENT_BTN_7 = "payment_btn_7";
    public static final String PAYMENT_BTN_7_TYPE = "payment_btn_7_type";
    public static final String PAYMENT_BTN_7_NAME = "payment_btn_7_name";
    public static final String PAYMENT_BTN_7_LOGO = "payment_btn_7_logo";
    public static final String PAYMENT_BTN_7_DESC = "payment_btn_7_desc";
    public static final String PAYMENT_BTN_7_COINS = "payment_btn_7_coins";
    public static final String WEBSITE_LIST = "website_list";
    public static final String VIDEO_LIST = "video_list";
    public static final String APP_DATE = "app_last_date";
    public static final String APPS_LIST = "apps_list";
    public static final int GET_SETTINGS_FROM_ADMIN_PANNEL_TIMEOUT = 1000 * 30; // it means 30 seconds u can increase it if have low internet connection
    public static final String PAYMENT_LIST = "payment_list";
    public static final String GAME_LIST = "game_list";


    private static PrefManager prefManager;


    //Vpn
    public static final String VPN_LOGO = "vpn_logo";
    public static final String VPN_MSG = "vpn_msg";
    public static final String VPN_LINK = "vpn_link";
    public static final String IS_P_UPDATE_ENABLED = "is_p_update_enabled";

    public static final String GAME_HOME_BANNER_HIDE = "game_home_banner_hide";
    public static final String GAME_HOME_BANNER_GAME_ACTIVITY = "game_home_banner_game_activity";
    public static final String GAME_HOME_BANNER_LINK = "game_home_banner_link";

    // these are get from admin pannel
    public static final String AD_TYPE = "ads_type";
    public static final String DAILY_SCRATCH_COUNT = "daily_scratch_count";
    public static final String DAILY_SPIN_COUNT = "daily_spin_count";
    public static final String ADS_BEETWEEN = "ads_beetween";
    public static final String DAILY_CHECK_IN_POINTS = "daily_check_in_points";
    public static final String ADS_CLICK_COINS = "ads_click_coins";
    public static final String ADS_CLICK_AFTER_X_CLICK = "ads_click_after_x_click";
    public static final String ADS_CLICK_TIME = "ads_click_time";
    public static final String DAILY_WATCH_POINTS = "daily_watch_points";
    public static final String MINIMUM_REDEEM_POINTS = "minimum_redeem_amount";
    public static final String COIN_TO_RUPEE = "COIN_TO_RUPEE";
    public static final String APPLOVIN_BANNER_ID = "applovin_banner_id";
    public static final String APPLOVIN_INTERSTITAL_ID = "applovin_interstital_id";
    public static final String APPLOVIN_REWARDED_ID = "applovin_rewarded_id";
    public static final String UNITY_REWARDED_ID = "unity_rewarded_id";
    public static final String UNITY_INTERSTITAL_ID = "unity_interstital_id";
    public static final String UNITY_BANNER_ID = "unity_banner_id";

    public static final String STARTAPP_BANNER_ID = "startapp_banner_id";
    public static final String STARTAPP_INTERSTITAL_ID = "startapp_interstital_id";
    public static final String VPN_ENABLE = "is_vpn_enable";
    public static final String IS_PROMOTION_DIALOG_ENABLE = "is_promotion_dialog_enable";
    public static final String SHARE_TEXT = "share_text";


    public static final String TAG = "Constant";
    public static String GAME_ID;
    public static boolean TEST_MODE;
    public static MediaPlayer spinSound, scratchSound;
    public static Dialog adsLoadingDialog;
    public static int adsDialogTime = 3000;
    public static int invalidClickCount = 0;
    public static int ONE_THOUSAND_MILISECOND = 1000;
    public static int VISIT_REQUEST_CODE = 100;


    public static void GotoNextActivity(Context context, Class nextActivity, String msg) {
        if (context != null && nextActivity != null) {
            if (msg == null) {
                msg = "";
            }
            Intent intent = new Intent(context, nextActivity);
            intent.putExtra("Intent", msg);
            context.startActivity(intent);
        }
    }

    public static boolean isValidEmailAddress(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        boolean isMatches = pattern.matcher(email).matches();
        Log.e("Boolean Value", "" + isMatches);
        return isMatches;
    }


    public static void saveInvalidCounter(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("saveInvaliClick", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("invalidClickValue", Constant.invalidClickCount);
        editor.apply();
    }

    public static void loadInvalidCounter(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("saveInvaliClick", MODE_PRIVATE);
        Constant.invalidClickCount = sharedPreferences.getInt("invalidClickValue", MODE_PRIVATE);
    }

    public static void deleteInvalidCounter(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("saveInvaliClick", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (sharedPreferences.contains("invalidClickValue")) {
            editor.remove("invalidClickValue");
            Constant.invalidClickCount = 0;
        }
    }


    public static void showToastMessage(Context context, String message) {
        if (context != null && message != null) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        }
    }

    public static void adsShowingDialog(Context context) {
        adsLoadingDialog = new Dialog(context);
        adsLoadingDialog.setContentView(R.layout.ads_loading_dialog);
        adsLoadingDialog.setCancelable(false);
        if (adsLoadingDialog.getWindow() != null) {
            adsLoadingDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
    }

    public static void showAdsLoadingDialog() {
        adsLoadingDialog.show();
    }

    public static void dismissAdsLoadingDialog() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                adsLoadingDialog.dismiss();
            }
        }, Constant.adsDialogTime);
    }

    public static void loadingTimer(Dialog dialog) {

    }


    public static void setString(Context context, String preKey, String setString) {
        if (prefManager == null) {
            prefManager = new PrefManager(context);
        }
        prefManager.setString(preKey, setString);
    }

    public static String getString(Context context, String prefKey) {
        if (prefManager == null) {
            prefManager = new PrefManager(context);
        }
        return prefManager.getString(prefKey);
    }


    public static void hideKeyboard(Activity activity) {
        if (activity == null) {
            return;
        }
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager inm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static void ratingApp(Context context) {
        if (context != null) {
            Uri uri = Uri.parse("market://details?id=" + context.getPackageName());
            Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
            try {
                context.startActivity(goToMarket);
            } catch (ActivityNotFoundException e) {
                context.startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://play.google.com/store/apps/details?id=" + context.getPackageName())));
            }
        }
    }

    public static void addPoints(Context context, int points, int type, String scratchType, String number) {
        if (context != null) {
            String po = Constant.getString(context, Constant.USER_POINTS);
            if (po.equals("")) {
                po = "0";
            }
            if (type == 1) {
                Constant.setString(context, Constant.USER_POINTS, String.valueOf(po));
                Intent serviceIntent = new Intent(context, PointsService.class);
                serviceIntent.putExtra("points", Constant.getString(context, Constant.USER_POINTS));
                serviceIntent.putExtra("type", scratchType);
                serviceIntent.putExtra("number", number);
                context.startService(serviceIntent);
            } else {
                int current_Points = Integer.parseInt(po);
                String total_points = String.valueOf(current_Points + points);
                Constant.setString(context, Constant.USER_POINTS, total_points);
                Intent serviceIntent = new Intent(context, PointsService.class);
                serviceIntent.putExtra("points", Constant.getString(context, Constant.USER_POINTS));
                serviceIntent.putExtra("type", scratchType);
                serviceIntent.putExtra("number", number);
                context.startService(serviceIntent);
            }
        }
    }

    public static void addDate(Context context, String type, String date, String number) {
        try {
            Intent serviceIntent = new Intent(context, UpdateDateService.class);
            serviceIntent.putExtra("type", type);
            serviceIntent.putExtra("date", date);
            serviceIntent.putExtra("number", number);
            context.startService(serviceIntent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static boolean isOnline(Context context) {
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();

            StrictMode.setThreadPolicy(policy);

            int timeoutMs = 2000;
            Socket sock = new Socket();
            SocketAddress sockaddr = new InetSocketAddress("8.8.8.8", 53);

            sock.connect(sockaddr, timeoutMs);
            sock.close();
            Log.i("CONNECTION STATUS:", "connected");

            return true;
        } catch (IOException ioException) {
            Log.i("CONNECTION STATUS:", "disconnected");
            return false;
        }
    }

    public static void showInternetErrorDialog(Activity activity, String msg) {
        new SweetAlertDialog(activity, SweetAlertDialog.ERROR_TYPE)
                .setTitleText("No Internet!")
                .setContentText(msg)
                .setConfirmText("Ok")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        activity.finishAffinity();
                    }
                })
                .show();

    }


    public static void showBlockedDialog(final Context context, Activity activity, String msg) {
        SweetAlertDialog sweetAlertDialog;
        sweetAlertDialog = new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE);
        sweetAlertDialog.setCancelable(false);
        sweetAlertDialog.setCanceledOnTouchOutside(false);
        sweetAlertDialog.setTitleText("Blocked");
        sweetAlertDialog.setContentText(msg);
        sweetAlertDialog.setConfirmText("Ok");
        sweetAlertDialog.showCancelButton(false);


        sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.dismiss();
                activity.finishAffinity();
            }
        }).show();
    }

    public static void referApp(Context context, String refer_code) {
        try {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, Constant.getString(context, Constant.SHARE_TEXT) + "' " + refer_code + " '" + " Download Link = " + " https://play.google.com/store/apps/details?id=" + context.getPackageName());
            sendIntent.setType("text/plain");
            context.startActivity(sendIntent);
        } catch (Exception e) {
            Log.e("TAG", "referApp: " + e.getMessage().toString());
        }
    }


    public static void blockedHackingApps(Context context, Activity activity) {
        boolean isApkEditorInstalled = appInstalledOrNot(context, "com.gmail.heagoo.apkeditor");
        boolean isApkEditorProInstalled = appInstalledOrNot(context, "com.gmail.heagoo.apkeditor.pro");
        boolean isApkParserInstalled = appInstalledOrNot(context, "com.gmail.heagoo.apkeditor.parser");
        boolean isMagiSkInstalled = appInstalledOrNot(context, "com.topjohnwu.magisk");
        boolean isDIdChangerInstalled = appInstalledOrNot(context, "com.silverlab.app.deviceidchanger.free");
        boolean isLuckyPatcherInstalled = appInstalledOrNot(context, "ru.aaaaaace.installer");
        boolean isLpInstallerInstalled = appInstalledOrNot(context, "ru.qzfpzneh.bjcscnynp");


        if (isApkEditorInstalled) {
            appsCheckerDialog(context, activity, "You can't use this app because we detected Apk editor on your phone.");
            Log.i("checkInstalled", "Apk Editor is already installed.");
        } else if (isApkEditorProInstalled) {
            appsCheckerDialog(context, activity, "You can't use this app because we detected Apk editor pro on your phone.");
            Log.i("checkInstalled", "Apk Editor Pro is already installed.");
        } else if (isApkParserInstalled) {
            appsCheckerDialog(context, activity, "You can't use this app because we detected Apk parser on your phone.");
            Log.i("checkInstalled", "Apk Parser is already installed.");
        } else if (isMagiSkInstalled) {
            appsCheckerDialog(context, activity, "You can't use this app because your device is rooted.");
            Log.i("checkInstalled", "MagiSk is already installed.");
        } else if (isDIdChangerInstalled) {
            appsCheckerDialog(context, activity, "You can't use this app because we detected Device ID Changer on your phone.");
            Log.i("checkInstalled", "Device Id Changer is already installed.");
        } else if (isLuckyPatcherInstalled) {
            appsCheckerDialog(context, activity, "You can't use this app because we detected Lucky Patcher on your phone.");
            Log.i("checkInstalled", "Lucky Patcher is already installed.");
        } else if (isLpInstallerInstalled) {
            appsCheckerDialog(context, activity, "You can't use this app because we detected LP Installer on your phone.");
            Log.i("checkInstalled", "LP Installer is already installed.");
        } else {
            Log.i("checkInstalled", "Application is not currently installed.");
        }
    }

    public static boolean appInstalledOrNot(Context context, String packageName) {
        PackageManager pm = context.getPackageManager();
        try {
            pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
        }

        return false;
    }



    public static void appsCheckerDialog(final Context context, Activity activity, String msg) {
        SweetAlertDialog sweetAlertDialog;
        sweetAlertDialog = new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE);
        sweetAlertDialog.setCancelable(false);
        sweetAlertDialog.setCanceledOnTouchOutside(false);
        sweetAlertDialog.setTitleText("Warning!");
        sweetAlertDialog.setContentText(msg);
        sweetAlertDialog.setConfirmText("Ok");
        sweetAlertDialog.showCancelButton(false);


        sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.dismiss();
                activity.finishAffinity();
            }
        }).show();
    }

}
