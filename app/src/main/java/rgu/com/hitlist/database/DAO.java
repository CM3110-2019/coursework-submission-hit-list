package rgu.com.hitlist.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.OnConflictStrategy;


import java.util.List;

@Dao
public interface DAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(WatchListItem WatchListItem);

    @Delete
    void delete(WatchListItem WatchListItem);

    @Query( "SELECT * FROM  watchlist_table ORDER BY db_id DESC")
    List<WatchListItem> getAllWatchListItems();

    @Query("DELETE FROM watchlist_table")
    public void nukeTable();
}

