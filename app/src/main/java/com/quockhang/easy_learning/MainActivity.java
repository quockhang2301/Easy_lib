package com.quockhang.easy_learning;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.quockhang.easy_lib.helpers.Simple_help;

public class MainActivity extends AppCompatActivity {

    Button btn_clickme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.w("easy", Simple_help.hello());


        btn_clickme = findViewById(R.id.btn_clickme);
        btn_clickme.setOnClickListener(view -> {

        });
    }
}
