
package com.tiangong.datatransport;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.tiangong.R;
import com.tiangong.bean.TGEquip;
import com.tiangong.bean.TGEquipStatus;
import com.tiangong.bean.TGUserCenter;
import com.tiangong.db.TGDBUtils;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

@Sharable
public class BusinessHandler extends SimpleChannelInboundHandler<DatagramPacket> {
    private DataResponseCallBack callback;
    private DataFromServerAutoCallBack autoUpdateUI;
    private NoDataResponseCallBack nodataCallback;
    private View view;
    private TGEquip equip;
    private Context mContext;
    private ChannelHandlerContext tx;
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            try {
                Toast.makeText(
                        mContext,
                        mContext.getString(R.string.opt_overtime,
                                new JSONObject(UDPClient.OPT).optString("action", null)),
                        Toast.LENGTH_LONG).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    };

    public interface DataResponseCallBack {
        void responseData(String message, View view, TGEquip equip);
    }

    public interface NoDataResponseCallBack {
        void responseNoData();
    }

    public interface DataFromServerAutoCallBack {
        void reloadStatus();
    }

    // private JSONObject request;

    public BusinessHandler(Context mContext) {
        this.mContext = mContext;
    }

    public void setCallBack(DataResponseCallBack back, View view, TGEquip equip) {
        this.callback = back;
        this.view = view;
        this.equip = equip;
    }

    public void setAutoCallBack(DataFromServerAutoCallBack back) {
        this.autoUpdateUI = back;
    }

    public void setNoDataCallBack(NoDataResponseCallBack back) {
        this.nodataCallback = back;
    }


    public NoDataResponseCallBack getNodataCallback() {
        return nodataCallback;
    }

    public void setNodataCallback(NoDataResponseCallBack nodataCallback) {
        this.nodataCallback = nodataCallback;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        tx = ctx;
    }

    
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);

    }


    public ChannelHandlerContext getTx() {
        return tx;
    }

    public void setTx(ChannelHandlerContext tx) {
        this.tx = tx;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext arg0, DatagramPacket msg) throws Exception {
        tx = arg0;
        String response = msg.content().toString(CharsetUtil.UTF_8);
        UDPClient.sending = false;
        if (null == response || response.length() == 0) {
            return;
        }
        JSONObject resJson = new JSONObject(response);

        // { "opt": "control" ,"roomid": 3 ,"style": 1 ,"ffid": "tgzn" ,"nodeid": 1, "action": "on" ,"value" : "ok" }
        if (resJson.get("opt").equals("find")) {
            TGUserCenter.getDefaultUserCenter(mContext).setIp((String) resJson.get("ip"));
            TGUserCenter.getDefaultUserCenter(mContext).setDdns((String) resJson.get("ddns"));
            downloadDB(TGUserCenter.getDefaultUserCenter(mContext).getIp());
            return;
        }

        //{"ffid":"tgzn","opt":"sync","equips":[{ "roomid":1, "style":2,"nodeid":1,"status":{"value1":"40"}},{ "roomid":1, "style":2,"nodeid":2,"status":{"value1":"40"}}]}
        if (resJson.get("opt").equals("sync")) {
            HashMap<String, ArrayList<TGEquipStatus>> syncInfo = UDPClient.getUDPInstance(mContext)
                    .getSyncInfo();
            JSONArray array = (JSONArray) resJson.get("equips");
            if (array.length() != 0) {
                Integer roomid = 0;
                ArrayList<TGEquipStatus> equips = new ArrayList<TGEquipStatus>();
                for (int index = 0; index < array.length(); index++) {
                    JSONObject dict = (JSONObject) array.get(index);
                    roomid = Integer.valueOf(dict.getInt("roomid"));

                    Integer style = Integer.valueOf(dict.getInt("style"));
                    Integer nodeid = Integer.valueOf(dict.getInt("nodeid"));
                    JSONObject status = dict.getJSONObject("status");

                    ArrayList<TGEquipStatus> list = syncInfo.get(String.valueOf(roomid));
                    if (null != list && list.size() > 0) {
                        for (int count = 0; count < list.size(); count++) {
                            TGEquipStatus eq_s = list.get(count);
                            int equipNodeid = eq_s.getEquipNodeid();
                            int equipStyle = eq_s.getEquipStyle();
                            if (nodeid == equipNodeid && style == equipStyle) {
                                eq_s.setEquipNodeid(nodeid);
                                eq_s.setEquipStyle(style);
                                eq_s.setStatus(status);
                                eq_s.setEquipRoomId(roomid);
                            }
                        }
                        if (autoUpdateUI != null) {
                            autoUpdateUI.reloadStatus();
                        }
                        return;

                    } else {
                        TGEquipStatus TGStatus = new TGEquipStatus();
                        TGStatus.setEquipRoomId(roomid);
                        TGStatus.setEquipStyle(style);
                        TGStatus.setEquipNodeid(nodeid);
                        TGStatus.setStatus(status);
                        equips.add(TGStatus);
                    }
                }
                syncInfo.put(String.valueOf(roomid), equips);
                Log.e("bgxiao", "syncInfo:" + syncInfo.toString());
                if (autoUpdateUI != null) {
                    autoUpdateUI.reloadStatus();
                }
            }
            return;
        }

        if (resJson.getString("opt").equals("login")) {
            if (resJson.getString("status").equals("sussess")) {
                TGUserCenter.getDefaultUserCenter(mContext).setLogined(true);
                TGUserCenter.getDefaultUserCenter(mContext).setUserName(resJson.getString("user"));
                TGUserCenter.getDefaultUserCenter(mContext).setPassword(
                        resJson.getString("password"));
            }
            if (null != callback) {
                callback.responseData(response, view, equip);
                return;
            }
        }
        // { "opt": "control" ,"roomid": 1 ,"style": 1 ,"ffid": "tgzn" ,"nodeid": 1, "action": "on" ,"value" : "ok" }
        // { "opt": "control" ,"roomid": 3 ,"style": 1 ,"ffid": "tgzn" ,"nodeid": 1, "action": "on" ,"value" : "ok" }
        if (resJson.getString("opt").equals("control") /*|| resJson.getString("opt").equals("scene")*/) {
            //loadInfo(resJson);
            int style = resJson.optInt("style", 0);
            if (style == 1 || style == 2 || style == 7) {
                if (null != callback) {
                    callback.responseData(response, view, equip);
                    return;
                }
            }
        }
        if (resJson.getString("opt").equals("scene")) {
//            int style = resJson.optInt("style", 0);
//            if (style == 1 || style == 2 || style == 7) {
//                if (null != callback) {equips
//                    callback.responseData(response, view, equip);
//                    return;
//                }
//            }
        }
    }

    private void downloadDB(String result) {
        HttpUtils down = new HttpUtils();
        String uri = "http://" + result + ":8080/tiangong/tgzn.db";
        down.download(uri, TGDBUtils.dirPathd, true, true, new RequestCallBack<File>() {
            @Override
            public void onSuccess(ResponseInfo<File> arg0) {
                TGDBUtils.getDB();
            }

            @Override
            public void onFailure(HttpException arg0, String arg1) {
                // TGUserCenter.getDefaultUserCenter(mContext).setLauncher(false);
            }

            @Override
            public void onStart() {
                super.onStart();
            }

        });

    }
}
