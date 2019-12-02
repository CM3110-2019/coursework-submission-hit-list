package rgu.com.hitlist.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import rgu.com.hitlist.database.DAO;
import rgu.com.hitlist.database.WatchListItem;
import rgu.com.hitlist.database.WatchlistDB;
import rgu.com.hitlist.model.Movie;
import rgu.com.hitlist.adapter.MyRecyclerViewAdapter;
import rgu.com.hitlist.R;

public class WatchListActivity extends AppCompatActivity implements MyRecyclerViewAdapter.ItemClickListener {

    MyRecyclerViewAdapter adapter;
    WatchlistDB watchlistDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
                //recycler view updater

                Toast.makeText(WatchListActivity.this, "database created", Toast.LENGTH_SHORT).show();

                watchlistDB.getInstance(getApplicationContext());







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
}
