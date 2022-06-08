package com.canadia.library.api.repositories;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.canadia.library.api.interfaces.IApiResponse;
import com.canadia.library.api.services.APIService;
import com.canadia.library.models.APIResponseModel;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class AppRepository {

    private static final String BaseURL = "http://172.16.9.128:8000/api/";

    private Context context;

    public AppRepository(Context context) {
        this.context = context;
    }


    public void get(String endpoint, IApiResponse result) {
        APIService.getInstance(this.context.getApplicationContext()).
                getRequestQueue();
        String url = BaseURL + endpoint;
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url, null, result::OnResponse, result::OnError);
        APIService.getInstance(context).addToRequestQueue(jsonRequest);
    }

    public void post(String endpoint, IApiResponse result, Object input){
        APIService.getInstance(this.context.getApplicationContext()).
                getRequestQueue();
        String url = BaseURL + endpoint;

        Gson gson = new Gson();
        String jsonString = gson.toJson(input);
        JSONObject inputJson =new JSONObject();
        try {
            inputJson = new JSONObject(jsonString);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, inputJson,
                result::OnResponse, result::OnError);
        APIService.getInstance(context).addToRequestQueue(jsonRequest);

    }

    public void put(String endpoint, long id , IApiResponse result, Object input){
        APIService.getInstance(this.context.getApplicationContext()).
                getRequestQueue();
        String url = BaseURL + endpoint +"/" +id;

        Gson gson = new Gson();
        String jsonString = gson.toJson(input);
        JSONObject inputJson =new JSONObject();
        try {
            inputJson = new JSONObject(jsonString);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.PUT, url, inputJson,
                result::OnResponse, result::OnError);
        APIService.getInstance(context).addToRequestQueue(jsonRequest);

    }

    public void delete(String endpoint, long id, IApiResponse result){
        APIService.getInstance(this.context.getApplicationContext()).
                getRequestQueue();
        String url = BaseURL + endpoint + "/" + id;
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.DELETE, url, null, result::OnResponse, result::OnError);
        APIService.getInstance(context).addToRequestQueue(jsonRequest);
    }



}
