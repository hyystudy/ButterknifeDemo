package com.jccy.mycalendar;

import android.app.Application;

import com.jccy.mycalendar.Presenter.AppPresenter;
import com.jccy.mycalendar.realm.RealmUtils;


/**
 * Created by ivan on 2017/5/28.
 */

public class CalendarApplication extends Application {
    private static CalendarApplication mInstance;
    private AppPresenter appPresenter;
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        appPresenter=AppPresenter.getInstance();
        appPresenter.attach(this);
        System.out.println("我初始化了");
        RealmUtils.getRealmInstance(this);
    }
    public static CalendarApplication getInstance() {
        return mInstance;
    }
}
