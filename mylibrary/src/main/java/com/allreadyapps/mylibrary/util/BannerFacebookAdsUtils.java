package com.allreadyapps.mylibrary.util;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.LinearLayout;

import com.allreadyapps.mylibrary.R;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdListener;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.AudienceNetworkAds;


public class BannerFacebookAdsUtils {
    private static BannerFacebookAdsUtils sharedInstance;
    private String TAG = "BannerFacebookAdsUtils";

    private AdView adView;

    public static BannerFacebookAdsUtils getSharedInstance() {
        if (sharedInstance == null) {
            sharedInstance = new BannerFacebookAdsUtils();
        }
        return sharedInstance;
    }

    public void adViewDestroy(){
        if (adView != null) {
            adView.destroy();
        }
    }

    public void showFBBannerAds(final Context mContext, final int bannerLayout) {
        try {
            AudienceNetworkAds.initialize(mContext);
             adView = new AdView(mContext, mContext.getResources().getString(R.string.FB_BANNER_ID), AdSize.BANNER_HEIGHT_50);
            final LinearLayout adContainer = (LinearLayout) ((Activity) mContext).findViewById(bannerLayout);
            AdListener adListener = new AdListener() {
                @Override
                public void onError(Ad ad, AdError adError) {
                    Log.e("Error: ", adError.getErrorMessage());
                }

                @Override
                public void onAdLoaded(Ad ad) {
                    adContainer.removeView(adView);
                    adContainer.addView(adView);
                }

                @Override
                public void onAdClicked(Ad ad) {

                }

                @Override
                public void onLoggingImpression(Ad ad) {

                }
            };
            adView.loadAd(adView.buildLoadAdConfig().withAdListener(adListener).build());


        } catch (Exception e) {
            Log.e(TAG, e.getMessage() + " A");
        }
    }
}
