package com.canadia.library.models;

import com.android.volley.VolleyError;

import org.json.JSONObject;

public class APIResponseModel {
    public JSONObject data;
    public VolleyError error;

    public APIResponseModel(){}

    public static APIResponseModel clone(JSONObject data, VolleyError error) {
        APIResponseModel result = new APIResponseModel();
        result.data = data;
        result.error = error;
        return result;
    }


}
