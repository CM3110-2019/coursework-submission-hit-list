package rgu.com.hitlist.tmdbApi;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


public class FetchApi {
    private static final String API_KEY = "beec08af73f4b8ae411ad8148d339a5a";
    private static final String LANGUAGE = "en";
    private static final String SEARCH_MOVIE_URL = "https://api.themoviedb.org/3/search/movie";
    private static final String GET_MOVIE_URL = "https://api.themoviedb.org/3/movie/";
    private static final String GET_TRENDING_MOVIE_URL = "https://api.themoviedb.org/3/trending/movie/";
    private static final String GET_TRENDING_TV_URL = "https://api.themoviedb.org/3/trending/tv/";
    private static final String GET_TRENDING_PERSON_URL = "https://api.themoviedb.org/3/trending/person/";

    public static void Search(String query, Context context, Response.Listener<String> res, Response.ErrorListener err) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = SEARCH_MOVIE_URL +"?api_key=" + API_KEY + "&language=" + LANGUAGE + "&query=" + query;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, res, err);

        queue.add(stringRequest);
    }

    public static void GetMovie(String id, Context context, Response.Listener<String> res, Response.ErrorListener err) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = GET_MOVIE_URL + id +"?api_key=" + API_KEY + "&language=" + LANGUAGE;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, res, err);

        queue.add(stringRequest);
    }

    public static void TrendingMoviesDay(Context context, Response.Listener<String> res, Response.ErrorListener err){
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = GET_TRENDING_MOVIE_URL +"/day?api_key=" + API_KEY + "&language=" + LANGUAGE;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, res, err);

        queue.add(stringRequest);
    }

    public static void TrendingTVDay(Context context, Response.Listener<String> res, Response.ErrorListener err){
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = GET_TRENDING_TV_URL +"/day?api_key=" + API_KEY + "&language=" + LANGUAGE;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, res, err);

        queue.add(stringRequest);
    }

    public static void TrendingPersonDay(Context context, Response.Listener<String> res, Response.ErrorListener err){
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = GET_TRENDING_PERSON_URL +"/day?api_key=" + API_KEY + "&language=" + LANGUAGE;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, res, err);

        queue.add(stringRequest);
    }
}

