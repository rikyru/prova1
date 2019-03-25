package com.example.ruggiu.prova1;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class RecordAdapter extends BaseAdapter {

    private Context recordContext;
    private List<Record> recordList;

    public RecordAdapter(Context context, List<Record> records) {
        recordList = records;
        recordContext = context;
    }

    @Override
    public int getCount() {
        return recordList.size();
    }

    @Override
    public Object getItem(int i) {
        return recordList.get(i);
    }

    @Override
    public long getItemId(int i) {
       return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        RecordViewHolder holder;

        if (view ==null){
            LayoutInflater recordInflater = (LayoutInflater)
                    recordContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = recordInflater.inflate(R.layout.record, null);

            holder = new RecordViewHolder();
            holder.systolicView = (TextView) view.findViewById(R.id.record_systolic);
            holder.diastolicView = (TextView) view.findViewById(R.id.record_diastolic);
            holder.timestampView = (TextView) view.findViewById(R.id.record_timestamp);
            view.setTag(holder);

        }else {
            holder = (RecordViewHolder) view.getTag();
        }

        Record record = (Record) getItem(i);
        holder.systolicView.setText(record.systolic);
        holder.diastolicView.setText(record.diastolic);
        holder.timestampView.setText(record.timestamp);
        return view;
    }
    public void add(Record record) {
        recordList.add(record);
        notifyDataSetChanged();
    }
    private static class RecordViewHolder {

        public TextView systolicView;
        public TextView diastolicView;
        public TextView timestampView;
    }
}
