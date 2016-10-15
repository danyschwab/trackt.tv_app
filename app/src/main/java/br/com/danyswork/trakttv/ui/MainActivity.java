package br.com.danyswork.trakttv.ui;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ProgressBar;

import br.com.danyswork.trakttv.R;
import br.com.danyswork.trakttv.model.Movies;
import br.com.danyswork.trakttv.presenter.TraktTVPresenter;
import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {


    @Bind(R.id.list_movies)
    RecyclerView mRecyclerView;

    @Bind(R.id.progress_bar)
    ProgressBar mProgressBar;

    private MoviesAdapter mAdapter;
    private TraktTVPresenter mPresenter;
    private boolean mIsSearch = false;
    private String mSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mPresenter = new TraktTVPresenter(this);
        mPresenter.getPopularMovies();
        mAdapter = new MoviesAdapter(this);

        setup();

        mRecyclerView.setAdapter(mAdapter);
    }

    private void setup() {
        LinearLayoutManager layoutParams = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutParams);

        setupRecyclerScroll(layoutParams);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main_activity, menu);

        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                mPresenter.cancelSearch();
                mProgressBar.setVisibility(View.VISIBLE);
                mPresenter.search(s);
                mAdapter.clearContent();
                setup();
                mIsSearch = true;
                mSearch = s;
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (!TextUtils.isEmpty(s)) {
                    mPresenter.cancelSearch();
                    mProgressBar.setVisibility(View.VISIBLE);
                    mPresenter.search(s);
                    mAdapter.clearContent();
                    setup();
                    mIsSearch = true;
                    mSearch = s;
                    return true;
                }
                return false;
            }
        });

        return true;
    }

    public void setContent(Movies movie) {
        mProgressBar.setVisibility(View.GONE);
        mAdapter.setContent(movie);
    }

    private void setupRecyclerScroll(final LinearLayoutManager layoutParams) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mRecyclerView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View view, int i, int i1, int i2, int i3) {
                    if (layoutParams.findLastVisibleItemPosition() == mAdapter.getItemCount() - 1) {
                        mProgressBar.setVisibility(View.VISIBLE);
                        if (mIsSearch) {
                            mPresenter.search(mSearch);
                        } else {
                            mPresenter.getPopularMovies();
                        }
                    }
                }
            });
        }
    }
}
