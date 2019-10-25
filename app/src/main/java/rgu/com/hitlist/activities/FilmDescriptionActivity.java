package rgu.com.hitlist.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.app.ActionBar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import rgu.com.hitlist.R;
import rgu.com.hitlist.adapter.MyRecyclerViewAdapter;
import rgu.com.hitlist.model.Movie;
import rgu.com.hitlist.tmdbApi.DownloadImageTask;
import rgu.com.hitlist.tmdbApi.FetchApi;

public class FilmDescriptionActivity extends AppCompatActivity implements Response.Listener<String>, Response.ErrorListener, View.OnClickListener {

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

        if(intent.getSerializableExtra("movie") != null) {
            movie = (Movie)intent.getSerializableExtra("movie");
            Log.d("debug", movie.toString());
            FetchApi.GetMovie(String.valueOf(movie.getId()), this, this, this);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAddToWatchList:
                Log.d("debug", "added to the wath list");
                break;
            case R.id.btnOpenHomepage:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(movie.getHomepage())));
                break;
        }
    }

    @Override
    public void onResponse(String response) {

        movie = new Gson().fromJson(response, Movie.class);

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
