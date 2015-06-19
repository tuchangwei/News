package io.github.tuchangwei.news;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by vale on 6/19/15.
 */
public class NewsAdapter extends BaseAdapter {


    List<NewsModel> mList;
    LayoutInflater mInflater;

    public NewsAdapter(Context context, List<NewsModel> list) {

        mInflater = LayoutInflater.from(context);
        mList = list;
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
        viewHolder.mImage.setImageResource(R.mipmap.ic_launcher);

        return view;
    }

    public class  ViewHolder {

        ImageView mImage;
        TextView mTitle;
        TextView mContent;


    }

}
