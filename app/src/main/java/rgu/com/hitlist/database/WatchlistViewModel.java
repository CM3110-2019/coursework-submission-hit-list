package rgu.com.hitlist.database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class WatchlistViewModel extends AndroidViewModel {
    private WatchlistRepository watchlistRepository;
    private LiveData<List<WatchListItem>> allWatchlistItems;

    public WatchlistViewModel(@NonNull Application application) {
        super(application);
        watchlistRepository = new WatchlistRepository(application);
        allWatchlistItems = watchlistRepository.getAllWatchListItems();
    }

    public void insert(WatchListItem watchListItem){
        watchlistRepository.insert(watchListItem);
    }
    public void update(WatchListItem watchListItem){
        watchlistRepository.update(watchListItem);
    }
    public void delete(WatchListItem watchListItem){
        watchlistRepository.delete(watchListItem);
    }

    public LiveData<List<WatchListItem>> getAllWatchlistItems() {
        return allWatchlistItems;
    }
}