package com.tcmsoso.webapplication.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tcmsoso.webapplication.R;
import com.tcmsoso.webapplication.db.BookMark;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class BookMarkAdapter extends RecyclerView.Adapter<BookMarkAdapter.ViewHolder> {

    private Context mContext;
    private List<BookMark> bookMarkList;

    DateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 hh时mm分 EE", Locale.CHINA);

    static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        TextView bookmarkTitle;
        TextView bookmarkDate;

        public ViewHolder(View view){
            super(view);
            cardView = (CardView) view;
            bookmarkTitle = (TextView) view.findViewById(R.id.book_mark_title);
            bookmarkDate = (TextView) view.findViewById(R.id.book_mark_date);
        }
    }

    public BookMarkAdapter(List<BookMark> BookMarkList){
       bookMarkList = BookMarkList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        if(mContext == null){
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.bookmark_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder,int position){
        BookMark bookMark = bookMarkList.get(position);
        holder.bookmarkTitle.setText(bookMark.getTitle());
        holder.bookmarkDate.setText(dateFormat.format(bookMark.getDate()));
    }

    @Override
    public int getItemCount(){
        return bookMarkList.size();
    }
}
