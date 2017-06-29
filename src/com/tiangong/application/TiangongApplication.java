
package com.tiangong.application;

import android.app.Application;

import com.tiangong.bean.TGUserCenter;
import com.tiangong.datatransport.UDPClient;
import com.tiangong.db.TGDBUtils;

public class TiangongApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        TGDBUtils.importDatabase(this);
        //TGDBUtils.getDB();
        if (!TGUserCenter.getDefaultUserCenter(this).isLauncher()) {
            UDPClient.getUDPInstance(this).startFind();
        }

    }

}
