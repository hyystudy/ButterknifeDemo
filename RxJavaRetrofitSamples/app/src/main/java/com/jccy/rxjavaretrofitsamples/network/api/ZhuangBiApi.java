package com.jccy.rxjavaretrofitsamples.network.api;

import com.jccy.rxjavaretrofitsamples.model.ZhuangBiImage;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by heyangyang on 2017/12/6.
 */

public interface ZhuangBiApi {
    @GET("search")
    Observable<List<ZhuangBiImage>> search(@Query("q") String query);
}
