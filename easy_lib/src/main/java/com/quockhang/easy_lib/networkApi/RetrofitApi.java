package com.quockhang.easy_lib.networkApi;

import com.quockhang.easy_lib.interfaces.INetApi;
import com.quockhang.easy_lib.models.TransferPacket;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleEmitter;
import io.reactivex.rxjava3.core.SingleOnSubscribe;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

public class RetrofitApi implements INetApi {
    private final static RetrofitApi mInstance = new RetrofitApi();
    private final Retrofit mRetrofit;

    private RetrofitApi() {
        Dispatcher dispatcher = new Dispatcher();
        dispatcher.setMaxRequests(PARALLEL_REQUEST);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
                .readTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
                .writeTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
                .dispatcher(dispatcher)
                .build();

        mRetrofit = new Retrofit.Builder()
                .baseUrl("http://foobar")
                .addConverterFactory(ScalarsConverterFactory.create())
                .client(okHttpClient)
                .build();
    }

    public static RetrofitApi getmInstance() {
        return mInstance;
    }

    public Single<TransferPacket> asyncRequest(String url) {
        return Single.create(new SingleOnSubscribe<TransferPacket>() {

            @Override
            public void subscribe(@NonNull SingleEmitter<TransferPacket> emitter) throws Throwable {

            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }


    protected interface IRetrofitService {
        @GET
        Call<ResponseBody> GetRequest(@Url String url);

        @POST
        Call<ResponseBody> PostRequest(@Url String url);

        @FormUrlEncoded
        @POST
        Call<ResponseBody> PostData(@Url String url, @FieldMap Map<String, String> params);
    }
}
