package com.tiangong.bean;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.tiangong.utils.TGPreference;

public class TGUserCenter {

    public static final String DemoUserName = "damon";
    public static final String DemoPassWord = "123456";
    public static final String IPKEY = "TGSettingIP";
    public static final String IP1KEY = "TGSettingIP1";
    public static final String DDNSKEY = "TGSettingDDNS";
    public static final String TGUSERNAME = "TGUserName";
    public static final String TGPASSWD = "TGPassword";
    public static final String UPDATEKEY = "TGUpdateIP";
    public static final String TGLAUNCHER = "launchered";
    public static final String TGNETCHANGE = "net_check";
    private boolean isLogined;
    private String ip;
    private String ip1; //外网ip
    private String ddns;
    private String server;
    private String userName;
    private String password;
    private boolean isLauncher;
    private boolean outerNet;
    private static TGUserCenter mInstance;
    private static Context mContext;
    private SharedPreferences preferenc;
    
    public static TGUserCenter getDefaultUserCenter(Context tx) {
        if (mInstance == null) {
            mContext = tx;
            mInstance = new TGUserCenter();
        }
        return mInstance;
    }

    private  TGUserCenter() {
        preferenc = TGPreference.getPreferenc(mContext);
        ip1 = preferenc.getString(IP1KEY, "");
        ip = preferenc.getString(IPKEY, "");
        server = preferenc.getString(UPDATEKEY, "");
        ddns = preferenc.getString(DDNSKEY, "");
        userName = preferenc.getString(TGUSERNAME, "");
        password = preferenc.getString(TGPASSWD, "");
        isLauncher = preferenc.getBoolean(TGLAUNCHER, false);
        outerNet = preferenc.getBoolean(TGNETCHANGE, false);
    }
    public boolean isLogined() {
        return isLogined;
    }
    public void setLogined(boolean isLogined) {
        this.isLogined = isLogined;
    }
    public String getIp() {
        return ip;
    }
    public void setIp(String ip) {
        this.ip = ip;
        if (this.ip.length() > 0) {
            Editor edit = preferenc.edit();
            edit.putString(IPKEY, this.ip);
            edit.commit();
        }
    }
    public String getIp1() {
        return ip1;
    }
    public void setIp1(String ip1) {
        this.ip1 = ip1;
        if (this.ip1.length() > 0) {
            Editor edit = preferenc.edit();
            edit.putString(IP1KEY, this.ip1);
            edit.commit();
        }
    }
    public String getDdns() {
        return ddns;
    }
    public void setDdns(String ddns) {
        this.ddns = ddns;
        if (this.ddns.length() > 0) {
            Editor edit = preferenc.edit();
            edit.putString(DDNSKEY, this.ddns);
            edit.commit();
        }
    }


    public void setTgnetchange(boolean checked) {
        this.outerNet = checked;
        Editor edit = preferenc.edit();
        edit.putBoolean(TGNETCHANGE, this.outerNet);
        edit.commit();
    }

    public boolean getTgnetchange() {
        return outerNet;
    }

    public void setServer(String server) {
        this.server = server;
        if (this.server.length() > 0) {
            Editor edit = preferenc.edit();
            edit.putString(UPDATEKEY, this.server);
            edit.commit();
        }
    }
    
    public String getServer() {
        return server;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
        if (this.userName.length() > 0) {
            Editor edit = preferenc.edit();
            edit.putString(TGUSERNAME, this.userName);
            edit.commit();
        }
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
        if (this.password.length() > 0) {
            Editor edit = preferenc.edit();
            edit.putString(TGPASSWD, this.password);
            edit.commit();
        }
    }
    public static String getDemousername() {
        return DemoUserName;
    }
    public static String getDemopassword() {
        return DemoPassWord;
    }

    public boolean isLauncher() {
        return isLauncher;
    }

    public void setLauncher(boolean isLauncher) {
        this.isLauncher = isLauncher;
        Editor edit = preferenc.edit();
        edit.putBoolean(TGLAUNCHER, isLauncher);
        edit.commit();
    }

}
