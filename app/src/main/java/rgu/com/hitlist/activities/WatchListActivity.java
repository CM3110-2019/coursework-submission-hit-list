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
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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

public class WatchListActivity extends AppCompatActivity implements MyRecyclerViewAdapter.ItemClickListener, View.OnClickListener, Response.ErrorListener {

    MyRecyclerViewAdapter adapter;
    private DAO DAO;
    // create a global list of watchlistitems
    public List<WatchListItem> allItems;
    List<Media> data = new ArrayList<>();
    public Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //recycler view updater
        context = getApplicationContext();

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
        //startActivity(new Intent(this, FilmDescriptionActivity.class));

        Media m = data.get(position);
        if(m instanceof Movie) {
            Intent movieIntent = new Intent(this, FilmDescriptionActivity.class);
            movieIntent.putExtra("movie", m);
            startActivity(movieIntent);
        } else if(m instanceof Tv) {
            Intent TvIntent = new Intent(this, TVDescriptionActivity.class);
            TvIntent.putExtra("tv", m);
            startActivity(TvIntent);
        }
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


            /*String namesList = buildNames(items);
            TextView tvNames= findViewById(R.id.tvNames);
            tvNames.setText(namesList);*/


            //put the items fetched into the global list
            allItems = items;
            setAdapter();
        }

    }

    void setAdapter() {
        //Log.d("debug", allItems+"");

        for(WatchListItem i : allItems) {
            switch(i.getType()) {
                case "movie":
                    data.add(new Movie(i.getId(), i.getName(), i.getPopularity(), i.getPoster_path()));
                    break;
                case "tv":
                    data.add(new Tv(i.getId(), i.getName(), i.getPopularity(), i.getPoster_path()));
                    break;
            }
        }

        Log.d("debug", data+"");


        RecyclerView recyclerView = findViewById(R.id.rvWatchList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyRecyclerViewAdapter(this, data);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

    }



   private String buildNames(List<WatchListItem> items) {
        StringBuilder names = new StringBuilder();
        for (WatchListItem item : items){
            names.append("NAME: "+item.getName()).append(" TYPE: "+item.getType()).append("\n");

        }

        return names.toString();
    }

    class DeleteWatchList extends AsyncTask<WatchListItem, Void, Void>{

        @Override
        protected Void doInBackground(WatchListItem... watchListItems) {

            DAO.destroyTable();

            return null;
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.d("debug", "error");
    }
}











