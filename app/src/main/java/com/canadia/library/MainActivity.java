package com.canadia.library;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.VolleyError;
import com.canadia.library.adapters.Adapter;
import com.canadia.library.api.interfaces.IApiResponse;
import com.canadia.library.api.repositories.AppRepository;
import com.canadia.library.helper.OnAuthorButtonClick;
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

    AppRepository repository;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        getAuthors(this);
        context = this;
        repository = new AppRepository(context);

        getAuthors();

    }

    private void getAuthors() {

        repository.get("authors", new IApiResponse() {
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
                    adapter = new Adapter(context, authors, authors.size(), new OnAuthorButtonClick() {
                        @Override
                        public void onAuthorButtonClick(AuthorModel data) {
                            toAuthorDetail(data);
                        }
                    });

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
    private void toAuthorDetail(AuthorModel authorModel){
        Intent intent = new Intent(context, BookActivity.class);
        intent.putExtra("author_name",authorModel.getName());
        intent.putExtra("author_age",String.valueOf(authorModel.getAge()));
        intent.putExtra("author_province",authorModel.getProvince());
        intent.putExtra("id",authorModel.getID());
        context.startActivity(intent);
    }
}