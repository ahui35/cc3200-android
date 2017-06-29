
package com.tiangong.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.tiangong.R;
import com.tiangong.adapter.PlayItemAdapter;
import com.tiangong.adapter.ViewPagerAdapter;
import com.tiangong.bean.TGEquip;
import com.tiangong.datatransport.UDPClient;

import java.util.ArrayList;

public class ProjectorViewControllerFragment extends Fragment implements OnClickListener{
    private ArrayList<TGEquip> peojectors;
    private ViewPager projector_pager;
    private LayoutInflater inflater;
    private ImageButton projector_on,
            projector_off,
            projector_hdmi1,
            projector_hdmi2;

    public ProjectorViewControllerFragment(ArrayList<TGEquip> equips) {
        this.peojectors = equips;
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
        projector_pager = (ViewPager) parent.findViewById(R.id.common_view_pager);

    }

    private void setUI() {
        ArrayList<View> list = new ArrayList<View>();
        for (int count = 0; count < peojectors.size(); count++) {
            View curtainView = inflater.inflate(R.layout.projector_control_item_layout, null);
            initChildView(curtainView);
            setListener();
            list.add(curtainView);
        }
        ViewPagerAdapter adapter = new ViewPagerAdapter(this.getActivity(), list);
        projector_pager.setAdapter(adapter);
    }

    private void initChildView(View view) {
        projector_on = (ImageButton) view.findViewById(R.id.projector_on);
        projector_off = (ImageButton) view.findViewById(R.id.projector_off);
        projector_hdmi1 = (ImageButton) view.findViewById(R.id.projector_hdmi1);
        projector_hdmi2 = (ImageButton) view.findViewById(R.id.projector_hdmi2);

    }

    private void setListener() {
        projector_on.setOnClickListener(this);
        projector_off.setOnClickListener(this);
        projector_hdmi1.setOnClickListener(this);
        projector_hdmi2.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        String action = (String)v.getTag();
        UDPClient.getUDPInstance(getActivity()).sendControlWithEquip(peojectors.get(projector_pager.getCurrentItem()), action, -1);
        
    }
}
