package io.github.tuchangwei.news;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import io.github.tuchangwei.news.util.DebugLog;

/**
 * Created by vale on 6/19/15.
 */
public class NewsAdapter extends BaseAdapter implements AbsListView.OnScrollListener{


    List<NewsModel> mList;
    LayoutInflater mInflater;
    ImageLoader mImageLoader;
    ListView mListView;
    Boolean mFirstLoading = true;
    int mStartLoadingItem;
    int mEndLoadingItem;
    public NewsAdapter(Context context, List<NewsModel> list,ListView listView) {

        mInflater = LayoutInflater.from(context);
        mList = list;
        mImageLoader = new ImageLoader();
        mImageLoader.mListView = listView;
        mListView = listView;
        mListView.setOnScrollListener(this);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder viewHolder = null;
        if (view == null) {

            view = mInflater.inflate(R.layout.news_item,viewGroup,false);
            viewHolder = new ViewHolder();
            viewHolder.mContent = (TextView) view.findViewById(R.id.news_content);
            viewHolder.mTitle = (TextView) view.findViewById(R.id.news_title);
            viewHolder.mImage = (ImageView) view.findViewById(R.id.news_image);
            view.setTag(viewHolder);

        } else {

            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.mContent.setText(mList.get(i).content);
        viewHolder.mTitle.setText(mList.get(i).title);

        if (mImageLoader.getBitmap(mList.get(i).url) == null) {

            viewHolder.mImage.setImageResource(R.mipmap.ic_launcher);
        } else {

            viewHolder.mImage.setImageBitmap(mImageLoader.getBitmap(mList.get(i).url));
        }

        viewHolder.mImage.setTag(mList.get(i).url);
        //mImageLoader.imageView = viewHolder.mImage;
        //mImageLoader.loadImage();
        return view;
    }

    public void loadImagesFromStartToEnd () {

        mImageLoader.mList = mList;
        mImageLoader.loadImageFrom(mStartLoadingItem, mEndLoadingItem);
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {

        DebugLog.d("" + i);
        if (i == SCROLL_STATE_IDLE) {

            loadImagesFromStartToEnd();

        } else {

            mImageLoader.cancelAllTasks();
        }


    }

    @Override
    public void onScroll(AbsListView absListView, int i, int i1, int i2) {

        DebugLog.d(""+i+" "+i1+" "+i2);
        mStartLoadingItem = i;
        mEndLoadingItem = i+i1;

        if (mFirstLoading==true&&i1>0) {

            mFirstLoading = false;
            loadImagesFromStartToEnd();

        }
    }

    public class  ViewHolder {

        ImageView mImage;
        TextView mTitle;
        TextView mContent;


    }

}
