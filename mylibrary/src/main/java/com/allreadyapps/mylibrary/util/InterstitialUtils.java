package com.allreadyapps.mylibrary.util;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.allreadyapps.mylibrary.R;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;


public class InterstitialUtils {
    private static InterstitialUtils sharedInstance;
    private InterstitialAd mInterstitialAd;
    private AdCloseListener adCloseListener;
    private boolean isReloaded = false;
    private String TAG = "InterstitialUtils";

    public static InterstitialUtils getSharedInstance() {
        if (sharedInstance == null) {
            sharedInstance = new InterstitialUtils();
        }
        return sharedInstance;
    }

    public void init(Context mContext) {
        try {
            MobileAds.initialize(mContext, new OnInitializationCompleteListener() {
                @Override
                public void onInitializationComplete(InitializationStatus initializationStatus) {
                }
            });

            mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                @Override
                public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                    super.onAdFailedToShowFullScreenContent(adError);
                }

                @Override
                public void onAdShowedFullScreenContent() {
                    mInterstitialAd = null;
                    Log.d("TAG", "The ad was shown.");
                }

                @Override
                public void onAdDismissedFullScreenContent() {
                    if (adCloseListener != null) {
                        adCloseListener.onAdClosed();
                    }
                    loadInterstitialAd(mContext);
                }

                @Override
                public void onAdImpression() {
                    super.onAdImpression();
                }
            });

        } catch (Exception e) {
            Log.e(TAG, "Catch in init Interstitial");
        }
        loadInterstitialAd(mContext);

    }

    private void loadInterstitialAd(Context mContext) {
        if (mInterstitialAd != null) {
            AdRequest adRequest = new AdRequest.Builder().build();
            InterstitialAd.load(mContext, mContext.getResources().getString(R.string.INTERSTITIAL_ID), adRequest,
                    new InterstitialAdLoadCallback() {
                        @Override
                        public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                            mInterstitialAd = interstitialAd;
                            Log.i(TAG, "onAdLoaded");
                        }

                        @Override
                        public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                            Log.i(TAG, loadAdError.getMessage());
                            mInterstitialAd = null;
                            if (!isReloaded) {
                                isReloaded = true;
                                loadInterstitialAd(mContext);
                            }
                        }
                    });
        }
    }

    public void showInterstitialAd(AdCloseListener adCloseListener, Activity mActivity, Context mContext) {
        if (mInterstitialAd != null) {
            isReloaded = false;
            this.adCloseListener = adCloseListener;
            mInterstitialAd.show(mActivity);
        } else {
            loadInterstitialAd(mContext);
            adCloseListener.onAdClosed();
        }

    }


    public interface AdCloseListener {
        public void onAdClosed();
    }

}
