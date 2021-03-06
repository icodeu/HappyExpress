package com.icodeyou.library.util.bean;

import android.content.Context;
import android.util.Log;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by huan on 16/4/6.
 */
public class PostStation extends BmobObject implements Serializable {
    private static final long serialVersionUID = 5610376467028035205L;

    private String name;
    private String address;
    private Double longtitude; // 经度
    private Double latitude; // 纬度
    private String desc;
    private String openTime;
    private String imgUrl;
    private String telephone;

    private int distance;

    public static void createThisTable(Context context) {
        PostStation station = new PostStation();
        station.setName("弯角码头");
        station.setAddress("北京交通大学19号楼后");
        station.setLongtitude(116.341355);
        station.setLatitude(39.950259);
        station.setDesc("弯角码头，服务于交大师生");
        station.setOpenTime("09:00 - 18:00");
        station.setImgUrl("http://avatar.csdn.net/6/1/D/1_icodeyou.jpg");
        station.setTelephone("18210854168");

        station.save(context, new SaveListener() {
            @Override
            public void onSuccess() {
                Log.d("PostStation", "创建Table - PostStation 成功");
            }

            @Override
            public void onFailure(int code, String msg) {
                Log.d("PostStation", "创建Table - PostStation 失败");
            }
        });
    }

    public PostStation(String name, String imgUrl, int distance) {
        this.imgUrl = imgUrl;
        this.name = name;
        this.distance = distance;
    }

    public PostStation() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(Double longtitude) {
        this.longtitude = longtitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Override
    public String toString() {
        return "PostStation{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", longtitude=" + longtitude +
                ", latitude=" + latitude +
                ", desc='" + desc + '\'' +
                ", openTime='" + openTime + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", telephone='" + telephone + '\'' +
                ", distance=" + distance +
                '}';
    }
}
