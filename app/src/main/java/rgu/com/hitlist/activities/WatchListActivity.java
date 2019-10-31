package rgu.com.hitlist.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

import rgu.com.hitlist.model.Movie;
import rgu.com.hitlist.adapter.MyRecyclerViewAdapter;
import rgu.com.hitlist.R;

public class WatchListActivity extends AppCompatActivity implements MyRecyclerViewAdapter.ItemClickListener {

    MyRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
