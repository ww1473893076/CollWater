package com.example.admin.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "HelloWorldActivity";
    private TextView textView;
    private Button button;
    private Log log;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        int pid = intent.getIntExtra("pid",0);

        log.i("我们接收到了id",""+pid);
        Log.d(TAG, "onCreate execute");
        this.textView = (TextView)findViewById(R.id.abc);
        this.button=(Button)findViewById(R.id.jxx) ;
        this.button.setOnClickListener((v)->{

                startActivity(new Intent(MainActivity.this,Main2Activity.class));

        });
//        this.button.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public  void onClick(View v){
//                startActivity(new Intent(MainActivity.this,Main2Activity.class));
//            }
//        });

        String weatherId = "CN101020200";

        String weatherUrl = "http://guolin.tech/api/weather?cityid=" + weatherId + "&key=102b365ce560434fbb06a5268e492069";
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

