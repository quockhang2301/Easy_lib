package com.quockhang.easy_learning;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.quockhang.easy_lib.helpers.Simple_help;
import com.quockhang.easy_lib.viewModels.AbsBaseViewModel;

public class MainActivity extends AppCompatActivity {

    Button btn_clickme;
    AbsBaseViewModel mymodel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mymodel = new ViewModelProvider(this).get(AbsBaseViewModel.class);
        //mymodel = new AbsBaseViewModel();

        Log.w("easy", Simple_help.hello());


        btn_clickme = findViewById(R.id.btn_clickme);
        btn_clickme.setOnClickListener(view -> {
            mymodel.send_request();
        });

        mymodel.getLiveData().removeObservers(this);
        mymodel.getLiveData().observe(this, packet -> {
            Log.w("packet 1", packet + "");

        });


    }

    @Override
    protected void onDestroy() {
        mymodel.getLiveData().removeObservers(this);
        super.onDestroy();
    }


}
