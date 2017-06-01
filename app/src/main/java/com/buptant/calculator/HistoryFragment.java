package com.buptant.calculator;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chenlei on 2017/5/28.
 */

public class HistoryFragment extends Fragment {

    public static String ACTION_UPDATE = "update";

    private ListView listView;
    private TextView tv_clear;
    private MyAdapter myAdapter;
    private SharedPreferences sharedPreferences;

    private Map map;
    private Map sortedMap;

    private UpdateReceiver updateReceiver;

    List<Record> list = new ArrayList<>();
    private IntentFilter mIntentFilter;
    private AlertDialog.Builder clearBuilder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getActivity().getSharedPreferences("history", Context.MODE_APPEND);
        map = sharedPreferences.getAll();
        if(map.size() > 0){
            sortedMap = sortMapByKey(map);
            Iterator entries = sortedMap.entrySet().iterator();
            while(entries.hasNext()){
                Map.Entry entry = (Map.Entry)entries.next();
                Record record = new Record();
                record.setTime(entry.getKey().toString());
                record.setExpression(entry.getValue().toString());
                list.add(record);
            }
        }
        myAdapter = new MyAdapter(list, getContext());
        updateReceiver = new UpdateReceiver();
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(ACTION_UPDATE);
        getActivity().registerReceiver(updateReceiver, mIntentFilter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.history, container, false);
        listView = (ListView) view.findViewById(R.id.lv_history);
        tv_clear = (TextView) view.findViewById(R.id.tv_clear);
        listView.setAdapter(myAdapter);
        tv_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
        return view;
    }

    private void showDialog() {
        clearBuilder = new AlertDialog.Builder(getActivity());
        clearBuilder.setIcon(R.mipmap.ic_launcher);
        clearBuilder.setTitle("是否清除历史记录");
        clearBuilder.setMessage("确定清除吗?");
        clearBuilder.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        sharedPreferences.edit().clear().apply();
                        list.clear();
                        Intent i = new Intent(HistoryFragment.ACTION_UPDATE);
                        getActivity().sendBroadcast(i);
                    }
                });
        clearBuilder.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        clearBuilder.show();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public static Map<String, String> sortMapByKey(Map<String, String> oriMap) {
        if (oriMap == null || oriMap.isEmpty()) {
            return null;
        }
        Map<String, String> sortedMap = new LinkedHashMap<String, String>();
        List<Map.Entry<String, String>> entryList = new ArrayList<Map.Entry<String, String>>(
                oriMap.entrySet());
        Collections.sort(entryList, new MapKeyComparator());

        Iterator<Map.Entry<String, String>> iter = entryList.iterator();
        Map.Entry<String, String> tmpEntry = null;
        while (iter.hasNext()) {
            tmpEntry = iter.next();
            sortedMap.put(tmpEntry.getKey(), tmpEntry.getValue());
        }
        return sortedMap;
    }

    static class MapKeyComparator implements Comparator<Map.Entry<String, String>> {

        @Override
        public int compare(Map.Entry<String, String> me1, Map.Entry<String, String> me2) {

            return me2.getKey().compareTo(me1.getKey());
        }
    }

    class UpdateReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(ACTION_UPDATE)) {
                list.clear();
                map = sharedPreferences.getAll();
                if (map != null) {
                    sortedMap = sortMapByKey(map);
                    if (sortedMap != null) {
                        Iterator entries = sortedMap.entrySet().iterator();
                        while (entries.hasNext()) {
                            Map.Entry entry = (Map.Entry) entries.next();
                            String time = (String) entry.getKey();
                            String expression = (String) entry.getValue();

                            if (time != null) {
                                Record record = new Record();
                                record.setTime(time);
                                record.setExpression(expression);
                                list.add(record);
                            }
                        }
                    }
                }
                myAdapter.notifyDataSetChanged();
            }
        }
    }
}
