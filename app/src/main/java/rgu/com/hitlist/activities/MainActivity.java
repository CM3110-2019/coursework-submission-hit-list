package rgu.com.hitlist.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
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

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import rgu.com.hitlist.R;
import rgu.com.hitlist.adapter.MyRecyclerViewAdapter;
import rgu.com.hitlist.database.WatchlistDatabaseHelper;
import rgu.com.hitlist.model.Movie;
import rgu.com.hitlist.tmdbApi.FetchApi;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, Response.ErrorListener{

    WatchlistDatabaseHelper userDB;
    MyRecyclerViewAdapter adapter;

    ArrayList<Movie> trendingMovieData;
    ArrayList<Movie> trendingTVData;
    ArrayList<Movie> trendingPersonData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FetchApi.TrendingMoviesDay(this, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
//                    JSONObject jsonResponse = new JSONObject(response);
//                    JSONArray results = jsonResponse.getJSONArray("results");
//                    trendingMovieData = new Gson().fromJson(results.toString(), new TypeToken<List<Movie>>(){}.getType());
                    JSONParser(trendingMovieData, response);
                }
                catch (JSONException e){
                    Log.d("debug", "JSONException: " + e);
                }
            }
        }, this);

        FetchApi.TrendingTVDay(this, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONParser(trendingTVData, response);
                }
                catch (JSONException e){
                    Log.d("debug", "JSONException: " + e);
                }
            }
        }, this);

        FetchApi.TrendingPersonDay(this, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONParser(trendingPersonData, response);
                }
                catch (JSONException e){
                    Log.d("debug", "JSONException: " + e);
                }
            }
        }, this);

        //creates the empty database when the MainActivity is created
        userDB = new WatchlistDatabaseHelper(this);
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
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    public void JSONParser(ArrayList<Movie> dataT, String response) throws JSONException {

        JSONObject jsonResponse = new JSONObject(response);
        JSONArray results = jsonResponse.getJSONArray("results");
        dataT = new Gson().fromJson(results.toString(), new TypeToken<List<Movie>>(){}.getType());

        RecyclerView recyclerView = findViewById(R.id.rvTrending);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyRecyclerViewAdapter(this, dataT);
        adapter.setClickListener((MyRecyclerViewAdapter.ItemClickListener) this);
        recyclerView.setAdapter(adapter);

    }


}
