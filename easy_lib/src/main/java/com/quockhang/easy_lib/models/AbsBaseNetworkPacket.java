package com.quockhang.easy_lib.models;

import java.util.HashMap;
import java.util.Map;

public abstract class AbsBaseNetworkPacket {

    public final String TAG;

    public final String url;

    public final Map<String, String> params = new HashMap<>();

    // optional client data
    public final Map<String, Object> sender_data = new HashMap<>();

    public boolean isSuccess;

    public int statusCode;

    protected AbsBaseNetworkPacket(String url, String TAG) {
        this.url = url;
        this.TAG = TAG;
    }


}
