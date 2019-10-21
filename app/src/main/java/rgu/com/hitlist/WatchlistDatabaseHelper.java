package rgu.com.hitlist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class WatchlistDatabaseHelper extends SQLiteOpenHelper {
    //creating the table name and the column names
    public static final String DATABASE_NAME = "WatchList.db";
    public static final String TABLE_NAME = "userWatchList_table";
    public static final String COLUMN_1 = "ID";
    public static final String COLUMN_2 = "TITLE";

    public WatchlistDatabaseHelper(Context context) {
        //super class of the data base
        super(context, DATABASE_NAME, null, 1);

        //ignore this its just for testing it will create an empty database
        SQLiteDatabase db = this.getWritableDatabase() ;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //create the database method
        db.execSQL("create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, TITLE STRING)");
    }

    @Override
    //update the database method
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }
}