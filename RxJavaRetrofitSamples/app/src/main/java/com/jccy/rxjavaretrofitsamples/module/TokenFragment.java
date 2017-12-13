package com.jccy.rxjavaretrofitsamples.module;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.jccy.rxjavaretrofitsamples.BaseFragment;
import com.jccy.rxjavaretrofitsamples.R;
import com.jccy.rxjavaretrofitsamples.adapter.ItemListAdapter;
import com.jccy.rxjavaretrofitsamples.model.FakeThing;
import com.jccy.rxjavaretrofitsamples.model.FakeToken;
import com.jccy.rxjavaretrofitsamples.model.GankBeauty;
import com.jccy.rxjavaretrofitsamples.model.GankBeautyResults;
import com.jccy.rxjavaretrofitsamples.model.Item;
import com.jccy.rxjavaretrofitsamples.model.ZhuangBiImage;
import com.jccy.rxjavaretrofitsamples.network.NetWork;
import com.jccy.rxjavaretrofitsamples.network.api.FakeApi;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by heyangyang on 2017/12/6.
 */

public class TokenFragment extends BaseFragment {

    private static final String TAG = "TokenFragment";

    @BindView(R.id.user_text)
    TextView mTvUser;
    @OnClick(R.id.help)
    void onLoadClicked(){
        new AlertDialog.Builder(getContext())
                .setTitle(getDialogTitleId())
                .setView(getDialogRes())
                .show();
    }

   @OnClick(R.id.load)
   void onLoadBtnClicked(){
       loadFakeThing();
   }

    private void loadFakeThing() {
        FakeApi.getFakeToken("fake_path")
                .flatMap(new Function<FakeToken, Observable<FakeThing>>() {
                    @Override
                    public Observable<FakeThing> apply(FakeToken fakeToken) throws Exception {
                        return FakeApi.getFakeData(fakeToken);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<FakeThing>() {
                    @Override
                    public void accept(FakeThing fakeThing) throws Exception {
                        mTvUser.setText(getString(R.string.user_data, fakeThing.id, fakeThing.name));
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(getContext(), "Load Failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void initView() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.token_fragment_layout;
    }

    @Override
    protected int getDialogTitleId() {
        return R.string.title_zip;
    }

    @Override
    protected int getDialogRes() {
        return R.layout.token_dialog_layout;
    }

}
