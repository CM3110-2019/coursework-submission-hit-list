package rgu.com.hitlist.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "watchlist_table")
public class WatchListItem {
    @PrimaryKey(autoGenerate = true)
    private int db_id;

    @ColumnInfo(name = "item_id")
    private long id;

    @ColumnInfo(name  ="item_name")
    String name;

    @ColumnInfo(name = "item_type")
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