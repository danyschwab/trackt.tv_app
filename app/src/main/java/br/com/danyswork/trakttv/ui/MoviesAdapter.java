package br.com.danyswork.trakttv.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import br.com.danyswork.trakttv.R;
import br.com.danyswork.trakttv.model.Movies;
import br.com.danyswork.trakttv.util.Constants;

class MoviesAdapter extends RecyclerView.Adapter<MovieViewHolder> {

    private List<Movies> mList;
    private Context mContext;

    MoviesAdapter(Context context) {
        mContext = context;
    }

    void setContent(Movies movie) {
        if (this.mList == null) {
            this.mList = new ArrayList<>();
        }
        if (movie != null) {
            if (!mList.contains(movie)) {
                this.mList.add(movie);
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item_layout, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {

        if (mList.isEmpty())
            return;

        final Movies movie = mList.get(position);

        if (movie != null) {
            holder.mTitle.setText(movie.getTitle());
            holder.mYear.setText(String.valueOf(movie.getYear()));
            holder.mOverview.setText(movie.getOverview());

            Picasso.with(mContext)
                    .load(Constants.IMAGE_LOADER_URL + movie.getPosterPath())
                    .placeholder(R.mipmap.boldee_icons)
                    .into(holder.mImage);
        }
    }

    @Override
    public int getItemCount() {
        return (mList != null ? mList.size() : 0);
    }

    void clearContent() {
        mList.clear();
    }
}
