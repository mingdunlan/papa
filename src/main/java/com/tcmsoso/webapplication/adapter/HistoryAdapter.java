package com.tcmsoso.webapplication.adapter;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.tcmsoso.webapplication.R;
import com.tcmsoso.webapplication.db.History;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class HistoryAdapter extends ArrayAdapter<History> {

    DateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 hh时mm分 EE");
    private int resourceId;
    public HistoryAdapter(Context context, int ViewResourceId,List<History> objects){
        super(context,ViewResourceId,objects);
        resourceId = ViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        History history = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        TextView historyTitle = (TextView)view.findViewById(R.id.history_item_title);
        TextView historyDate =(TextView) view.findViewById(R.id.history_item_date);
        historyTitle.setText(history.getTitle());
        historyDate.setText(dateFormat.format(history.getHistoryDate()));
        return view;
    }
}
