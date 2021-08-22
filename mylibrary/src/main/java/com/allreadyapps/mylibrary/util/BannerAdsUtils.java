package com.allreadyapps.mylibrary.util;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.allreadyapps.mylibrary.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;


public class BannerAdsUtils {

    private static BannerAdsUtils sharedInstance;

    public static BannerAdsUtils getSharedInstance() {
        if (sharedInstance == null) {
            sharedInstance = new BannerAdsUtils();
        }
        return sharedInstance;
    }

    public void showBannerAdsLayout(final Context mContext, final int ID_LAYOUT) {
        try {

            MobileAds.initialize(mContext);
            final AdView mAdView = new AdView((Activity) mContext);
            mAdView.setAdSize(AdSize.BANNER);
            mAdView.setAdUnitId(mContext.getResources().getString(R.string.BANNER_ID));
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);
            mAdView.setAdListener(new AdListener() {
                @Override
                public void onAdFailedToLoad(LoadAdError loadAdError) {
                    super.onAdFailedToLoad(loadAdError);
                }

                @Override
                public void onAdLoaded() {
                    ((LinearLayout) ((Activity) mContext).findViewById(ID_LAYOUT))
                            .removeView(mAdView);
                    ((LinearLayout) ((Activity) mContext).findViewById(ID_LAYOUT))
                            .addView(mAdView);
                }
            });
        } catch (Exception e) {
            Log.e("catch in show banner", e.getMessage() + "a");
        }
    }
}
