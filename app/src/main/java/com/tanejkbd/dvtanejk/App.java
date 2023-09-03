package com.tanejkbd.dvtanejk;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.ProcessLifecycleOwner;
import androidx.multidex.MultiDex;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.applovin.sdk.AppLovinSdk;
import com.applovin.sdk.AppLovinSdkConfiguration;
import com.facebook.ads.AdSettings;
import com.onesignal.OneSignal;
import com.tanejkbd.dvtanejk.utils.Constant;
import com.startapp.sdk.adsbase.StartAppAd;
import com.startapp.sdk.adsbase.StartAppSDK;
import com.unity3d.ads.UnityAds;

import java.util.Arrays;


public class App extends Application implements LifecycleObserver {
    private static App mInstance;
    public static final String TAG = App.class.getSimpleName();
    private static final String ONESIGNAL_APP_ID = "09dc6265-515d-4055-96dc-d4264c33c3e8";

    private RequestQueue mRequestQueue;


    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        ProcessLifecycleOwner.get().getLifecycle().addObserver(mInstance);

        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);



        // OneSignal Initialization
        OneSignal.initWithContext(this);
        OneSignal.setAppId(ONESIGNAL_APP_ID);
    }



    public static void initAds() {
        String adType = Constant.getString(getInstance(), Constant.AD_TYPE);
        if (adType.equalsIgnoreCase("startapp")) {
            StartAppSDK.setUserConsent(getInstance(),
                    "pas",
                    System.currentTimeMillis(),
                    false);
            StartAppSDK.init(mInstance, Constant.getString(getInstance(),Constant.STARTAPP_APP_ID), false);
            StartAppAd.disableSplash();
        } else if (adType.equalsIgnoreCase("unity")){
            String GAME_ID = Constant.getString(getInstance(),Constant.UNITY_GAME_ID);
            boolean TEST_MODE = mInstance.getResources().getBoolean(R.bool.test_mode);
            UnityAds.initialize(mInstance, GAME_ID, TEST_MODE);
        } else if (adType.equalsIgnoreCase("applovin")){
            AdSettings.setDataProcessingOptions( new String[] {} );
            AppLovinSdk.getInstance(mInstance).setMediationProvider( "max" );
            AppLovinSdk.initializeSdk(mInstance, new AppLovinSdk.SdkInitializationListener() {
                @Override
                public void onSdkInitialized(final AppLovinSdkConfiguration configuration)
                {
                    if (getInstance().getResources().getBoolean(R.bool.test_mode)){
                        AppLovinSdk.getInstance(mInstance).getSettings()
                                .setTestDeviceAdvertisingIds(Arrays.asList(getInstance().getResources().getString(R.string.test_device_id)));
                    }
                }
            } );
        }
    }


    public static App getContext() {
        if (mInstance == null) {
            mInstance = new App();
        }
        return mInstance;
    }

    @Override
    public void registerActivityLifecycleCallbacks(ActivityLifecycleCallbacks callback) {
        super.registerActivityLifecycleCallbacks(callback);
    }

    public static synchronized App getInstance() {
        if (mInstance == null) {
            mInstance = new App();
        }
        return mInstance;
    }


    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

}
