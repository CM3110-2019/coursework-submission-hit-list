package rgu.com.hitlist.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import rgu.com.hitlist.R;
import rgu.com.hitlist.database.DAO;
import rgu.com.hitlist.database.WatchListItem;
import rgu.com.hitlist.database.WatchlistDB;
import rgu.com.hitlist.model.Movie;
import rgu.com.hitlist.model.Tv;
import rgu.com.hitlist.tmdbApi.DownloadImageTask;
import rgu.com.hitlist.tmdbApi.FetchApi;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;

import java.util.Iterator;
import java.util.Map;

public class TVDescriptionActivity extends AppCompatActivity implements Response.Listener<String>, Response.ErrorListener, View.OnClickListener {

    Tv tv;
    private DAO DAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tvdescription);

        this.DAO = WatchlistDB.getInstance(this).DAO();

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();

        if(intent.getSerializableExtra("tv") != null) {
            tv = (Tv)intent.getSerializableExtra("tv");
            Log.d("debug", tv.toString());
            FetchApi.GetMedia(String.valueOf(tv.getId()), this, "tv", this, this);

        }

        Button btnAddToWatchList = findViewById(R.id.btnAddTvToWatchList);
        btnAddToWatchList.setOnClickListener(this);
        Button btnOpenHomepage = findViewById(R.id.btnOpenTvHomepage);
        btnOpenHomepage.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //destroy the activity and show the one that started it
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    class InsertTVWatchList extends AsyncTask<WatchListItem, Void, Void> {

        @Override
        protected Void doInBackground(WatchListItem... watchListItems) {

            DAO.insert(watchListItems[0]);

            return null;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAddTvToWatchList:
                long tvID = this.tv.getId();
                String id = Long.toString(tvID);
                String original_name = this.tv.getOriginal_name();
                String overview = this.tv.getOverview();
                String status = this.tv.getStatus();

                WatchListItem item =new WatchListItem(id,original_name,overview,status);
                InsertTVWatchList insertTask = new InsertTVWatchList();
                insertTask.execute(item);
                Toast.makeText(this, "Added to your Watchlist", Toast.LENGTH_SHORT).show();
                Log.d("debug", "added to the watch list");


                break;
            case R.id.btnOpenTvHomepage:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(tv.getHomepage())));
                break;
        }
    }

    @Override
    public void onResponse(String response) {
        tv = new Gson().fromJson(response, Tv.class);
        ProgressBar pbTv = findViewById(R.id.pbTv);

        ImageView ivTvCover = findViewById(R.id.ivTvCover);
        TextView tvTvName = findViewById(R.id.tvTvName);
        TextView tvTvOverview = findViewById(R.id.tvTvOverview);
        TextView tvTvFirstAirDate = findViewById(R.id.tvTvFirstAirDate);
        TextView tvTvVoteAverage = findViewById(R.id.tvTvVoteAverage);
        TextView tvTvVoteCount = findViewById(R.id.tvTvVoteCount);
        TextView tvTvGenre = findViewById(R.id.tvTvGenre);
        TextView tvTvProdCompanies = findViewById(R.id.tvTvProdCompanies);

        new DownloadImageTask(ivTvCover, "w500", pbTv).execute(tv.getBackdrop_path());
        tvTvName.setText(tv.getName());
        tvTvOverview.setText(tv.getOverview());
        tvTvFirstAirDate.setText(getString(R.string.tvReleaseDate, tv.getFirst_air_date()));
        tvTvVoteAverage.setText(getString(R.string.tvVoteAverage, String.valueOf(tv.getVote_average())));
        tvTvVoteCount.setText(getString(R.string.tvVoteCount, String.valueOf(tv.getVote_count())));

        String genres = "";
        Iterator<Map<String, String>> genreIt = tv.getGenres().iterator();
        while(genreIt.hasNext()) {
            genres += genreIt.next().get("name");
            if(genreIt.hasNext()) genres += ", ";
        }
        tvTvGenre.setText(getString(R.string.tvGenre, genres));

        String prodComp = "";
        Iterator<Map<String, String>> prodCompIt = tv.getProduction_companies().iterator();
        while(prodCompIt.hasNext()) {
            prodComp += prodCompIt.next().get("name");
            if(prodCompIt.hasNext()) prodComp +=", ";
        }
        tvTvProdCompanies.setText(getString(R.string.tvProdCompanies, prodComp));

    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }
}

