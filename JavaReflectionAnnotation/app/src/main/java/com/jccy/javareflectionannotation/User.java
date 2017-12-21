package com.jccy.javareflectionannotation;

import android.util.Log;

/**
 * Created by heyangyang on 2017/12/21.
 */

public class User {
    @BindName(name = "zal")
    private String name;

    @BindAddress(address = "henan")
    private String address;

    public User() {
    }

    private String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    private String getAddress() {
        return address;
    }

    private void setAddress(String address) {
        this.address = address;
    }

    public void printUser(){
        Log.d("User", "User name: " + name + " address: " + address);
    }

    @BindUrl(url = "apple")
    private void getUserHttp(String params){
        Log.d("MainActivity", "http://www.jianshu.com/" + params);
    }
}
