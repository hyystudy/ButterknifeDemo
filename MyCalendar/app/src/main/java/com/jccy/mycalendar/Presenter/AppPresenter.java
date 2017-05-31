package com.jccy.mycalendar.Presenter;

import com.jccy.mycalendar.DaoEntity.DaoMaster;
import com.jccy.mycalendar.DaoEntity.DaoSession;

import org.greenrobot.greendao.database.Database;

/**
 * Created by ivan on 2017/5/31.
 */

public class AppPresenter extends BasePresenter {
//    /**
//     * 一个标志，标准的SQLite加密成SQLCipher开关.
//     */
//    private static final boolean ENCRYPTED = false;
//    private DaoSession daoSession;
//    public DaoSession getDaoSession() {
//        return daoSession;
//    }
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
