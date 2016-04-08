package com.icodeyou.happyexpress.bean;

/**
 * Created by huan on 16/4/6.
 */
public class LogisticsInfo {

    private String time;
    private String date;
    private String info;

    public LogisticsInfo() {
    }

    public LogisticsInfo(String time, String date, String info) {
        this.time = time;
        this.date = date;
        this.info = info;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
