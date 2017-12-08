package com.jccy.rxjavaretrofitsamples.network.api;

import com.jccy.rxjavaretrofitsamples.model.GankBeautyResults;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by heyangyang on 2017/12/7.
 */

public interface GankApi {
    @GET("data/福利/{number}/{page}")
    Observable<GankBeautyResults> getGankBeauties(@Path("number") int number, @Path("page") int page);
}
