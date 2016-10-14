package br.com.danyswork.trakttv.request;

import com.android.volley.Response;
import com.android.volley.VolleyError;

public abstract class ResponseListener implements Response.ErrorListener, Response.Listener<String> {

    @Override
    public abstract void onResponse(String response);

    @Override
    public abstract void onErrorResponse(VolleyError error);
}