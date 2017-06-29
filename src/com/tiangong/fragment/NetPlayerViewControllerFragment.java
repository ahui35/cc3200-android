
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

public class NetPlayerViewControllerFragment extends Fragment implements OnClickListener{
    private TGEquip equip;

    private ImageButton btn_net_power, btn_net_volmute, btn_net_left, btn_net_up, btn_net_right,  btn_net_down, btn_net_enter, 
    btn_net_return, btn_net_menu,  btn_net_home, btn_net_play, btn_net_pause, btn_net_voldel, btn_net_voladd;
    public NetPlayerViewControllerFragment(TGEquip eq) { 
        this.equip = eq;
    }

    @Override
    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable
    ViewGroup container, @Nullable
    Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.netplayer_control_layout, null);
        initView(view);
        setListener();
        return view;
    }

    private void initView(View view) {
        btn_net_power = (ImageButton)view.findViewById(R.id.btn_net_power);
        btn_net_volmute = (ImageButton)view.findViewById(R.id.btn_net_volmute);

        btn_net_left = (ImageButton)view.findViewById(R.id.btn_net_left);
        btn_net_up = (ImageButton)view.findViewById(R.id.btn_net_up);
        btn_net_right = (ImageButton)view.findViewById(R.id.btn_net_right);
        btn_net_down = (ImageButton)view.findViewById(R.id.btn_net_down);
        btn_net_enter = (ImageButton)view.findViewById(R.id.btn_net_enter);


        btn_net_return = (ImageButton)view.findViewById(R.id.btn_net_return);
        btn_net_menu = (ImageButton)view.findViewById(R.id.btn_net_menu);
        btn_net_home = (ImageButton)view.findViewById(R.id.btn_net_home);
        btn_net_play = (ImageButton)view.findViewById(R.id.btn_net_play);
        btn_net_pause = (ImageButton)view.findViewById(R.id.btn_net_pause);
        btn_net_voldel =  (ImageButton)view.findViewById(R.id.btn_net_voldel);
        btn_net_voladd = (ImageButton)view.findViewById(R.id.btn_net_voladd);
        UDPClient.getUDPInstance(getActivity()).sendControlWithEquip(equip, "turn", -1);

    }

    private void setListener() {
        btn_net_power.setOnClickListener(this);

        btn_net_volmute.setOnClickListener(this);

        btn_net_left.setOnClickListener(this);
        btn_net_up.setOnClickListener(this);
        btn_net_right.setOnClickListener(this);
        btn_net_down.setOnClickListener(this);
        btn_net_enter.setOnClickListener(this);


        btn_net_return.setOnClickListener(this);
        btn_net_menu.setOnClickListener(this);
        btn_net_home.setOnClickListener(this);
        btn_net_play.setOnClickListener(this);
        btn_net_pause.setOnClickListener(this);
        btn_net_voldel.setOnClickListener(this);
        btn_net_voladd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String action = (String)v.getTag();
        UDPClient.getUDPInstance(getActivity()).sendControlWithEquip(equip, action, -1);
    }
}
