
package com.tiangong.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tiangong.R;
import com.tiangong.bean.TGEquip;

import java.util.ArrayList;

public class PlayItemAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<TGEquip> list;
    private LayoutInflater mInflater;

    public PlayItemAdapter(Context context, ArrayList<TGEquip> data) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.list = data;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.play_control_item_layout, null);
            holder.img = (ImageView) convertView.findViewById(R.id.play_img);
            holder.name = (TextView) convertView.findViewById(R.id.play_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        TGEquip equip = list.get(position);
        int id = mContext.getResources().getIdentifier("play_btn_" + equip.getSubstyle(), "drawable", "com.tiangong");
        holder.img.setImageResource(id);
        holder.name.setText(equip.getEquipName());
        return convertView;
    }

    private final class ViewHolder {
        private ImageView img;
        private TextView name;
    }

}
