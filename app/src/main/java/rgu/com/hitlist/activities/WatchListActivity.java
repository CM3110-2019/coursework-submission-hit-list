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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import rgu.com.hitlist.database.DAO;
import rgu.com.hitlist.database.WatchListItem;
import rgu.com.hitlist.database.WatchlistDB;
import rgu.com.hitlist.adapter.MyRecyclerViewAdapter;
import rgu.com.hitlist.R;

public class WatchListActivity extends AppCompatActivity implements MyRecyclerViewAdapter.ItemClickListener {

    MyRecyclerViewAdapter adapter;
    private DAO DAO;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
                //recycler view updater

        this.DAO = WatchlistDB.getInstance(this).DAO();

        GetAllItemsTask getTask = new GetAllItemsTask();
        getTask.execute();







        setContentView(R.layout.activity_watch_list);
        setTitle(R.string.titleWatchList);

        // data to populate the RecyclerView with
        /*List<Movie> data = new ArrayList<>();
        data.add(new Movie("The Irishman"));
        data.add(new Movie("title2"));
        data.add(new Movie("title3"));
        data.add(new Movie("title4"));*/


        // set up the RecyclerView
        /*RecyclerView recyclerView = findViewById(R.id.rvWatchList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyRecyclerViewAdapter(this, data);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);*/
    }

    @Override
    public void onItemClick(View view, int position) {
        //Toast.makeText(this, "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, FilmDescriptionActivity.class));
    }
    class GetAllItemsTask extends AsyncTask<Void, Void, List<WatchListItem>> {
        @Override
        protected List<WatchListItem> doInBackground(Void... params) {
            return DAO.getAllWatchListItems();
        }

        protected void onPreExecute(List<WatchListItem> items) {
            super.onPostExecute(items);
            // use items to update the UI - e.g. create a new RecyclerView

            Log.d("debug", "Found " + items.size() + " items");
        }
    }






}
