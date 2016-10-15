package br.com.danyswork.trakttv.presenter;

import android.app.AlertDialog;
import android.util.Log;

import com.android.volley.VolleyError;

import java.util.ArrayList;
import java.util.List;

import br.com.danyswork.trakttv.model.MovieTMDB;
import br.com.danyswork.trakttv.model.Movies;
import br.com.danyswork.trakttv.model.Search;
import br.com.danyswork.trakttv.request.GsonUtils;
import br.com.danyswork.trakttv.request.Repository;
import br.com.danyswork.trakttv.request.ResponseListener;
import br.com.danyswork.trakttv.request.TraktTVVolley;
import br.com.danyswork.trakttv.ui.MainActivity;

public class TraktTVPresenter {

    private Repository mRepository;
    private MainActivity mActivity;
    private int page;
    private int limit;
    private int pageSearch;

    public TraktTVPresenter(MainActivity activity) {
        this.page = 1;
        this.pageSearch = 1;
        this.limit = 10;
        this.mActivity = activity;
        this.mRepository = new Repository();
    }

    public void getPopularMovies() {
        mRepository.getMostPopular(page, limit, new ResponseListener() {
            @Override
            public void onResponse(String response) {
                ++page;
                List<Movies> list = GsonUtils.fromJsonList(response, Movies.class);
                getImages(list);
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TraktTV", error.getMessage());
            }
        });
    }

    private void getImages(List<Movies> list) {
        for (final Movies movies : list) {
            mRepository.getImages(movies.getIds().getTMDB(), new ResponseListener() {
                @Override
                public void onResponse(String response) {
                    MovieTMDB movieTMDB = GsonUtils.fromJson(response, MovieTMDB.class);
                    movies.setPosterPath(movieTMDB.getPosterPath());
                    mActivity.setContent(movies);
                }

                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("TraktTV", error.getMessage());
                }
            });
        }
    }

    public void cancelSearch() {
        TraktTVVolley.cancelAllRequest();
        pageSearch = 0;
    }

    public void search(String s) {
        mRepository.search(s, pageSearch, limit, new ResponseListener() {
            @Override
            public void onResponse(String response) {
                ++pageSearch;
                List<Search> searchList = GsonUtils.fromJsonList(response, Search.class);
                if (searchList != null && !searchList.isEmpty()) {
                    List<Movies> list = getMovies(searchList);
                    getImages(list);
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TraktTV", error.getMessage());
            }
        });
    }

    private List<Movies> getMovies(List<Search> searchList) {
        List<Movies> list = new ArrayList<>();
        for (Search search : searchList) {
            list.add(search.getMovie());
        }
        return list;
    }
}
