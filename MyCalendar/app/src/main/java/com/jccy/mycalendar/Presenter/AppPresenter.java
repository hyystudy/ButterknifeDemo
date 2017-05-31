package com.jccy.mycalendar.Presenter;


/**
 * Created by ivan on 2017/5/31.
 */

public class AppPresenter extends BasePresenter {

    @Override
    protected boolean registerBusEvent() {
        return true;
    }
    private AppPresenter(){

    }
    private static class SingletonHolder {
        private static final AppPresenter INSTANCE = new AppPresenter();
    }
    public static final AppPresenter getInstance() {
        return AppPresenter.SingletonHolder.INSTANCE;
    }
}
