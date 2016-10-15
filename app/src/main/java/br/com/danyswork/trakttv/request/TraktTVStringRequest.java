package br.com.danyswork.trakttv.request;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import br.com.danyswork.trakttv.BuildConfig;
import br.com.danyswork.trakttv.Utils.Constants;

class TraktTVStringRequest extends StringRequest {

    private static final int DEFAULT_TIMEOUT_MS = 2 * 60 * 1000;
    private static final int DEFAULT_MAX_RETRIES = 0;
    private static final float DEFAULT_BACKOFF_MULT = 1;

    TraktTVStringRequest(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
    }

    TraktTVStringRequest(int method, String url, ResponseListener listener) {
        super(method, url, listener, listener);
    }


    @Override
    public String getBodyContentType() {
        return Constants.APPLICATION_JSON;
    }

    @Override
    public Map<String, String> getHeaders() {
        HashMap<String, String> header = new HashMap<>();

        //Content-Type
        header.put("Content-Type", Constants.APPLICATION_JSON);
        header.put("trakt-api-version", Constants.TRAKT_API_VERSION);
        header.put("trakt-api-key", BuildConfig.TRAKT_API_KEY);

        return header;
    }


    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        String parsed = new String(response.data);
        return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
    }


    @Override
    protected VolleyError parseNetworkError(VolleyError volleyError) {
        return super.parseNetworkError(volleyError);
    }

    @Override
    public Request<?> setRetryPolicy(RetryPolicy retryPolicy) {
        retryPolicy = new DefaultRetryPolicy(DEFAULT_TIMEOUT_MS, DEFAULT_MAX_RETRIES, DEFAULT_BACKOFF_MULT);
        return super.setRetryPolicy(retryPolicy);
    }

}
