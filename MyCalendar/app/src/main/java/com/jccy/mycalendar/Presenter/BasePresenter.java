package com.jccy.mycalendar.Presenter;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.UiThread;
import android.support.v4.app.Fragment;
import android.support.v4.os.AsyncTaskCompat;
import android.support.v4.util.SparseArrayCompat;
import android.view.View;

import com.jccy.mycalendar.BuildConfig;
import com.jccy.mycalendar.CalendarApplication;
import com.jccy.mycalendar.EventBus.EventBusManager;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by ivan on 2017/5/28.
 * presenter的基类
 */

public abstract class BasePresenter implements Ipresenter {

    private Object mTarget;

    SparseArrayCompat<RenderAction> mActionList;
    SparseArrayCompat<WeakReference<RenderAction>> mCachedAction;
    List<AsyncTask<Long, Void, Void>> mDelayedTask;
    HashMap<String, RenderAction> mDelayedAction;
    protected abstract boolean registerBusEvent();

    @Override
    public <T> void attach(T target) {
        this.mTarget=target;
        if (registerBusEvent()) {
            try {
                EventBusManager.eventBus().register(this);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 延时执行操作
     *
     * @param action 延迟动作
     * @param mills  单位毫秒，默认为1000毫秒
     */
    @UiThread
    public void delayedInvokeAction(RenderAction<Void> action, long... mills) {
        if (mDelayedTask == null) {
            mDelayedTask = new ArrayList<>();
            mDelayedAction = new HashMap<>();
        }
        AsyncTask<Long, Void, Void> task = new AsyncTask<Long, Void, Void>() {
            @Override
            protected Void doInBackground(Long[] params) {
                try {
                    Thread.sleep(params != null && params.length > 0 ? params[0] : 1000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void v) {
                if (mTarget!=null) {
                    RenderAction action = mDelayedAction.get(toString());
                    if (action != null) {
                        try {
                            action.onUIUpdate(null);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        };
        mDelayedAction.put(task.toString(), action);
        mDelayedTask.add(task);
        if (mills != null && mills.length > 1) {
            AsyncTaskCompat.executeParallel(task, mills[0]);
        } else {
            AsyncTaskCompat.executeParallel(task);
        }
    }

    //后使用
    @Override
    public void addAction(int key, RenderAction action) {
        if (mActionList == null) {
            mActionList = new SparseArrayCompat<>();
        }
        mActionList.put(key, action);
    }

    //先调用
    @Override
    public boolean invokeAction(int key, Object data) {
        if (mActionList != null && mTarget!=null) {
            try {
                RenderAction action = mActionList.get(key);
                if (action != null) {
                    action.onUIUpdate(data);
                } else {
                    System.out.println("action==null");
                }
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                if (BuildConfig.DEBUG) {
                    throw new RuntimeException(e);
                }
            }
        } else {
            System.out.println("mActionList==null");
        }
        return false;
    }

    @Override
    public Context getContext() {
        if (getTarget() instanceof View) {
            return ((View) getTarget()).getContext();
        } else if (getTarget() instanceof Fragment) {
            return ((Fragment) getTarget()).getActivity();
        } else if (getTarget() instanceof Context) {
            return (Context) getTarget();
        } else {
            return CalendarApplication.getInstance();
        }
    }

    @Override
    public <T> void detach(T target) {
        if (registerBusEvent()) {
            try {
                EventBusManager.eventBus().unregister(this);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
        if (mActionList!=null){
            mActionList.clear();
        }
        mTarget=null;

    }

    public Object getTarget() {
        return mTarget;
    }
}
