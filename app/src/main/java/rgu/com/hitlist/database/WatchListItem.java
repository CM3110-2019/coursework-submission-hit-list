package rgu.com.hitlist.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "watchlist_table",indices = {@Index(value = {"item_id",},
        unique = true)})
public class WatchListItem {


    @PrimaryKey(autoGenerate = true)
    private int db_id;

    @ColumnInfo(name = "item_id")
    private long id;

    @ColumnInfo(name  ="item_pop")
    private float popularity;

    @ColumnInfo(name  ="item_poster")
    private String poster_path;

    @ColumnInfo(name  ="item_name")
    private String name;

    @ColumnInfo(name = "item_type")
    private String type;


    public WatchListItem(long id, String name,float popularity,String poster_path, String type) {
        this.id = id;
        this.popularity = popularity;
        this.poster_path = poster_path;
        this.name = name;
        this.type = type;

    }

    public int getDb_id() {
        return db_id;
    }

    public void setDb_id(int db_id) {
        this.db_id = db_id;
    }

    public float getPopularity() {
        return popularity;
    }

    public String getPoster_path() {
        return poster_path;
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