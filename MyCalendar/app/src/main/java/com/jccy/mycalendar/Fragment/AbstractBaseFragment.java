package com.jccy.mycalendar.Fragment;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jccy.mycalendar.Presenter.BasePresenter;


/**
 *
 * Created by ivan on 2017/5/31.
 * @Description: Fragment的基类
 *
 */
public abstract class AbstractBaseFragment<P extends BasePresenter> extends Fragment {

    protected P mPresenter;

    public AbstractBaseFragment() {
    }

    /**
     * 默认不开启，需要的页面自行开启即可
     * @return
     */
    protected boolean isControllerEnable() {
        return false;
    }

    protected boolean isEventBusEnable() {
        return true;
    }

    protected abstract Class<? extends P> getPresenterClass();

    protected P getPresenter() {
        return mPresenter;
    }

    /**
     * fragment加入Activity时的回调方法。
     */
    @Override
    public final void onAttach( Activity activity ) {
        super.onAttach(activity);

    }

    /**
     * 初始化操作 资源对象&全局ApplicationContext
     */
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        if (getPresenterClass() != null) {
            try {
                mPresenter = getPresenterClass().newInstance();
            } catch (java.lang.InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        if (mPresenter != null) {
            mPresenter.attach(this);
        }
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState ) {
        View view = null;
        if (getLayoutId() != -1) {
            view = inflater.inflate(getLayoutId(), container, false);
        }
        initView(view);
        return view;
    }

    @Override
    public void onActivityCreated( Bundle savedInstanceState ) {
        super.onActivityCreated(savedInstanceState);
        if (isEventBusEnable()) {
            onRegisterEventBus();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (isEventBusEnable()) {
            onUnRegisterEventBus();
        }

        if (mPresenter != null) {
            mPresenter.detach(this);
        }
    }

    /**
     * 获取需要给当前的Fragment设置的layout文件的id
     *
     * @return 直接return R.layout.layout_id;
     */
    public abstract int getLayoutId();

    /**
     * 执行控件初始化操作，可以在此初始化皮肤
     *
     * @param view
     *            根据{@link #getLayoutId()}返回的id创建的view
     */
    public abstract void initView( View view );

    public void onRegisterEventBus () {
    }

    public void onUnRegisterEventBus () {
    }
    
    @Override
    public void onResume() {
    	super.onResume();

    }
    
    @Override
    public void onPause() {
    	super.onPause();
    }

}