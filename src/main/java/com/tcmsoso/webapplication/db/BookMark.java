package com.tcmsoso.webapplication.db;

import org.litepal.crud.LitePalSupport;

import java.util.Date;

public class BookMark extends LitePalSupport {
    private int id;
    private String title;
    private Date date;
    private String markUrl;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMarkUrl() {
        return markUrl;
    }

    public void setMarkUrl(String markUrl) {
        this.markUrl = markUrl;
    }
}
