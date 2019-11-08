package rgu.com.hitlist.tmdbApi;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

import rgu.com.hitlist.R;

public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    private static final String GET_IMAGE_URL = "https://image.tmdb.org/t/p/";
    ImageView imageView;
    String size;
    ProgressBar progressBar;

    public DownloadImageTask(ImageView imageView, String size) {
        this.imageView = imageView;
        this.size = size;
    }

    @Override
    public Bitmap doInBackground(String... strings) {
        String urldisplay = GET_IMAGE_URL + size + strings[0];
        Bitmap bitmap = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            bitmap = BitmapFactory.decodeStream(in);
        } catch (MalformedURLException e) {
            Log.e("debug", "MalformedURLException: " + e);
        } catch (IOException e) {
            Log.e("debug", "IOException: " + e);
        }
        return bitmap;
    }

    public void onPostExecute(Bitmap result) {
        imageView.setImageBitmap(result);
    }
}
