package com.maschiotech.esqueletowebview2;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.Nullable;
import android.webkit.MimeTypeMap;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.InputStream;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Felipe on 16/07/2016.
 */
public class MinhaWebView extends WebViewClient {

    private MainActivity activity;
    private final String urlSite;

    public MinhaWebView(MainActivity activity, String urlSite) {

        this.activity = activity;
        this.urlSite = urlSite;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {

        Dummy.log("shouldOverrideUrlLoading -> " + url);

        Uri parse1 = Uri.parse(url);
        String host1 = parse1.getHost();

        Uri parse2 = Uri.parse(urlSite);
        String host2 = parse2.getHost();

        if (host1.equals(host2)) {
            // Este é o site do app, então não faz override; carrega o site com o webview
            return false;
        }

        // Não é o site do app, deixa o android se virar com a URL
        Intent intent = new Intent(Intent.ACTION_VIEW, parse1);
        activity.startActivity(intent);
        return true;
    }

    @Override
    public void onLoadResource(WebView view, String url) {

        Dummy.log("onLoadResource " + url);

        super.onLoadResource(view, url);
    }

    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, String url) {

        WebResourceResponse wrr = shouldInterceptRequest(url);
        if (wrr != null) return wrr;

        return super.shouldInterceptRequest(view, url);
    }

    @Override
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {

        String url = request.getUrl().toString();

        WebResourceResponse wrr = shouldInterceptRequest(url);
        if (wrr != null) return wrr;

        return super.shouldInterceptRequest(view, request);
    }

    @Nullable
    private WebResourceResponse shouldInterceptRequest(String url) {

        Dummy.log("shouldInterceptRequest -> " + url);

        String extenssao = MimeTypeMap.getFileExtensionFromUrl(url);
        if(extenssao.equals("jpg") || extenssao.equals("png")) {

            int idx1 = url.lastIndexOf("/");
            int idx2 = url.lastIndexOf(".");
            String imageName = url.substring(idx1 + 1, idx2);

            Context context = activity.getApplicationContext();
            String packageName = context.getPackageName();

            Resources resources = activity.getResources();
            int resourceId = resources.getIdentifier(imageName, "raw", packageName);

            if(resourceId != 0) {

                InputStream is = resources.openRawResource(resourceId);
                return new WebResourceResponse("", "UTF-8", is);
            }
        }

        return null;
    }

    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {

        Intent intent = new Intent(activity, MensagemActivity.class);
        activity.startActivity(intent);
        return;
    }

    @Override
    public void onPageFinished(WebView view, String url) {

        activity.closeLoading();
    }
}
