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

import java.util.ArrayList;
import java.util.List;

import rgu.com.hitlist.database.DAO;
import rgu.com.hitlist.database.WatchListItem;
import rgu.com.hitlist.database.WatchlistDB;
import rgu.com.hitlist.adapter.MyRecyclerViewAdapter;
import rgu.com.hitlist.R;

public class WatchListActivity extends AppCompatActivity implements MyRecyclerViewAdapter.ItemClickListener, View.OnClickListener {

    MyRecyclerViewAdapter adapter;
    private DAO DAO;
    // create a list of watchlistitems
    public List<WatchListItem> allItems;


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
            allItems = items;

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

    class DeleteWatchList extends AsyncTask<WatchListItem, Void, Void>{

        @Override
        protected Void doInBackground(WatchListItem... watchListItems) {

            DAO.nukeTable();

            return null;
        }
    }







}











