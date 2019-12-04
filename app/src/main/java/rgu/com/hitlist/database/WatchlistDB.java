package rgu.com.hitlist.database;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import android.content.Context;
import android.os.AsyncTask;

@Database(entities = {WatchListItem.class}, version = 5, exportSchema = false)
public abstract class WatchlistDB extends RoomDatabase {

    public static WatchlistDB instance;

    public abstract DAO DAO();

    public static synchronized WatchlistDB getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    WatchlistDB.class,  "watchlist_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }


}