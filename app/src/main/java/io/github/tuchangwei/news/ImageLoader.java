package io.github.tuchangwei.news;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.util.LruCache;
import android.widget.ImageView;
import android.widget.ListView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vale on 6/19/15.
 */
public class ImageLoader {


    private LruCache lruCache;
    public List<NewsModel> mList;
    public ListView mListView;
    private List<ImageLoaderAsyncTask> mTasks = new ArrayList<ImageLoaderAsyncTask>();

    public ImageLoader() {

        int maxSize = (int) Runtime.getRuntime().maxMemory()/4;
        lruCache = new LruCache(maxSize);
    }

    public void setBitmapCache(String key, Bitmap bitmap) {

        lruCache.put(key, bitmap);
    }

    public Bitmap getBitmap(String key) {

        return (Bitmap) lruCache.get(key);
    }

    public void loadImageFrom(int start, int end) {

        for (int i=start; i< end; i++) {

            String urlStr = mList.get(i).url;
            ImageView imageView = (ImageView) mListView.findViewWithTag(urlStr);
            Bitmap bitmap = getBitmap(urlStr);
            if (bitmap != null) {

                imageView.setImageBitmap(bitmap);

            } else {

                ImageLoaderAsyncTask task = new ImageLoaderAsyncTask(imageView);
                task.execute(urlStr);
                mTasks.add(task);
            }
        }

    }

    public  void cancelAllTasks() {

        for (ImageLoaderAsyncTask task: mTasks) {
            task.cancel(false);

        }
    }
    public class ImageLoaderAsyncTask extends AsyncTask<String,Void,Bitmap> {

        ImageView mImageView;
        String mUrlStr;
        public ImageLoaderAsyncTask(ImageView mImageView) {

            this.mImageView = mImageView;
        }

        @Override
        protected Bitmap doInBackground(String... strings) {

            mUrlStr = strings[0];
            Bitmap bitmap = null;
            try {
                URL url1 = new URL(mUrlStr);
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

                String tag = (String) mImageView.getTag();

                setBitmapCache(tag,bitmap);

                if (tag.equals(mUrlStr)) {

                    //这里是个异步加载，当图片加载完成后， ImageView可能已经滚到其他位置了。
                    //这里是希望当前的ImageView加载当前的url图片
                    mImageView.setImageBitmap(bitmap);

                }

            }
        }
    }
}
