package rgu.com.hitlist.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DAO {

    @Insert
    void insert(WatchListItem WatchListItem);

    @Update
    void update(WatchListItem WatchListItem);

    @Delete
    void delete(WatchListItem WatchListItem);

    @Query( "SELECT * FROM  watchlist_table ORDER BY id DESC")
    LiveData<List<WatchListItem>> getAllWatchListItems();
}
