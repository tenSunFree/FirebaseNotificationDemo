package com.example.administrator.firebasenotificationdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Main2Activity extends AppCompatActivity {

    private WebView webView;
    private String webViewUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        /** 取得從CustomService 傳遞過來的Url, 然後賦予WebView */
        webViewUrl = getIntent().getStringExtra("webViewUrl");
        initializeWebView();

        /** 結束CustomService */
        Intent serviceIntent = new Intent(this, CustomService.class);
        stopService(serviceIntent);
    }

    /** 初始化WebView */
    private void initializeWebView() {
        webView = findViewById(R.id.webView);
        webView.loadUrl(webViewUrl);
        webView.getSettings().setJavaScriptEnabled(true);                                           // 支持App内部JavaScript交互
        webView.getSettings().setLayoutAlgorithm(                                                   // 自适应手机屏幕
                WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webView.getSettings().setUseWideViewPort(true);                                             // 设置webview推荐使用的窗口
        webView.getSettings().setLoadWithOverviewMode(true);                                        // 设置webview加载的页面的模式
        webView.getSettings().setBuiltInZoomControls(false);                                        // 设置是否出现缩放工具
        webView.getSettings().setSupportZoom(true);                                                 // 设置可以支持缩放
        webView.getSettings().setUseWideViewPort(true);                                             // 扩大比例的缩放
        webView.getSettings().setDefaultTextEncodingName("utf-8");                                  // 设置编码为utf-8
        webView.setWebViewClient(new WebViewClient());                                              // 不調用系統瀏覽器
    }
}
