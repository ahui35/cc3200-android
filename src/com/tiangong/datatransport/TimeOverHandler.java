package com.tiangong.datatransport;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.tiangong.R;
import com.tiangong.datatransport.BusinessHandler.NoDataResponseCallBack;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.ReadTimeoutHandler;

import org.json.JSONException;
import org.json.JSONObject;


@Sharable
public class TimeOverHandler extends ReadTimeoutHandler {

    private Context mContext;
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            try {
                Toast.makeText(mContext, mContext.getString(R.string.opt_overtime, new JSONObject(UDPClient.OPT).optString("action", null)), Toast.LENGTH_LONG).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        
    };
    public TimeOverHandler(int timeoutSeconds, Context mContext) {
        super(timeoutSeconds);
        this.mContext = mContext;
    }

    @Override
    protected void readTimedOut(ChannelHandlerContext ctx) throws Exception {
        super.readTimedOut(ctx);
        UDPClient.sending = false;
        mHandler.sendEmptyMessage(0);
        NoDataResponseCallBack nodataCallback = UDPClient.getUDPInstance(mContext).getHandler().getNodataCallback();
        if (null != nodataCallback) {
            nodataCallback.responseNoData();
        }
    }

    
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //super.channelRead(ctx, msg);
        ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //super.exceptionCaught(ctx, cause);
    }

}
