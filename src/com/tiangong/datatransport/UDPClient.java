
package com.tiangong.datatransport;

import android.content.Context;
import android.net.DhcpInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.tiangong.R;
import com.tiangong.bean.TGEquip;
import com.tiangong.bean.TGEquipStatus;
import com.tiangong.bean.TGFloor;
import com.tiangong.bean.TGRoom;
import com.tiangong.bean.TGScene;
import com.tiangong.bean.TGUserCenter;
import com.tiangong.db.TGDBUtils;
import com.tiangong.utils.GetBroadAddress;
import com.tiangong.utils.NetWorkUtils;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.util.CharsetUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A UDP broadcast client that asks for a quote of the moment (QOTM) to {@link QuoteOfTheMomentServer}.
 *
 * Inspired by <a href="http://docs.oracle.com/javase/tutorial/networking/datagrams/clientServer.html">the official
 * Java tutorial</a>.
 */
public final class UDPClient {

    private Channel channel;
    private Bootstrap b;
    public /*static*/ String host = "101.69.228.162";
    private static int port = 20000;

    private static UDPClient mInstance;

    private JSONObject sendingdict;
    private Context mContext;
    private static final int TIMEOUTSEC = 3000;
    private static final int MAXCOUNTFORFIND = 3;
    private int countForFind;
    public static boolean sending;
    private Timer timer;
    private HashMap<String, ArrayList<TGEquipStatus>> syncInfo;
    public static String OPT = "";

    private BusinessHandler handler;
    private DhcpInfo dhcpInfo;

    private NioEventLoopGroup group;
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    Toast.makeText(mContext, R.string.net_check, Toast.LENGTH_LONG).show();
                    break;
                case 2:
                    Toast.makeText(mContext, R.string.db_fail, Toast.LENGTH_LONG).show();
                default:
                    break;
            }
        }

    };
    private DefineIdelHandler timeOverHandler;


    public UDPClient(final Context tx) {
        group = new NioEventLoopGroup();
        b = new Bootstrap();
        handler = new BusinessHandler(tx);
        timeOverHandler = new DefineIdelHandler(20, 10, 30, tx);
        b.group(group)
            .channel(NioDatagramChannel.class)
            .handler(new ChannelInitializer<Channel>() {

                @Override
                protected void initChannel(Channel arg0) throws Exception {
                    ChannelPipeline pipeline = arg0.pipeline();
                    pipeline.addLast(timeOverHandler);
                    pipeline.addLast(handler);
                }
            })
            .option(ChannelOption.SO_BROADCAST, true)
            .option(ChannelOption.SO_REUSEADDR, true);
        try {
            ChannelFuture future = b.bind(10000).sync();
            channel = future.channel();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        port = 20000;
        this.mContext = tx;
        this.syncInfo = new HashMap<String, ArrayList<TGEquipStatus>>();

        WifiManager wifii = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
        dhcpInfo = wifii.getDhcpInfo();

    }

    
    public void getHandler(BusinessHandler handler) {
        this.handler = handler;
    }

    public NioEventLoopGroup getGroup() {
        return group;
    }

    public void setGroup(NioEventLoopGroup group) {
        this.group = group;
    }


    public Channel getChannel() {
        return channel;
    }


    public void setChannel(Channel channel) {
        this.channel = channel;
    }




    private TimerTask task = new TimerTask() {

        @Override
        public void run() {
            find();
        }
    };

//    public void sendData() throws Exception {
//        try {b
//            channel = b.bind(10000).sync().channel();
//            InetSocketAddress address = new InetSocketAddress(PORT);
//            // Broadcast the QOTM request to port 8080.
//            channel.writeAndFlush(new DatagramPacket(
//                    Unpooled.copiedBuffer("QOTM?", CharsetUtil.UTF_8),
//                    address)).sync();
//
//            // QuoteOfTheMomentClientHandler will close the DatagramChannel when a
//            // response is received.  If the channel is not closed within 5 seconds,
//            // print an error message and quit.
//            if (!channel.closeFuture().await(5000)) {
//                System.err.println("QOTM request timed out.");
//            }
//        } finally {
//            //group.shutdownGracefully();
//        }
//    }

    public static UDPClient getUDPInstance(Context tx) {
        if (mInstance == null) {
            mInstance = new UDPClient(tx);
        }
        return mInstance;
    }

    public void writeCommand(final String command, final String host, final int port) {
        Log.e("bgxiao","write command:" + command + "host:" + host + "port:" + port);
        try {
            OPT = command;
            channel.writeAndFlush(new DatagramPacket(
                    Unpooled.copiedBuffer(command, CharsetUtil.UTF_8),
                    new InetSocketAddress(host, port))).sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void loginWithNameAndPasswd(String name, String passwd) {
        JSONObject login = new JSONObject();
        try {
            login.put("ffid", "tgzn");
            login.put("opt", "login");
            login.put("user", name);
            login.put("password", passwd);
            sending = true;
            sendingdict = login;
            writeCommand(login.toString(), host, port);
        } catch (JSONException e) {
        }
    }

    public void startFind() {
        countForFind = 0;
        if (TGUserCenter.getDefaultUserCenter(mContext).getIp().length() != 0
                || TGUserCenter.getDefaultUserCenter(mContext).getDdns().length() != 0) {
            return;
        }
        // find();
        TGUserCenter.getDefaultUserCenter(mContext).setLauncher(true);
        timer = new Timer();
        timer.schedule(task, 2000, 3000);

    }

    private GetBroadAddress getNetMask() {
        WifiManager wifii = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
        DhcpInfo d = wifii.getDhcpInfo();
        GetBroadAddress getBroadAddress = new GetBroadAddress();
        getBroadAddress.setNetaddr(NetWorkUtils.int2ip(d.ipAddress));
        getBroadAddress.setNetmask(NetWorkUtils.int2ip(d.netmask));
        getBroadAddress.execCalc();
        return getBroadAddress;
    }

    private void find() {
        if (TextUtils.isEmpty(TGUserCenter.getDefaultUserCenter(mContext).getIp())) {
            if (countForFind == MAXCOUNTFORFIND) {
                timer.cancel();
                return;
            }
            String bdaddress = getNetMask().getNetbroadcastaddr();
            JSONObject findopt = new JSONObject();
            try {
                findopt.put("ffid", "tgzn");
                findopt.put("opt", "find");
                writeCommand(findopt.toString(), bdaddress, 20000);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            countForFind++;
        } else {
            timer.cancel();
        }
    }

    public JSONObject statusForRoomId(int roomid, int nodeid, int style) {
        ArrayList<TGEquipStatus> eq_list = syncInfo.get(String.valueOf(roomid));
        if (null != eq_list) {
            for (int count = 0; count < eq_list.size(); count++) {
                TGEquipStatus status = eq_list.get(count);
                if (status.getEquipStyle() == style && status.getEquipNodeid() == nodeid) {
                    return status.getStatus();
                }
            }
        }
        return null;
    }

    // 开关灯
    public void setEquiStatus(boolean checked, String roomid, int style, int nodeid) {
        ArrayList<TGEquipStatus> list = syncInfo.get(roomid);
        if (null != list) {
            if (list.size() > 0) {
                for (int count = 0; count < list.size(); count++) {
                    TGEquipStatus status = list.get(count);
                    if (status.getEquipStyle() == style && status.getEquipNodeid() == nodeid) {
                        String result = checked ? "on" : "off";
                        try {
                            status.getStatus().put("power", result);
                            return;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                String result = checked ? "on" : "off";
                JSONObject object = new JSONObject();
                try {
                    object.put("power", result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                TGEquipStatus TGStatus = new TGEquipStatus();
                TGStatus.setEquipRoomId(Integer.valueOf(roomid));
                TGStatus.setEquipStyle(style);
                TGStatus.setEquipNodeid(nodeid);
                TGStatus.setStatus(object);
                list.add(TGStatus);

            } else {
                String result = checked ? "on" : "off";
                JSONObject object = new JSONObject();
                try {
                    object.put("power", result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                TGEquipStatus TGStatus = new TGEquipStatus();
                TGStatus.setEquipRoomId(Integer.valueOf(roomid));
                TGStatus.setEquipStyle(style);
                TGStatus.setEquipNodeid(nodeid);
                TGStatus.setStatus(object);
                list.add(TGStatus);
            }
        } else {
            String result = checked ? "on" : "off";
            JSONObject object = new JSONObject();
            try {
                object.put("power", result);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            ArrayList<TGEquipStatus> equips = new ArrayList<TGEquipStatus>();
            TGEquipStatus TGStatus = new TGEquipStatus();
            TGStatus.setEquipRoomId(Integer.valueOf(roomid));
            TGStatus.setEquipStyle(style);
            TGStatus.setEquipNodeid(nodeid);
            TGStatus.setStatus(object);
            equips.add(TGStatus);
            syncInfo.put(roomid, equips);
        }
    }

    // 调光灯
    public void setSeekEquiStatus(int process, String roomid, int style, int nodeid) {
        ArrayList<TGEquipStatus> list = syncInfo.get(roomid);
        if (null != list) {
            if (list.size() > 0) {
                for (int count = 0; count < list.size(); count++) {
                    TGEquipStatus status = list.get(count);
                    if (status.getEquipStyle() == style && status.getEquipNodeid() == nodeid && roomid.equals(String.valueOf(status.getEquipRoomId())) ) {
                        try {
                            status.getStatus().put("value1", process);
                            return;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                JSONObject object = new JSONObject();
                try {
                    object.put("value1", process);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                TGEquipStatus TGStatus = new TGEquipStatus();
                TGStatus.setEquipRoomId(Integer.valueOf(roomid));
                TGStatus.setEquipStyle(style);
                TGStatus.setEquipNodeid(nodeid);
                TGStatus.setStatus(object);
                list.add(TGStatus);
            } else {
                JSONObject object = new JSONObject();
                try {
                    object.put("value1", process);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                TGEquipStatus TGStatus = new TGEquipStatus();
                TGStatus.setEquipRoomId(Integer.valueOf(roomid));
                TGStatus.setEquipStyle(style);
                TGStatus.setEquipNodeid(nodeid);
                TGStatus.setStatus(object);
                list.add(TGStatus);
            }
        } else {
            JSONObject object = new JSONObject();
            try {
                object.put("value1", process);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            ArrayList<TGEquipStatus> equips = new ArrayList<TGEquipStatus>();
            TGEquipStatus TGStatus = new TGEquipStatus();
            TGStatus.setEquipRoomId(Integer.valueOf(roomid));
            TGStatus.setEquipStyle(style);
            TGStatus.setEquipNodeid(nodeid);
            TGStatus.setStatus(object);
            equips.add(TGStatus);
            syncInfo.put(roomid, equips);
        }
    }

    public void sendControlWithEquip(TGEquip equip, String action, float value) {
        if (sending) {
            return;
        }
        JSONObject command = new JSONObject();
        try {
            command.put("ffid", "tgzn");
            command.put("opt", "control");
            command.put("roomid", equip.getRoomid());
            command.put("style", equip.getEquipstyle());
            command.put("nodeid", equip.getEquipaddr());
            if (!TextUtils.isEmpty(action)) {
                command.put("action", action);
            }
            if (value >= 0) {
                command.put("value", value);
            }
            writeCommand(command.toString(), host, port);
            // writeCommand(command.toString(), host, port);
            // 音量不判断超时
            if ("vol+".equals(action) || "vol-".equals(action)) {
                sending = false;
                return;
            }

            sending = true;

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void sendSceneWithEquip(TGScene equip, String action, float value) {
        if (sending) {
            return;
        }
        JSONObject scene = new JSONObject();
        try {
            scene.put("ffid", "tgzn");
            scene.put("opt", "scene");
            scene.put("roomid", equip.getRoomid());
            scene.put("nodeid", equip.getSceneaddr());
            scene.put("ffid", "tgzn");
            writeCommand(scene.toString(), host, port);
        } catch (JSONException e) {
        }
    }

    public void syncAllRooms() {
        if (null == TGDBUtils.getDb()) {
            mHandler.sendEmptyMessage(2);
            return;
        }
        syncInfo.clear();
        ArrayList<TGFloor> floors = TGDBUtils.getAllFloorList();
        for (TGFloor floor : floors) {
            ArrayList<TGRoom> rooms = TGDBUtils.getRoomListByFloorId(floor.getMyfloorid());
            for (TGRoom room : rooms) {
                JSONObject dict = new JSONObject();
                try {
                    dict.put("ffid", "tgzn");
                    dict.put("opt", "sync");
                    dict.put("roomid", room.getRoomId());

                    writeCommand(dict.toString(), host, port);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public HashMap<String, ArrayList<TGEquipStatus>> getSyncInfo() {
        return syncInfo;
    }

    public BusinessHandler getHandler() {
        return handler;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

/*    public void setPort(int port) {
        this.port = port;
    }*/
}
