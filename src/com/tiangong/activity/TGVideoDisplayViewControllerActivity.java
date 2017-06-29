package com.tiangong.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.TextView;

import com.tiangong.R;
import com.tiangong.bean.TGEquip;
import com.tiangong.bean.TGUserCenter;

public class TGVideoDisplayViewControllerActivity extends AppCompatActivity implements OnClickListener {

    private WebView ads_wv;
    private TGEquip eq;
    private String ddns;
    private String account;
    private String password;
    private String equipName;
    private ImageButton toolbar_back;
    private TextView toolbar_title;
    private Intent intent;
    private String uri;
    private int port;

    @Override
    protected void onCreate(@Nullable
    Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_control_layout);
        initView();
        initData();
        setListener();
        load();
    }
    private void initView() {
        ads_wv = (WebView)findViewById(R.id.ads_wv);
        toolbar_back = (ImageButton)findViewById(R.id.toolbar_back);
        toolbar_title = (TextView)findViewById(R.id.toolbar_title);
    }

    private void setListener() {
        toolbar_back.setOnClickListener(this);
    }
    private void initData() {
        intent = getIntent();
        port = intent.getIntExtra("port", 8080);
        ddns = intent.getStringExtra("ddns");
        account = intent.getStringExtra("account");
        password = intent.getStringExtra("password");
        equipName = intent.getStringExtra("equipName");
        toolbar_title.setText(equipName);

    }

    
    private void load() {
        if (TGUserCenter.getDefaultUserCenter(this).getTgnetchange()) {
            uri = "http://" + ddns + "\\:" + port;
        } else {
            uri = "http://" + TGUserCenter.getDefaultUserCenter(this).getIp() + "\\:" + port;
        }
        ads_wv.loadUrl(ddns);
        WebChromeClient wvcc = new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }

        };
        ads_wv.setWebChromeClient(wvcc);
        WebViewClient wvc = new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                ads_wv.loadUrl(url);

                return true;
            }

        };
        ads_wv.setWebViewClient(wvc);
    }


    @Override
    public void onClick(View v) {
        finish();
        
    }
}
