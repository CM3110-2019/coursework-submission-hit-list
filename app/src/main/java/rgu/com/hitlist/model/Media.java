package rgu.com.hitlist.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public abstract class Media implements Serializable {
    private String homepage;
    private long id;
    private float popularity;

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
}
