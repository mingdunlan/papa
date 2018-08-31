package com.tcmsoso.webapplication;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.tcmsoso.webapplication.adapter.HistoryAdapter;
import com.tcmsoso.webapplication.db.History;

import org.litepal.LitePal;

import java.util.List;

public class HistoryList extends AppCompatActivity {

    List<History> historyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_list);

        //初始化
        historyList = LitePal.findAll(History.class);
        HistoryAdapter adapter = new HistoryAdapter(HistoryList.this,R.layout.history_item,historyList);
        ListView listView = (ListView) findViewById(R.id.history_list_view);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                History history = historyList.get(i);
                String url = history.getWebUrl();

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });
//        for(History item: historyList){
//            Log.d("MainActivity---History:  ",item.getTitle()+"------"+item.getWebUrl()+"-----"+item.getHistoryDate().toString());
//        }
    }
}
