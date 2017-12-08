package com.jccy.rxjavaretrofitsamples.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by heyangyang on 2017/12/7.
 */

public class GankBeautyResults {
    boolean error;
    @SerializedName("results")
    public List<GankBeauty> gankBeauties;
}
