
package com.tiangong.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.tiangong.bean.TGEquip;
import com.tiangong.bean.TGFloor;
import com.tiangong.bean.TGRoom;
import com.tiangong.bean.TGScene;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class TGDBUtils {
    public static final String dirPath = Environment.getExternalStorageDirectory().getPath() + "/tiangong";
    public static final String dirPathd = dirPath + "/tgzn.db";

    private static final String[] icons = {"menu_0",
        "menu_1",
        "menu_6",
        "menu_7",
        "menu_8",
        "menu_2",
        "menu_4",
        "menu_3"
        };
    private static SQLiteDatabase db;

    public static SQLiteDatabase getDb() {
        return db;
    }

    public static void setDb(SQLiteDatabase db) {
        TGDBUtils.db = db;
    }

    public static void importDatabase(Context mContext) {
        File dir = new File(dirPath);
        if (!dir.exists()) {
            if (dir.mkdirs()) {
            } else {
            }
        }
//        File file = new File(dir, "tgzn.db");
//        try {
//            if (!file.exists()) {
//                file.createNewFile();
//            }
//            InputStream is = mContext.getApplicationContext().getResources()
//                    .openRawResource(R.raw.tgzn);
//            FileOutputStream fos = new FileOutputStream(file);
//            byte[] buffere = new byte[is.available()];
//            is.read(buffere);
//            fos.write(buffere);
//            is.close();
//            fos.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public static SQLiteDatabase getDB() {
        File dir = new File(TGDBUtils.dirPath + "/tgzn.db");
        if (dir.exists()) {
            db = SQLiteDatabase.openOrCreateDatabase(dirPath + "/tgzn.db", null);
        }
        return db;
    }

    public static ArrayList<TGFloor> getAllFloorList() {
        ArrayList<TGFloor> floorList = new ArrayList<TGFloor>();
        Cursor floors_cursor = db.query("floors", null, null, null, null, null, null);
        //floors_cursor.moveToFirst();
        while (floors_cursor.moveToNext()) {
            TGFloor floor = new TGFloor();
            int floorid = floors_cursor.getInt(floors_cursor.getColumnIndex("myfloorid"));
            String floorName = floors_cursor.getString(floors_cursor.getColumnIndex("floorname"));
            floor.setMyfloorid(floorid);
            floor.setFloorname(floorName);
            floorList.add(floor);
        }
        floors_cursor.close();
        return floorList;
    }

    public static ArrayList<TGRoom> getRoomListByFloorId(int floorId) {
        ArrayList<TGRoom> roomList = new ArrayList<TGRoom>();
        Cursor rooms = db.query("rooms", null, "floorid = ?", new String[]{String.valueOf(floorId)}, null, null, null);
        while (rooms.moveToNext()) {
            TGRoom room = new TGRoom();
            int roomid = rooms.getInt(rooms.getColumnIndex("myroomid"));
            String roomname = rooms.getString(rooms.getColumnIndex("roomname"));
            int style = rooms.getInt(rooms.getColumnIndex("style"));
            room.setRoomId(roomid);
            room.setRoomName(roomname);
            room.setStyle(style);
            roomList.add(room);
        }
        rooms.close();
        return roomList;
    }

    /*
    1  开关灯类
    2  调光灯类
    3  窗帘类
    4  播放器类
    5  功放类 // 需要查询子类型显示UI
    6  投影仪
    7  空调类 // 需要查询子类型显示UI
    8  背景音乐类
    9  地暖类
    10 摄像头单独一张表
   */
    public static ArrayList<HashMap<String, Object>> getEquipListByRoomId(int roomId) {
        ArrayList<HashMap<String, Object>> equips = new ArrayList<HashMap<String, Object>>();

        for (int i = 2; i < 10; i++) {
            HashMap<String, Object> dict = new HashMap<String, Object>();
            dict.put("iconName", icons[i - 2]);

            ArrayList<TGEquip> array = new ArrayList<TGEquip>();
            dict.put("equips", array);
            equips.add(dict);

            Cursor result = null;
            if (i == 2) {
                result = db.query("equips", null, "roomid = ? and (equipstyle= ? or equipstyle = ?)", 
                        new String[]{String.valueOf(roomId), String.valueOf(1), String.valueOf(2)}, 
                        null, 
                        null, 
                        null);
            } else {
                result = db.query("equips", null, "roomid = ? and equipstyle = ?", 
                        new String[]{String.valueOf(roomId), String.valueOf(i)}, 
                        null, 
                        null, 
                        null);
            }
            while (result.moveToNext()) {
                TGEquip equip = new TGEquip();
                equip.setRoomid(result.getInt(result.getColumnIndex("roomid")));
                equip.setEquipaddr(result.getInt(result.getColumnIndex("equipaddr")));
                equip.setEquipstyle(result.getInt(result.getColumnIndex("equipstyle")));
                equip.setSubstyle(result.getInt(result.getColumnIndex("substyle")));
                equip.setEquipName(result.getString(result.getColumnIndex("equipname")));
                array.add(equip);
            }
            result.close();
        }
        //摄像头

        HashMap<String, Object> dict = new HashMap<String, Object>();
        dict.put("iconName", "menu_5");

        ArrayList<TGEquip> array = new ArrayList<TGEquip>();
        dict.put("equips", array);
        equips.add(dict);
        Cursor videos = db.query("videos", null, "roomid = ?", 
                new String[]{String.valueOf(roomId)}, 
                null, 
                null, 
                null);
        while (videos.moveToNext()) {
            TGEquip equip = new TGEquip();
            equip.setRoomid(videos.getInt(videos.getColumnIndex("roomid")));
            equip.setEquipaddr(videos.getInt(videos.getColumnIndex("myvideoid")));
            equip.setEquipstyle(10);
            equip.setSubstyle(0);
            equip.setEquipName(videos.getString(videos.getColumnIndex("videoname")));
            equip.setDdns(videos.getString(videos.getColumnIndex("ddns")));
            equip.setPort(videos.getInt(videos.getColumnIndex("port")));
            equip.setPassword(videos.getString(videos.getColumnIndex("password")));
            equip.setAccount(videos.getString(videos.getColumnIndex("account")));
            array.add(equip);
        }
        videos.close();

        ArrayList<HashMap<String, Object>> arrayToDelete = new ArrayList<HashMap<String, Object>>();
        for (int i = 0; i < equips.size(); i++) {
            HashMap<String, Object> delete_dict = equips.get(i);
            @SuppressWarnings("unchecked")
            ArrayList<TGEquip> list = (ArrayList<TGEquip>)delete_dict.get("equips");
            if (list.size() == 0) {
                arrayToDelete.add(delete_dict);
            }
        }
        equips.removeAll(arrayToDelete);
        return equips;
    }

    public static ArrayList<TGScene> getScenesForRoom(int roomId) {
        ArrayList<TGScene> sceneList = new ArrayList<TGScene>();
        Cursor scenes = db.query("scenes", null, "roomid = ?", new String[]{String.valueOf(roomId)}, null, null, null);
        while (scenes.moveToNext()) {
            TGScene scene = new TGScene();
            scene.setRoomid(scenes.getInt(scenes.getColumnIndex("roomid")));
            scene.setMysceneid(scenes.getInt(scenes.getColumnIndex("mysceneid")));
            scene.setScenename(scenes.getString(scenes.getColumnIndex("scenename")));
            scene.setSceneaddr(scenes.getInt(scenes.getColumnIndex("sceneaddr")));
            sceneList.add(scene);
        }
        scenes.close();
        return sceneList;
    }
}
