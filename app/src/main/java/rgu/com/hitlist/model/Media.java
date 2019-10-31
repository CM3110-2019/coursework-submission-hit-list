package rgu.com.hitlist.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class Media implements Serializable {
    private String backdrop_path;
    private List<Map<String, String>> genres;
    private String homepage;
    private long id;
    private String original_language;
    private String overview;
    private float popularity;
    private String poster_path;
    private List<Map<String, String>> production_companies;
    private String status;
    private float vote_average;
    private int vote_count;

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public List<Map<String, String>> getGenres() {
        return genres;
    }

    public void setGenres(List<Map<String, String>> genres) {
        this.genres = genres;
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

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
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

    public List<Map<String, String>> getProduction_companies() {
        return production_companies;
    }

    public void setProduction_companies(List<Map<String, String>> production_companies) {
        this.production_companies = production_companies;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public float getVote_average() {
        return vote_average;
    }

    public void setVote_average(float vote_average) {
        this.vote_average = vote_average;
    }

    public int getVote_count() {
        return vote_count;
    }

    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }
}
