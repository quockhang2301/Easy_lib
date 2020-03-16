package com.quockhang.easy_lib.networkApi;

import android.util.Log;

import com.quockhang.easy_lib.interfaces.IGetHtmlResponse;
import com.quockhang.easy_lib.models.NetworkStringPacket;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleEmitter;
import io.reactivex.rxjava3.core.SingleOnSubscribe;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public abstract class AbsBaseNetworkApi {
    protected final int TIMEOUT = 4000;
    protected final int PARALLEL_REQUEST = 8;

    public static final int METHOD_GET = 0;
    public static final int METHOD_POST = 1;

    protected AbsBaseNetworkApi() {

    }

    public Disposable asyncTextRequest(NetworkStringPacket packet, IGetHtmlResponse htmlCallback, int html_method) {
        return Single.create(new SingleOnSubscribe<NetworkStringPacket>() {
            @Override
            public void subscribe(@NonNull SingleEmitter<NetworkStringPacket> emitter) throws Throwable {
                asyncGetTextResponse(emitter, packet, html_method);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(success -> {
                    htmlCallback.onGetHtmlResponse(packet);
                }, err -> {
                    Log.e("retrofit err", err + "");
                });
    }

    // custom text khi các thư viện (volley, retrofit,...) request trả về onFailure
    protected abstract String getFailureMessage(Throwable t);

    protected abstract void asyncGetTextResponse(@NonNull SingleEmitter<NetworkStringPacket> emitter, NetworkStringPacket packet, int html_method);


}
