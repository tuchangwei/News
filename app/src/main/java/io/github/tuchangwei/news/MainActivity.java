package io.github.tuchangwei.news;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {


    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.list_view);


        //测试数据
        List<NewsModel> list = new ArrayList<NewsModel>();
        list.add(new NewsModel("这是内容","这是标题","iconurl"));
        NewsAdapter newsAdapter = new NewsAdapter(this,list);
        listView.setAdapter(newsAdapter);
    }

    public class NewsTask extends AsyncTask <String, Void, List<NewsModel>> {


        @Override
        protected List<NewsModel> doInBackground(String... strings) {
            return null;
        }

        @Override
        protected void onPostExecute(List<NewsModel> newsModels) {
            super.onPostExecute(newsModels);

        }
    }
}
