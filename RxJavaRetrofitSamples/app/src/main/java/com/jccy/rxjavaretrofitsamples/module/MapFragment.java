package com.jccy.rxjavaretrofitsamples.module;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.Toast;

import com.jccy.rxjavaretrofitsamples.BaseFragment;
import com.jccy.rxjavaretrofitsamples.adapter.ItemListAdapter;
import com.jccy.rxjavaretrofitsamples.R;
import com.jccy.rxjavaretrofitsamples.model.GankBeauty;
import com.jccy.rxjavaretrofitsamples.model.GankBeautyResults;
import com.jccy.rxjavaretrofitsamples.model.Item;
import com.jccy.rxjavaretrofitsamples.network.NetWork;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by heyangyang on 2017/12/6.
 */

public class MapFragment extends BaseFragment {

    private static final String TAG = "MapFragment";
    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh_layout) SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.next_page)
    Button mNextBtn;
    @BindView(R.id.last_page)
    Button mLastBtn;
    private ItemListAdapter listAdapter;
    private int mCurrentPage;


    @OnClick(R.id.help)
    void onLoadClicked(){
        new AlertDialog.Builder(getContext())
                .setTitle(getDialogTitleId())
                .setView(getDialogRes())
                .show();
    }

    @OnClick(R.id.next_page)
    void onNextBtnClicked(){
        mCurrentPage ++;
        mLastBtn.setEnabled(mCurrentPage > 1);
        swipeRefreshLayout.setRefreshing(true);
        loadGankBeauties();
    }

    @OnClick(R.id.last_page)
    void onLastBtnClicked(){
        mCurrentPage --;
        mLastBtn.setEnabled(mCurrentPage > 1);
        swipeRefreshLayout.setRefreshing(true);
        loadGankBeauties();
    }

    private void loadGankBeauties() {
        unSubscribe();
        NetWork.getGankApi()
                .getGankBeauties(10, mCurrentPage)
                .map(new Function<GankBeautyResults, List<Item>>() {
                    @Override
                    public List<Item> apply(GankBeautyResults gankBeautyResults) throws Exception {
                        List<GankBeauty> gankBeauties = gankBeautyResults.gankBeauties;
                        ArrayList<Item> items = new ArrayList<>(gankBeauties.size());

                        SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SS'Z'");
                        SimpleDateFormat outputDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                        for (GankBeauty gankBeauty :
                                gankBeauties) {
                            Item item = new Item();
                            item.image_url = gankBeauty.url;
                            try{
                                Date date = inputDateFormat.parse(gankBeauty.createdAt);
                                item.des = outputDateFormat.format(date);
                            }catch (Exception e){
                                item.des = "unknown date";
                            }
                            items.add(item);
                        }
                        return items;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Item>>() {
                    @Override
                    public void accept(List<Item> items) throws Exception {
                        swipeRefreshLayout.setRefreshing(false);
                        listAdapter.setImages(items);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        swipeRefreshLayout.setRefreshing(false);
                        Toast.makeText(getContext(), "Load Failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    @Override
    protected void initView() {
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        listAdapter = new ItemListAdapter(getContext());
        recyclerView.setAdapter(listAdapter);

        mLastBtn.setEnabled(mCurrentPage > 0);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.map_fragment_layout;
    }

    @Override
    protected int getDialogTitleId() {
        return R.string.title_map;
    }

    @Override
    protected int getDialogRes() {
        return R.layout.map_dialog_layout;
    }

}
