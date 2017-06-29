
package com.tiangong.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;

import com.tiangong.R;
import com.tiangong.adapter.MenuAdapter;
import com.tiangong.bean.TGEquip;
import com.tiangong.bean.TGScene;
import com.tiangong.datatransport.UDPClient;
import com.tiangong.db.TGDBUtils;
import com.tiangong.fragment.AirConditionerViewControllerFragment;
import com.tiangong.fragment.CameraViewControllerFragment;
import com.tiangong.fragment.CurtainViewControllerFragment;
import com.tiangong.fragment.HeatingViewControllerFragment;
import com.tiangong.fragment.LightViewControllerFragment;
import com.tiangong.fragment.MusicViewControllerFragment;
import com.tiangong.fragment.NOAirConditionerViewControllerFragment;
import com.tiangong.fragment.PlayViewControllerFragment;
import com.tiangong.fragment.PowerAmplifierViewControllerFragment;
import com.tiangong.fragment.ProjectorViewControllerFragment;

import java.util.ArrayList;
import java.util.HashMap;

public class MainViewForRoomActivity extends AppCompatActivity implements OnClickListener {

    private int roomid;
    private FragmentManager fm;
    private HorizontalScrollView hs;
    private String roomName;
    private TextView room_info;
    private ImageView btn_home;
    private String floor_name;
    private ImageButton category;
    private PopupWindow popWindow;
    private int top_margin;
    private ArrayList<HashMap<String, Object>> equips;
    private FragmentTransaction transaction;
    private ArrayList<String> analysisData;
    private LinearLayout scenes_container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_view_layout);
        initView();
        getIntentData();
        setViewUI();
        initSceneData();
        addListener();
        analysisData = analysisData();

    }

    private void initView() {
        hs = (HorizontalScrollView) findViewById(R.id.scr_h);
        room_info = (TextView) findViewById(R.id.room_info);
        btn_home = (ImageView) findViewById(R.id.btn_home);
        category = (ImageButton) findViewById(R.id.category);
        
        scenes_container = (LinearLayout) findViewById(R.id.scenes_container);
    }

    private void getIntentData() {
        fm = this.getSupportFragmentManager();
        transaction = fm.beginTransaction();
        top_margin = getResources().getDimensionPixelSize(R.dimen.dp_seventy_two);
        roomid = getIntent().getIntExtra("roomid", 0);
        floor_name = getIntent().getStringExtra("floorname");
        roomName = getIntent().getStringExtra("roomname");
    }

    private void setViewUI() {
        room_info.setText(floor_name + "." + roomName);
    }

    private void addListener() {
        btn_home.setOnClickListener(this);
        category.setOnClickListener(this);
        btn_home.setOnClickListener(this);
    }

    @SuppressWarnings("unchecked")
    private ArrayList<String> analysisData() {
        ArrayList<String> icon_list = new ArrayList<String>();
        equips = TGDBUtils.getEquipListByRoomId(roomid);
        respondsToMenu(((ArrayList<TGEquip>) equips.get(0).get("equips")));
        for (int count = 0; count < equips.size(); count++) {
            HashMap<String, Object> map = equips.get(count);
            String icon_name = (String) map.get("iconName");
            icon_list.add(icon_name);
            /*ArrayList<TGEquip> list = (ArrayList<TGEquip>) map.get("equips");
            for (int s = 0; s < list.size(); s++) {
            }*/
        }
        return icon_list;
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.category:
                category.setVisibility(View.GONE);
                showGategoryPopWindow(category);
                break;
            case R.id.btn_home:
                finish();
                break;

            default:
                break;
        }
    }

    private void initSceneData() {
        //LinearLayout scenes_container = new LinearLayout(this);
        //hs.addView(scenes_container);
        //FrameLayout.LayoutParams params = (FrameLayout.LayoutParams)scenes_container.getLayoutParams();
        //params.height = LayoutParams.WRAP_CONTENT;
        //params.width = LayoutParams.MATCH_PARENT;
        //params.gravity = Gravity.CENTER_HORIZONTAL;
        //params.leftMargin = 8;
        //params.rightMargin = 50;
        //scenes_container.setLayoutParams(params);
        //scenes_container.setGravity(Gravity.CENTER_HORIZONTAL);
        //scenes_container.setOrientation(LinearLayout.HORIZONTAL);
        ArrayList<TGScene> scenes = TGDBUtils.getScenesForRoom(roomid);
        if (scenes.size() <= 6) {
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams)scenes_container.getLayoutParams();
            params.gravity = Gravity.CENTER_HORIZONTAL;
            scenes_container.setLayoutParams(params);
        }
        for (int index = 0; index < scenes.size(); index++) {

            final TGScene scene = scenes.get(index);

            FrameLayout parent = new FrameLayout(this);
            scenes_container.addView(parent);
            //LinearLayout.LayoutParams fParams = (LinearLayout.LayoutParams) parent
                    //.getLayoutParams();
            //fParams.width = 115;
            //fParams.width = LayoutParams.WRAP_CONTENT;
            //fParams.gravity = Gravity.CENTER;
            //fParams.gravity = Gravity.CENTER_HORIZONTAL;
            //fParams.weight = 1.0f;
            //parent.setOrientation(LinearLayout.VERTICAL);
            //parent.setLayoutParams(fParams);

            ImageView btn_scene = new ImageView(this);
            int id = getResources().getIdentifier("icon_footer_" + (index % 4 + 1), "drawable",
                    this.getPackageName());
            btn_scene.setImageResource(id);
            
            btn_scene.setOnClickListener(new OnClickListener() {
                
                @Override
                public void onClick(View v) {
                     AlertDialog dialog = new AlertDialog.Builder(MainViewForRoomActivity.this)
                    .setPositiveButton(MainViewForRoomActivity.this.getResources().getString(R.string.sure), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            UDPClient.getUDPInstance(getApplication()).sendSceneWithEquip(scene, "", -1);
                            dialog.dismiss();
                        }
                    })
                    .setNegativeButton(MainViewForRoomActivity.this.getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                        
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .setMessage(MainViewForRoomActivity.this.getResources().getString(R.string.hint_scene))
                    .show();
                    
                }
            });
            parent.addView(btn_scene);

            TextView labbel = new TextView(this);
            labbel.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
            labbel.setTextColor(getResources().getColor(android.R.color.white));
            labbel.setText(scene.getScenename());
            parent.addView(labbel);

        }
        //hs.smoothScrollTo(0, 0);
    }

    private void showGategoryPopWindow(View parent) {
        if (popWindow == null) {
            View view = this.getLayoutInflater().inflate(R.layout.menu_gridview_layout, null);
            popWindow = new PopupWindow(view, LayoutParams.MATCH_PARENT, 380,
                    true);
            initPop(view, analysisData);
        }
        popWindow.setAnimationStyle(R.style.menupopwindow_anim_style);
        popWindow.setFocusable(true);
        popWindow.setOutsideTouchable(true);
        popWindow.setBackgroundDrawable(new BitmapDrawable());
        popWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        popWindow.showAtLocation(parent, Gravity.TOP, 0, top_margin);
        popWindow.setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss() {
                // attributes.alpha = 1f;
                // RoomViewActivity.this.getWindow().setAttributes(attributes);
                category.setVisibility(View.VISIBLE);
            }
        });

    }

    private void initPop(View view, final ArrayList<String> data) {
        GridView menu_gridview = (GridView) view.findViewById(R.id.menu_gridview);
        final MenuAdapter adapter = new MenuAdapter(this, data);
        menu_gridview.setAdapter(adapter);
        menu_gridview.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                popWindow.dismiss();
                HashMap<String, Object> map = equips.get(position);
                @SuppressWarnings("unchecked")
                ArrayList<TGEquip> equips = (ArrayList<TGEquip>) map.get("equips");
                respondsToMenu(equips);
            }
        });
    }

    private void respondsToMenu(ArrayList<TGEquip> equips) {
        TGEquip equip = equips.get(0);
        switch (equip.getEquipstyle()) {
            case 1:
                showLight(equips);
                break;
            case 2:
                showLight(equips);
                break;
            case 3:
                showCurtain(equips);
                break;
            case 4:
                showPlayer(equips);
                break;
            case 5:
                showPowerAmplifier(equips);
                break;
            case 6:
                showProjector(equips);
                break;
            case 7:
                showAirConditioner(equips);
                break;
            case 8:
                showMusic(equips);
                break;

            case 9:
                showHeating(equips);
                break;
            case 10:
                showCamera(equips);
                break;
            default:
                break;
        }
    }

    private void showLight(ArrayList<TGEquip> curtain) {
        LightViewControllerFragment lightViewControllerFragment = new LightViewControllerFragment(
                curtain);
        FragmentTransaction tr = fm.beginTransaction();
        tr.replace(R.id.main_view_container, lightViewControllerFragment);
        tr.commit();
    }

    private void showCurtain(ArrayList<TGEquip> curtain) {
        CurtainViewControllerFragment curtainFragmnet = new CurtainViewControllerFragment(curtain);
        FragmentTransaction tr = fm.beginTransaction();
        tr.replace(R.id.main_view_container, curtainFragmnet);
        tr.commit();
    }

    private void showPlayer(ArrayList<TGEquip> curtain) {
        PlayViewControllerFragment playFragmnet = new PlayViewControllerFragment(curtain, fm);
        FragmentTransaction tr = fm.beginTransaction();
        tr.replace(R.id.main_view_container, playFragmnet);
        tr.commit();
    }

    private void showPowerAmplifier(ArrayList<TGEquip> powers) {
        PowerAmplifierViewControllerFragment pf = new PowerAmplifierViewControllerFragment(powers);
        FragmentTransaction tr = fm.beginTransaction();
        tr.replace(R.id.main_view_container, pf);
        tr.commit();
    }

    private void showProjector(ArrayList<TGEquip> projector) {

        ProjectorViewControllerFragment prp = new ProjectorViewControllerFragment(projector);
        FragmentTransaction tr = fm.beginTransaction();
        tr.replace(R.id.main_view_container, prp);
        tr.commit();
    }
    
    private void showMusic(ArrayList<TGEquip> music) {

        MusicViewControllerFragment mu = new MusicViewControllerFragment(music);
        FragmentTransaction tr = fm.beginTransaction();
        tr.replace(R.id.main_view_container, mu);
        tr.commit();
    }
    
    private void showHeating(ArrayList<TGEquip> heats) {

        HeatingViewControllerFragment hr = new HeatingViewControllerFragment(heats);
        FragmentTransaction tr = fm.beginTransaction();
        tr.replace(R.id.main_view_container, hr);
        tr.commit();
    }
    
    private void showCamera(ArrayList<TGEquip> cameras) {

        CameraViewControllerFragment hr = new CameraViewControllerFragment(cameras);
        FragmentTransaction tr = fm.beginTransaction();
        tr.replace(R.id.main_view_container, hr);
        tr.commit();
    }

    private void showAirConditioner(ArrayList<TGEquip> airConditioners) {

        TGEquip equip = airConditioners.get(0);
        Fragment view = null;
        if (equip.getSubstyle() == 1) {
            view = new NOAirConditionerViewControllerFragment(airConditioners);
        } else if (equip.getSubstyle() == 2){
            view = new AirConditionerViewControllerFragment(airConditioners);
        }
        FragmentTransaction tr = fm.beginTransaction();
        tr.replace(R.id.main_view_container, view);
        tr.commit();
    }
}
