package com.icodeyou.library.util.bean;

import android.content.Context;
import android.util.Log;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by huan on 16/4/8.
 */
public class User extends BmobUser {

    public static final int TYPE_COMMON = 0;
    public static final int TYPE_COURIER = 1;

    private Integer userType;
    // 用户所在的经纬度
    private Double longtitude;
    private Double latitude;

    public static void registerUser(Context context, String name, String password, int type) {
        User user = new User();
        user.setUsername(name);
        user.setPassword(password);
        user.setEmail(name + "@163.com");

        user.setUserType(type);
        user.setLatitude(39.949145);
        user.setLongtitude(116.339818);

        //注意：不能用save方法进行注册
        user.signUp(context, new SaveListener() {
            @Override
            public void onSuccess() {
                Log.d("User","用户注册成功");
            }
            @Override
            public void onFailure(int code, String msg) {
                Log.d("User","用户注册失败 " + msg);
            }
        });
    }

    public static void login(Context context, String name, String password) {
        BmobUser.loginByAccount(context, name, password, new LogInListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if(user!=null){
                    Log.d("User","用户登录成功");
                }else {
                    Log.d("User", "用户登录失败 " + e.toString());
                    e.printStackTrace();
                }
            }
        });
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
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

    @Override
    public String toString() {
        return "User{" +
                "userType=" + userType +
                ", longtitude=" + longtitude +
                ", latitude=" + latitude +
                '}';
    }
}
