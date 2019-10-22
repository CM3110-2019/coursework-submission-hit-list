package rgu.com.hitlist.tmdbApi;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    private static final String GET_IMAGE_URL = "https://image.tmdb.org/t/p/w200";
    ImageView imageView;

    public DownloadImageTask(ImageView imageView) {
        this.imageView = imageView;
    }

    @Override
    public Bitmap doInBackground(String... strings) {
        String urldisplay = GET_IMAGE_URL + strings[0];
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
