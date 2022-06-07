package com.canadia.library;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.canadia.library.helper.OnButtonClick;
import com.canadia.library.helper.Tool;
import com.canadia.library.models.BookModel;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class BookDetailActivity extends AppCompatActivity implements View.OnClickListener {
    TextView t_title,t_desc;
   ImageView edit,delete;
    String book_detail_s;
    JSONObject book_detail_j;
    BookModel book_Detail;
    Context activityContext;
    Tool tool;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        bindViewID();
        // get data string from intent
        Intent intent = getIntent();
        book_detail_s = intent.getStringExtra("bookDetail");
        activityContext = this;
        tool =new Tool();
        try {
            //convert string to json object
            book_detail_j= new JSONObject(book_detail_s);
            //convert json object to model
            book_Detail = new BookModel().fromJson(book_detail_j);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        t_title.setText(book_Detail.getTitle());
        t_desc.setText(book_Detail.getBody());
        edit.setOnClickListener(this);
        delete.setOnClickListener(this);

    }
    public void bindViewID(){
        t_title = findViewById(R.id.book_title);
        t_desc = findViewById(R.id.book_desc);
        edit = findViewById(R.id.edit);
        delete = findViewById(R.id.delete);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.edit) {
            tool.dialogForm(BookDetailActivity.this, book_Detail, new OnButtonClick() {
                @Override
                public void buttonClick(BookModel data) {
                    updateBookRequest(activityContext, book_Detail);
                    tool.dialog.dismiss();
                    finish();
                }
            });
        }else if(v.getId() == R.id.delete){
            try {
                deleteBookRequest(activityContext,book_Detail.getId());
            } catch (IOException e) {
                e.printStackTrace();
            }
            finish();
        }
    }
    private void updateBookRequest(Context context, BookModel book_Detail_p) {
        // url to post our data
        String url = "http://172.16.9.128:8000/api/books/"+book_Detail_p.getId();
        RequestQueue queue = Volley.newRequestQueue(context);
        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        StringRequest request = new StringRequest(Request.Method.PUT, url, new com.android.volley.Response.Listener<String>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject respObj = new JSONObject(response);
                    String message = respObj.getString("message");
                    Toast.makeText(context,message , Toast.LENGTH_SHORT).show();
                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(context,e.getMessage() , Toast.LENGTH_SHORT).show();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Fail to get response = " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("title", book_Detail_p.getTitle());
                params.put("body", book_Detail_p.getBody());
                params.put("author_id", String.valueOf(book_Detail.getAuthor_id()));
                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };
        queue.add(request);
    }
    private void deleteBookRequest(Context context, long id) throws IOException {
        String url = "http://172.16.9.128:8000/api/books/" + id;
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest request = new StringRequest(Request.Method.DELETE, url, new com.android.volley.Response.Listener<String>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject respObj = new JSONObject(response);
                    String message = respObj.getString("message");
                    Toast.makeText(context,message , Toast.LENGTH_SHORT).show();
                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(context,e.getMessage() , Toast.LENGTH_SHORT).show();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Fail to get response = " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(request);
    }
}