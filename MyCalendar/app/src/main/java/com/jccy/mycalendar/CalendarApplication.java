package com.jccy.mycalendar;

import android.app.Application;

/**
 * Created by ivan on 2017/5/28.
 */

public class CalendarApplication extends Application {
    private static CalendarApplication mInstance;
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance=this;

    }
    public static CalendarApplication getInstance() {
        return mInstance;
    }
}
