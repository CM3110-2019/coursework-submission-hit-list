package rgu.com.hitlist.database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class WatchlistRepository {
    private DAO DAO;
    private LiveData<List<WatchListItem>> allWatchListItems;

    public WatchlistRepository(Application application){
        WatchlistDB database = WatchlistDB.getInstance(application);
        DAO = database.DAO();
        allWatchListItems = DAO.getAllWatchListItems();
    }
    public void insert(WatchListItem watchListItem){
        new InsertWatchlistAsyncTask(DAO).execute(watchListItem);
    }
    public void update(WatchListItem watchListItem){
        new UpdateWatchlistAsyncTask(DAO).execute(watchListItem);
    }
    public void delete(WatchListItem watchListItem){
        new DeleteWatchlistAsyncTask(DAO).execute(watchListItem);
    }

    public LiveData<List<WatchListItem>> getAllWatchListItems() {
        return allWatchListItems;
    }

    private static class InsertWatchlistAsyncTask extends AsyncTask<WatchListItem, Void, Void>{
        private DAO DAO;

        private InsertWatchlistAsyncTask(DAO DAO){
            this.DAO = DAO;
        }
        @Override
        protected Void doInBackground(WatchListItem... watchListItems) {
            DAO.insert(watchListItems[0]);
            return null;
        }
    }
    private static class UpdateWatchlistAsyncTask extends AsyncTask<WatchListItem, Void, Void>{
        private DAO DAO;

        private UpdateWatchlistAsyncTask(DAO DAO){
            this.DAO = DAO;
        }
        @Override
        protected Void doInBackground(WatchListItem... watchListItems) {
            DAO.update(watchListItems[0]);
            return null;
        }
    }
    private static class DeleteWatchlistAsyncTask extends AsyncTask<WatchListItem, Void, Void>{
        private DAO DAO;

        private DeleteWatchlistAsyncTask(DAO DAO){
            this.DAO = DAO;
        }
        @Override
        protected Void doInBackground(WatchListItem... watchListItems) {
            DAO.delete(watchListItems[0]);
            return null;
        }
    }

}
