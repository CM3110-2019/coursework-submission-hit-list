package rgu.com.hitlist.database;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import android.content.Context;
import android.os.AsyncTask;

@Database(entities = {WatchListItem.class}, version = 1)
public abstract class WatchlistDB extends RoomDatabase {

    public static WatchlistDB instance;

    public abstract DAO DAO();

    public static synchronized WatchlistDB getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    WatchlistDB.class,  "watchlist_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopAsyncTask(instance).execute();
        }
    };

    private static class PopAsyncTask extends AsyncTask<Void,Void,Void>{
        private DAO DAO;
        private PopAsyncTask(WatchlistDB db){
            DAO = db.DAO();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            //creating a test to see that i can populate the db
            DAO.insert(new WatchListItem("1","a","a test","live"));
            return null;
        }
    }

}
