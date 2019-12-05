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
    // create a global list of watchlistitems
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

        }


        @Override
        protected void onPostExecute(List<WatchListItem> items) {
            super.onPostExecute(items);


            String namesList = buildNames(items);
            TextView tvNames= findViewById(R.id.tvNames);
            tvNames.setText(namesList);


            //put the items fetched into the global list
            allItems = items;

        }

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







}











