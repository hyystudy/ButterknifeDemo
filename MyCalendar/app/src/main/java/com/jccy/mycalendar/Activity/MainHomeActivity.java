package com.jccy.mycalendar.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.jccy.mycalendar.Presenter.MainActivityPresenter;
import com.jccy.mycalendar.Presenter.MainHomePresenter;
import com.jccy.mycalendar.R;

/**
 * Created by ivan on 2017/5/29.
 */

public class MainHomeActivity extends AbstractBaseActivity<MainHomePresenter> {

    /**
     * 触发主页面
     * @param context Activity/Context
     *
     */
    public static void launch(Context context) {
        Intent intent = new Intent(context, MainHomeActivity.class);
        context.startActivity(intent);

    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    @Override
    protected Class getPresenterClass() {
        return MainActivityPresenter.class;
    }
}
