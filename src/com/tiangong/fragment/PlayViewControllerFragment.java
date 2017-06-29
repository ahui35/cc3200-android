
package com.tiangong.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.tiangong.R;
import com.tiangong.adapter.PlayItemAdapter;
import com.tiangong.bean.TGEquip;

import java.util.ArrayList;

public class PlayViewControllerFragment extends Fragment {
    private ArrayList<TGEquip> curtain;
    private GridView play_gridview;
    private FragmentManager fm;


    public PlayViewControllerFragment(ArrayList<TGEquip> equips, FragmentManager fm) {
        this.curtain = equips;
        this.fm = fm;
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
                int substyle = equip.getSubstyle();
                switch (substyle) {
                    case 1:
                        showBlurartViewControl(equip);
                        break;
                    case 2:
                        showNetPlayerViewControl(equip);
                        break;
                    case 3:
                        showHardDiskViewControl(equip);
                        break;
                    case 4:
                        showSTBViewControl(equip);
                        break;
                    case 5:
                        showKaraoViewControl(equip);
                        break;

                    default:
                        break;
                }
                
            }
        });
    }
    
    private void showKaraoViewControl(TGEquip e) {
        KaraokeViewControllerFragment kv = new KaraokeViewControllerFragment(e);
        FragmentTransaction tr = fm.beginTransaction();
        tr.replace(R.id.main_view_container, kv);
        tr.commit();
    }

    private void showBlurartViewControl(TGEquip e) {
        BluRayViewControllerFragment br = new BluRayViewControllerFragment(e);
        FragmentTransaction tr = fm.beginTransaction();
        tr.replace(R.id.main_view_container, br);
        tr.commit();
    }
    
    private void showNetPlayerViewControl(TGEquip e) {
        NetPlayerViewControllerFragment nbr = new NetPlayerViewControllerFragment(e);
        FragmentTransaction nt = fm.beginTransaction();
        nt.replace(R.id.main_view_container, nbr);
        nt.commit();
    }
    
    private void showHardDiskViewControl(TGEquip e) {
        HardDiskPlayerViewControllerFragment hdv = new HardDiskPlayerViewControllerFragment(e);
        FragmentTransaction hd = fm.beginTransaction();
        hd.replace(R.id.main_view_container, hdv);
        hd.commit();
    }

    private void showSTBViewControl(TGEquip e) {
        STBViewControllerFragment stb = new STBViewControllerFragment(e);
        FragmentTransaction hd = fm.beginTransaction();
        hd.replace(R.id.main_view_container, stb);
        hd.commit();
    }
}
