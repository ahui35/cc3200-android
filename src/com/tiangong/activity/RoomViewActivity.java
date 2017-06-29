
package com.tiangong.activity;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;
import android.widget.Toast;

import com.tiangong.R;
import com.tiangong.bean.TGFloor;
import com.tiangong.bean.TGRoom;
import com.tiangong.datatransport.UDPClient;
import com.tiangong.db.TGDBUtils;
import com.tiangong.utils.Utils;

import java.util.ArrayList;

public class RoomViewActivity extends AppCompatActivity implements OnClickListener {

    private Button choose_floor;
    private LinearLayout rooms_container;
    private ArrayList<TGFloor> floors;
    private PopupWindow popWindow;
    private LinearLayout floor_item_container;
    private android.view.WindowManager.LayoutParams attributes;
    private int margindp;
    private int heightdp;
    private int driverdp;
    private ArrayList<LinearLayout> containerList;
    private String floor_name = "1楼";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.room_layout);
        initView();
        setListener();
        addViewByFloorId(1);
        UDPClient.getUDPInstance(this).syncAllRooms();
        Log.e("bgxiao","syncAllRooms");

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (null == TGDBUtils.getDb()) {
            Toast.makeText(this, R.string.db_fail, Toast.LENGTH_LONG).show();
            return;
        }
        floors = TGDBUtils.getAllFloorList();
        choose_floor.setText(floors.get(0).getFloorname());

    }

    private void addViewByFloorId(int floorIndex) {
        // query room by floorId
        if (null == TGDBUtils.getDb()) {
            Log.e("bgxiao","null null");
            Toast.makeText(this, R.string.db_fail, Toast.LENGTH_LONG).show();
            return;
        }
        ArrayList<TGRoom> rooms = TGDBUtils.getRoomListByFloorId(floorIndex);
        int count = rooms.size();

        if (count != 0) {

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            params.weight = 1.0f;

            int toal_row = count % 3 == 0 ? (count / 3) : (count / 3 + 1);

            rooms_container.removeAllViews();
            containerList = new ArrayList<LinearLayout>();

            for (int row_index = 0; row_index < toal_row; row_index++) {
                LinearLayout row_contaimer = new LinearLayout(this);
                containerList.add(row_contaimer);
                rooms_container.addView(row_contaimer);
            }

            for (int index = 0; index < count; index++) {
                final TGRoom room = rooms.get(index);
                int row = index / 3;
                LinearLayout container = containerList.get(row);

                Button room_img = new Button(this);
                int id = getResources().getIdentifier("img_room_" + room.getStyle(), "drawable", this.getPackageName());
                room_img.setBackground(null);
                room_img.setText(room.getRoomName());
                room_img.setTextColor(getResources().getColor(android.R.color.white));
                //if (id  == 0) {
                //    room_img.setCompoundDrawablesWithIntrinsicBounds(0, new BitmapDrawable(), 0, 0);
                //} else {
                    room_img.setCompoundDrawablesWithIntrinsicBounds(0, id, 0, 0);
                //}
                //room_img.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(id), null, null);
                room_img.setLayoutParams(params);

                container.addView(room_img);

                room_img.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Intent mainView = new Intent();
                        mainView.putExtra("floorname", floor_name);
                        mainView.putExtra("roomname", room.getRoomName());
                        mainView.putExtra("roomid", room.getRoomId());
                        mainView.setClass(RoomViewActivity.this, MainViewForRoomActivity.class);
                        startActivity(mainView);
                    }
                });
            }
        }
    }
    private void initView() {
        choose_floor = (Button) findViewById(R.id.btn_choose_floor);
        rooms_container = (LinearLayout) findViewById(R.id.rooms_container);
        attributes = this.getWindow().getAttributes();
    }

    private void setListener() {
        choose_floor.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_choose_floor:
                showPopupWindow(choose_floor);
                attributes.alpha = 0.5f;
                this.getWindow().setAttributes(attributes);
                break;

            default:
                break;
        }
    }

    private void showPopupWindow(View parent) {
        if (popWindow == null) {
            View view = this.getLayoutInflater().inflate(R.layout.floor_select_menu, null);
            popWindow = new PopupWindow(view, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT,
                    true);
            initPop(view, floors);
        }
        popWindow.setAnimationStyle(R.style.mypopwindow_anim_style);
        popWindow.setFocusable(true);
        popWindow.setOutsideTouchable(true);
        popWindow.setBackgroundDrawable(new BitmapDrawable());
        popWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        popWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        popWindow.setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss() {
                attributes.alpha = 1f;
                RoomViewActivity.this.getWindow().setAttributes(attributes);
            }
        });

    }

    private void initPop(View view, ArrayList<TGFloor> floors) {
        margindp = Utils.dp2px(this, 10);
        heightdp = Utils.dp2px(this, 48);
        driverdp = Utils.dp2px(this, 1);
        floor_item_container = (LinearLayout) view.findViewById(R.id.floor_item_container);
        for (int count = 0; count < floors.size(); count++) {
            TGFloor floorBean = floors.get(count);

            TextView floor_item = new TextView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            params.leftMargin = margindp;
            params.rightMargin = margindp;
            // params.gravity = Gravity.CENTER;
            params.height = heightdp;
            if (count != 0) {
                params.topMargin = driverdp;
            }
            floor_item.setLayoutParams(params);
            if (count == 0) {
                floor_item.setBackgroundResource(R.drawable.setting_up_selector);
            } else if (count == floors.size() - 1) {
                floor_item.setBackgroundResource(R.drawable.setting_down_selector);
            } else {
                floor_item.setBackgroundResource(R.drawable.setting_center_selector);
            }
            floor_item.setGravity(Gravity.CENTER);
            floor_item.setTextColor(getResources().getColor(R.color.text_body));
            floor_item.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            floor_item.setText(floorBean.getFloorname());
            floor_item_container.addView(floor_item);
            floor_item.setTag(floorBean.getMyfloorid());
            floor_item.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    TextView floor = (TextView) v;
                    floor_name = floor.getText().toString();
                    choose_floor.setText(floor_name);

                    popWindow.dismiss();
                    addViewByFloorId((int)v.getTag());

                }
            });
        }
        view.findViewById(R.id.cancel).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                popWindow.dismiss();

            }
        });
    }
}
