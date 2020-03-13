package com.quockhang.easy_lib.models;

public class TransferPacket {

    // chuyển nhận dữ liệu 1 chiều hoặc 2 chiều
    public final String TAG;

    // đầu gửi truyền data vào request_data
    public Object request_data;

    // đầu nhận truyền data vào result_data rùi gửi trả lại cho sender
    public Object result_data;

    public TransferPacket(String TAG, Object request_data) {
        this.TAG = TAG;
        this.request_data = request_data;
    }
}
