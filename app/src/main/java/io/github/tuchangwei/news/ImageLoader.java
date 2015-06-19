package io.github.tuchangwei.news;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by vale on 6/19/15.
 */
public class ImageLoader {

    private ImageView imageView;
    private String urlStr;

    public ImageLoader(ImageView imageView, String urlStr) {
        this.imageView = imageView;
        this.urlStr = urlStr;
    }

    public void loadImage() {

        new ImageLoaderAsyncTask().execute(urlStr);

    }


    public class ImageLoaderAsyncTask extends AsyncTask<String,Void,Bitmap> {


        @Override
        protected Bitmap doInBackground(String... strings) {

            String urlStr = strings[0];
            Bitmap bitmap = null;
            try {
                URL url1 = new URL(urlStr);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url1.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
                inputStream.close();
                httpURLConnection.disconnect();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if (bitmap != null) {

                ImageLoader.this.imageView.setImageBitmap(bitmap);
            }
        }
    }
}
