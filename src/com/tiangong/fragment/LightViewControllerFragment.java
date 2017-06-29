
package com.tiangong.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.tiangong.R;
import com.tiangong.bean.TGEquip;
import com.tiangong.datatransport.BusinessHandler.DataFromServerAutoCallBack;
import com.tiangong.datatransport.BusinessHandler.DataResponseCallBack;
import com.tiangong.datatransport.BusinessHandler.NoDataResponseCallBack;
import com.tiangong.datatransport.UDPClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class LightViewControllerFragment extends Fragment implements DataResponseCallBack, DataFromServerAutoCallBack, NoDataResponseCallBack {

    private ArrayList<TGEquip> equips;
    private ArrayList<TGEquip> switchs;// 开关灯
    private ArrayList<TGEquip> lights;// 调光灯
    private ArrayList<LinearLayout> lightLinearContainer;
    private LinearLayout equips_container;
    private boolean autoupdate  = false;
    
    private String message;
    private View view;
    private TGEquip equip;
    private UDPClient mInstance;

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    reloadStatusUpdateUI();
                    break;
                case 3:
                    if (null != mInstance && null != message && null != view && null != equip) {
                        updateseek(mInstance, message, view, equip);
                    }
                    break;

                default:
                    break;
            }
        }

    };
    public LightViewControllerFragment(ArrayList<TGEquip> list) {
        this.equips = list;
    }

    @Override
    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable
    ViewGroup container, @Nullable
    Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.light_control_layout, null);
        initView(parent);
        UDPClient.getUDPInstance(getActivity()).getHandler().setAutoCallBack(this);
        return parent;
    }

    @Override
    public void onActivityCreated(@Nullable
    Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        createView();
        //reloadStatus();
    }

    private void initView(View v) {
        equips_container = (LinearLayout) v.findViewById(R.id.equips_container);
    }

    private void initData() {
        switchs = new ArrayList<TGEquip>();
        lights = new ArrayList<TGEquip>();

        for (int index = 0; index < equips.size(); index++) {
            TGEquip equip = equips.get(index);
            if (equip.getEquipstyle() == 1) {
                switchs.add(equip);
            } else if (equip.getEquipstyle() == 2) {
                lights.add(equip);
            }
        }
    }

    private void createView() {
        initData();
        if (switchs.size() > 0) {
            int count = switchs.size();
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            params.weight = 1.0f;

            int toal_row = count % 2 == 0 ? (count / 2) : (count / 2 + 1);

            lightLinearContainer = new ArrayList<LinearLayout>();
            for (int row_index = 0; row_index < toal_row; row_index++) {
                LinearLayout row_contaimer = new LinearLayout(this.getActivity());
                lightLinearContainer.add(row_contaimer);
                equips_container.addView(row_contaimer);
            }
            for (int i = 0; i < switchs.size(); i++) {
                final TGEquip light = switchs.get(i);
                int row = i / 2;
                LinearLayout container = lightLinearContainer.get(row);
                LinearLayout.LayoutParams parent_params = (LinearLayout.LayoutParams) container
                        .getLayoutParams();
                parent_params.topMargin = getActivity().getResources().getDimensionPixelSize(
                        R.dimen.dp_twenty_four);

                LinearLayout check_label = new LinearLayout(getActivity());
                check_label.setOrientation(LinearLayout.VERTICAL);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0,
                        LayoutParams.WRAP_CONTENT);
                layoutParams.gravity = Gravity.CENTER;
                layoutParams.weight = 1.0f;

                container.addView(check_label, layoutParams);

                // LinearLayout.LayoutParams check_label_params = new
                // LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                // check_label_params.gravity = Gravity.CENTER;

                TextView light_name = new TextView(getActivity());
                light_name.setTextColor(getResources().getColor(android.R.color.white));
                light_name.setText(light.getEquipName());
                check_label.addView(light_name);

                final CheckBox light_img = new CheckBox(this.getActivity());
                light_img.setButtonDrawable(getActivity().getResources().getDrawable(
                        R.drawable.light_button_selector));
                light_img.setTag(100 + i);
                light_img.setChecked(getSwitchStatus(light));
                light_img.setOnCheckedChangeListener(new OnCheckedChangeListener() {
                    
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (!autoupdate) {
                            TGEquip equip = switchs.get(((Integer)buttonView.getTag()) - 100);
                            UDPClient.getUDPInstance(getActivity()).getHandler().setCallBack(LightViewControllerFragment.this, light_img, equip);
                            UDPClient.getUDPInstance(getActivity()).getHandler().setNoDataCallBack(LightViewControllerFragment.this);
                            if (isChecked) {
                                UDPClient.getUDPInstance(getActivity()).sendControlWithEquip(equip, "on", -1);
                            } else {
                                UDPClient.getUDPInstance(getActivity()).sendControlWithEquip(equip, "off", -1);
                            }
                        } 
                        
                    }
                });
                check_label.addView(light_img);

            }
        }

        if (lights.size() > 0) {
            for (int i = 0; i < lights.size(); i++) {
                TGEquip light_equip = lights.get(i);

                TextView label = new TextView(getActivity());
                label.setText(light_equip.getEquipName());
                label.setTextColor(getResources().getColor(android.R.color.white));
                LinearLayout.LayoutParams label_params = new LinearLayout.LayoutParams(
                        LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                label_params.topMargin = getActivity().getResources().getDimensionPixelSize(
                        R.dimen.dp_sixteen);

                equips_container.addView(label, label_params);

                LinearLayout seek_container = new LinearLayout(getActivity());
                equips_container.addView(seek_container);
                LinearLayout.LayoutParams linear_params = (LinearLayout.LayoutParams) seek_container
                        .getLayoutParams();
                linear_params.gravity = Gravity.CENTER_VERTICAL;
                linear_params.topMargin = getActivity().getResources().getDimensionPixelSize(
                        R.dimen.dp_ten);
                // seek_container.setGravity(Gravity.CENTER_VERTICAL);
                seek_container.setOrientation(LinearLayout.HORIZONTAL);
                LinearLayout.LayoutParams params = new android.widget.LinearLayout.LayoutParams(0,
                        LayoutParams.WRAP_CONTENT);
                params.width = 0;
                params.weight = 1.0f;

                final SeekBar seek = new SeekBar(getActivity());
                seek.setThumb(getActivity().getResources().getDrawable(R.drawable.btn_toggle2x));
                seek.setProgressDrawable(getActivity().getResources().getDrawable(
                        R.drawable.seekbar_style));
                seek.setProgress(getLightStatus(light_equip));
                seek.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
                    
                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                    }
                    
                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }
                    
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        if (!autoupdate) {
                            TGEquip equip = lights.get((int)seekBar.getTag() - 10000);
                            UDPClient.getUDPInstance(getActivity()).getHandler().setCallBack(LightViewControllerFragment.this, seek, equip);
                            UDPClient.getUDPInstance(getActivity()).getHandler().setNoDataCallBack(LightViewControllerFragment.this);
                            UDPClient.getUDPInstance(getActivity()).sendControlWithEquip(equip, "dimmer", progress);
                        }
                    }
                });
                ImageView temp = new ImageView(getActivity());
                temp.setImageResource(R.drawable.icon_light_big_sel);
                seek.setTag(10000 + i);
                seek_container.addView(seek, params);

                seek_container.addView(temp);
            }
        }
    }

    private void reloadStatusUpdateUI() {
        int i = 0;
        for (TGEquip equip : switchs) {
            JSONObject status = UDPClient
                    .getUDPInstance(getActivity())
                    .statusForRoomId(equip.getRoomid(), equip.getEquipaddr(), equip.getEquipstyle());
            CheckBox cbt = (CheckBox) equips_container.findViewWithTag(100 + i);
            if (null != status) {
                String object = status.optString("power", "off");
                if ("off".equals(object)) {
                    cbt.setChecked(false);
                } else {
                    cbt.setChecked(true);
                }
            } else {
                cbt.setChecked(false);
            }
            i++;
        }

        i = 0;
        for (TGEquip equip : lights) {
            JSONObject status = UDPClient
                    .getUDPInstance(getActivity())
                    .statusForRoomId(equip.getRoomid(), equip.getEquipaddr(), equip.getEquipstyle());
            SeekBar slider = (SeekBar) equips_container.findViewWithTag(10000 + i);
            if (null != status) {
                int object = status.optInt("value1", 0);
                slider.setProgress(object);
            } else {
                slider.setProgress(0);
            }
            i++;
        }
        autoupdate = false;
    }

    private boolean getSwitchStatus(TGEquip equip) {
        boolean isCheck = false;
        JSONObject status = UDPClient
                .getUDPInstance(getActivity())
                .statusForRoomId(equip.getRoomid(), equip.getEquipaddr(), equip.getEquipstyle());
        if (null != status) {
            String object = status.optString("power", "off");
            if ("off".equals(object)) {
                isCheck = false;
            } else {
                isCheck = true;
            }
        }
        return isCheck;
    }

    private int getLightStatus(TGEquip equip) {
        int value = 0;
        JSONObject status = UDPClient
                .getUDPInstance(getActivity())
                .statusForRoomId(equip.getRoomid(), equip.getEquipaddr(), equip.getEquipstyle());
        
        if (null != status) {
            value = status.optInt("value1", 0);
        }
        return value;
    }
    @Override
    public void responseData(String message, View view, TGEquip equip) {
        UDPClient instance = UDPClient.getUDPInstance(getActivity());
        instance.getHandler().setCallBack(null, null, null);
        this.message = message;
        this.view = view;
        this.equip = equip;
        this.mInstance = instance;
        mHandler.sendEmptyMessage(3);

    }

    private void updateseek(UDPClient instance, String message, View view, TGEquip equip) {
        if (view instanceof CheckBox) {
            if (null == message) {
                ((CheckBox)view).setChecked(false);
                return;
            }
            // byte[] bs = ((IoBuffer) message).array();
            String response = message;
            boolean checked = false;
            try {
                JSONObject resJson = new JSONObject(response.trim());
                if (resJson.getString("opt").equals("control") || resJson.getString("opt").equals("scene")) {

                    if (resJson.getString("value").equals("ok")) {
                        if (resJson.getString("action").equals("on")) {
                            checked = true;
                        } else {
                            checked = false;
                        }

                    } else {
                        if (resJson.getString("action").equals("on")) {
                            checked = false;
                        } else {
                            checked = true;
                        }
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            instance.setEquiStatus(checked, String.valueOf(equip.getRoomid()), equip.getEquipstyle(), equip.getEquipaddr());
            ((CheckBox)view).setChecked(checked);

        } else if(view instanceof SeekBar) {
            SeekBar sek = (SeekBar)view;
            if (null == message) {
                sek.setProgress(sek.getProgress());
                return;
            }
            // byte[] bs = ((IoBuffer) message).array();
            String response = message;
         
            try {
                JSONObject resJson = new JSONObject(response.trim());
                if (resJson.getString("value").equals("ok")) {
                    sek.setProgress(sek.getProgress());

                } else {
                    sek.setProgress(0);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            instance.setSeekEquiStatus(sek.getProgress(), String.valueOf(equip.getRoomid()), equip.getEquipstyle(), equip.getEquipaddr());
        }
    }
    @Override
    public void reloadStatus() {
        Log.e("bgxiao","reloadStatus");
        autoupdate = true;
        mHandler.sendEmptyMessage(0);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        UDPClient.getUDPInstance(getActivity()).getHandler().setAutoCallBack(null);
        UDPClient.getUDPInstance(getActivity()).getHandler().setCallBack(null, null, null);
        UDPClient.getUDPInstance(getActivity()).getHandler().setNodataCallback(null);
        UDPClient.getUDPInstance(getActivity()).getHandler().setNoDataCallBack(null);
        autoupdate = false;
    }

    @Override
    public void responseNoData() {
        autoupdate = true;
        mHandler.sendEmptyMessage(0);
    }

    
}
