package com.icodeyou.happyexpress.model;

/**
 * Created by huan on 16/4/8.
 */
public interface RequestCallback<T> {

    void onSuccess(T t);
    void onFail(T t);

}
