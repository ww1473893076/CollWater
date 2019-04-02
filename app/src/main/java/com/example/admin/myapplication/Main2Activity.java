package com.example.admin.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.Callback;
import okhttp3.Response;

public class Main2Activity extends AppCompatActivity {
    private TextView textView;
    private Button button;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        this.textView = (TextView)findViewById(R.id.textView2);
        this.button = (Button)findViewById(R.id.button);

        this.button.setOnClickListener((v)->{

            startActivity(new Intent(Main2Activity.this,MainActivity.class));

        });

//        this.button.setOnClickListener((v)->{
//
//            startActivity(new Intent(MainActivity.this,Main2Activity.class));
//
//        });

        String weatherUrl ="http://guolin.tech/api/china";
        HttpUtil.sendOkHttpRequest(weatherUrl, new Callback() {
            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText(responseText);
                    }
                });
            }

            @Override
            public void onFailure(okhttp3.Call call, IOException e) {

            }
        });

            }

}
