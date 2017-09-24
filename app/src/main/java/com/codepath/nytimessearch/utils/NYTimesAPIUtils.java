package com.codepath.nytimessearch.utils;

import android.util.Log;

import com.codepath.nytimessearch.R;
import com.codepath.nytimessearch.models.SearchFilter;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by michaelsignorotti on 9/24/17.
 */

public class NYTimesAPIUtils {


    public static RequestParams formatParams(SearchFilter searchFilter, String query, String apiKey, int pageNum) {

        RequestParams params = new RequestParams();
        params.put("api-key", apiKey);
        params.put("page", pageNum);
        params.put("q", query);

        if (searchFilter != null) {
            if (searchFilter.getBeginDate() != null) {
                params.put("begin_date", searchFilter.getBeginDateParam());
            }
            params.put("sort", searchFilter.getSortOrder().toLowerCase());

            if (searchFilter.isArt() || searchFilter.isFashionAndStyle() || searchFilter.isSports()) {
                String newsDesk = "news_desk:(";

                if (searchFilter.isArt()) {
                    newsDesk += "\"Arts\"";
                }

                if (searchFilter.isFashionAndStyle()) {
                    newsDesk += "\"Fashion & Style\"";
                }

                if (searchFilter.isSports()) {
                    newsDesk += "\"Sports\"";
                }

                newsDesk += ")";

                params.put("fq", newsDesk);
            }
        }

        return params;
    }
}
