package com.allreadyapps.mylibrary.util;

import android.content.Context;
import android.util.Log;

import com.allreadyapps.mylibrary.R;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;


public class InterstitialFacebookAdsUtils {

    private static InterstitialFacebookAdsUtils sharedInstance;
    private InterstitialAd interstitialAd;
    private AdCloseListener adCloseListener;
    private boolean isReloaded = false;

    public static InterstitialFacebookAdsUtils getSharedInstance() {
        if (sharedInstance == null) {
            sharedInstance = new InterstitialFacebookAdsUtils();
        }
        return sharedInstance;
    }

    public void interstitialAdDestroy(){
        if (interstitialAd != null) {
            interstitialAd.destroy();
        }
    }

    public void init(Context mContext) {
        try {
            AudienceNetworkAds.initialize(mContext);
            interstitialAd = new InterstitialAd(mContext, mContext.getResources().getString(R.string.FB_INTERSTITIALS_ID));
            interstitialAd.setAdListener(new InterstitialAdListener() {
                @Override
                public void onInterstitialDisplayed(Ad ad) {

                }

                @Override
                public void onInterstitialDismissed(Ad ad) {
                    if (adCloseListener != null) {
                        adCloseListener.onAdClosed();
                    }
                    loadInterstitialAd();
                }

                @Override
                public void onError(Ad ad, AdError adError) {
                    if (!isReloaded) {
                        isReloaded = true;
                        loadInterstitialAd();
                    }
                }

                @Override
                public void onAdLoaded(Ad ad) {

                }

                @Override
                public void onAdClicked(Ad ad) {

                }

                @Override
                public void onLoggingImpression(Ad ad) {

                }
            });

            loadInterstitialAd();

        } catch (Exception ex) {
            Log.e("Catch in init FB Inters", ex.getMessage() + " a");
        }

    }

    public void showInterstitialAd(AdCloseListener adCloseListener) {
        if (canShowInterstitialAd()) {
            isReloaded = false;
            this.adCloseListener = adCloseListener;
            interstitialAd.show();
        } else {
            loadInterstitialAd();
            adCloseListener.onAdClosed();
        }

    }

    private void loadInterstitialAd() {
        if (interstitialAd != null && !interstitialAd.isAdLoaded()) {
            interstitialAd.loadAd();
        }
    }

    private boolean canShowInterstitialAd() {
        return interstitialAd != null && interstitialAd.isAdLoaded();
    }


    public interface AdCloseListener {
        public void onAdClosed();


    }

}
