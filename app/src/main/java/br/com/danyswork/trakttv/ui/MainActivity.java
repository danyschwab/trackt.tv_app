package br.com.danyswork.trakttv.ui;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

import br.com.danyswork.trakttv.R;
import br.com.danyswork.trakttv.model.Movies;
import br.com.danyswork.trakttv.presenter.TraktTVPresenter;
import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.swipe)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Bind(R.id.list_movies)
    RecyclerView mRecyclerView;

    private MoviesAdapter mAdapter;
    private TraktTVPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mPresenter = new TraktTVPresenter(this);
        mPresenter.getPopularMovies();
        mAdapter = new MoviesAdapter();

        LinearLayoutManager layoutParams = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutParams);

        setupRecyclerScroll(layoutParams);

        mRecyclerView.setAdapter(mAdapter);
    }

    public void setContent(List<Movies> list){
        mAdapter.setContent(list);
    }

    private void setupRecyclerScroll(final LinearLayoutManager layoutParams) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mRecyclerView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View view, int i, int i1, int i2, int i3) {
                    mSwipeRefreshLayout.setEnabled(layoutParams.findFirstCompletelyVisibleItemPosition() == mAdapter.getItemCount());
                }
            });
        }
    }
}
