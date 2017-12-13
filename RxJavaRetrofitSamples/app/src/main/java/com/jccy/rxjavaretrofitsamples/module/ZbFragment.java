package com.jccy.rxjavaretrofitsamples.module;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.Toast;

import com.jccy.rxjavaretrofitsamples.BaseFragment;
import com.jccy.rxjavaretrofitsamples.R;
import com.jccy.rxjavaretrofitsamples.adapter.ZbListAdapter;
import com.jccy.rxjavaretrofitsamples.model.ZhuangBiImage;
import com.jccy.rxjavaretrofitsamples.network.NetWork;

import java.util.List;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by heyangyang on 2017/12/6.
 */

public class ZbFragment extends BaseFragment {

    private static final String TAG = "ZbFragment";
    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    private ZbListAdapter zbListAdapter;


    @OnClick(R.id.help)
    void onLoadClicked(){
        new AlertDialog.Builder(getContext())
                .setTitle(getDialogTitleId())
                .setView(getDialogRes())
                .show();
    }


    @OnCheckedChanged({R.id.radio_one, R.id.radio_two, R.id.radio_three, R.id.radio_four})
    void onCheckChanged(RadioButton radioButton, boolean checked){
        if (checked){
            unSubscribe();
            swipeRefreshLayout.setRefreshing(true);
            loadPictures(radioButton.getText().toString());
        }
    }

    private void loadPictures(String key) {
        NetWork.getZhuangBiApi()
                .search(key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<ZhuangBiImage>>() {
                    @Override
                    public void accept(List<ZhuangBiImage> zhuangBiImages) throws Exception {
                        swipeRefreshLayout.setRefreshing(false);
                        zbListAdapter.setImages(zhuangBiImages);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        swipeRefreshLayout.setRefreshing(false);
                        Log.d(TAG, "load failed");
                        Toast.makeText(getContext(), "Load failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void initView() {
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        zbListAdapter = new ZbListAdapter(getContext());
        recyclerView.setAdapter(zbListAdapter);

        swipeRefreshLayout.setEnabled(false);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.zb_fragment_layout;
    }

    @Override
    protected int getDialogTitleId() {
        return R.string.base;
    }

    @Override
    protected int getDialogRes() {
        return R.layout.zb_dialog_layout;
    }

}
