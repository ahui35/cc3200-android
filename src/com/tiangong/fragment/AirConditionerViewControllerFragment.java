
package com.tiangong.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tiangong.R;
import com.tiangong.adapter.ViewPagerAdapter;
import com.tiangong.bean.TGEquip;

import java.util.ArrayList;

public class AirConditionerViewControllerFragment extends Fragment {
    private ArrayList<TGEquip> airs;
    private ViewPager common_view_pager;
    private LayoutInflater inflater;


    public AirConditionerViewControllerFragment(ArrayList<TGEquip> equips) {
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
        common_view_pager = (ViewPager)parent.findViewById(R.id.common_view_pager);

    }

    private void setUI() {
        ArrayList<View> list = new ArrayList<View>();
        for (int count = 0; count < airs.size(); count++) {
            View curtainView = inflater.inflate(R.layout.noair_control_item_layout, null);
            list.add(curtainView);
        }
        ViewPagerAdapter adapter = new ViewPagerAdapter(this.getActivity(), list);
        common_view_pager.setAdapter(adapter);
    }
}
