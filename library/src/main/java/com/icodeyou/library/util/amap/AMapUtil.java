package com.icodeyou.library.util.amap;

import android.content.Context;
import android.util.Log;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMapUtils;
import com.amap.api.maps2d.model.LatLng;
import com.icodeyou.library.util.ConstantUtil;
import com.icodeyou.library.util.PreferencesUtils;
import com.icodeyou.library.util.StringUtils;

/**
 * 高德地图相关Util
 */
public class AMapUtil {
    private static final String TAG = "AMapUtil";

    /**
     * 定位
     */
    public static AMapLocationClient startLocation(Context context, AMapLocationListener locationListener) {
        AMapLocationClient locationClient = null;
        //声明mLocationOption对象
        AMapLocationClientOption locationOption = null;

//        mLocationListener = new AMapLocationListener() {
//            @Override
//            public void onLocationChanged(AMapLocation aMapLocation) {
//                if (aMapLocation != null) {
//                    if (aMapLocation.getErrorCode() == 0) {
//                        //定位成功回调信息，设置相关消息
//                        aMapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
//                        aMapLocation.getLatitude();//获取纬度
//                        aMapLocation.getLongitude();//获取经度
//                        aMapLocation.getAccuracy();//获取精度信息
//                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                        Date date = new Date(aMapLocation.getTime());
//                        df.format(date);//定位时间
//                        aMapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
//                        aMapLocation.getCountry();//国家信息
//                        aMapLocation.getProvince();//省信息
//                        aMapLocation.getCity();//城市信息
//                        aMapLocation.getDistrict();//城区信息
//                        aMapLocation.getStreet();//街道信息
//                        aMapLocation.getStreetNum();//街道门牌号信息
//                        aMapLocation.getCityCode();//城市编码
//                        aMapLocation.getAdCode();//地区编码
//                        aMapLocation.getAoiName();//获取当前定位点的AOI信息
//                        Log.d(TAG, "aMap = " + aMapLocation.toString());
//                    } else {
//                        //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
//                        Log.e("AmapError","location Error, ErrCode:"
//                                + aMapLocation.getErrorCode() + ", errInfo:"
//                                + aMapLocation.getErrorInfo());
//                    }
//                }
//            }
//        };

        //初始化定位
        locationClient = new AMapLocationClient(context);
        //设置定位回调监听
        locationClient.setLocationListener(locationListener);

        //初始化定位参数
        locationOption = new AMapLocationClientOption();
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        locationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置是否返回地址信息（默认返回地址信息）
        locationOption.setNeedAddress(true);
        //设置是否只定位一次,默认为false
        locationOption.setOnceLocation(false);
        //设置是否强制刷新WIFI，默认为强制刷新
        locationOption.setWifiActiveScan(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        locationOption.setMockEnable(false);
        //设置定位间隔,单位毫秒,默认为2000ms
        locationOption.setInterval(2000);
        //给定位客户端对象设置定位参数
        locationClient.setLocationOption(locationOption);
        //启动定位
        locationClient.startLocation();
        return locationClient;
    }

    /**
     * 计算两点间距离
     */
    public static float calculateDistance(LatLng start, LatLng end) {
        float distance = AMapUtils.calculateLineDistance(start, end);
        return distance;
    }

    /**
     * 计算两点间距离
     */
    public static String calculateDistance(Context context, double startLatitude, double startLongtitude) {
        // 从本地取出当前用户坐标
        String longtitudeStr = PreferencesUtils.getString(context, ConstantUtil.PREFER_KEY_LONGTITUDE, "0");
        String latitudeStr = PreferencesUtils.getString(context, ConstantUtil.PREFER_KEY_LATITUDE, "0");
        double longtitude = Double.valueOf(longtitudeStr);
        double latitude = Double.valueOf(latitudeStr);

        if (StringUtils.equals("0", longtitudeStr) || StringUtils.equals("0", latitudeStr)) {
            return "未知";
        } else {
            // 计算两点间距离
            LatLng start = new LatLng(startLatitude, startLongtitude);
            LatLng end = new LatLng(latitude, longtitude);
            int distance = (int) calculateDistance(start, end);
            Log.d(TAG, "start = " + start + " end = " + end + "  计算出距离 distance = "  + distance);
            return String.valueOf(distance) + "米";
        }
    }

}
