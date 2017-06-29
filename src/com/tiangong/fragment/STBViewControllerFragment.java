
package com.tiangong.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

import com.tiangong.R;
import com.tiangong.bean.TGEquip;
import com.tiangong.datatransport.UDPClient;

public class STBViewControllerFragment extends Fragment implements OnClickListener {

    private ImageButton btn_stb_power,
            btn_stb_power1,
            btn_stb_menu,
            btn_stb_return,
            btn_stb_left,
            btn_stb_up,
            btn_stb_right,
            btn_stb_down,
            btn_stb_enter,

            btn_stb_1,
            btn_stb_2,
            btn_stb_3,
            btn_stb_4,
            btn_stb_5,
            btn_stb_6,
            btn_stb_7,
            btn_stb_8,
            btn_stb_9,
            btn_stb_0,
            btn_stb_separator,
            btn_stb_source,
            btn_stb_pdel,
            btn_stb_padd,

            btn_stb_voldel,
            btn_stb_voladd,
            btn_stb_volmute;
    private TGEquip equip;

    public STBViewControllerFragment(TGEquip eq) {
        this.equip = eq;
    }

    @Override
    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable
    ViewGroup container, @Nullable
    Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.stb_control_layout, null);
        initView(view);
        setListener();
        return view;
    }

    private void initView(View view) {
        btn_stb_power = (ImageButton) view.findViewById(R.id.btn_stb_power);
        btn_stb_power1 = (ImageButton) view.findViewById(R.id.btn_stb_power1);
        btn_stb_menu = (ImageButton) view.findViewById(R.id.btn_stb_menu);
        btn_stb_return = (ImageButton) view.findViewById(R.id.btn_stb_return);
        btn_stb_left = (ImageButton) view.findViewById(R.id.btn_stb_left);
        btn_stb_up = (ImageButton) view.findViewById(R.id.btn_stb_up);
        btn_stb_right = (ImageButton) view.findViewById(R.id.btn_stb_right);
        btn_stb_down = (ImageButton) view.findViewById(R.id.btn_stb_down);
        btn_stb_enter = (ImageButton) view.findViewById(R.id.btn_stb_enter);

        btn_stb_1 = (ImageButton) view.findViewById(R.id.btn_stb_1);
        btn_stb_2 = (ImageButton) view.findViewById(R.id.btn_stb_2);
        btn_stb_3 = (ImageButton) view.findViewById(R.id.btn_stb_3);
        btn_stb_4 = (ImageButton) view.findViewById(R.id.btn_stb_4);
        btn_stb_5 = (ImageButton) view.findViewById(R.id.btn_stb_5);
        btn_stb_6 = (ImageButton) view.findViewById(R.id.btn_stb_6);
        btn_stb_7 = (ImageButton) view.findViewById(R.id.btn_stb_7);
        btn_stb_8 = (ImageButton) view.findViewById(R.id.btn_stb_8);
        btn_stb_9 = (ImageButton) view.findViewById(R.id.btn_stb_9);
        btn_stb_0 = (ImageButton) view.findViewById(R.id.btn_stb_0);
        btn_stb_separator = (ImageButton) view.findViewById(R.id.btn_stb_separator);
        btn_stb_source = (ImageButton) view.findViewById(R.id.btn_stb_source);
        btn_stb_pdel = (ImageButton) view.findViewById(R.id.btn_stb_pdel);
        btn_stb_padd = (ImageButton) view.findViewById(R.id.btn_stb_padd);

        btn_stb_voldel = (ImageButton) view.findViewById(R.id.btn_stb_voldel);
        btn_stb_voladd = (ImageButton) view.findViewById(R.id.btn_stb_voladd);
        btn_stb_volmute = (ImageButton) view.findViewById(R.id.btn_stb_volmute);
        UDPClient.getUDPInstance(getActivity()).sendControlWithEquip(equip, "turn", -1);

    }

    private void setListener() {
        btn_stb_power.setOnClickListener(this);
        btn_stb_power1.setOnClickListener(this);
        btn_stb_menu.setOnClickListener(this);
        btn_stb_return.setOnClickListener(this);
        btn_stb_left.setOnClickListener(this);
        btn_stb_up.setOnClickListener(this);
        btn_stb_right.setOnClickListener(this);
        btn_stb_down.setOnClickListener(this);
        btn_stb_enter.setOnClickListener(this);

        btn_stb_1.setOnClickListener(this);
        btn_stb_2.setOnClickListener(this);
        btn_stb_3.setOnClickListener(this);
        btn_stb_4.setOnClickListener(this);
        btn_stb_5.setOnClickListener(this);
        btn_stb_6.setOnClickListener(this);
        btn_stb_7.setOnClickListener(this);
        btn_stb_8.setOnClickListener(this);
        btn_stb_9.setOnClickListener(this);
        btn_stb_0.setOnClickListener(this);
        btn_stb_separator.setOnClickListener(this);
        btn_stb_source.setOnClickListener(this);
        btn_stb_pdel.setOnClickListener(this);
        btn_stb_padd.setOnClickListener(this);

        btn_stb_voldel.setOnClickListener(this);
        btn_stb_voladd.setOnClickListener(this);
        btn_stb_volmute.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        String action = (String)v.getTag();
        UDPClient.getUDPInstance(getActivity()).sendControlWithEquip(equip, action, -1);
        
    }
}
