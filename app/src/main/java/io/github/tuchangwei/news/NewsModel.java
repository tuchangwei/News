package io.github.tuchangwei.news;

/**
 * Created by vale on 6/19/15.
 */
public class NewsModel {

    String url,content,title;

    public NewsModel(String content, String title, String url) {
        this.content = content;
        this.title = title;
        this.url = url;
    }
}
