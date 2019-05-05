package com.example.admin.myapplication;

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

import okhttp3.Callback;
import okhttp3.Response;

public class CityActivity extends AppCompatActivity {
    private String[] data = {"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",};
    private int[] cids = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};


    private static final String TAG = "HelloWorldActivity";
    private TextView textView;
    private Button button;
    private Log log;
    private ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        final int pid = intent.getIntExtra("pid", 0);
        //int cid = intent.getIntExtra("cid", 0);
        log.i("我们接收到了id", "" + pid);
        Log.d(TAG, "onCreate execute");
        this.textView = (TextView) findViewById(R.id.abc);
        this.button = (Button) findViewById(R.id.jxx);

        this.button.setOnClickListener((v) -> {

            startActivity(new Intent(CityActivity.this, ProvinceActivity.class));

        });
        this.listview = (ListView) findViewById(R.id.listview);
                ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, data);
                listview.setAdapter(adapter);

                this.listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Log.i("点击了哪一个",""+position+cids[position] + ":"+data[position]);
                        Intent intent = new Intent(CityActivity.this,CountyActivity.class);
                        intent.putExtra("pid",pid);
                        intent.putExtra("cid",cids[position]);
                        startActivity(intent);
                    }
                });


//        this.button.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public  void onClick(View v){
//                startActivity(new Intent(CityActivity.this,ProvinceActivity.class));
//            }
//        });

        // String weatherId = "CN101020200";
        String weatherUrl = "http://guolin.tech/api/china/"+pid;
        HttpUtil.sendOkHttpRequest(weatherUrl, new Callback() {
            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                parseJSONObject(responseText);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();

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
                this.cids[i]=jsonObject.getInt("id");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}