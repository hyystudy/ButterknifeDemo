package com.jccy.rxjavaretrofitsamples.module;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.Toast;

import com.jccy.rxjavaretrofitsamples.BaseFragment;
import com.jccy.rxjavaretrofitsamples.R;
import com.jccy.rxjavaretrofitsamples.adapter.ItemListAdapter;
import com.jccy.rxjavaretrofitsamples.model.GankBeauty;
import com.jccy.rxjavaretrofitsamples.model.GankBeautyResults;
import com.jccy.rxjavaretrofitsamples.model.Item;
import com.jccy.rxjavaretrofitsamples.model.ZhuangBiImage;
import com.jccy.rxjavaretrofitsamples.network.NetWork;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by heyangyang on 2017/12/6.
 */

public class ZipFragment extends BaseFragment {

    private static final String TAG = "ZipFragment";
    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh_layout) SwipeRefreshLayout swipeRefreshLayout;
    private ItemListAdapter listAdapter;

    @OnClick(R.id.help)
    void onLoadClicked(){
        new AlertDialog.Builder(getContext())
                .setTitle(getDialogTitleId())
                .setView(getDialogRes())
                .show();
    }

   @OnClick(R.id.load)
   void onLoadBtnClicked(){
        loadGankBeautiesAndZhuangbiImages();
   }

    private void loadGankBeautiesAndZhuangbiImages() {
       swipeRefreshLayout.setRefreshing(true);
        unSubscribe();
       Observable.zip(NetWork.getGankApi()
               .getGankBeauties(200, 1), NetWork.getZhuangBiApi().search("装逼"), new BiFunction<GankBeautyResults, List<ZhuangBiImage>, List<Item>>() {
           @Override
           public List<Item> apply(GankBeautyResults gankBeautyResults, List<ZhuangBiImage> zhuangBiImages) throws Exception {
               List<Item> items = new ArrayList<>();
               List<Item> gankResult = mapGankResult(gankBeautyResults.gankBeauties);
               for (int i = 0; i < gankResult.size()/2; i++){
                   items.add(gankResult.get(i * 2));
                   items.add(gankResult.get(i * 2 + 1));
               }

               for (int i = 0; i < Math.min(50, zhuangBiImages.size()); i++){
                   Item item = new Item();
                   ZhuangBiImage zhuangBiImage = zhuangBiImages.get(i);
                   item.des = zhuangBiImage.description;
                   item.image_url = zhuangBiImage.image_url;
                   items.add(i * 3 + 2 , item);
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
                        Toast.makeText(getContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private List<Item> mapGankResult(List<GankBeauty> gankBeauties) {
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


    @Override
    protected void initView() {
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        listAdapter = new ItemListAdapter(getContext());
        recyclerView.setAdapter(listAdapter);
        swipeRefreshLayout.setEnabled(false);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.zip_fragment_layout;
    }

    @Override
    protected int getDialogTitleId() {
        return R.string.title_zip;
    }

    @Override
    protected int getDialogRes() {
        return R.layout.zip_dialog_layout;
    }

}
