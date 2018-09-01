package com.tcmsoso.webapplication.util;

import com.tcmsoso.webapplication.db.BookMark;

import org.litepal.LitePal;

import java.util.List;

public class util {

    util(){

    }


    public static boolean isExistBookMark(String title,String bookMarkUrl){
        List<BookMark> bookMarkList = LitePal.where("title like ? and markUrl like ?",title,bookMarkUrl).order("title").find(BookMark.class);
        if (bookMarkList.size()==0){
            return false;
        } else {
            return true;
        }

    }

    public static boolean deleteBookMark(String title,String bookMarkUrl){
        try {
            LitePal.deleteAll(BookMark.class,"title like ? and markUrl like ?",title,bookMarkUrl);
        } catch (Exception e){
            return false;
        }
        return true;
    }
}
