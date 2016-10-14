package br.com.danyswork.trakttv.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import br.com.danyswork.trakttv.R;
import br.com.danyswork.trakttv.model.Movies;

class MoviesAdapter extends RecyclerView.Adapter<MovieViewHolder> {

    private List<Movies> mList;

    void setContent(List<Movies> list) {
        if (this.mList == null) {
            this.mList = new ArrayList<>();
        }
        if (list == null || list.isEmpty()) {
            this.mList.clear();
        } else {
            this.mList.addAll(list);
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
        }
    }

    @Override
    public int getItemCount() {
        return (mList != null ? mList.size() : 0);
    }
}
