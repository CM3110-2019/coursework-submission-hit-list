package rgu.com.hitlist.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public abstract class Media implements Serializable {
    private String homepage;
    private long id;
    private float popularity;
    private String poster_path;

    @Override
    public String toString() {
        return "Media{" +
                "id=" + id +
                '}';
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public float getPopularity() {
        return popularity;
    }

    public void setPopularity(float popularity) {
        this.popularity = popularity;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }
}
