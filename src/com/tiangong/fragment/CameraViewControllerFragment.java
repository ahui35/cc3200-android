
package com.tiangong.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.tiangong.R;
import com.tiangong.activity.TGVideoDisplayViewControllerActivity;
import com.tiangong.adapter.PlayItemAdapter;
import com.tiangong.bean.TGEquip;

import java.util.ArrayList;

public class CameraViewControllerFragment extends Fragment {
    private ArrayList<TGEquip> curtain;
    private GridView play_gridview;

    public CameraViewControllerFragment(ArrayList<TGEquip> equips) {
        this.curtain = equips;

    }

    @Override
    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable
    ViewGroup container, @Nullable
    Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.play_control_layout, null);
        initView(view);
        setUI();
        return view;
    }

    private void initView(View parent) {
        play_gridview = (GridView)parent.findViewById(R.id.play_gridview);

    }

    private void setUI() {
        PlayItemAdapter adapter = new PlayItemAdapter(this.getActivity(), curtain);
        play_gridview.setAdapter(adapter);
        play_gridview.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TGEquip equip = curtain.get(position);
                showVideoViewControl(equip);
            }
        });
    }
    
    private void showVideoViewControl(TGEquip e) {
        Intent video = new Intent();
        video.putExtra("port", e.getPort());
        video.putExtra("ddns", e.getDdns());
        video.putExtra("account", e.getAccount());
        video.putExtra("password", e.getPassword());
        video.putExtra("equipName", e.getEquipName());
        video.setClass(getActivity(), TGVideoDisplayViewControllerActivity.class);
        startActivity(video);
    }

}
