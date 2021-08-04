package com.allreadyapps.mylibrary.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.allreadyapps.mylibrary.R;


public abstract class AllreadyAppsActivity extends AppCompatActivity {
    private static final int TIME_DELAY = 2000;
    private static long back_pressed;

    protected abstract int getLayoutID();

    //protected abstract int getStatusBarID();

    protected abstract void settingView();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(getLayoutID());
        settingView();
//        ads = new ExitAds(this);
    }

    protected int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height",
                "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    protected void setStatusBarColor(View statusBar, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            int statusBarHeight = getStatusBarHeight();
            statusBar.getLayoutParams().height = statusBarHeight;
            statusBar.setBackgroundColor(color);
        }
    }

    protected boolean isPackageInstalled(String packageName, Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            return true;

        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    protected void exitAppNoInternet() {

        if (back_pressed + TIME_DELAY > System.currentTimeMillis()) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            System.exit(0);
        } else {
            Toast.makeText(this, R.string.back, Toast.LENGTH_SHORT).show();
        }
        back_pressed = System.currentTimeMillis();

    }

    protected void makeToast(String strToast) {
        Toast.makeText(this, strToast, Toast.LENGTH_SHORT).show();
    }


    // Exit App
    protected void exitApp(Context mContext) {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
        System.exit(0);
    }


//    protected void exitDialog() {
//
//        ads.showExitDialog();
//    }


    /**
     * @param text        Mô tả cho link
     * @param packageName packageName của ứng dụng muốn chia sẻ
     */
    protected void shareLink(String text, String packageName) {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "");
        emailIntent.putExtra(Intent.EXTRA_TEXT, text + "\n\n"
                + "https://play.google.com/store/apps/details?id=" + packageName);
        startActivity(emailIntent);
        Log.v("Link", packageName + "");
    }

    /**
     * @param packageName packageName của ứng dụng muốn người dùng đánh giá
     */
    protected void rateApp(String packageName) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("market://details?id=" + packageName));
        Log.v("packageName", packageName);
        startActivity(intent);
    }

    protected void openApps(Context mContext, String packageName) {
        Log.d("onClick Item", "click Quang Cao");
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("market://details?id=" + packageName));
        Log.v("namePakage", packageName);
        mContext.startActivity(intent);
    }


    protected void openLinks(Context mContext, String link) {
        Log.d("onClick Item", "click Quang Cao");
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(link));
        Log.v("Link open", link);
        mContext.startActivity(intent);
    }

}
