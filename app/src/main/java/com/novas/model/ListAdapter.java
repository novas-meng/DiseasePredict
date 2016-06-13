package com.novas.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.novas.diseasepredict.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by novas on 16/4/26.
 */
public class ListAdapter extends BaseAdapter
{
    public List<page> list;
    Context context;
    public ListAdapter(List<page> pagelist,Context context)
    {
        this.list=pagelist;
        this.context=context;
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
        ViewHolder holder=new ViewHolder();
        if(convertView==null)
        {
            LayoutInflater layoutInflater=LayoutInflater.from(context);
            convertView=layoutInflater.inflate(R.layout.listview_item,null);
            holder.titletextview=(TextView)convertView.findViewById(R.id.listview_title);
            holder.titletextview.setText(list.get(position).title);
            convertView.setTag(holder);
        }
        else
        {
            holder=(ViewHolder)convertView.getTag();
            holder.titletextview.setText(list.get(position).title);
        }
        return convertView;
    }
}
class ViewHolder
{
    TextView titletextview;
}
