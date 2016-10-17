package br.com.danyswork.trakttv.ui;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
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

        MenuItem item = menu.findItem(R.id.search);

        final SearchView searchView =
                (SearchView) item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if (!TextUtils.isEmpty(s)) {
                    clearContents();
                    mPresenter.search(s);
                    mIsSearch = true;
                    mSearch = s;
                    return true;
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (!TextUtils.isEmpty(s)) {
                    clearContents();
                    mPresenter.search(s);
                    mIsSearch = true;
                    mSearch = s;
                    return true;
                }
                return false;
            }
        });

        ImageView closeButton = (ImageView) searchView.findViewById(R.id.search_close_btn);

        // Set on click listener
        closeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                searchView.setQuery("", false);
                clearContents();
                mProgressBar.setVisibility(View.GONE);
            }
        });

        MenuItemCompat.setOnActionExpandListener(item, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                searchView.setQuery("", false);
                clearContents();
                mProgressBar.setVisibility(View.VISIBLE);
                mPresenter.getPopularMovies();
                return true;  // Return true to collapse action view
            }

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                // Do something when expanded
                return true;  // Return true to expand action view
            }
        });

        return true;
    }

    private void clearContents() {
        mPresenter.cancelSearch();
        mProgressBar.setVisibility(View.VISIBLE);
        mAdapter.clearContent();
        setup();
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
