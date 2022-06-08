package com.canadia.library.api.repositories;

import android.content.Context;
import android.util.Pair;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.canadia.library.api.services.APIService;
import com.canadia.library.models.APIResponseModel;

import org.json.JSONObject;

import java.util.concurrent.atomic.AtomicReference;

public class AppRepository {

    private static final String BaseURL = "http://172.16.9.128:8000/api/";

    private Context context;
    private  APIResponseModel apiResponse;

    public AppRepository(Context context) {
        this.context = context;
    }

    RequestQueue queue = APIService.getInstance(this.context.getApplicationContext()).
            getRequestQueue();

    public APIResponseModel get(String endpoint) {
        String url = BaseURL + endpoint;
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url, null, response ->{
            apiResponse = APIResponseModel.clone(response,null);
        }, error -> {
            apiResponse = APIResponseModel.clone(null,error);
        });
        APIService.getInstance(context).addToRequestQueue(jsonRequest);
        return apiResponse;
    }

}
