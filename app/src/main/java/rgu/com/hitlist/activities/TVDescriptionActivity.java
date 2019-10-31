package rgu.com.hitlist.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import rgu.com.hitlist.R;
import rgu.com.hitlist.model.Movie;
import rgu.com.hitlist.model.Tv;
import rgu.com.hitlist.tmdbApi.DownloadImageTask;
import rgu.com.hitlist.tmdbApi.FetchApi;

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

import java.util.Iterator;
import java.util.Map;

public class TVDescriptionActivity extends AppCompatActivity implements Response.Listener<String>, Response.ErrorListener, View.OnClickListener {

    Tv tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tvdescription);

        //display the back button
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();

        if(intent.getSerializableExtra("movie") != null) {
            tv = (Tv)intent.getSerializableExtra("movie");
            Log.d("debug", tv.toString());
            FetchApi.GetMovie(String.valueOf(tv.getId()), this, this, this);
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
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(tv.getHomepage())));
                break;
        }
    }

    @Override
    public void onResponse(String response) {

        tv = new Gson().fromJson(response, Tv.class);

        ImageView ivCover = findViewById(R.id.ivCover);
        TextView tvMovieTitle = findViewById(R.id.tvMovieTitle);
        TextView tvMovieOverview = findViewById(R.id.tvMovieOverview);
        TextView tvMovieReleaseDate = findViewById(R.id.tvMovieReleaseDate);
        TextView tvMovieVoteAverage = findViewById(R.id.tvMovieVoteAverage);
        TextView tvMovieVoteCount = findViewById(R.id.tvMovieVoteCount);
        TextView tvMovieBudget = findViewById(R.id.tvMovieBudget);
        TextView tvMovieGenre = findViewById(R.id.tvMovieGenre);
        TextView tvMovieProdCompanies = findViewById(R.id.tvMovieProdCompanies);

        new DownloadImageTask(ivCover, "w500").execute(tv.getBackdrop_path());
        tvMovieTitle.setText(tv.getName());
        tvMovieOverview.setText(tv.getOverview());
        tvMovieVoteAverage.setText(getString(R.string.tvVoteAverage, String.valueOf(tv.getVote_average())));
        tvMovieVoteCount.setText(getString(R.string.tvVoteCount, String.valueOf(tv.getVote_count())));

        String genres = "";
        Iterator<Map<String, String>> genreIt = tv.getGenres().iterator();
        while(genreIt.hasNext()) {
            genres += genreIt.next().get("name");
            if(genreIt.hasNext()) genres += ", ";
        }
        tvMovieGenre.setText(getString(R.string.tvGenre, genres));

        String prodComp = "";
        Iterator<Map<String, String>> prodCompIt = tv.getProduction_companies().iterator();
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
