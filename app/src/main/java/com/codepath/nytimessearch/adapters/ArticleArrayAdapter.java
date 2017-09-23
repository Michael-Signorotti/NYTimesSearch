package com.codepath.nytimessearch.adapters;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.nytimessearch.R;
import com.codepath.nytimessearch.models.Article;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.loopj.android.http.AsyncHttpClient.log;

/**
 * Created by michaelsignorotti on 9/23/17.
 */

public class ArticleArrayAdapter extends ArrayAdapter<Article> {

    public ArticleArrayAdapter(Context context, List<Article> articles) {
        super(context, android.R.layout.simple_list_item_1, articles);
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the article for this position
        Article article = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_article_result, parent, false);
        }

        ImageView ivImage = (ImageView) convertView.findViewById(R.id.ivThumbnail);
        ivImage.setImageResource(0);

        TextView tvHeadLine = convertView.findViewById(R.id.tvHeadLine);
        tvHeadLine.setText(article.getHeadline());

        String thumbnail = article.getThumbnail();


        if (!TextUtils.isEmpty(thumbnail)) {
            Picasso.with(getContext()).load(thumbnail).into(ivImage);
        }

        return convertView;
    }

}
