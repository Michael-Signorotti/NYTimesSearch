package com.codepath.nytimessearch.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by michaelsignorotti on 9/23/17.
 */

public class Article implements Serializable{

    String webUrl;
    String headline;
    String thumbnail;


    public Article(JSONObject jsonObject) {
        try {
            this.webUrl = jsonObject.getString("web_url");
            this.headline = jsonObject.getJSONObject("headline").getString("main");

            JSONArray jsonArray = jsonObject.getJSONArray("multimedia");

            if (jsonArray.length() > 0) {
                JSONObject multimediaObject = jsonArray.getJSONObject(0);
                this.thumbnail = "http://www.nytimes.com/" + multimediaObject.getString("url");
            } else {
                this.thumbnail = "";
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public static ArrayList<Article> fromJsonArray(JSONArray jsonArray) {
        ArrayList<Article> articles = new ArrayList<Article>();

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                articles.add(new Article(jsonArray.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return articles;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public String getHeadline() {
        return headline;
    }

    public String getThumbnail() {
        return thumbnail;
    }
}
