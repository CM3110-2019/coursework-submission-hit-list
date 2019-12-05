package rgu.com.hitlist.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import rgu.com.hitlist.adapter.TrendingRecyclerViewAdapter;
import rgu.com.hitlist.database.DAO;
import rgu.com.hitlist.database.WatchListItem;
import rgu.com.hitlist.database.WatchlistDB;
import rgu.com.hitlist.adapter.MyRecyclerViewAdapter;
import rgu.com.hitlist.R;
import rgu.com.hitlist.model.Media;
import rgu.com.hitlist.model.Movie;
import rgu.com.hitlist.model.People;
import rgu.com.hitlist.model.Tv;
import rgu.com.hitlist.tmdbApi.FetchApi;

public class WatchListActivity extends AppCompatActivity implements MyRecyclerViewAdapter.ItemClickListener, View.OnClickListener , Response.Listener<String> ,Response.ErrorListener{

    MyRecyclerViewAdapter adapter;
    private DAO DAO;
    // create a list of watchlistitems
    public List<WatchListItem> allItems;
    List<Media> searchData;
    String dataType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //recycler view updater

        this.DAO = WatchlistDB.getInstance(this).DAO();

        GetAllItemsTask getTask = new GetAllItemsTask();
        getTask.execute();


        setContentView(R.layout.activity_watch_list);
        setTitle(R.string.titleWatchList);
        Button btnDeleteAll = findViewById(R.id.btnDeleteAll);
        btnDeleteAll.setOnClickListener(this);


    }

    @Override
    public void onItemClick(View view, int position) {
        //Toast.makeText(this, "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, FilmDescriptionActivity.class));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnDeleteAll:
                DeleteWatchList deleteWatchList =new DeleteWatchList();
                deleteWatchList.execute();

                GetAllItemsTask updateAfterDelete= new GetAllItemsTask();
                updateAfterDelete.execute();

                break;
        }
    }

    @Override
    public void onResponse(String response) {
        try {
        JSONObject jsonResponse = new JSONObject();
        JSONArray results = new JSONArray();
        jsonResponse = new JSONObject(response);
        results = jsonResponse.getJSONArray("results");
        String res =  results.toString();

            switch(dataType) {
                case "movie":
                    searchData = new Gson().fromJson(results.toString(), new TypeToken<List<Movie>>(){}.getType());
                    break;
                case "tv":
                    searchData = new Gson().fromJson(results.toString(), new TypeToken<List<Tv>>(){}.getType());
                    break;
                case "person":
                    searchData = new Gson().fromJson(results.toString(), new TypeToken<List<People>>(){}.getType());
                    break;

            }

            if(searchData.size() == 0) {
                Toast.makeText(this, getString(R.string.toastNoResult), Toast.LENGTH_LONG).show();
            } /*else {
                Toast.makeText(this, getString(R.string.toastResult, String.valueOf(searchData.size())), Toast.LENGTH_LONG).show();
            }*/ // the api always returns 20 results

            RecyclerView recyclerView = findViewById(R.id.rvWatchList);
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            adapter = new MyRecyclerViewAdapter(this, searchData);
            adapter.setClickListener(this);
            recyclerView.setAdapter(adapter);
        }
        catch (JSONException e){
            e.printStackTrace();
        }

    }

    class GetAllItemsTask extends AsyncTask<Void, Void, List<WatchListItem>> {


        @Override
        protected List<WatchListItem> doInBackground(Void... params) {
            return DAO.getAllWatchListItems();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            // use items to update the UI - e.g. create a new RecyclerView


            // set up the RecyclerView
            /*RecyclerView recyclerView = findViewById(R.id.rvWatchList);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            adapter = new MyRecyclerViewAdapter(this, data);
            adapter.setClickListener(this);
            recyclerView.setAdapter(adapter);*/

        }


        @Override
        protected void onPostExecute(List<WatchListItem> items) {
            super.onPostExecute(items);

            /*this was for testing it uses the buildNames function and puts it in a text box
            i have removed the text box for the app going live
            String namesList = buildNames(items);
            int i =0;
            TextView tvNames= findViewById(R.id.tvNames);
            tvNames.setText(namesList);*/


            //put the items fetched into the list made earlier
            List<WatchListItem> allItems = items;
            for(WatchListItem watchListItem : allItems){
                if(watchListItem.getType().equals("movie")){
                    dataType = "movie";
                    FetchApi.GetMedia(Long.toString(watchListItem.getId()), getApplicationContext(), "movie", WatchListActivity.this, WatchListActivity.this);
                }
                else if(watchListItem.getType().equals("tv")){
                    dataType = "tv";
                    FetchApi.GetMedia((Long.toString(watchListItem.getId())),getApplicationContext(), "tv",WatchListActivity.this, WatchListActivity.this);
                }
            }

        }

    }


    /* this builds a a string of all the items names
   private String buildNames(List<WatchListItem> items) {
        StringBuilder names = new StringBuilder();
        for (WatchListItem item : items){
            names.append(item.getName()).append(" ");

        }
        return names.toString();
    }*/

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.d("debug", error.toString());
    }


    class DeleteWatchList extends AsyncTask<WatchListItem, Void, Void>{

        @Override
        protected Void doInBackground(WatchListItem... watchListItems) {

            DAO.nukeTable();

            return null;
        }
    }







}











