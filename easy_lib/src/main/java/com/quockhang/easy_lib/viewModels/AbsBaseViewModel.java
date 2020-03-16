package com.quockhang.easy_lib.viewModels;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.quockhang.easy_lib.interfaces.IGetHtmlResponse;
import com.quockhang.easy_lib.models.AbsBaseNetworkPacket;
import com.quockhang.easy_lib.models.NetworkStringPacket;
import com.quockhang.easy_lib.models.TransferPacket;
import com.quockhang.easy_lib.networkApi.AbsBaseNetworkApi;
import com.quockhang.easy_lib.networkApi.RetrofitApi;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class AbsBaseViewModel extends ViewModel implements IGetHtmlResponse {

    protected final CompositeDisposable disposables = new CompositeDisposable();
    protected final AbsBaseNetworkApi networkApi = new RetrofitApi();
    protected MutableLiveData<TransferPacket> liveData;

    public void send_request() {
        disposables.add(networkApi.asyncTextRequest(new NetworkStringPacket("https://stackoverflow.com/questions/41826478/do-i-have-to-unsubscribe-from-completed-observable", "ahihi"),
                this, AbsBaseNetworkApi.METHOD_GET));
    }

    @Override
    public void onGetHtmlResponse(AbsBaseNetworkPacket packet) {
        Log.w("HtmlResponse", packet + "");
        String TAG = packet.TAG;
        if (TAG.equals("ahihi")) {
            NetworkStringPacket stringPacket = (NetworkStringPacket) packet;
            liveData.setValue(new TransferPacket(packet.TAG, stringPacket.responseText));
        }
    }

    public MutableLiveData<TransferPacket> getLiveData() {
        if (liveData == null) {
            liveData = new MutableLiveData<>();
        }
        return liveData;
    }

    @Override
    protected void onCleared() {
        disposables.clear();
        disposables.dispose();
        super.onCleared();
    }
}
