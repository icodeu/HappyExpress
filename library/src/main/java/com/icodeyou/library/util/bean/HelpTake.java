package com.icodeyou.library.util.bean;

import android.content.Context;
import android.util.Log;

import java.io.Serializable;
import java.util.Date;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by huan on 16/4/10.
 */
public class HelpTake extends BmobObject implements Serializable{

    private ExpressInfo expressInfo; // 关联的ExpressInfo的ObjectId

    private User publishedUser; // 发布的用户
    private User grabUser; // 抢单的用户
    private BmobDate grabTime; // 抢单时间

    // 快件信息
    private String expressCompany;
    private String recvPackageAddress;
    private String recvOwnerMobile;
    private String recvOwnerName;
    private String mailmanMobile;
    // 交付信息
    private String deliveryTime;
    private String deliveryAddress;

    private boolean isPayed; // 用户是否付款
    private String orderId; // 付款单号
    private Boolean isTaked; // 是否交付完成
    private String trackingNumber; //运单号

    public static void createThisTable(Context context) {
        HelpTake helpTake = new HelpTake();

        User pubUser = BmobUser.getCurrentUser(context, User.class);
        helpTake.setPublishedUser(pubUser);
        helpTake.setGrabUser(pubUser);

        helpTake.setGrabTime(new BmobDate(new Date()));
        helpTake.setPayed(false);
        helpTake.setTaked(false);
        helpTake.setTrackingNumber("1000234");

        helpTake.save(context, new SaveListener() {
            @Override
            public void onSuccess() {
                Log.d("HelpTake", "创建Table - HelpTake 成功");
            }

            @Override
            public void onFailure(int code, String msg) {
                Log.d("HelpTake", "创建Table - HelpTake 失败");
            }
        });
    }

    public ExpressInfo getExpressInfo() {
        return expressInfo;
    }

    public void setExpressInfo(ExpressInfo expressInfo) {
        this.expressInfo = expressInfo;
    }

    public User getPublishedUser() {
        return publishedUser;
    }

    public void setPublishedUser(User publishedUser) {
        this.publishedUser = publishedUser;
    }

    public User getGrabUser() {
        return grabUser;
    }

    public void setGrabUser(User grabUser) {
        this.grabUser = grabUser;
    }

    public BmobDate getGrabTime() {
        return grabTime;
    }

    public void setGrabTime(BmobDate grabTime) {
        this.grabTime = grabTime;
    }

    public String getExpressCompany() {
        return expressCompany;
    }

    public void setExpressCompany(String expressCompany) {
        this.expressCompany = expressCompany;
    }

    public String getRecvPackageAddress() {
        return recvPackageAddress;
    }

    public void setRecvPackageAddress(String recvPackageAddress) {
        this.recvPackageAddress = recvPackageAddress;
    }

    public String getRecvOwnerMobile() {
        return recvOwnerMobile;
    }

    public void setRecvOwnerMobile(String recvOwnerMobile) {
        this.recvOwnerMobile = recvOwnerMobile;
    }

    public String getRecvOwnerName() {
        return recvOwnerName;
    }

    public void setRecvOwnerName(String recvOwnerName) {
        this.recvOwnerName = recvOwnerName;
    }

    public String getMailmanMobile() {
        return mailmanMobile;
    }

    public void setMailmanMobile(String mailmanMobile) {
        this.mailmanMobile = mailmanMobile;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public boolean isPayed() {
        return isPayed;
    }

    public void setPayed(boolean payed) {
        isPayed = payed;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Boolean getTaked() {
        return isTaked;
    }

    public void setTaked(Boolean taked) {
        isTaked = taked;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    @Override
    public String toString() {
        return "HelpTake{" +
                "expressInfo=" + expressInfo +
                ", publishedUser=" + publishedUser +
                ", grabUser=" + grabUser +
                ", grabTime=" + grabTime +
                ", expressCompany='" + expressCompany + '\'' +
                ", recvPackageAddress='" + recvPackageAddress + '\'' +
                ", recvOwnerMobile='" + recvOwnerMobile + '\'' +
                ", recvOwnerName='" + recvOwnerName + '\'' +
                ", mailmanMobile='" + mailmanMobile + '\'' +
                ", deliveryTime='" + deliveryTime + '\'' +
                ", deliveryAddress='" + deliveryAddress + '\'' +
                ", isPayed=" + isPayed +
                ", orderId='" + orderId + '\'' +
                ", isTaked=" + isTaked +
                ", trackingNumber='" + trackingNumber + '\'' +
                '}';
    }
}
