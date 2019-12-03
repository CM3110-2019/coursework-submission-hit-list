package rgu.com.hitlist.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.app.ActionBar;

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
import java.util.List;
import java.util.Map;

import rgu.com.hitlist.R;
import rgu.com.hitlist.database.DAO;
import rgu.com.hitlist.database.WatchListItem;
import rgu.com.hitlist.database.WatchlistDB;
import rgu.com.hitlist.model.Movie;
import rgu.com.hitlist.tmdbApi.DownloadImageTask;
import rgu.com.hitlist.tmdbApi.FetchApi;

public class FilmDescriptionActivity extends AppCompatActivity implements Response.Listener<String>, Response.ErrorListener, View.OnClickListener {

    Movie movie;
    private DAO DAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_description);

        this.DAO = WatchlistDB.getInstance(this).DAO();

        //display the back button
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();

        if(intent.getSerializableExtra("movie") != null) {
            movie = (Movie)intent.getSerializableExtra("movie");
            Log.d("debug", movie.toString());
            FetchApi.GetMedia(String.valueOf(movie.getId()), this, "movie", this, this);
        }

        Button btnAddToWatchList = findViewById(R.id.btnAddToWatchList);
        btnAddToWatchList.setOnClickListener(this);
        Button btnOpenHomepage = findViewById(R.id.btnOpenHomepage);
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

    class InsertWatchList extends AsyncTask<WatchListItem, Void, Void>{

        @Override
        protected Void doInBackground(WatchListItem... watchListItems) {

            DAO.insert(watchListItems[0]);

            return null;
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAddToWatchList:


                long movieID = this.movie.getId();
                String name = this.movie.getTitle();
                String type = "movie";

                WatchListItem item =new WatchListItem(movieID,name,type);
                InsertWatchList insertTask = new InsertWatchList();
                insertTask.execute(item);
                Toast.makeText(this, "Added "+this.movie.getTitle()+" to your Watchlist", Toast.LENGTH_SHORT).show();
                Log.d("debug", "added to the watch list");


                break;
            case R.id.btnOpenHomepage:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(movie.getHomepage())));
                break;
        }
    }

    @Override
    public void onResponse(String response) {

        movie = new Gson().fromJson(response, Movie.class);
        ProgressBar pbDescription = findViewById(R.id.pbDescription);

        ImageView ivCover = findViewById(R.id.ivCover);
        TextView tvMovieTitle = findViewById(R.id.tvMovieTitle);
        TextView tvMovieTagline = findViewById(R.id.tvMovieTagline);
        TextView tvMovieOverview = findViewById(R.id.tvMovieOverview);
        TextView tvMovieReleaseDate = findViewById(R.id.tvMovieReleaseDate);
        TextView tvMovieVoteAverage = findViewById(R.id.tvMovieVoteAverage);
        TextView tvMovieVoteCount = findViewById(R.id.tvMovieVoteCount);
        TextView tvMovieBudget = findViewById(R.id.tvMovieBudget);
        TextView tvMovieGenre = findViewById(R.id.tvMovieGenre);
        TextView tvMovieProdCompanies = findViewById(R.id.tvMovieProdCompanies);

        new DownloadImageTask(ivCover, "w500", pbDescription).execute(movie.getBackdrop_path());
        tvMovieTitle.setText(movie.getTitle());
        tvMovieTagline.setText(movie.getTagline());
        tvMovieOverview.setText(movie.getOverview());
        tvMovieReleaseDate.setText(getString(R.string.tvReleaseDate, movie.getRelease_date()));
        tvMovieVoteAverage.setText(getString(R.string.tvVoteAverage, String.valueOf(movie.getVote_average())));
        tvMovieVoteCount.setText(getString(R.string.tvVoteCount, String.valueOf(movie.getVote_count())));
        tvMovieBudget.setText(getString(R.string.tvBudget, String.valueOf(movie.getBudget())));

        String genres = "";
        Iterator<Map<String, String>> genreIt = movie.getGenres().iterator();
        while(genreIt.hasNext()) {
            genres += genreIt.next().get("name");
            if(genreIt.hasNext()) genres += ", ";
        }
        tvMovieGenre.setText(getString(R.string.tvGenre, genres));

        String prodComp = "";
        Iterator<Map<String, String>> prodCompIt = movie.getProduction_companies().iterator();
        while(prodCompIt.hasNext()) {
            prodComp += prodCompIt.next().get("name");
            if(prodCompIt.hasNext()) prodComp +=", ";
        }
        tvMovieProdCompanies.setText(getString(R.string.tvProdCompanies, prodComp));
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }


}
