package br.com.danyswork.trakttv.request;

import com.android.volley.Request;

import br.com.danyswork.trakttv.BuildConfig;
import br.com.danyswork.trakttv.Utils.Constants;

public class Repository {

    private static URLBuilder getURLBase() {
        return new URLBuilder(Constants.BASE_URL);
    }

private static URLBuilder getURLBaseTMDB(){
    return new URLBuilder(Constants.BASE_URL_TMDB);
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

    public void getImages(int tmdbId, ResponseListener listener) {
        String url = getImages(tmdbId);
        TraktTVVolley.addRequest(Request.Method.GET, url, listener);
    }

    private String getImages(int tmdbId) {
        URLBuilder urlBuilder = getURLBaseTMDB();
        urlBuilder.addPath(String.valueOf(tmdbId));
        urlBuilder.addQueryParameter(Constants.API_KEY, BuildConfig.TMDB_API_KEY);
        return urlBuilder.build();
    }
}
