package com.quockhang.easy_lib.interfaces;

import com.quockhang.easy_lib.models.TransferPacket;

public interface IReceiveData {
    void onReceivedData(TransferPacket packet);
}
