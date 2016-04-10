package com.icodeyou.library.util;

import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 别乱改,要改自己new一个
 *
 * @author hudajun
 */
public final class StringUtils {

    public static final String Empty = "";

    /**
     * 判断是否为空
     *
     * @param text
     * @return
     */
    public static boolean isEmpty(String text) {
        return text == null || "".equals(text.trim()) || text.trim().length() == 0
                || "null".equals(text.trim());
    }

    public static boolean isNotEmpty(String text) {
        return !isEmpty(text);
    }

    public static boolean isBlank(String text) {
        return isEmpty(text);
    }

    public static boolean equals(String a, String b) {
        if (a == null && b == null)
            return true;

        if ((a == null && b != null) || (a != null && b == null)) {
            return false;
        }

        return a.equals(b);
    }

    public static boolean in(String str, String... strings) {
        if (str == null && strings == null)
            return true;

        if ((str == null && strings != null) || (str != null && strings == null)) {
            return false;
        }
        for (String s : strings) {
            if (str.equals(s)) {
                return true;
            }
        }

        return false;
    }


    public static boolean notEquals(String a, String b) {
        return !equals(a, b);
    }


    public static boolean equalsAutoConvert(Object a, String b) {
        if ((a == null && b != null) || (a != null && b == null)) {
            return false;
        }
        if (a == null && b == null) {
            return true;
        }

        try {
            return equals((String) a, b);
        } catch (Exception ex) {
        }
        return false;
    }


    public static boolean equalsCaseNotSensitive(String a, String b) {
        if (a == null && b == null)
            return true;

        if ((a == null && b != null) || (a != null && b == null)) {
            return false;
        }

        return a.toLowerCase().equals(b.toLowerCase());
    }

    public static final String getString(Object obj) {
        return obj == null ? null : (String) obj;
    }


    public static boolean isAPrefixOfBCaseNotSensetive(String a, String b) {
        if (a == null && b == null)
            return false;

        if ((a == null && b != null) || (a != null && b == null)) {
            return false;
        }

        return b.toLowerCase().startsWith(a.toLowerCase());
    }


    /**
     * 判断是否为空
     *
     * @param text
     * @return
     */
    public static boolean isNullOrEmpty(String text) {
        return text == null || "".equals(text.trim()) || text.trim().length() == 0
                || "null".equals(text.trim());
    }

    /**
     * 获得MD5加密字符串
     *
     * @param str 字符串
     * @return
     */
    public static String getMD5Str(String str) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(str.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        byte[] byteArray = messageDigest.digest();
        StringBuffer md5StrBuff = new StringBuffer();
        for (int i = 0; i < byteArray.length; i++) {
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
                md5StrBuff.append("0").append(
                        Integer.toHexString(0xFF & byteArray[i]));
            else
                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
        }
        return md5StrBuff.toString();
    }

    /**
     * 得到字符串长度
     *
     * @param text
     * @return
     */
    public static int getCharCount(String text) {
        String Reg = "^[\u4e00-\u9fa5]{1}$";
        int result = 0;
        for (int i = 0; i < text.length(); i++) {
            String b = Character.toString(text.charAt(i));
            if (b.matches(Reg))
                result += 2;
            else
                result++;
        }
        return result;
    }

    /**
     * 获取截取后的字符串
     *
     * @param text   原字符串
     * @param length 截取长度
     * @return
     */
    public static String getSubString(String text, int length) {
        return getSubString(text, length, true);
    }

    /**
     * 获取截取后的字符串
     *
     * @param text   原字符串
     * @param length 截取长度
     * @param isOmit 是否加上省略号
     * @return
     */
    public static String getSubString(String text, int length, boolean isOmit) {
        if (isNullOrEmpty(text)) {
            return "";
        }
        if (getCharCount(text) <= length + 1) {
            return text;
        }

        StringBuffer sb = new StringBuffer();
        String Reg = "^[\u4e00-\u9fa5]{1}$";
        int result = 0;
        for (int i = 0; i < text.length(); i++) {
            String b = Character.toString(text.charAt(i));
            if (b.matches(Reg)) {
                result += 2;
            } else {
                result++;
            }

            if (result <= length + 1) {
                sb.append(b);
            } else {
                if (isOmit) {
                    sb.append("...");
                }
                break;
            }
        }
        return sb.toString();
    }

    /**
     * 电话号码验证
     *
     * @param phoneNumber 手机号码
     * @return
     */
    public static boolean validatePhoneNumber(String phoneNumber) {
        Pattern pattern = Pattern
                .compile("^((13[0-9])|(15[0-9])|(18[0-9]))\\d{8}$");
        Matcher m = pattern.matcher(phoneNumber);
        return m.matches();
    }

    /**
     * 电话号码位数验证
     *
     * @param phoneNumber 手机号码
     * @return
     */
    public static boolean validatePhoneNumberLength(String phoneNumber) {

        return phoneNumber.length() == 11;
    }

    /**
     * 邮箱验证
     *
     * @param mail 邮箱
     * @return
     */
    public static boolean validateEmail(String mail) {
        Pattern pattern = Pattern
                .compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
        Matcher m = pattern.matcher(mail);
        return m.matches();
    }

    /**
     * 验证字符串内容是否合法
     *
     * @param content 字符串内容
     * @return
     */
    public static boolean validateLegalString(String content) {
        String illegal = "`~!#%^&*=+\\|{};:'\",<>/?○●★☆☉♀♂※¤╬の〆";
        boolean legal = true;
        L1:
        for (int i = 0; i < content.length(); i++) {
            for (int j = 0; j < illegal.length(); j++) {
                if (content.charAt(i) == illegal.charAt(j)) {
                    legal = false;
                    break L1;
                }
            }
        }
        return legal;
    }

    /**
     * 获取更新的时间
     *
     * @param dateStr
     * @return
     * @throws Exception
     */
    public static String getCreateString(String dateStr) {
        if (dateStr != null && !"".equals(dateStr)) {
            try {
                if (dateStr.indexOf(".") > -1) {
                    dateStr = dateStr.substring(0, dateStr.indexOf("."));
                }
                SimpleDateFormat sdf = new SimpleDateFormat(
                        "yyyy-MM-dd HH:mm:ss");
                Date date = sdf.parse(dateStr);
                Calendar calendar = Calendar.getInstance();

                int oneMinuteUnit = 60;
                int oneHourUnit = 60 * 60;
                int oneDayUnit = 60 * 60 * 24;
                long i = (calendar.getTimeInMillis() - date.getTime()) / 1000;
                if (i < oneMinuteUnit && i > 0) {
                    return i + "秒前";
                } else if (i < oneHourUnit && i > oneMinuteUnit) {
                    return i / 60 + "分钟前";
                } else if (i < oneDayUnit && i > oneHourUnit) {
                    return (i / (60 * 60)) + "小时前";
                } else {
                    return dateStr;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    public static String getCreateString(Date date) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (calendar.get(Calendar.YEAR) - (date.getYear() + 1900) > 0) {
            return sdf.format(date);
        } else if (calendar.get(Calendar.MONTH) - date.getMonth() > 0) {
            return sdf.format(date);
        } else if (calendar.get(Calendar.DAY_OF_MONTH) - date.getDate() > 0) {
            return sdf.format(date);
        } else if (calendar.get(Calendar.HOUR_OF_DAY) - date.getHours() > 0) {
            int i = calendar.get(Calendar.HOUR_OF_DAY) - date.getHours();
            return i + "小时前";
        } else if (calendar.get(Calendar.MINUTE) - date.getMinutes() > 0) {
            int i = calendar.get(Calendar.MINUTE) - date.getMinutes();
            return i + "分钟前";
        } else if (calendar.get(Calendar.SECOND) - date.getSeconds() > 0) {
            int i = calendar.get(Calendar.SECOND) - date.getSeconds();
            return i + "秒前";
        } else {
            return sdf.format(date);
        }
    }


    public static String getDateString(Date date) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (calendar.get(Calendar.YEAR) - (date.getYear() + 1900) > 0) {
            return sdf.format(date);
        } else if (calendar.get(Calendar.MONTH) - date.getMonth() > 0) {
            return sdf.format(date);
        } else if (calendar.get(Calendar.DAY_OF_MONTH) - date.getDate() > 6) {
            return sdf.format(date);
        } else if ((calendar.get(Calendar.DAY_OF_MONTH) - date.getDate() > 0)
                && (calendar.get(Calendar.DAY_OF_MONTH) - date.getDate() < 6)) {
            int i = calendar.get(Calendar.HOUR_OF_DAY) - date.getHours();
            return i + "天前";
        } else if (calendar.get(Calendar.HOUR_OF_DAY) - date.getHours() > 0) {
            int i = calendar.get(Calendar.HOUR_OF_DAY) - date.getHours();
            return i + "小时前";
        } else if (calendar.get(Calendar.MINUTE) - date.getMinutes() > 0) {
            int i = calendar.get(Calendar.MINUTE) - date.getMinutes();
            return i + "分钟前";
        } else if (calendar.get(Calendar.SECOND) - date.getSeconds() > 0) {
            int i = calendar.get(Calendar.SECOND) - date.getSeconds();
            return i + "秒前";
        } else if (calendar.get(Calendar.SECOND) - date.getSeconds() == 0) {
            return "刚刚";
        } else {
            return sdf.format(date);
        }
    }

    /**
     * 如果为空显示暂无信息
     *
     * @param tv  控件名
     * @param str 信息
     */
    public static void viewText(TextView tv, String str) {
        if (isNullOrEmpty(str)) {
            tv.setText("暂无资料");
        } else {
            tv.setText(str);
        }
    }

    /**
     * 对流转化成字符串
     *
     * @param is
     * @return
     */
    public static String getContentByString(InputStream is) {
        try {
            if (is == null)
                return null;
            byte[] b = new byte[1024];
            int len = -1;
            StringBuilder sb = new StringBuilder();
            while ((len = is.read(b)) != -1) {
                sb.append(new String(b, 0, len));
            }
            return sb.toString();
        } catch (Exception e) {
            // TODO: handle exception
        }
        return null;
    }

    /**
     * 对流转化成字符串
     *
     * @param is
     * @return
     */
    public static String getStringByStream(InputStream is) {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(is,
                    "UTF-8"));
            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = in.readLine()) != null) {
                buffer.append(line + "\n");
            }
            return buffer.toString().replaceAll("\n\n", "\n");
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * 截取字符串，去掉sign后边的
     *
     * @param source 原始字符串
     * @param sign
     * @return
     */
    public static String splitByIndex(String source, String sign) {
        String temp = "";
        if (isNullOrEmpty(source)) {
            return temp;
        }
        int length = source.indexOf(sign);
        if (length > -1) {
            temp = source.substring(0, length);
        } else {
            return source;
        }
        return temp;
    }

    /**
     * 截取字符串，返回sign分隔的字符串
     */
    public static String splitNumAndStr(String res, String sign) {
        StringBuffer buffer;
        String reg = "\\d+";
        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(res);
        if (m.find()) {
            buffer = new StringBuffer();
            String s = m.group();
            buffer.append(s);
            buffer.append(sign);
            buffer.append(res.replace(s, ""));
            return buffer.toString();
        }
        return null;
    }

    /**
     * 保留小数点后一位
     *
     * @param d
     * @return
     * @throws Exception
     */
    public static String formatNumber(double d) {
        try {
            DecimalFormat df = new DecimalFormat("#,##0.0");
            return df.format(d);
        } catch (Exception e) {
        }
        return "";
    }

    public static String formatNumber(String d) {
        return formatNumber(Double.parseDouble(d));
    }

    /**
     * 把对象放进map里
     *
     * @param o 实体
     */
    public static Map<String, String> getMapForEntry(Object o) {
        Map<String, String> map = new HashMap<String, String>();
        try {
            Field[] fields = o.getClass().getFields();
            for (Field f : fields) {
                String key = f.getName();
                try {
                    String value = (String) f.get(o);
                    if (StringUtils.isNullOrEmpty(value)
                            || value.indexOf("不限") > -1) {
                        continue;
                    }
                    map.put(key, value);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
        }
        return map;
    }

    /**
     * map 转化为实体
     *
     * @param <T>
     * @param map
     * @param clazz
     * @return
     */
    public static <T> T setMapForEntry(Map<String, String> map, Class<T> clazz) {
        T t = null;
        try {
            t = clazz.newInstance();
            for (Entry<String, String> entry : map.entrySet()) {
                String key = entry.getKey();
                Field field = t.getClass().getField(key);
                field.set(t, entry.getValue());
            }
        } catch (Exception e) {
        }
        return t;
    }

    /**
     * 实体转化
     *
     * @param o
     * @return
     */
    public static <T> T convertEntry(Object o) {
        T t = null;
        try {
            t = (T) o.getClass().newInstance();
            Field[] fields = o.getClass().getFields();
            for (Field f : fields) {
                try {
                    String value = (String) f.get(o);
                    if (StringUtils.isNullOrEmpty(value)
                            || value.indexOf("不限") > -1) {
                        f.set(t, "");
                    } else {
                        f.set(t, value);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
        }
        return t;
    }

    /*
     * 获取字符串格式的当前时间
     */
    public static String getStringDate() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /*
     * 时间格式转换，yyyy-MM-dd xx:xx:xx为：yyyy-MM-dd
     */
    public static String getStringDate(String date) {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date d = new Date();
        try {
            d = formatter.parse(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String dateString = formatter.format(d);
        return dateString;

    }

    /*
     * 截取sign后边的数字
     */
    public static String getStringNum(String str, String sign) {
        String reg = ":split:";
        return str.replace(sign, reg).replaceAll(reg + "\\d+", "")
                .replaceAll(" ", "").trim();

    }

    public static String getRegText(String xml, String tag) {
        Pattern pattern = Pattern.compile("<" + tag + ">(.*?)</" + tag + ">",
                Pattern.UNICODE_CASE | Pattern.DOTALL);
        Matcher m = pattern.matcher(xml);
        if (m.find()) {
            xml = m.group(1);
            xml = xml.replace("<![CDATA[", "").replace("<![cdata[", "")
                    .replace("]]>", "");
            return xml;
        } else {
            return null;
        }
    }

    /*
     * 时间格式转换，yyyy-MM-dd xx:xx:xx为：MM-dd xx:xx 不要年和秒
     */
    public static String getStringForDate(long date) {

        SimpleDateFormat f = new SimpleDateFormat("MM-dd HH:mm");
        Date d = new Date(date);
        String dateString = f.format(d);
        return dateString;

    }

    /*
     * 时间格式转换，yyyy-MM-dd xx:xx:xx为：MM-dd xx:xx 不要年和秒
     */
    public static String getStringForDate_yyyy_MM_dd() {

        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        Date d = new Date(System.currentTimeMillis());
        String dateString = f.format(d);
        return dateString;

    }

    /*
     * 时间格式转换，yyyy-MM-dd xx:xx:xx为：MM-dd xx:xx 不要年和秒
     */
    public static String getStringForDate(String date) {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        SimpleDateFormat f = new SimpleDateFormat("MM-dd HH:mm");
        Date d = new Date();
        try {
            d = formatter.parse(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String dateString = f.format(d);
        return dateString;

    }

    /*
     * 判断价格是否为0或空
     */
    public static boolean isPriceZero(String price) {
        if (isNullOrEmpty(price)) {
            return true;
        }
        price = splitByIndex(price, ".");
        return "0".equals(price);

    }

    /**
     * 取价格的整数，去掉单位
     *
     * @param price
     * @return
     */
    public static String getPrice(String price) {
        Pattern p = Pattern.compile("^\\d+");
        Matcher m = p.matcher(price);
        if (m.find()) {
            return m.group();
        }
        return "";
    }

    /**
     * 判断是否全为数字
     *
     * @param content
     * @return
     */
    public static boolean isAllNumber(String content) {

        if (isNullOrEmpty(content)) {
            return false;
        }
        Pattern p = Pattern.compile("\\-*\\d+");
        Matcher m = p.matcher(content);
        return m.matches();
    }

    /**
     * 整数转字节数组
     *
     * @param i
     * @return
     */
    public static byte[] intToByte(int i) {
        byte[] bt = new byte[4];
        bt[0] = (byte) (0xff & i);
        bt[1] = (byte) ((0xff00 & i) >> 8);
        bt[2] = (byte) ((0xff0000 & i) >> 16);
        bt[3] = (byte) ((0xff000000 & i) >> 24);
        return bt;
    }

    /**
     * 字节数组转整数
     *
     * @param bytes
     * @return
     */
    public static int bytesToInt(byte[] bytes) {
        int num = bytes[0] & 0xFF;
        num |= ((bytes[1] << 8) & 0xFF00);
        num |= ((bytes[2] << 16) & 0xFF0000);
        num |= ((bytes[3] << 24) & 0xFF000000);
        return num;
    }

    public static boolean isNotNullOrEmpty(String text) {
        return !isNullOrEmpty(text);
    }

    public static boolean isAnyNullOrEmpty(String... strings) {
        for (String s : strings) {
            if (isNullOrEmpty(s)) {
                return true;
            }
        }

        return false;
    }


    public static boolean isAllEmpty(String... strings) {
        for (String s : strings) {
            if (isNotEmpty(s)) {
                return false;
            }
        }
        return true;
    }


    public static boolean isLargerThanX(String text, int i) {
        if (isNullOrEmpty(text)) {
            return false;
        }
        return text.trim().length() > i;
    }

    public static boolean isLengthNotSmallerThanX(String text, int i) {
        if (isNullOrEmpty(text)) {
            return false;
        }
        return text.length() >= i;
    }


    public static boolean startWithCaseIgnore(String tag, String text) {
        if (StringUtils.isNullOrEmpty(tag) || StringUtils.isNullOrEmpty(text)) {
            return false;
        }
        String t = tag.toLowerCase();
        String anchor = text.toLowerCase();
        return anchor.startsWith(t);
    }
}
