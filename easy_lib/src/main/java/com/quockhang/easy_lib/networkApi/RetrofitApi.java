package com.quockhang.easy_lib.networkApi;

import android.util.Log;

import com.quockhang.easy_lib.models.NetworkStringPacket;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleEmitter;
import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

public class RetrofitApi extends AbsBaseNetworkApi {
    //private final static RetrofitApi mInstance = new RetrofitApi();
    private final Retrofit mRetrofit;

    public RetrofitApi() {
        super();
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


//    public static RetrofitApi getmInstance() {
//        return mInstance;
//    }

    @Override
    protected String getFailureMessage(Throwable t) {
        if (t instanceof SocketTimeoutException) {
            return "Lỗi máy chủ phản hồi quá lâu";
        }
        return t.getMessage() + "";
    }

    @Override
    protected void asyncGetTextResponse(@NonNull SingleEmitter<NetworkStringPacket> emitter,
                                        NetworkStringPacket packet, int html_method) {
        IRetrofitService service = mRetrofit.create(IRetrofitService.class);
        Call<ResponseBody> callString = null;
        if (html_method == METHOD_GET) {
            callString = service.GetRequest(packet.url);
        } else if (html_method == METHOD_POST) {
            callString = service.PostData(packet.url, packet.params);
        }
        if (callString == null) {
            emitter.onError(new NullPointerException("unknown html_method '" + html_method + "'"));
        }

        Log.w("retrofit", "connecting to " + packet.url);
        callString.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                packet.statusCode = response.code();
                packet.isSuccess = response.isSuccessful();
                if (response.isSuccessful()) {
                    try {
                        packet.responseText = response.body().string();
                        emitter.onSuccess(packet);
                    } catch (IOException e) {
                        //e.printStackTrace();
                        emitter.onError(e);
                    }
                } else {
                    try {
                        packet.responseText = response.errorBody().string();
                        emitter.onSuccess(packet);
                    } catch (IOException e) {
                        //e.printStackTrace();
                        emitter.onError(e);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("retrofit Failure", t + "");
                packet.isSuccess = false;
                packet.statusCode = 0;
                packet.responseText = getFailureMessage(t);
                emitter.onSuccess(packet);
            }
        });
    }


//    public Single<TransferPacket> asyncRequest(String url) {
//        return Single.create(new SingleOnSubscribe<TransferPacket>() {
//
//            @Override
//            public void subscribe(@NonNull SingleEmitter<TransferPacket> emitter) throws Throwable {
//
//            }
//        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
//    }


    protected interface IRetrofitService {
        @GET
        Call<ResponseBody> GetRequest(@Url String url);

//        @POST
//        Call<ResponseBody> PostRequest(@Url String url);

        @FormUrlEncoded
        @POST
        Call<ResponseBody> PostData(@Url String url, @FieldMap Map<String, String> params);
    }
}
