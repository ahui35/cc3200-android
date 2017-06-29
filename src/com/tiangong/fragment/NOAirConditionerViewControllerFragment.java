
package com.tiangong.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.tiangong.R;
import com.tiangong.adapter.ViewPagerAdapter;
import com.tiangong.bean.TGEquip;
import com.tiangong.datatransport.UDPClient;

import java.util.ArrayList;

public class NOAirConditionerViewControllerFragment extends Fragment implements OnClickListener{
    private ArrayList<TGEquip> airs;
    private ViewPager common_view_pager;
    private LayoutInflater inflater;

    private ImageButton air_power,
            air_cool,
            air_heat,
            air_dry,
            air_fan,
            air_swing_a,
            air_swing_v,
            air_swing_h,
            air_fspeed_a,
            air_fspeed_1,
            air_fspeed_m,
            air_fspeed_h,
            air_18,
            air_20,
            air_22,
            air_24,
            air_26,
            air_28,
            air_30;

    public NOAirConditionerViewControllerFragment(ArrayList<TGEquip> equips) {
        this.airs = equips;
    }

    @Override
    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable
    ViewGroup container, @Nullable
    Bundle savedInstanceState) {
        this.inflater = inflater;
        View view = inflater.inflate(R.layout.common_viewpager_control_layout, null);
        initView(view);
        setUI();
        return view;
    }

    private void initView(View parent) {
        common_view_pager = (ViewPager) parent.findViewById(R.id.common_view_pager);

    }

    private void setUI() {
        ArrayList<View> list = new ArrayList<View>();
        for (int count = 0; count < airs.size(); count++) {
            View curtainView = inflater.inflate(R.layout.noair_control_item_layout, null);
            initChildView(curtainView);
            setListener();
            list.add(curtainView);
        }
        ViewPagerAdapter adapter = new ViewPagerAdapter(this.getActivity(), list);
        common_view_pager.setAdapter(adapter);
    }

    private void initChildView(View view) {
        air_power = (ImageButton) view.findViewById(R.id.air_power);
        air_cool = (ImageButton) view.findViewById(R.id.air_cool);
        air_heat = (ImageButton) view.findViewById(R.id.air_heat);
        air_dry = (ImageButton) view.findViewById(R.id.air_dry);
        air_fan = (ImageButton) view.findViewById(R.id.air_fan);
        air_swing_a = (ImageButton) view.findViewById(R.id.air_swing_a);
        air_swing_v = (ImageButton) view.findViewById(R.id.air_swing_v);
        air_swing_h = (ImageButton) view.findViewById(R.id.air_swing_h);
        air_fspeed_a = (ImageButton) view.findViewById(R.id.air_fspeed_a);
        air_fspeed_1 = (ImageButton) view.findViewById(R.id.air_fspeed_1);
        air_fspeed_m = (ImageButton) view.findViewById(R.id.air_fspeed_m);
        air_fspeed_h = (ImageButton) view.findViewById(R.id.air_fspeed_h);
        air_18 = (ImageButton) view.findViewById(R.id.air_18);
        air_20 = (ImageButton) view.findViewById(R.id.air_20);
        air_22 = (ImageButton) view.findViewById(R.id.air_22);
        air_24 = (ImageButton) view.findViewById(R.id.air_24);
        air_26 = (ImageButton) view.findViewById(R.id.air_26);
        air_28 = (ImageButton) view.findViewById(R.id.air_28);
        air_30 = (ImageButton) view.findViewById(R.id.air_30);

    }

    private void setListener() {
        air_power.setOnClickListener(this);
        air_cool.setOnClickListener(this);
        air_heat.setOnClickListener(this);
        air_dry.setOnClickListener(this);
        air_fan.setOnClickListener(this);
        air_swing_a.setOnClickListener(this);
        air_swing_v.setOnClickListener(this);
        air_swing_h.setOnClickListener(this);
        air_fspeed_a.setOnClickListener(this);
        air_fspeed_1.setOnClickListener(this);
        air_fspeed_m.setOnClickListener(this);
        air_fspeed_h.setOnClickListener(this);
        air_18.setOnClickListener(this);
        air_20.setOnClickListener(this);
        air_22.setOnClickListener(this);
        air_24.setOnClickListener(this);
        air_26.setOnClickListener(this);
        air_28.setOnClickListener(this);
        air_30.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String action = (String)v.getTag();
        UDPClient.getUDPInstance(getActivity()).sendControlWithEquip(airs.get(common_view_pager.getCurrentItem()), action, -1);
    }
}
