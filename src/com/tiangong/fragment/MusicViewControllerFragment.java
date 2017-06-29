
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
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.tiangong.R;
import com.tiangong.adapter.ViewPagerAdapter;
import com.tiangong.bean.TGEquip;
import com.tiangong.datatransport.UDPClient;

import java.util.ArrayList;

public class MusicViewControllerFragment extends Fragment implements OnClickListener {
    private ArrayList<TGEquip> musics;
    private ViewPager music_viewpager;
    private LayoutInflater inflater;

    private ImageButton music_power,
            music_last,
            music_play,
            music_pause,
            music_next,
            btn_music_voldel,
            btn_music_voladd,
            music_source;
    
    private SeekBar music_seek;

    public MusicViewControllerFragment(ArrayList<TGEquip> equips) {
        this.musics = equips;
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
        music_viewpager = (ViewPager) parent.findViewById(R.id.common_view_pager);

    }

    private void setUI() {
        ArrayList<View> list = new ArrayList<View>();
        for (int count = 0; count < musics.size(); count++) {
            View curtainView = inflater.inflate(R.layout.music_control_item_layout, null);
            initChildView(curtainView);
            setListener();
            list.add(curtainView);
        }
        ViewPagerAdapter adapter = new ViewPagerAdapter(this.getActivity(), list);
        music_viewpager.setAdapter(adapter);
    }

    private void initChildView(View view) {
        music_power = (ImageButton) view.findViewById(R.id.music_power);
        music_last = (ImageButton) view.findViewById(R.id.music_last);
        music_play = (ImageButton) view.findViewById(R.id.music_play);
        music_pause = (ImageButton) view.findViewById(R.id.music_pause);
        music_next = (ImageButton) view.findViewById(R.id.music_next);
        music_source = (ImageButton) view.findViewById(R.id.music_source);
        btn_music_voldel = (ImageButton) view.findViewById(R.id.btn_music_voldel);
        btn_music_voladd = (ImageButton) view.findViewById(R.id.btn_music_voladd);

        //music_seek = (SeekBar) view.findViewById(R.id.music_seek);
        
//        music_seek.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
//            
//            private int progress2 = 0;
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//            }
//            
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//            }
//            
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                TGEquip equip = musics.get(music_viewpager.getCurrentItem());
//                if (progress > progress2) {
//                    UDPClient.getUDPInstance(getActivity()).sendControlWithEquip(equip, "vol+", progress);
//                } else {
//                    UDPClient.getUDPInstance(getActivity()).sendControlWithEquip(equip, "vol-", progress);
//                }
//                progress2 = progress;
//                //UDPClient.getUDPInstance(getActivity()).getHandler().setCallBack(LightViewControllerFragment.this, seek, equip);
//            }
//        });

    }

    private void setListener() {
        music_power.setOnClickListener(this);
        music_last.setOnClickListener(this);
        music_play.setOnClickListener(this);
        music_pause.setOnClickListener(this);
        music_next.setOnClickListener(this);
        music_source.setOnClickListener(this);
        btn_music_voldel.setOnClickListener(this);
        btn_music_voladd.setOnClickListener(this);
        
    }

    @Override
    public void onClick(View v) {
        String action = (String)v.getTag();
        UDPClient.getUDPInstance(getActivity()).sendControlWithEquip(musics.get(music_viewpager.getCurrentItem()), action, -1);
        
    }
}
