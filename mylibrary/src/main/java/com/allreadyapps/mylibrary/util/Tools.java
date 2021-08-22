package com.allreadyapps.mylibrary.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.interstitial.InterstitialAd;

/**
 * Created by quang on 25/09/2015.
 */
public class Tools {
    private static InterstitialAd interstitialAd;

    public static void admobBannerLayout(final Context mContext,
                                         final int idLayout, String idAdmobBanner) {
        String id = "";
        try {
            id = idAdmobBanner;
            final AdView mAdView = new AdView((Activity) mContext);
            mAdView.setAdSize(AdSize.SMART_BANNER);
            mAdView.setAdUnitId(id);
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);
            mAdView.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {
                    ((LinearLayout) ((Activity) mContext).findViewById(idLayout))
                            .removeView(mAdView);
                    ((LinearLayout) ((Activity) mContext).findViewById(idLayout))
                            .addView(mAdView);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static boolean isConnectingToInternet(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


    // Set font
    public static void setFontsTextView(Activity mContext, TextView mTextView,
                                        String fontName) {
        try {
            Typeface type = Typeface.createFromAsset(mContext.getAssets(), "fonts/"
                    + fontName);
            mTextView.setTypeface(type);
        } catch (Exception e) {
            Log.e("Lỗi setFontsTextView", e.getMessage());
        }

    }

    public static void setFontsButton(Activity mContext, Button mButton,
                                      String fontName) {
        try {
            Typeface type = Typeface.createFromAsset(mContext.getAssets(), "fonts"
                    + fontName);
            mButton.setTypeface(type);
        } catch (Exception e) {
            Log.e("Lỗi setFontsButton", e.getMessage());
        }

    }


}
