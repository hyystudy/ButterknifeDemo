package com.jccy.mycalendar.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.jccy.mycalendar.EventBus.EventBusEntity;
import com.jccy.mycalendar.EventBus.EventBusManager;
import com.jccy.mycalendar.Presenter.BasePresenter;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by ivan on 2017/5/28.
 *
 * @Description 抽象的Activity基类，其它的Activity子类继承于该基类，并实现其中的抽象方法。
 */

public abstract class AbstractBaseActivity<P extends BasePresenter> extends AppCompatActivity {
    private P mPresenter;
    Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=this;
        if (isEventBusEnable()) {
            onRegisterEventBus();
        }

        if (getPresenterClass() != null) {
            try {
                //使用newInstance()来生成实例(反射)
                mPresenter = getPresenterClass().newInstance();
                mPresenter.attach(this);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 针对不同的activity设置全屏和title
     */
    public void setNoTitle() {
        //设置无标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    public void setFullScreen() {
        //设置全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    /**
     * 默认注册，无需注册的页面直接复写并返回false即可
     *
     * @return 是否注册EventBus
     */
    protected boolean isEventBusEnable() {
        return true;
    }

    protected P getPresenter() {
        return mPresenter;
    }

    protected abstract Class<P> getPresenterClass();

    /**
     * 空的订阅者，防止eventbus崩溃
     * */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMainEvent(EventBusEntity.Open event){

    }
    /***
     *
     * 注册事件总线
     */
    protected void onRegisterEventBus() {
        EventBusManager.eventBus().register(this);
    }

    /**
     * 注销事件总线
     */
    protected void onUnregisterEventBus() {
        EventBusManager.eventBus().unregister(this);
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isEventBusEnable()) {
            onUnregisterEventBus();
        }
    }
}
