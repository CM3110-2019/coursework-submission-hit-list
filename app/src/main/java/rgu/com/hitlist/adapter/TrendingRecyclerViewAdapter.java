package rgu.com.hitlist.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import rgu.com.hitlist.R;
import rgu.com.hitlist.model.Media;
import rgu.com.hitlist.model.Movie;
import rgu.com.hitlist.tmdbApi.DownloadImageTask;

public class TrendingRecyclerViewAdapter extends RecyclerView.Adapter<TrendingRecyclerViewAdapter.ViewHolder>{

    private List<Media> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private String type;

    // data is passed into the constructor
    public TrendingRecyclerViewAdapter(Context context, List<Media> data, String type) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.type = type;
    }

    // inflates the row layout from xml when needed
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = mInflater.inflate(R.layout.recyclerview_trending, parent, false);
        return new TrendingRecyclerViewAdapter.ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Media m = mData.get(position);
        if(type.equals("people")){
            new DownloadImageTask(holder.trPoster, "w200").execute(m.getProfile_path());
        }
        else {
            new DownloadImageTask(holder.trPoster, "w200").execute(m.getPoster_path());
        }
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView trPoster;

        ViewHolder(View itemView) {
            super(itemView);
            trPoster = itemView.findViewById(R.id.trPoster);
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
    public void setClickListener(TrendingRecyclerViewAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
