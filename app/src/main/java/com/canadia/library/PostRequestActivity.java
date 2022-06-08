package com.canadia.library;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PostRequestActivity extends AppCompatActivity {
    private EditText title, body;
    private Button send;
    long author_ID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_request);
        bindViewID();
        getDataFromIntent();
        // adding on click listener to our button.
        action();

    }
    // initializing our views
    public void bindViewID(){
        title = findViewById(R.id.title);
        body = findViewById(R.id.body);
        send = findViewById(R.id.btn_send);
    }
    // data get from intent
    public void getDataFromIntent(){
        Intent intent = getIntent();
        author_ID = intent.getLongExtra("author_id",0);
    }
    public void action(){
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // validating if the text field is empty or not.
                if (title.getText().toString().isEmpty() && body.getText().toString().isEmpty()) {
                    Toast.makeText(PostRequestActivity.this, "Please enter both the values", Toast.LENGTH_SHORT).show();
                    return;
                }
                // calling a method to post the data and passing our title ,body and author_id.
                postDataUsingVolley(title.getText().toString(), body.getText().toString(),String.valueOf(author_ID));
            }
        });
    }
    private void postDataUsingVolley(String title, String body, String author_ID) {
        // url to post our data
        String url = "http://172.16.9.128:8000/api/books";
        RequestQueue queue = Volley.newRequestQueue(PostRequestActivity.this);
        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        StringRequest request = new StringRequest(Request.Method.POST, url, response -> {
            try {
                JSONObject respObj = new JSONObject(response);
                String message = respObj.getString("Message");
                Toast.makeText(PostRequestActivity.this,message , Toast.LENGTH_SHORT).show();
                finish();
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(PostRequestActivity.this,e.getMessage() , Toast.LENGTH_SHORT).show();
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PostRequestActivity.this, "Fail to get response = " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override

            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("title", title);
                params.put("body", body);
                params.put("author_id", author_ID);
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
}