package rgu.com.hitlist.tmdbApi;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;


public class FetchApi {
    private static final String API_KEY = "beec08af73f4b8ae411ad8148d339a5a";
    private static final String LANGUAGE = "en";
    private static final String SEARCH_MOVIE_URL = "https://api.themoviedb.org/3/search/movie";

    private RequestQueue queue;

    public void Search(String query, Context context, Response.Listener<String> res, Response.ErrorListener err) {
        queue = Volley.newRequestQueue(context);
        String url = SEARCH_MOVIE_URL +"?api_key=" + API_KEY + "&language=" + LANGUAGE + "&query=" + query;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, res, err);

        queue.add(stringRequest);
    }
}

