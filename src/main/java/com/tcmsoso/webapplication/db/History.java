package com.tcmsoso.webapplication.db;

import org.litepal.crud.LitePalSupport;

import java.util.Date;

public class History extends LitePalSupport {

    private int id;
    private String title;
    private String webUrl;
    private Date historyDate;

    public int getId(){
        return id;
    }

    public void setId(){
        this.id=id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public Date getHistoryDate() {
        return historyDate;
    }

    public void setHistoryDate(Date historyDate) {
        this.historyDate = historyDate;
    }
}
