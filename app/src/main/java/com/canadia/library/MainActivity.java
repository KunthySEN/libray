package com.canadia.library;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.canadia.library.adapters.Adapter;
import com.canadia.library.models.AuthorModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<AuthorModel> list=new ArrayList<>();
    Adapter adapter;
    Context context;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addData(this);

    }
    private void  addData(Context context) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "http://172.16.9.128:8000/api/authors";

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject json = new JSONObject(response);
                            JSONArray objectList = json.getJSONArray("data");
                            for (int i=0;i<objectList.length();i++){
                                JSONObject data = objectList.getJSONObject(i);

                                AuthorModel aut = new AuthorModel(data.getLong("id"),data.getString("name"),data.getInt("age"),data.getString("province"));
                                list.add(aut);
                            }
                            recyclerView= findViewById(R.id.authors);
                            recyclerView.setLayoutManager(new LinearLayoutManager(context));
                            adapter = new Adapter(context,list,list.size());
                            recyclerView.setAdapter(adapter);
//                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        // Display the first 500 characters of the response string.
                    }
                }, new Response.ErrorListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onErrorResponse(VolleyError error) {
                //textView.setText("That didn't work!"+error.getMessage());
            }
        });
// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}