package com.example.admin.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterViewAnimator;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.text.BreakIterator;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class CountyActivity extends AppCompatActivity {
    private TextView textView =null;
    private String[] data = {"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",};
   private  String[] weather_ids={"","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","",};


    private ListView contrylistview;
    private int[] pids;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_county);
        this.textView = (TextView) findViewById(R.id.textView);
        this.contrylistview = (ListView) findViewById(R.id.contrylistview);
        Intent intent = getIntent();
        int pid = intent.getIntExtra("pid", 0);
        int cityid = intent.getIntExtra("cid", 0);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, data);
        contrylistview.setAdapter(adapter);
        this.contrylistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Log.i("点击了哪一个",""+position+":" + ":"ion]);
                Intent intent = new Intent(CountyActivity.this,Weatherctivity.class);
               //intent.putExtra("pid",pid);
                intent.putExtra("wid",weather_ids[position]);
                startActivity(intent);
            }
        });

        String url = "http://guolin.tech/api/china/"+pid+"/"+cityid;
        HttpUtil.sendOkHttpRequest(url, new Callback() {
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
            public void onFailure(Call call, IOException e) {
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
                this.weather_ids[i]=jsonObject.getString("wid");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}



