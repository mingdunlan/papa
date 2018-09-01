package com.tcmsoso.webapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.tcmsoso.webapplication.adapter.BookMarkAdapter;
import com.tcmsoso.webapplication.db.BookMark;

import org.litepal.LitePal;

import java.util.List;

public class BookMarkActivity extends AppCompatActivity {


    private List<BookMark> bookMarkList;
    private BookMarkAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_mark);

        //初始化
        bookMarkList = LitePal.findAll(BookMark.class);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new BookMarkAdapter(bookMarkList);
        recyclerView.setAdapter(adapter);

    }
}
