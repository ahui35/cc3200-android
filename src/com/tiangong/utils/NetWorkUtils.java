package com.tiangong.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

public class NetWorkUtils {

    /**
     * 检查网络是否可用
     * 
     * @param paramContext
     * @return
     */
    public static boolean checkEnable(Context paramContext) {
        boolean i = false;
        NetworkInfo localNetworkInfo = ((ConnectivityManager) paramContext
                .getSystemService("connectivity")).getActiveNetworkInfo();
        if ((localNetworkInfo != null) && (localNetworkInfo.isAvailable()))
            return true;
        return false;
    }

    /**
     * 将ip的整数形式转换成ip形式
     * 
     * @param ipInt
     * @return
     */
    public static String int2ip(int ipInt) {
        StringBuilder sb = new StringBuilder();
        sb.append(ipInt & 0xFF).append(".");
        sb.append((ipInt >> 8) & 0xFF).append(".");
        sb.append((ipInt >> 16) & 0xFF).append(".");
        sb.append((ipInt >> 24) & 0xFF);
        return sb.toString();
    }

    public static String getPhoneIp() { 
        try { 
            for (Enumeration en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) { 
                NetworkInterface intf = (NetworkInterface)en.nextElement(); 
                for (Enumeration enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) { 
                    InetAddress inetAddress = (InetAddress)enumIpAddr.nextElement(); 
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        return inetAddress.getHostAddress().toString(); 
                    } 
                } 
            } 
        } catch (Exception e) { 
        } 
        return "127.0.0.1"; 
    }
}