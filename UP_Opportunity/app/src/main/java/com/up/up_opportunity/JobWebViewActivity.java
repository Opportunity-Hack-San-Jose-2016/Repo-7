package com.up.up_opportunity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class JobWebViewActivity extends AppCompatActivity {

    private static final String TAG = JobWebViewActivity.class.getSimpleName();
    WebView webView;
    CustomWebViewClient customWebViewClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_web_view);

        Intent intent = getIntent();
        String link = intent.getStringExtra("link");

        Log.d(TAG, "WebView link: " + link);

        webView = (WebView) findViewById(R.id.job_webView);
        customWebViewClient = new CustomWebViewClient();
        webView.setWebViewClient(customWebViewClient);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(link);
        customWebViewClient.onLoadResource(webView, link);
    }

    private class CustomWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return super.shouldOverrideUrlLoading(view, url); //try return false;
        }

        @Override
        public void onLoadResource(WebView view, String url) {
            super.onLoadResource(view, url);
            Log.i("laborLaws", "onLoadResource: web page is loading");
        }
    }
}
