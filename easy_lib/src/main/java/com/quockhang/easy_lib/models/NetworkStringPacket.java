package com.quockhang.easy_lib.models;

import androidx.annotation.NonNull;

public class NetworkStringPacket extends AbsBaseNetworkPacket {

    public String responseText;


    public NetworkStringPacket(String url, String TAG) {
        super(url, TAG);
    }

    @NonNull
    @Override
    public String toString() {
        return "url: " + url + ". statusCode: " + statusCode +
                ". isSuccess: " + isSuccess + ". response: " + responseText;
    }
}
