package com.tiangong.bean;

public class TGEquip {

    private int equipstyle;
    private int substyle;
    private int roomid;
    private int equipaddr;
    private String equipName;

    //摄像头用
    private String ddns;
    private int port;
    private String account;
    private String password;
    public int getEquipstyle() {
        return equipstyle;
    }
    public void setEquipstyle(int equipstyle) {
        this.equipstyle = equipstyle;
    }
    public int getSubstyle() {
        return substyle;
    }
    public void setSubstyle(int substyle) {
        this.substyle = substyle;
    }
    public int getRoomid() {
        return roomid;
    }
    public void setRoomid(int roomid) {
        this.roomid = roomid;
    }
    public int getEquipaddr() {
        return equipaddr;
    }
    public void setEquipaddr(int equipaddr) {
        this.equipaddr = equipaddr;
    }
    public String getEquipName() {
        return equipName;
    }
    public void setEquipName(String equipName) {
        this.equipName = equipName;
    }
    public String getDdns() {
        return ddns;
    }
    public void setDdns(String ddns) {
        this.ddns = ddns;
    }
    public int getPort() {
        return port;
    }
    public void setPort(int port) {
        this.port = port;
    }
    public String getAccount() {
        return account;
    }
    public void setAccount(String account) {
        this.account = account;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

}
