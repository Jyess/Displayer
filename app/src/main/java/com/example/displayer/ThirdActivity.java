package com.example.displayer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ThirdActivity extends AppCompatActivity {

    public static final String TAG = ThirdActivity.class.getCanonicalName();
    public static final String URL_API = "https://randomuser.me/api/?results=100";
    protected RequestQueue requestQueue;

    private ImageView icon;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        this.icon = findViewById(R.id.icon);
        new IUTAsyncTask(this, this.icon).execute("https://img.icons8.com/cute-clipart/64/000000/hourglass-sand-bottom.png");

        listView = findViewById(R.id.listView);

        this.requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_API, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i(TAG, "Response : " + response);

                try {
                    JSONObject json = new JSONObject(response);
                    JSONArray array = json.getJSONArray("results");

                    List<Map<String,String>> dataList = new ArrayList<>(0);

                    Map<String, String> dataItem;

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject item = array.getJSONObject(i);
                        JSONObject itemName = item.getJSONObject("name");

                        String first = itemName.getString("first");
                        String last = itemName.getString("last");

                        dataItem = new HashMap<>(0);
                        dataItem.put("first", first);
                        dataItem.put("last", last);

                        dataList.add(dataItem);

                        Log.i(TAG, "User : " + itemName.getString("first") + " " + itemName.getString("last"));
                    }

                    SimpleAdapter sa = new SimpleAdapter(ThirdActivity.this,
                            dataList,
                            R.layout.listview_item,
                            new String[]{"first","last"},
                            new int[]{R.id.first, R.id.last}
                            );

                    listView.setAdapter(sa);

                } catch(JSONException e) {
                    Log.e(TAG, "Error : " + e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error : " + error);
            }
        });

        this.requestQueue.add(stringRequest);
    }
}
