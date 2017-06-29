
package com.tiangong.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.tiangong.R;
import com.tiangong.adapter.ViewPagerAdapter;
import com.tiangong.bean.TGEquip;
import com.tiangong.datatransport.UDPClient;

import java.util.ArrayList;

public class CurtainViewControllerFragment extends Fragment implements OnClickListener{
    private ArrayList<TGEquip> curtain;
    private ViewPager curtain_pager;
    private LayoutInflater inflater;
    private ImageButton curtain_on, curtain_off, curtain_stop;

    public CurtainViewControllerFragment(ArrayList<TGEquip> equips) {
        this.curtain = equips;
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
        curtain_pager = (ViewPager) parent.findViewById(R.id.common_view_pager);

        // curtain_on = (ImageButton) parent.findViewById(R.id.curtain_on);
        // curtain_off = (ImageButton) parent.findViewById(R.id.curtain_off);
        // curtain_stop = (ImageButton) parent.findViewById(R.id.curtain_stop);

    }

    private void setUI() {
        ArrayList<View> list = new ArrayList<View>();
        for (int count = 0; count < curtain.size(); count++) {
            TGEquip tgEquip = curtain.get(count);
            String equipName = tgEquip.getEquipName();
            View curtainView = inflater.inflate(R.layout.curtain_control_item_layout, null);
            ((TextView) curtainView.findViewById(R.id.curtain_title)).setText(equipName);
            curtainView.findViewById(R.id.curtain_on).setOnClickListener(this);
            curtainView.findViewById(R.id.curtain_off).setOnClickListener(this);
            curtainView.findViewById(R.id.curtain_stop).setOnClickListener(this);
            curtainView.findViewById(R.id.curtain_on).setTag(tgEquip);
            curtainView.findViewById(R.id.curtain_off).setTag(tgEquip);
            curtainView.findViewById(R.id.curtain_stop).setTag(tgEquip);
            list.add(curtainView);
        }
        ViewPagerAdapter adapter = new ViewPagerAdapter(this.getActivity(), list);
        curtain_pager.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
           switch (v.getId()) {
            case R.id.curtain_on:
                UDPClient.getUDPInstance(getActivity()).sendControlWithEquip((TGEquip)v.getTag(), "on", -1);
                break;
            case R.id.curtain_off:
                UDPClient.getUDPInstance(getActivity()).sendControlWithEquip((TGEquip)v.getTag(), "off", -1);
                break;
            case R.id.curtain_stop:
                UDPClient.getUDPInstance(getActivity()).sendControlWithEquip((TGEquip)v.getTag(), "stop", -1);
                break;

            default:
                break;
        }
    }
}
