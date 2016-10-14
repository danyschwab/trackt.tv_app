package br.com.danyswork.trakttv.ui;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import br.com.danyswork.trakttv.R;
import butterknife.Bind;
import butterknife.ButterKnife;

public class MovieViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.text_movie_title)
    TextView mTitle;

    @Bind(R.id.text_movie_year)
    TextView mYear;

    public MovieViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
