package br.com.danyswork.trakttv.presenter;

import com.android.volley.VolleyError;

import br.com.danyswork.trakttv.request.Repository;
import br.com.danyswork.trakttv.request.ResponseListener;
import br.com.danyswork.trakttv.ui.MainActivity;

public class TraktTVPresenter {

    private MainActivity mActivity;
    private int page;
    private int limit;

    public TraktTVPresenter(MainActivity activity) {
        this.page = 1;
        this.limit = 10;
        this.mActivity = activity;
    }

    public void getPopularMovies() {

        new Repository().getMostPopular(page, limit, new ResponseListener() {
            @Override
            public void onResponse(String response) {
                ++page;
                mActivity.setText(response);
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                mActivity.setText(error.getMessage());
            }
        });
    }
}
