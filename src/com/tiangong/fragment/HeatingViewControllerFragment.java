
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

public class HeatingViewControllerFragment extends Fragment implements OnClickListener {
    private ArrayList<TGEquip> hearts;
    private ViewPager common_view_pager;
    private LayoutInflater inflater;
    private ImageButton heat_tempdel,
            heat_tempadd,
            heat_18,
            heat_20,
            heat_22,
            heat_24,
            heat_26,
            heat_power,
            heat_28;

    public HeatingViewControllerFragment(ArrayList<TGEquip> equips) {
        this.hearts = equips;
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
        for (int count = 0; count < hearts.size(); count++) {
            View curtainView = inflater.inflate(R.layout.heat_control_item_layout, null);
            initChildView(curtainView);
            setListener();
            list.add(curtainView);
        }
        ViewPagerAdapter adapter = new ViewPagerAdapter(this.getActivity(), list);
        common_view_pager.setAdapter(adapter);
    }

    private void initChildView(View view) {
        heat_power = (ImageButton) view.findViewById(R.id.heat_power);
        heat_tempdel = (ImageButton) view.findViewById(R.id.heat_tempdel);
        heat_tempadd = (ImageButton) view.findViewById(R.id.heat_tempadd);
        heat_18 = (ImageButton) view.findViewById(R.id.heat_18);
        heat_20 = (ImageButton) view.findViewById(R.id.heat_20);
        heat_22 = (ImageButton) view.findViewById(R.id.heat_22);
        heat_24 = (ImageButton) view.findViewById(R.id.heat_24);
        heat_26 = (ImageButton) view.findViewById(R.id.heat_26);
        heat_28 = (ImageButton) view.findViewById(R.id.heat_28);

    }

    private void setListener() {
        heat_power.setOnClickListener(this);
        heat_tempdel.setOnClickListener(this);
        heat_tempadd.setOnClickListener(this);
        heat_18.setOnClickListener(this);
        heat_20.setOnClickListener(this);
        heat_22.setOnClickListener(this);
        heat_24.setOnClickListener(this);
        heat_26.setOnClickListener(this);
        heat_28.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String action = (String)v.getTag();
        UDPClient.getUDPInstance(getActivity()).sendControlWithEquip(hearts.get(common_view_pager.getCurrentItem()), action, -1);
        
    }
}
