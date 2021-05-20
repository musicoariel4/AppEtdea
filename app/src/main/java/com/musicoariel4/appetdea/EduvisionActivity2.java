package com.musicoariel4.appetdea;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class EduvisionActivity2 extends AppCompatActivity {
    WebView web2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eduvision2);

        web2=(WebView)findViewById(R.id.webView2);

        Intent intent2 = getIntent();
        Uri locationUri = intent2.getData();
        String url;
        url = locationUri.toString();
        web2.setWebViewClient(new WebViewClient());
        web2.loadUrl(url);
    }
}