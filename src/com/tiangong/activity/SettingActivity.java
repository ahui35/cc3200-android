
package com.tiangong.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.tiangong.R;
import com.tiangong.view.InPutDialog;

public class SettingActivity extends AppCompatActivity implements OnClickListener {
    private RelativeLayout relative_ip, relative_ddns, relative_update, relative_version;
    private ImageButton toolbar_back;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.setting_layout);
        initView();
        setListener();

    }

    private void initView() {
        toolbar_back = (ImageButton)findViewById(R.id.toolbar_back);
        relative_ip = (RelativeLayout) findViewById(R.id.relative_ip);
        relative_ddns = (RelativeLayout) findViewById(R.id.relative_ddns);
        relative_update = (RelativeLayout) findViewById(R.id.relative_update);
        relative_version = (RelativeLayout) findViewById(R.id.relative_version);
    }

    private void setListener() {
        toolbar_back.setOnClickListener(this);
        relative_ip.setOnClickListener(this);
        relative_ddns.setOnClickListener(this);
        relative_update.setOnClickListener(this);
        relative_version.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.relative_ip:
                InPutDialog config_ip = new InPutDialog(this, R.style.MyDialog, R.string.config_ip, 1);
                config_ip.show();
                break;
            case R.id.relative_ddns:
                InPutDialog config_ddns = new InPutDialog(this, R.style.MyDialog, R.string.config_ddns, 2);
                config_ddns.show();
                break;
            case R.id.relative_update:
                InPutDialog config_update_server = new InPutDialog(this, R.style.MyDialog, R.string.config_update_server, 3);
                config_update_server.show();
                break;
            case R.id.relative_version:
                break;

            default:
                break;
        }
    }
}
