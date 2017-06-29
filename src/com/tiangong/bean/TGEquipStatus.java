package com.tiangong.bean;

import org.json.JSONObject;

public class TGEquipStatus {
    private int equipRoomId;// 设备的roomid
    private int equipStyle; // 设备的style
    private int equipNodeid; // 设备的id
    private JSONObject status; //设备的状态
    public int getEquipRoomId() {
        return equipRoomId;
    }
    public void setEquipRoomId(int equipRoomId) {
        this.equipRoomId = equipRoomId;
    }
    public int getEquipStyle() {
        return equipStyle;
    }
    public void setEquipStyle(int equipStyle) {
        this.equipStyle = equipStyle;
    }
    public int getEquipNodeid() {
        return equipNodeid;
    }
    public void setEquipNodeid(int equipNodeid) {
        this.equipNodeid = equipNodeid;
    }
    public JSONObject getStatus() {
        return status;
    }
    public void setStatus(JSONObject status) {
        this.status = status;
    }


}
