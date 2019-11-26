package rgu.com.hitlist.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Watchlist_table")
public class WatchListItem {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String imdb_id;

    private String original_title;

    private String overview;

    private String status;

    public WatchListItem(String imdb_id, String original_title, String overview, String status) {
        this.imdb_id = imdb_id;
        this.original_title = original_title;
        this.overview = overview;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImdb_id() {
        return imdb_id;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public String getOverview() {
        return overview;
    }

    public String getStatus() {
        return status;
    }
}