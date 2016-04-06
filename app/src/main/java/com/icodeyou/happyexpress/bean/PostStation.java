package com.icodeyou.happyexpress.bean;

/**
 * Created by huan on 16/4/6.
 */
public class PostStation {

    private String imgUrl;
    private String name;
    private int distance;

    public PostStation(String name, String imgUrl, int distance) {
        this.imgUrl = imgUrl;
        this.name = name;
        this.distance = distance;
    }

    public PostStation() {
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }
}
