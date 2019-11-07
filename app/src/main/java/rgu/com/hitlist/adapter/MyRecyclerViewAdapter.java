package rgu.com.hitlist.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import rgu.com.hitlist.R;
import rgu.com.hitlist.model.Media;
import rgu.com.hitlist.model.Movie;
import rgu.com.hitlist.model.People;
import rgu.com.hitlist.model.Tv;
import rgu.com.hitlist.tmdbApi.DownloadImageTask;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    private List<Media> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    public MyRecyclerViewAdapter(Context context, List<Media> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_row, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Media m = mData.get(position);
        if(m instanceof Movie) {
            Movie movie = (Movie)m;
            holder.tvTitle.setText(movie.getTitle());
            holder.tvOverview.setText(movie.getOverview());
            holder.tvReleaseDate.setText(movie.getRelease_date());
            holder.tvVoteAverage.setText(String.valueOf(movie.getVote_average()));
            new DownloadImageTask(holder.ivPoster, "w200").execute(movie.getPoster_path());
        } else if(m instanceof Tv) {
            Tv tv = (Tv)m;
            holder.tvTitle.setText(tv.getName());
            holder.tvOverview.setText(tv.getOverview());
            holder.tvReleaseDate.setText(tv.getFirst_air_date());
            holder.tvVoteAverage.setText(String.valueOf(tv.getVote_average()));
            new DownloadImageTask(holder.ivPoster, "w200").execute(tv.getPoster_path());
        } else if(m instanceof People) {
            People p = (People) m;
            holder.tvTitle.setText(p.getName());
            new DownloadImageTask(holder.ivPoster, "w200").execute(p.getProfile_path());
        }

    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvTitle;
        TextView tvOverview;
        TextView tvReleaseDate;
        TextView tvVoteAverage;
        ImageView ivPoster;

        ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            tvReleaseDate = itemView.findViewById(R.id.tvReleaseDate);
            tvVoteAverage = itemView.findViewById(R.id.tvVoteAverage);
            ivPoster = itemView.findViewById(R.id.ivPoster);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    Media getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}