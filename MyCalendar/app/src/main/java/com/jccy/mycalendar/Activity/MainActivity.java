package com.jccy.mycalendar.Activity;

import android.os.Bundle;
import android.widget.Toast;


import com.jccy.mycalendar.EventBus.EventBusEntity;
import com.jccy.mycalendar.Presenter.MainActivityPresenter;
import com.jccy.mycalendar.Presenter.RenderAction;
import com.jccy.mycalendar.R;

import org.greenrobot.eventbus.Subscribe;

public class MainActivity extends AbstractBaseActivity<MainActivityPresenter> {



    RenderAction renderAction=new RenderAction() {
        @Override
        public void onUIUpdate(Object value) {

            MainHomeActivity.launch(context);
            finish();

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setNoTitle();
        setContentView(R.layout.activity_main);

        getPresenter().addAction(MainActivityPresenter.CHECK_LOGIN,renderAction );

        getPresenter().delayedInvokeAction(renderAction,2000);
    }
    @Override
    protected Class getPresenterClass() {
        return MainActivityPresenter.class;
    }
}
