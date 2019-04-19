package com.example.admin.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Callback;
import okhttp3.Response;

public class Main2Activity extends AppCompatActivity {
    private TextView textView;
    private Button button;
    private ListView listview;

    private List<String> data2=new ArrayList();
    private String[] data = {"北京", "浙江", "安徽","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","",};
    private String[] result;
    private int[] pids = new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
    //    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        this.textView = (TextView) findViewById(R.id.textView2);
        this.button = (Button) findViewById(R.id.button);
        this.listview = (ListView) findViewById(R.id.listview);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, data);
        listview.setAdapter(adapter);

        this.listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("点击了哪一个",""+position+Main2Activity.this.pids[position] + ":"+Main2Activity.this.data[position]);
                Intent intent = new Intent(Main2Activity.this,MainActivity.class);
                intent.putExtra("pid",Main2Activity.this.pids[position]);
                startActivity(intent);
            }
        });
        this.button.setOnClickListener((v) -> {
            startActivity(new Intent(Main2Activity.this, MainActivity.class));
        });
//        this.button.setOnClickListener((v)->{
//
//            startActivity(new Intent(MainActivity.this,Main2Activity.class));
//
//        });
        String weatherUrl = "http://guolin.tech/api/china";
        HttpUtil.sendOkHttpRequest(weatherUrl, new Callback() {
            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                final String responseText = response.body().string();
               parseJSONObject(responseText);

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
    private void parseJSONObject(String responseText) {
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(responseText);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                 jsonObject.getString("name");
                this.data[i] = jsonObject.getString("name");
                this.pids[i]=jsonObject.getInt("id");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
