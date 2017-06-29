
package com.tiangong.datatransport;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.tiangong.R;
import com.tiangong.datatransport.BusinessHandler.NoDataResponseCallBack;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;

import org.json.JSONException;
import org.json.JSONObject;

public class DefineIdelHandler extends IdleStateHandler {

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
    public DefineIdelHandler(int readerIdleTimeSeconds, int writerIdleTimeSeconds,
            int allIdleTimeSeconds, Context mContext) {
        super(20, 0, 30);
        this.mContext = mContext;
    }

    @Override
    protected void channelIdle(ChannelHandlerContext ctx, IdleStateEvent evt) throws Exception {
        super.channelIdle(ctx, evt);
        if (evt.state() == IdleState.READER_IDLE && UDPClient.sending) {
            UDPClient.sending = false;
            mHandler.sendEmptyMessage(0);
            NoDataResponseCallBack nodataCallback = UDPClient.getUDPInstance(mContext).getHandler().getNodataCallback();
            if (null != nodataCallback) {
                nodataCallback.responseNoData();
            }
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);
        UDPClient.sending = false;
    }

    
}
