package com.jccy.rxjavaretrofitsamples;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.Disposable;

/**
 * Created by heyangyang on 2017/12/6.
 */

public abstract class BaseFragment extends Fragment {

    protected Unbinder unbinder;
    protected Disposable disposable;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        unbinder = ButterKnife.bind(this, view);

        initView();
        return view;
    }

    protected abstract void initView();

    protected abstract int getLayoutId();

    protected abstract int getDialogTitleId();

    protected abstract int getDialogRes();


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

        unSubscribe();
    }

    protected void unSubscribe() {
        if (disposable != null && !disposable.isDisposed()){
            disposable.dispose();
        }
    }
}
