package com.quockhang.easy_lib.networkApi;

import com.quockhang.easy_lib.interfaces.INetApi;

public class RetrofitApi implements INetApi {
    private final static RetrofitApi mInstance = new RetrofitApi();
    private RetrofitApi (){

    }
    public static RetrofitApi getmInstance (){
        return mInstance;
    }
}
