
package com.tiangong.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.tiangong.R;
import com.tiangong.bean.TGEquip;
import com.tiangong.bean.TGUserCenter;
import com.tiangong.datatransport.BusinessHandler.DataResponseCallBack;
import com.tiangong.datatransport.UDPClient;
import com.tiangong.datatransport.UDPClient;
import com.tiangong.db.TGDBUtils;
import com.tiangong.utils.TGPreference;
import com.tiangong.view.SwitchButton;

import io.netty.channel.ChannelHandlerContext;

import org.apache.mina.core.buffer.IoBuffer;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity implements DataResponseCallBack{

    private EditText ed_username, ed_passwd;
    private SwitchButton sbt_choose_net;
    private ImageButton btn_setting;
    private Button btn_login;
    private Editor editor;
    private SharedPreferences preferenc;
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    Toast.makeText(LoginActivity.this, R.string.login_fail, Toast.LENGTH_SHORT).show();
                    break;

                default:
                    break;
            }
        }
        
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        initView();
    }

    
    @Override
    protected void onResume() {
        super.onResume();
        TGDBUtils.getDB();
    }

    private void initView() {
        // name & passwd
        ed_username = (EditText) findViewById(R.id.input_username);
        ed_passwd = (EditText) findViewById(R.id.input_password);

        // net
        sbt_choose_net = (SwitchButton) findViewById(R.id.sbt_net_choose);

        // setting
        btn_setting = (ImageButton) findViewById(R.id.tg_setting);

        // login
        btn_login = (Button) findViewById(R.id.btn_login);
        sbt_choose_net.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                TGUserCenter.getDefaultUserCenter(LoginActivity.this).setTgnetchange(isChecked);
            }
        });
       preferenc = TGPreference.getPreferenc(this);
       editor =  preferenc.edit();
       ed_username.setText(preferenc.getString("username", ""));
       ed_passwd.setText(preferenc.getString("passwd", ""));
    }

    public void setting(View view) {
        Intent setting = new Intent();
        setting.setClass(LoginActivity.this, SettingActivity.class);
        startActivity(setting);
    }

    public void login(View view) {
        // save username and passwd
        editor.putString("username", ed_username.getText().toString().trim());
        editor.putString("passwd", ed_passwd.getText().toString().trim());
        editor.commit();

        if (TextUtils.isEmpty(ed_username.getText().toString())
                || TextUtils.isEmpty(ed_passwd.getText().toString())) {
            Toast.makeText(this, R.string.input_username_passwd, Toast.LENGTH_LONG).show();
            return;
        }

        TGUserCenter us = TGUserCenter.getDefaultUserCenter(this);
        String ip = us.getIp();
        String ddns = us.getDdns();
        if (TextUtils.isEmpty(ip) || TextUtils.isEmpty(ddns)) {
            Toast.makeText(this, R.string.config_ip, Toast.LENGTH_SHORT).show();
            return;
        }

        if (sbt_choose_net.isChecked()) {
            UDPClient.getUDPInstance(this).setHost(us.getDdns()); //外网
        } else {
            UDPClient.getUDPInstance(this).setHost(us.getIp()); // 内网
        }

        if (TGUserCenter.DemoUserName.equals(ed_username.getText().toString())
                && TGUserCenter.DemoPassWord.equals(ed_passwd.getText().toString())) {
            loginSuccess();
            return;
        }

        UDPClient.getUDPInstance(this).getHandler().setCallBack(this, null, null);
        UDPClient.getUDPInstance(this).loginWithNameAndPasswd(ed_username.getText().toString(), ed_passwd.getText().toString());

    }
    private void loginSuccess() {
        Intent roomintent = new Intent();
        roomintent.setClass(LoginActivity.this, RoomViewActivity.class);
        startActivity(roomintent);
        return;

    }

    @Override
    public void responseData(String message, View view, TGEquip equip) {
        if (null == message) {
            return;
        }
        String response = message;
        try {
            JSONObject resJson = new JSONObject(response.trim());
            if (resJson.getString("status").equals("sussess")) {
                loginSuccess();
            } else {
                mHandler.sendEmptyMessage(1);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
