package rgu.com.hitlist.tmdbApi;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;


public class FetchApi {
    private static final String API_KEY = "beec08af73f4b8ae411ad8148d339a5a";
    private static final String SEARCH_URL = "https://api.themoviedb.org/3/search/";
    private static final String GET_URL = "https://api.themoviedb.org/3/";
    private static final String GET_TRENDING_MOVIE_URL = "https://api.themoviedb.org/3/trending/movie/";
    private static final String GET_TRENDING_TV_URL = "https://api.themoviedb.org/3/trending/tv/";
    private static final String GET_TRENDING_PERSON_URL = "https://api.themoviedb.org/3/trending/person/";
    private static final String GET_TRENDING_URL = "https://api.themoviedb.org/3/trending/";

    public static void Search(String query, Context context, String type, Response.Listener<String> res, Response.ErrorListener err) {
        SharedPreferences prefs = context.getSharedPreferences("LANGUAGE", MODE_PRIVATE);
        String language = prefs.getString("language", "en");
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = SEARCH_URL + type + "?api_key=" + API_KEY + "&language=" + language + "&query=" + query;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, res, err);

        queue.add(stringRequest);
    }

    public static void GetMedia(String id, Context context, String type, Response.Listener<String> res, Response.ErrorListener err) {
        SharedPreferences prefs = context.getSharedPreferences("LANGUAGE", MODE_PRIVATE);
        String language = prefs.getString("language", "en");
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = GET_URL + type + "/" + id + "?api_key=" + API_KEY + "&language=" + language;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, res, err);

        queue.add(stringRequest);
    }

    public static void TrendingDay(Context context, Response.Listener<String> res, Response.ErrorListener err, String type) {
        SharedPreferences prefs = context.getSharedPreferences("LANGUAGE", MODE_PRIVATE);
        String language = prefs.getString("language", "en");
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = GET_TRENDING_URL + type + "/day?api_key=" + API_KEY + "&language=" + language;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, res, err);

        queue.add(stringRequest);
    }

}

