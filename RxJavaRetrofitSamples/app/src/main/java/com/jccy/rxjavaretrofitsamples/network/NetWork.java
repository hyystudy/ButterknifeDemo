package com.jccy.rxjavaretrofitsamples.network;

import com.jccy.rxjavaretrofitsamples.network.api.GankApi;
import com.jccy.rxjavaretrofitsamples.network.api.ZhuangBiApi;

import okhttp3.OkHttpClient;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by heyangyang on 2017/12/6.
 */

public class NetWork {

    public static ZhuangBiApi zhuangBiApi;
    public static GankApi gankApi;
    public static OkHttpClient okHttpClient = new OkHttpClient();
    public static Converter.Factory converterFactory = GsonConverterFactory.create();

    public static ZhuangBiApi getZhuangBiApi(){
        if (zhuangBiApi == null){
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl("http://www.zhuangbi.info/")
                    .addConverterFactory(converterFactory)//设置数据解析器
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//支持Rxjava平台
                    .build();

            zhuangBiApi = retrofit.create(ZhuangBiApi.class);
        }
        return zhuangBiApi;
    }

    public static GankApi getGankApi(){
        if (gankApi == null){
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl("http://gank.io/api/")
                    .addConverterFactory(converterFactory)//设置数据解析器
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//支持Rxjava平台
                    .build();

            gankApi = retrofit.create(GankApi.class);
        }
        return gankApi;
    }
}
