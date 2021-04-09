package com.zip.androidcheckout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class CheckoutActivity extends AppCompatActivity {

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zip_checkout);

        Bundle args = getIntent().getExtras();
        String orderUrl = args.getString("orderUrl");
        String redirectSuccessUrl = args.getString("redirectSuccessUrl");
        String redirectFailureUrl = args.getString("redirectFailureUrl");


        WebView.setWebContentsDebuggingEnabled(true);
        WebView checkoutWebView = findViewById(R.id.webview);

        checkoutWebView.getSettings().setJavaScriptEnabled(true);
        checkoutWebView.getSettings().setAppCacheEnabled(false);
        checkoutWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);

        checkoutWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                String url = request.getUrl().toString();
                if(url.contains(redirectSuccessUrl)){ //Success
                    setResultStatusAndReturn("Success");
                }

                if(url.contains(redirectFailureUrl)){ //Failure
                    setResultStatusAndReturn("Failure");
                }

                Log.d("Redirect urls", request.getUrl().toString());
                return false;
            }
        });

        checkoutWebView.loadUrl(orderUrl);
    }

    private void setResultStatusAndReturn(String status) {
        Intent intent = new Intent();
        intent.putExtra("orderStatus", status);
        setResult(RESULT_OK, intent);
        finish();
    }
}