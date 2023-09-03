package com.tanejkbd.dvtanejk.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tanejkbd.dvtanejk.R;
import com.tanejkbd.dvtanejk.adapter.WebsiteAdapter;
import com.tanejkbd.dvtanejk.listener.UpdateListener;
import com.tanejkbd.dvtanejk.models.WebsiteModel;
import com.tanejkbd.dvtanejk.utils.Constant;
import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxAdViewAdListener;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.ads.MaxAdView;
import com.applovin.sdk.AppLovinSdkUtils;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.startapp.sdk.ads.banner.Banner;
import com.startapp.sdk.ads.banner.BannerListener;
import com.unity3d.ads.IUnityAdsInitializationListener;
import com.unity3d.ads.UnityAds;
import com.unity3d.services.banners.BannerErrorInfo;
import com.unity3d.services.banners.BannerView;
import com.unity3d.services.banners.UnityBannerSize;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class AppsActivity extends AppCompatActivity implements IUnityAdsInitializationListener,
        MaxAdViewAdListener,
        UpdateListener {
    AppsActivity activity;
    private BannerView bottomBanner;
    private MaxAdView adView;
    String adType;
    TextView user_points_text_view, noWebsiteFound;
    private LinearLayout image_container;
    private int visit_index = 0;
    private WebsiteAdapter websiteAdapter;
    private ArrayList<WebsiteModel> websiteModelArrayList = new ArrayList<>();
    private ArrayList<WebsiteModel> websiteModels = new ArrayList<>();
    private RecyclerView websiteRv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apps);
        activity = this;
        user_points_text_view = findViewById(R.id.user_points_text_view);
        image_container = findViewById(R.id.image_container);
        adType = Constant.getString(activity, Constant.AD_TYPE);
        noWebsiteFound = findViewById(R.id.noWebsiteFound);
        websiteRv = findViewById(R.id.websiteRv);
        websiteRv.setLayoutManager(new LinearLayoutManager(activity));
        String json = Constant.getString(activity, Constant.APPS_LIST);
        if (!json.equalsIgnoreCase("")) {
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<WebsiteModel>>() {
            }.getType();
            websiteModels = gson.fromJson(json, type);
            websiteAdapter = new WebsiteAdapter(websiteModelArrayList, activity, "app", activity);
            websiteRv.setAdapter(websiteAdapter);
        }
        setWebsiteData();
        image_container.setVisibility(View.GONE);
        if (adType.equalsIgnoreCase("startapp")) {
            LoadStartAppBanner();
        } else if (adType.equalsIgnoreCase("unity")) {
            loadUnityBanner();
        } else if (adType.equalsIgnoreCase("applovin")) {
            loadApplovinBanner();
        } else {
            image_container.setVisibility(View.VISIBLE);
        }
    }

    private void setWebsiteData() {
        websiteModelArrayList.clear();
        for (int i = 0; i < websiteModels.size(); i++) {
            if (!Constant.getString(activity, Constant.APP_DATE + websiteModels.get(i).getId()).equalsIgnoreCase(Constant.getString(activity, Constant.TODAY_DATE))) {
                if (!isAppInstalled(activity, websiteModels.get(i).getPackages())) {
                    websiteModelArrayList.add(websiteModels.get(i));
                }
            }
        }
        if (websiteModelArrayList.isEmpty()) {
            websiteRv.setVisibility(View.GONE);
            noWebsiteFound.setVisibility(View.VISIBLE);
        } else {
            websiteRv.setVisibility(View.VISIBLE);
            noWebsiteFound.setVisibility(View.GONE);
        }
        websiteAdapter.notifyDataSetChanged();
    }


    @Override
    protected void onResume() {
        user_points_text_view.setText(Constant.getString(activity, Constant.USER_POINTS));
        setWebsiteData();
        super.onResume();
    }


    public static boolean isAppInstalled(Context context, String packageName) {
        try {
            context.getPackageManager().getApplicationInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
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

    @Override
    public void onInitializationComplete() {

    }

    @Override
    public void onInitializationFailed(UnityAds.UnityAdsInitializationError unityAdsInitializationError, String s) {

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
    public void UpdateListener(@Nullable String coin, @Nullable String time, @Nullable String link, @Nullable String browser, @Nullable String id, int index, @Nullable String description, @Nullable String logo, @Nullable String packages) {
        Intent intent = new Intent(activity, InstallActivity.class);
        intent.putExtra("logo", logo);
        intent.putExtra("name", websiteModelArrayList.get(index).getVisit_title());
        intent.putExtra("desc", description);
        intent.putExtra("link", link);
        intent.putExtra("pkg", packages);
        intent.putExtra("coin", coin);
        intent.putExtra("timer", time);
        intent.putExtra("isEqual", index);
        startActivity(intent);
    }
}