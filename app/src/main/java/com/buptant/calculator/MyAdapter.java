package com.buptant.calculator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by chenlei on 2017/5/29.
 */

public class MyAdapter extends BaseAdapter {

    private List<Record> list;
    private Context context;

    public MyAdapter(List<Record> list, Context context){
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View view1 = LayoutInflater.from(context).inflate(R.layout.item, null);
        TextView tv_time = (TextView) view1.findViewById(R.id.tv_time);
        TextView tv_1 = (TextView) view1.findViewById(R.id.tv_1);
        tv_time.setText(list.get(i).getTime());
        tv_1.setText(list.get(i).getExpression());
        return view1;
    }
}
