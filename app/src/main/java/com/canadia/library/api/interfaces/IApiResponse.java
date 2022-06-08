package com.canadia.library.api.interfaces;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

public interface IApiResponse {
    void OnResponse(JSONObject response);
    void OnError(VolleyError error);
}
