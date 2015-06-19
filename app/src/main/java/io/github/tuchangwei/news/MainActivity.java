package io.github.tuchangwei.news;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {


    private ListView listView;
    public static final String URL = "http://www.imooc.com/api/teacher?type=4&num=30";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.list_view);


//        //测试数据
//        List<NewsModel> list = new ArrayList<NewsModel>();
//        list.add(new NewsModel("这是内容","这是标题","iconurl"));
//        NewsAdapter newsAdapter = new NewsAdapter(this,list);
//        listView.setAdapter(newsAdapter);

        new NewsAsyncTask().execute(URL);
    }

    public List<NewsModel> getJsonDataFromURL(String urlStr) {

        try {

            URL url = new URL(urlStr);
            //字节流
            InputStream inputStream = url.openStream();
            //将字节流转换为字符串流
            String jsonStr = readStream(inputStream);
            JSONObject jsonObject = new JSONObject(jsonStr);

            JSONArray newsJsonArray = jsonObject.getJSONArray("data");
            List<NewsModel> list = new ArrayList<NewsModel>();
            NewsModel model;
            for (int i=0;i<newsJsonArray.length();i++) {

                JSONObject news = newsJsonArray.getJSONObject(i);
                model = new NewsModel(news.getString("description"),news.getString("name"),news.getString("picSmall"));
                list.add(model);

            }


            return list;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String readStream(InputStream inputStream) throws IOException {

        InputStreamReader inputStreamReader = new InputStreamReader(inputStream,"utf-8");
        //将字符串流以buffer的形式读出
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String line ;
        String jsonStr = "";
        while ((line=bufferedReader.readLine())!=null) {

            jsonStr += line;
        }
        return jsonStr;
    }

    public class NewsAsyncTask extends AsyncTask <String, Void, List<NewsModel>> {


        @Override
        protected List<NewsModel> doInBackground(String... strings) {
            String urlStr = strings[0];
            return getJsonDataFromURL(urlStr);

        }

        @Override
        protected void onPostExecute(List<NewsModel> newsModels) {

            super.onPostExecute(newsModels);
            NewsAdapter newsAdapter = new NewsAdapter(MainActivity.this, newsModels);
            listView.setAdapter(newsAdapter);
        }
    }
}
