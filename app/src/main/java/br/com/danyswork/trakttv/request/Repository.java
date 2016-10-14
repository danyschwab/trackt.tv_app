package br.com.danyswork.trakttv.request;

import com.android.volley.Request;

import br.com.danyswork.trakttv.Utils.Constants;

public class Repository {

    private static URLBuilder getURLBase() {
        return new URLBuilder(Constants.BASE_URL);
    }

    public void getMostPopular(int page, int limit, ResponseListener listener) {
        String url = getMostPopularURL(page, limit);
        TraktTVVolley.addRequest(Request.Method.GET, url, listener);
    }


    private String getMostPopularURL(int page, int limit) {
        URLBuilder urlBuilder = getURLBase();
        urlBuilder.addPath(Constants.POPULAR);
        urlBuilder.addQueryParameter(Constants.EXTENDED, Constants.FULL);
        urlBuilder.addQueryParameter(Constants.PAGE, page);
        urlBuilder.addQueryParameter(Constants.LIMIT, limit);
        return urlBuilder.build();
    }
}
