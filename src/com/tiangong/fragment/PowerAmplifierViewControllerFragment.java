
package com.tiangong.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

import com.tiangong.R;
import com.tiangong.adapter.ViewPagerAdapter;
import com.tiangong.bean.TGEquip;
import com.tiangong.datatransport.UDPClient;

import java.util.ArrayList;

public class PowerAmplifierViewControllerFragment extends Fragment implements OnClickListener {
    private ArrayList<TGEquip> powers;
    private ViewPager power_pager;
    private LayoutInflater inflater;
    private ImageButton power_am_power,
    power_am_volmute,
    power_am_return ,
    power_am_soundeffect,
    power_am_menu ,
    power_am_left,
    power_am_up ,
    power_am_right ,
    power_am_down ,
    power_am_enter,
    power_am_voldel,
    power_am_voladd;

    public PowerAmplifierViewControllerFragment(ArrayList<TGEquip> equips) {
        this.powers = equips;
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
        power_pager = (ViewPager) parent.findViewById(R.id.common_view_pager);

    }

    private void setUI() {
        ArrayList<View> list = new ArrayList<View>();
        for (int count = 0; count < powers.size(); count++) {
            View curtainView = inflater.inflate(R.layout.power_control_item_layout, null);
            initChildView(curtainView);
            setListener();
            list.add(curtainView);
        }
        ViewPagerAdapter adapter = new ViewPagerAdapter(this.getActivity(), list);
        power_pager.setAdapter(adapter);
    }
    
    private void initChildView(View view) {
        power_am_power = (ImageButton)view.findViewById(R.id.power_am_power);
        power_am_volmute = (ImageButton)view.findViewById(R.id.power_am_volmute);
        power_am_return = (ImageButton)view.findViewById(R.id.power_am_return);
        power_am_soundeffect = (ImageButton)view.findViewById(R.id.power_am_soundeffect);
        power_am_menu = (ImageButton)view.findViewById(R.id.power_am_menu);
        power_am_left = (ImageButton)view.findViewById(R.id.power_am_left);
        power_am_up = (ImageButton)view.findViewById(R.id.power_am_up);
        power_am_right = (ImageButton)view.findViewById(R.id.power_am_right);
        power_am_down = (ImageButton)view.findViewById(R.id.power_am_down);
        power_am_enter = (ImageButton)view.findViewById(R.id.power_am_enter);
        
        power_am_voldel = (ImageButton)view.findViewById(R.id.power_am_voldel);
        power_am_voladd = (ImageButton)view.findViewById(R.id.power_am_voladd);
    }
    private void setListener() {
        power_am_power.setOnClickListener(this);
        power_am_volmute.setOnClickListener(this);
        power_am_return.setOnClickListener(this);
        power_am_soundeffect.setOnClickListener(this);
        power_am_menu.setOnClickListener(this);
        power_am_left.setOnClickListener(this);
        power_am_up.setOnClickListener(this);
        power_am_right.setOnClickListener(this);
        power_am_down.setOnClickListener(this);
        power_am_enter.setOnClickListener(this);
        
        power_am_voldel.setOnClickListener(this);
        power_am_voladd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String action = (String)v.getTag();
        UDPClient.getUDPInstance(getActivity()).sendControlWithEquip(powers.get(power_pager.getCurrentItem()), action, -1);
        
    }
}
