
package com.tiangong.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.tiangong.R;
import com.tiangong.bean.TGEquip;
import com.tiangong.datatransport.UDPClient;

public class KaraokeViewControllerFragment extends Fragment implements OnClickListener {

    private ImageButton btn_power, btn_mute, btn_menu, btn_return, btn_left, btn_up,btn_right,  btn_down, btn_enter;
    private ImageButton btn_play, btn_pause, btn_backward, btn_forward, btn_voldel,btn_voladd,btn_last,btn_next;
    private Button btn_lr, btn_past, btn_list, btn_repeat, btn_del, btn_insert;
    private TGEquip equip;
    @Override
    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable
    ViewGroup container, @Nullable
    Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.karaoke_control_layout, null);
        initView(view);
        setListener();
        return view;
    }

    public KaraokeViewControllerFragment(TGEquip eq) {
        this.equip = eq;
    }
    private void initView(View view) {
        btn_power = (ImageButton)view.findViewById(R.id.btn_power);
        btn_mute = (ImageButton)view.findViewById(R.id.btn_mute);
        btn_menu = (ImageButton)view.findViewById(R.id.btn_menu);
        btn_return = (ImageButton)view.findViewById(R.id.btn_return);
        btn_left = (ImageButton)view.findViewById(R.id.btn_left);
        btn_up = (ImageButton)view.findViewById(R.id.btn_up);
        btn_right = (ImageButton)view.findViewById(R.id.btn_right);
        btn_down = (ImageButton)view.findViewById(R.id.btn_down);
        btn_enter = (ImageButton)view.findViewById(R.id.btn_enter);

        btn_lr = (Button)view.findViewById(R.id.btn_lr);
        btn_past = (Button)view.findViewById(R.id.btn_past);
        btn_list = (Button)view.findViewById(R.id.btn_list);
        btn_repeat = (Button)view.findViewById(R.id.btn_repeat);
        btn_del = (Button)view.findViewById(R.id.btn_del);
        btn_insert = (Button)view.findViewById(R.id.btn_insert);

        btn_play = (ImageButton)view.findViewById(R.id.btn_play);
        btn_pause = (ImageButton)view.findViewById(R.id.btn_pause);
        btn_backward = (ImageButton)view.findViewById(R.id.btn_backward);
        btn_forward = (ImageButton)view.findViewById(R.id.btn_forward);
        btn_voldel = (ImageButton)view.findViewById(R.id.btn_voldel);
        btn_voladd =  (ImageButton)view.findViewById(R.id.btn_voladd);
        btn_last = (ImageButton)view.findViewById(R.id.btn_last);
        btn_next = (ImageButton)view.findViewById(R.id.btn_next);
        UDPClient.getUDPInstance(getActivity()).sendControlWithEquip(equip, "turn", -1);

    }

    private void setListener() {
        btn_power.setOnClickListener(this);
        btn_mute.setOnClickListener(this);
        btn_menu.setOnClickListener(this);
        btn_return.setOnClickListener(this);
        btn_left.setOnClickListener(this);
        btn_up.setOnClickListener(this);
        btn_right.setOnClickListener(this);
        btn_down.setOnClickListener(this);
        btn_enter.setOnClickListener(this);

        btn_lr.setOnClickListener(this);
        btn_past.setOnClickListener(this);
        btn_list.setOnClickListener(this);
        btn_repeat.setOnClickListener(this);
        btn_del.setOnClickListener(this);
        btn_insert.setOnClickListener(this);

        btn_play.setOnClickListener(this);
        btn_pause.setOnClickListener(this);
        btn_backward.setOnClickListener(this);
        btn_forward.setOnClickListener(this);
        btn_voldel.setOnClickListener(this);
        btn_voladd.setOnClickListener(this);
        btn_last.setOnClickListener(this);
        btn_next.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String action = (String)v.getTag();
        UDPClient.getUDPInstance(getActivity()).sendControlWithEquip(equip, action, -1);
    }
}
