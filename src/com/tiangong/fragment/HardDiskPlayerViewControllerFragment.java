
package com.tiangong.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.tiangong.R;
import com.tiangong.bean.TGEquip;
import com.tiangong.datatransport.UDPClient;

public class HardDiskPlayerViewControllerFragment extends Fragment implements OnClickListener {

    private TGEquip equip;
    private ImageButton btn_hd_power, btn_hd_volmute, btn_hd_menu, btn_hd_return, btn_hd_left, btn_hd_up,btn_hd_right,  btn_hd_down, btn_hd_enter;
    private ImageButton btn_hd_play, btn_hd_stop, btn_hd_pause, btn_hd_backward, btn_hd_forward,btn_hd_voldel,btn_hd_voladd,btn_hd_last,btn_hd_next,btn_hd_home;
    private Button btn_hd_subtitle, btn_hd_language, btn_hd_eject, btn_hd_diaplay;
    
    public HardDiskPlayerViewControllerFragment(TGEquip eq) {
        this.equip = eq;
    }
    @Override
    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable
    ViewGroup container, @Nullable
    Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.harddisk_control_layout, null);
        initView(view);
        setListener();
        return view;
    }

    private void initView(View view) {
        btn_hd_power = (ImageButton)view.findViewById(R.id.btn_hd_power);
        btn_hd_volmute = (ImageButton)view.findViewById(R.id.btn_hd_volmute);
        btn_hd_menu = (ImageButton)view.findViewById(R.id.btn_hd_menu);
        btn_hd_return = (ImageButton)view.findViewById(R.id.btn_hd_return);
        btn_hd_left = (ImageButton)view.findViewById(R.id.btn_hd_left);
        btn_hd_up = (ImageButton)view.findViewById(R.id.btn_hd_up);
        btn_hd_right = (ImageButton)view.findViewById(R.id.btn_hd_right);
        btn_hd_down = (ImageButton)view.findViewById(R.id.btn_hd_down);
        btn_hd_enter = (ImageButton)view.findViewById(R.id.btn_hd_enter);

        btn_hd_subtitle = (Button)view.findViewById(R.id.btn_hd_subtitle);
        btn_hd_language = (Button)view.findViewById(R.id.btn_hd_language);
        btn_hd_eject = (Button)view.findViewById(R.id.btn_hd_eject);
        btn_hd_diaplay = (Button)view.findViewById(R.id.btn_hd_diaplay);
        btn_hd_home = (ImageButton)view.findViewById(R.id.btn_hd_home);

        btn_hd_play = (ImageButton)view.findViewById(R.id.btn_hd_play);
        btn_hd_stop = (ImageButton)view.findViewById(R.id.btn_hd_stop);
        btn_hd_pause = (ImageButton)view.findViewById(R.id.btn_hd_pause);
        btn_hd_backward = (ImageButton)view.findViewById(R.id.btn_hd_backward);
        btn_hd_forward = (ImageButton)view.findViewById(R.id.btn_hd_forward);
        btn_hd_voldel =  (ImageButton)view.findViewById(R.id.btn_hd_voldel);
        btn_hd_voladd = (ImageButton)view.findViewById(R.id.btn_hd_voladd);
        btn_hd_last = (ImageButton)view.findViewById(R.id.btn_hd_last);
        btn_hd_next = (ImageButton)view.findViewById(R.id.btn_hd_next);
        UDPClient.getUDPInstance(getActivity()).sendControlWithEquip(equip, "turn", -1);
    }

    private void setListener() {
        btn_hd_power.setOnClickListener(this);
        btn_hd_power.setOnClickListener(this);
        btn_hd_volmute.setOnClickListener(this);
        btn_hd_menu.setOnClickListener(this);
        btn_hd_return.setOnClickListener(this);
        btn_hd_left.setOnClickListener(this);
        btn_hd_up.setOnClickListener(this);
        btn_hd_right.setOnClickListener(this);
        btn_hd_down.setOnClickListener(this);
        btn_hd_enter.setOnClickListener(this);

        btn_hd_subtitle.setOnClickListener(this);
        btn_hd_language.setOnClickListener(this);
        btn_hd_eject.setOnClickListener(this);
        btn_hd_diaplay.setOnClickListener(this);
        btn_hd_home.setOnClickListener(this);

        btn_hd_play.setOnClickListener(this);
        btn_hd_stop.setOnClickListener(this);
        btn_hd_pause.setOnClickListener(this);
        btn_hd_backward.setOnClickListener(this);
        btn_hd_forward.setOnClickListener(this);
        btn_hd_voldel.setOnClickListener(this);
        btn_hd_voladd.setOnClickListener(this);
        btn_hd_last.setOnClickListener(this);
        btn_hd_next.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        String action = (String)v.getTag();
        UDPClient.getUDPInstance(getActivity()).sendControlWithEquip(equip, action, -1);
        
    }

}
