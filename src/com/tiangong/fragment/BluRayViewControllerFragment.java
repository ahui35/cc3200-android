
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

public class BluRayViewControllerFragment extends Fragment implements OnClickListener {

    private TGEquip tgq;
    private ImageButton btn_blue_power, btn_blue_mute, btn_blue_menu, btn_blue_return,
            btn_blue_left, btn_blue_up, btn_blue_right,
            btn_blue_down, btn_blue_enter;
    private ImageButton btn_blue_play, btn_blue_home, btn_blue_pause, btn_blue_backward, btn_blue_forward,
            btn_blue_voldel, btn_blue_voladd, btn_blue_stop,
            btn_blue_last, btn_blue_next;
    private Button btn_blue_subtitle, btn_blue_language, btn_blue_display, btn_blue_source,
            btn_blue_eject, btn_blue_pop;

    public BluRayViewControllerFragment(TGEquip ep) {
        this.tgq = ep;
    }

    @Override
    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable
    ViewGroup container, @Nullable
    Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bluray_control_layout, null);
        initView(view);
        setListener();
        return view;
    }

    private void initView(View view) {
        btn_blue_power = (ImageButton) view.findViewById(R.id.btn_blue_power);
        btn_blue_mute = (ImageButton) view.findViewById(R.id.btn_blue_volmute);
        btn_blue_menu = (ImageButton) view.findViewById(R.id.btn_blue_menu);
        btn_blue_return = (ImageButton) view.findViewById(R.id.btn_blue_return);
        btn_blue_left = (ImageButton) view.findViewById(R.id.btn_blue_left);
        btn_blue_up = (ImageButton) view.findViewById(R.id.btn_blue_up);
        btn_blue_right = (ImageButton) view.findViewById(R.id.btn_blue_right);
        btn_blue_down = (ImageButton) view.findViewById(R.id.btn_blue_down);
        btn_blue_enter = (ImageButton) view.findViewById(R.id.btn_blue_enter);

        btn_blue_subtitle = (Button) view.findViewById(R.id.btn_blue_subtitle);
        btn_blue_language = (Button) view.findViewById(R.id.btn_blue_language);
        btn_blue_display = (Button) view.findViewById(R.id.btn_blue_display);
        btn_blue_source = (Button) view.findViewById(R.id.btn_blue_source);
        btn_blue_eject = (Button) view.findViewById(R.id.btn_blue_eject);
        btn_blue_pop = (Button) view.findViewById(R.id.btn_blue_pop);
        
        btn_blue_home = (ImageButton) view.findViewById(R.id.btn_blue_home);

        btn_blue_play = (ImageButton) view.findViewById(R.id.btn_blue_play);
        btn_blue_stop = (ImageButton) view.findViewById(R.id.btn_blue_stop);
        btn_blue_pause = (ImageButton) view.findViewById(R.id.btn_blue_pause);
        btn_blue_backward = (ImageButton) view.findViewById(R.id.btn_blue_backward);
        btn_blue_forward = (ImageButton) view.findViewById(R.id.btn_blue_forward);
        btn_blue_voldel = (ImageButton) view.findViewById(R.id.btn_blue_voldel);
        btn_blue_voladd = (ImageButton) view.findViewById(R.id.btn_blue_voladd);
        btn_blue_last = (ImageButton) view.findViewById(R.id.btn_blue_last);
        btn_blue_next = (ImageButton) view.findViewById(R.id.btn_blue_next);
        
        UDPClient.getUDPInstance(getActivity()).sendControlWithEquip(tgq, "turn", -1);
    }

    private void setListener() {
        btn_blue_power.setOnClickListener(this);
        btn_blue_power.setOnClickListener(this);
        btn_blue_mute.setOnClickListener(this);
        btn_blue_menu.setOnClickListener(this);
        btn_blue_return.setOnClickListener(this);
        btn_blue_left.setOnClickListener(this);
        btn_blue_up.setOnClickListener(this);
        btn_blue_right.setOnClickListener(this);
        btn_blue_down.setOnClickListener(this);
        btn_blue_enter.setOnClickListener(this);
        btn_blue_stop.setOnClickListener(this);
        btn_blue_subtitle.setOnClickListener(this);
        btn_blue_language.setOnClickListener(this);
        btn_blue_display.setOnClickListener(this);
        btn_blue_source.setOnClickListener(this);
        btn_blue_eject.setOnClickListener(this);
        btn_blue_pop.setOnClickListener(this);

        btn_blue_home.setOnClickListener(this);
        btn_blue_play.setOnClickListener(this);
        btn_blue_pause.setOnClickListener(this);
        btn_blue_backward.setOnClickListener(this);
        btn_blue_forward.setOnClickListener(this);
        btn_blue_voldel.setOnClickListener(this);
        btn_blue_voladd.setOnClickListener(this);
        btn_blue_last.setOnClickListener(this);
        btn_blue_next.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        String action = (String) v.getTag();
        UDPClient.getUDPInstance(getActivity()).sendControlWithEquip(tgq, action, -1);
    }
}
