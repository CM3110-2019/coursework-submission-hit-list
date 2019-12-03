package rgu.com.hitlist.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "watchlist_table")
public class WatchListItem {
    @PrimaryKey(autoGenerate = true)
    private int db_id;

    private long id;

    String name;

    String type;


    public WatchListItem(long id, String name, String type) {
        this.id = id;
        this.name = name;
        this.type = type;

    }

    public int getDb_id() {
        return db_id;
    }

    public void setDb_id(int db_id) {
        this.db_id = db_id;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }
}