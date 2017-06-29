
package com.tiangong.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.tiangong.R;

import java.util.ArrayList;

public class MenuAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<String> list;
    private LayoutInflater mInflater;

    public MenuAdapter(Context context, ArrayList<String> data) {
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
            convertView = mInflater.inflate(R.layout.menu_item, null);
            holder.menu = (ImageView) convertView.findViewById(R.id.menu_item);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        int id = mContext.getResources().getIdentifier(list.get(position) + "_nor", "drawable", "com.tiangong");
        holder.menu.setImageResource(id);
        return convertView;
    }

    private final class ViewHolder {
        private ImageView menu;
    }

}
