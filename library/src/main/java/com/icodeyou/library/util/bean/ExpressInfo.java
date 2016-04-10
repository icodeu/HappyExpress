package com.icodeyou.library.util.bean;

import android.content.Context;
import android.util.Log;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by huan on 16/4/8.
 */
public class ExpressInfo extends BmobObject implements Serializable{

    public static final int TYPE_GRAB = 0;
    public static final int TYPE_COMMON = 1;

    public static final int STATUS_PUBLISHING = 0;
    public static final int STATUS_GRABED = 1;
    public static final int STATUS_DONE = 2;
    public static final int STATUS_CANCEL = 3;

    /**
     * 寄件人信息
     */
    private String sendAddress;
    private String sendName;
    private String sendMobile;

    /**
     * 收件人信息
     */
    private String recvAddress;
    private String recvName;
    private String recvMobile;

    /**
     * 创建订单的用户
     */
    private User publishedUser;

    private Integer type; // [抢单0 普通1]
    private Integer status; // [正在发布0 被抢单1 完成2 取消3]

    private Double money;

    public static void createThisTable(Context context) {
        ExpressInfo info = new ExpressInfo();
        info.setSendAddress("北京交通大学");
        info.setSendName("欢欢");
        info.setSendMobile("18214562314");

        info.setRecvAddress("上海交通大学");
        info.setRecvName("乐乐");
        info.setRecvMobile("18545124789");

        User pubUser = BmobUser.getCurrentUser(context, User.class);
        info.setPublishedUser(pubUser);

        info.setType(0);
        info.setStatus(0);
        info.setMoney(10.0);

        info.save(context, new SaveListener() {
            @Override
            public void onSuccess() {
                Log.d("ExpressInfo", "创建Table - ExpressInfo 成功");
            }

            @Override
            public void onFailure(int code, String msg) {
                Log.d("ExpressInfo", "创建Table - ExpressInfo 失败");
            }
        });
    }

    public String getSendAddress() {
        return sendAddress;
    }

    public void setSendAddress(String sendAddress) {
        this.sendAddress = sendAddress;
    }

    public String getSendName() {
        return sendName;
    }

    public void setSendName(String sendName) {
        this.sendName = sendName;
    }

    public String getSendMobile() {
        return sendMobile;
    }

    public void setSendMobile(String sendMobile) {
        this.sendMobile = sendMobile;
    }

    public String getRecvAddress() {
        return recvAddress;
    }

    public void setRecvAddress(String recvAddress) {
        this.recvAddress = recvAddress;
    }

    public String getRecvName() {
        return recvName;
    }

    public void setRecvName(String recvName) {
        this.recvName = recvName;
    }

    public String getRecvMobile() {
        return recvMobile;
    }

    public void setRecvMobile(String recvMobile) {
        this.recvMobile = recvMobile;
    }

    public User getPublishedUser() {
        return publishedUser;
    }

    public void setPublishedUser(User publishedUser) {
        this.publishedUser = publishedUser;
    }


    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    @Override
    public String toString() {
        return "ExpressInfo{" +
                "sendAddress='" + sendAddress + '\'' +
                ", sendName='" + sendName + '\'' +
                ", sendMobile='" + sendMobile + '\'' +
                ", recvAddress='" + recvAddress + '\'' +
                ", recvName='" + recvName + '\'' +
                ", recvMobile='" + recvMobile + '\'' +
                ", publishedUser=" + publishedUser +
                ", type=" + type +
                ", status=" + status +
                ", money=" + money +
                '}';
    }
}
