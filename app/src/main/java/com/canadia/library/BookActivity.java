package com.canadia.library;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.canadia.library.adapters.BookAdapter;
import com.canadia.library.models.BookModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class BookActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView add;
    RecyclerView recyclerView;
    List<BookModel> list=new ArrayList<BookModel>();
    BookAdapter adapter;
    Context c;
    TextView author_name,author_age,author_province;
    String name,age,province;

    long author_ID;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        bindViewID();
        Intent intent = getIntent();
        author_ID = intent.getLongExtra("id",0);
        name = intent.getStringExtra("author_name");
        author_name.setText(name);
        age = intent.getStringExtra("author_age");
        author_age.setText(age);
        add.setOnClickListener(this);
        province = intent.getStringExtra("author_province");
        author_province.setText(province);
    }
    private void  addData(Context c, long id) {
        RequestQueue queue = Volley.newRequestQueue(c);
        String url = "http://172.16.9.128:8000/api/books/filter/"+id;
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
                                BookModel book = new BookModel().fromJson(data);
//BookModel book = new BookModel(data.getString("title"),data.getString("body"));
                                list.add(book);
                            }
                            recyclerView= findViewById(R.id.books);
                            recyclerView.setLayoutManager(new LinearLayoutManager(c));
                            adapter = new BookAdapter(c,list,list.size());
                            recyclerView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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
    @Override
    public void onResume(){
        super.onResume();
        if(list.size()>0) {
            list.clear();
        }
        addData(this,author_ID);
    }
    public void bindViewID(){
        add = findViewById(R.id.add);
        author_name = findViewById(R.id.name);
        author_age = findViewById(R.id.age);
        author_province = findViewById(R.id.province);
    }
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.add) {
            Intent intent = new Intent(getBaseContext(),PostRequestActivity.class);
            intent.putExtra("author_id",author_ID);
            startActivity(intent);
        }
    }
}