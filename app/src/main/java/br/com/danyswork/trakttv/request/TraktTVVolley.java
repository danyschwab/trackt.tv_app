package br.com.danyswork.trakttv.request;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpStack;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.List;

public class TraktTVVolley extends Volley {

    private static HttpStack httpStack = null;

    private static RequestQueue singletonQueue = null;


    public static void initialize(Context context) {
        if (singletonQueue == null) {
            singletonQueue = Volley.newRequestQueue(context, httpStack);
            System.setProperty("http.keepAlive", "false");
        }
    }

    public static void addRequest(TraktTVStringRequest request) {
        singletonQueue.add(request);
    }

    public static void addRequest(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        addRequest(new TraktTVStringRequest(method, url, listener, errorListener));
    }

    public static void addRequest(int method, String url, ResponseListener listener) {
        addRequest(new TraktTVStringRequest(method, url, listener));
    }

        public static void cancelAllRequest() {
            singletonQueue.cancelAll("Trakt");
        }
}
