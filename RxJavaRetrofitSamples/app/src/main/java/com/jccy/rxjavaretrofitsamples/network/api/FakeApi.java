package com.jccy.rxjavaretrofitsamples.network.api;

import com.jccy.rxjavaretrofitsamples.model.FakeThing;
import com.jccy.rxjavaretrofitsamples.model.FakeToken;

import java.util.Random;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * Created by heyangyang on 2017/12/10.
 */

public class FakeApi {
    
    public static Observable<FakeToken> getFakeToken(final String path){
        return Observable.just(path)
                .map(new Function<String, FakeToken>() {
                    @Override
                    public FakeToken apply(String s) throws Exception {
                        FakeToken fakeToken = new FakeToken();
                        long fakeNetworkTimeCost = new Random().nextInt(500) + 500;
                        try{
                            Thread.sleep(fakeNetworkTimeCost);
                        }catch (InterruptedException e){

                        }
                        fakeToken.token = createToken(path);
                        return fakeToken;
                    }
                });
    }

    private static String createToken(String path) {
        return "fake_token_" + path;
    }


    public static Observable<FakeThing> getFakeData(FakeToken fakeToken){
        return Observable.just(fakeToken)
                .map(new Function<FakeToken, FakeThing>() {
                    @Override
                    public FakeThing apply(FakeToken fakeToken) throws Exception {
                        long fakeNetworkTimeCost = new Random().nextInt(500) + 500;
                        try{
                            Thread.sleep(fakeNetworkTimeCost);
                        }catch (InterruptedException e){

                        }
                        FakeThing fakeThing = new FakeThing();
                        fakeThing.id = new Random().nextInt(1000);
                        fakeThing.name = "FAKE_USER_" + fakeThing.id;
                        return fakeThing;
                    }
                });
    }
}
