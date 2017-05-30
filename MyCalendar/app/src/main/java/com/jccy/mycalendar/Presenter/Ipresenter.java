package com.jccy.mycalendar.Presenter;

import android.content.Context;

/**
 * Created by ivan on 2017/5/28.
 */

public interface Ipresenter {
    /**
     * @param target view/activity/fragment
     */
    <T> void attach(T target);

    /**
     * @param key    回调接口对应的key
     * @param action 回调接口
     */
    void addAction(int key, RenderAction action);

    /**
     * @param key  回调接口对应的key
     * @param data 传给回调接口的值
     * @return
     */
    boolean invokeAction(int key, Object data);

    Context getContext();

    <T> void detach(T target);
}
