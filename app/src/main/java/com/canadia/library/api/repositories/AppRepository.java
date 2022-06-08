package com.canadia.library.api.repositories;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.canadia.library.api.interfaces.IApiResponse;
import com.canadia.library.api.services.APIService;
import com.canadia.library.models.APIResponseModel;

import org.json.JSONObject;

public class AppRepository {

    private static final String BaseURL = "http://172.16.9.128:8000/api/";

    private Context context;
    private  APIResponseModel apiResponse;

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

//    public void post(String endpoint, IApiResponse postResult, JsonRequest jsonRequest){
//        APIService.getInstance(this.context.getApplicationContext()).
//                getRequestQueue();
//        String url = BaseURL + endpoint;
//        JSONObject jsonObject = new JSONObject(Request.Method.POST,url,postResult.OnResponse());
//
//    }

}
