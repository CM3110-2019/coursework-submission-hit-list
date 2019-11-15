package rgu.com.hitlist.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import rgu.com.hitlist.R;
import rgu.com.hitlist.adapter.MyRecyclerViewAdapter;
import rgu.com.hitlist.adapter.TrendingRecyclerViewAdapter;
import rgu.com.hitlist.database.WatchlistDatabaseHelper;
import rgu.com.hitlist.model.Media;
import rgu.com.hitlist.model.Movie;
import rgu.com.hitlist.model.People;
import rgu.com.hitlist.model.Tv;
import rgu.com.hitlist.tmdbApi.FetchApi;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, Response.ErrorListener{

    WatchlistDatabaseHelper userDB;
    TrendingRecyclerViewAdapter adapter; //Need different adapter

    List<Media> trendingMovieData;
    List<Media> trendingTVData;
    List<Media> trendingPersonData;

    SwipeRefreshLayout refreshLayout;

    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        refreshLayout = findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getTrendings();
                refreshLayout.setRefreshing(false);
            }
        });

        getTrendings();
        //creates the empty database when the MainActivity is created
        userDB = new WatchlistDatabaseHelper(this);

        Log.d("debug", Locale.getDefault().getLanguage());
        prefs = getSharedPreferences("LANGUAGE", MODE_PRIVATE);
        if(prefs.getString("language", "").equals("")) {
            editor = getSharedPreferences("LANGUAGE", MODE_PRIVATE).edit();
            editor.putString("language", Locale.getDefault().getLanguage());
            editor.apply();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main_menu, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.actionSearch).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconified(false);
        searchView.requestFocusFromTouch();

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actionWatchList:
                startActivity(new Intent(this, WatchListActivity.class));
                return true;
            case R.id.en:
                editor = getSharedPreferences("LANGUAGE", MODE_PRIVATE).edit();
                editor.putString("language", "en");
                editor.apply();
                return true;
            case R.id.fr:
                editor = getSharedPreferences("LANGUAGE", MODE_PRIVATE).edit();
                editor.putString("language", "fr");
                editor.apply();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.d("debug", error.toString());
    }

    public void getTrendings() {
        FetchApi.TrendingDay(this, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONParserMovie(response);

                }
                catch (JSONException e){
                    Log.d("debug", "JSONException: " + e);
                }
            }

        }, this, "movie");

        FetchApi.TrendingDay(this, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONParserTV(response);

                }
                catch (JSONException e){
                    Log.d("debug", "JSONException: " + e);
                }
            }
        }, this, "tv");

        FetchApi.TrendingDay(this, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONParserPerson(response);
                }
                catch (JSONException e){
                    Log.d("debug", "JSONException: " + e);
                }
            }
        }, this, "person");

    }

    public void JSONParserMovie(String response) throws JSONException {
        JSONObject jsonResponse = new JSONObject(response);
        JSONArray results = jsonResponse.getJSONArray("results");
        trendingMovieData = new Gson().fromJson(results.toString(), new TypeToken<List<Movie>>(){}.getType());

        RecyclerView recyclerView = findViewById(R.id.rvTrending);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        adapter = new TrendingRecyclerViewAdapter(this, trendingMovieData, "movie"); //maybe make 3 adapter declerations
        final Intent intent = new Intent(this, FilmDescriptionActivity.class);
        adapter.setClickListener(new TrendingRecyclerViewAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                intent.putExtra("movie",  trendingMovieData.get(position)); //add input extra for tv and change film description so that it recognises the difference betweeen the data
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    public void JSONParserTV(String response) throws JSONException {

        JSONObject jsonResponse = new JSONObject(response);
        JSONArray results = jsonResponse.getJSONArray("results");
        trendingTVData = new Gson().fromJson(results.toString(), new TypeToken<List<Tv>>(){}.getType());

        RecyclerView recyclerView = findViewById(R.id.rvTV);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        adapter = new TrendingRecyclerViewAdapter(getApplicationContext(), trendingTVData, "tv"); //maybe make 3 adapter declerations
        final Intent intent = new Intent(this, TVDescriptionActivity.class);
        adapter.setClickListener(new TrendingRecyclerViewAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                intent.putExtra("tv",  trendingTVData.get(position));
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);

    }

    public void JSONParserPerson(String response) throws JSONException {

        JSONObject jsonResponse = new JSONObject(response);
        JSONArray results = jsonResponse.getJSONArray("results");
        trendingPersonData = new Gson().fromJson(results.toString(), new TypeToken<List<People>>(){}.getType());
        adapter = new TrendingRecyclerViewAdapter(getApplicationContext(), trendingPersonData, "people");

        RecyclerView recyclerView = findViewById(R.id.rvPeople);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        final Intent intent = new Intent(this, PeopleDescriptionActvity.class);
        adapter.setClickListener(new TrendingRecyclerViewAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                intent.putExtra("people",  trendingPersonData.get(position));
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);


    }

}
