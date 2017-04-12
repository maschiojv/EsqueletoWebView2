package com.maschiotech.esqueletowebview2;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private WebView mWebView;
    private ProgressDialog progressDialog;
    private boolean isLoaded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        progressDialog = null;
        isLoaded = false;

        super.onCreate(savedInstanceState);

        if(!isOnline()) {

            Intent intent = new Intent(this, MensagemActivity.class);
            startActivity(intent);
            return;
        }

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_main);

        mWebView = (WebView) findViewById(R.id.activity_main_webview);

        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setSupportZoom(true);
        webSettings.setLoadWithOverviewMode(true);

        Resources resources = getResources();
        String urlSite = resources.getString(R.string.urlSite);

        mWebView.setWebViewClient(new MinhaWebView(this, urlSite));
        mWebView.loadUrl(urlSite);

        Timer myTimer = new Timer();
        LoaderTask loaderTask = new LoaderTask();
        myTimer.schedule(loaderTask, 30000);

        showLoading();
    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        boolean isOnline = netInfo != null && netInfo.isConnectedOrConnecting();
        return isOnline;
    }

    public void showLoading() {

        if (progressDialog == null) {

            progressDialog = new ProgressDialog(this);
            progressDialog.setTitle(R.string.carregaodo);
            Resources resources = getResources();
            String aguardeMsg = resources.getString(R.string.aguarde_msg);
            progressDialog.setMessage(aguardeMsg);
            progressDialog.setIndeterminate(true);
            progressDialog.show();
        }
    }

    public void closeLoading() {

        isLoaded = true;

        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    private class LoaderTask extends TimerTask {

        public void run() {

            if (!isLoaded) {

                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                    progressDialog = null;
                }

                Intent intent = new Intent(MainActivity.this, MensagemActivity.class);
                startActivity(intent);
            }
        }
    }
}
