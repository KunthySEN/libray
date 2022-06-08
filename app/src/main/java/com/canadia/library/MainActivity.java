package com.canadia.library;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.VolleyError;
import com.canadia.library.adapters.Adapter;
import com.canadia.library.api.interfaces.IApiResponse;
import com.canadia.library.api.repositories.AppRepository;
import com.canadia.library.models.AuthorModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView authorsView;
    List<AuthorModel> authors = new ArrayList<>();
    Adapter adapter;
    Context context;

    AppRepository respository;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        getAuthors(this);
        context = this;
        respository = new AppRepository(context);

        getAuthors();

    }

    private void getAuthors() {

        respository.get("authors", new IApiResponse() {
            @Override
            public void OnResponse(JSONObject response){
                Log.e("tMain", "Response: " + response);
                try {
                    JSONArray objectList = response.getJSONArray("data");
                    List<AuthorModel> responseAuthors = new ArrayList<>();
                    for (int i=0;i<objectList.length();i++) {

                        JSONObject data = objectList.getJSONObject(i);
                        AuthorModel author = new AuthorModel(data.getLong("id"), data.getString("name"), data.getInt("age"), data.getString("province"));
                        responseAuthors.add(author);
                    }

                    authors = responseAuthors;
                    authorsView= findViewById(R.id.authors);
                    authorsView.setLayoutManager(new LinearLayoutManager(context));
                    adapter = new Adapter(context,authors,authors.size());
                    authorsView.setAdapter(adapter);

                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void OnError(VolleyError error) {
                Log.e("tMain", "Error: " + error);
            }
        });

    }
//    private void  getAuthors(Context context) {
//        RequestQueue queue = Volley.newRequestQueue(context);
//        String url = "http://172.16.9.128:8000/api/authors";
//
//// Request a string response from the provided URL.
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
//                response -> {
//                    try {
//                        JSONObject json = new JSONObject(response);
//                        JSONArray objectList = json.getJSONArray("data");
//                        for (int i=0;i<objectList.length();i++){
//                            JSONObject data = objectList.getJSONObject(i);
//
//                            AuthorModel aut = new AuthorModel(data.getLong("id"),data.getString("name"),data.getInt("age"),data.getString("province"));
//                            list.add(aut);
//                        }
//                        recyclerView= findViewById(R.id.authors);
//                        recyclerView.setLayoutManager(new LinearLayoutManager(context));
//                        adapter = new Adapter(context,list,list.size());
//                        recyclerView.setAdapter(adapter);
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }, error -> {
//                    //textView.setText("That didn't work!"+error.getMessage());
//                });
//// Add the request to the RequestQueue.
//        queue.add(stringRequest);
//    }
}