package rgu.com.hitlist.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.app.ActionBar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import rgu.com.hitlist.R;
import rgu.com.hitlist.model.Movie;
import rgu.com.hitlist.tmdbApi.DownloadImageTask;

public class FilmDescriptionActivity extends AppCompatActivity {

    Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_description);

        //display the back button
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();
        movie = (Movie)intent.getSerializableExtra("movie");
        Log.d("debug", movie.toString());


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

        new DownloadImageTask(ivCover, "w500").execute(movie.getBackdrop_path());
        tvMovieTitle.setText(movie.getTitle());
        tvMovieTagline.setText(movie.getTagline());
        tvMovieOverview.setText(movie.getOverview());
        tvMovieReleaseDate.setText(getString(R.string.tvReleaseDate, movie.getRelease_date()));
        tvMovieVoteAverage.setText(getString(R.string.tvVoteAverage, String.valueOf(movie.getVote_average())));
        tvMovieVoteCount.setText(getString(R.string.tvVoteCount, String.valueOf(movie.getVote_count())));
        tvMovieBudget.setText(getString(R.string.tvBudget, String.valueOf(movie.getBudget())));
        tvMovieGenre.setText(getString(R.string.tvGenre, movie.getGenres()));
        tvMovieProdCompanies.setText(getString(R.string.tvProdCompanies, movie.getProduction_companies()));

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
}
