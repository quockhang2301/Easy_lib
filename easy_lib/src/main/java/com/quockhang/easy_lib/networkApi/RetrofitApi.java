package com.quockhang.easy_lib.networkApi;

import com.quockhang.easy_lib.interfaces.INetApi;

import java.util.Map;
import java.util.concurrent.TimeUnit;

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
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
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
