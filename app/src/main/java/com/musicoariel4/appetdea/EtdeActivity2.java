package com.musicoariel4.appetdea;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class EtdeActivity2 extends AppCompatActivity {
    WebView web1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etde2);

        web1=(WebView)findViewById(R.id.webView1);

        Intent intent = getIntent();
        Uri locationUri = intent.getData();
        String url;
        url = locationUri.toString();
        web1.setWebViewClient(new WebViewClient());
        web1.loadUrl(url);
    }
}