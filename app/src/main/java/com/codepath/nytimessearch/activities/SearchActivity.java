package com.codepath.nytimessearch.activities;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

import com.codepath.nytimessearch.interfaces.FilterSearchDialogListener;
import com.codepath.nytimessearch.R;
import com.codepath.nytimessearch.adapters.ArticleArrayAdapter;
import com.codepath.nytimessearch.fragments.FilterSearchDialogFragment;
import com.codepath.nytimessearch.models.Article;
import com.codepath.nytimessearch.models.SearchFilter;
import com.codepath.nytimessearch.utils.EndlessScrollListener;
import com.codepath.nytimessearch.utils.NYTimesAPIUtils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

import cz.msebera.android.httpclient.Header;

public class SearchActivity extends AppCompatActivity implements FilterSearchDialogListener {

    EditText etQuery;
    GridView gvResults;
    Button btnSearch;

    ArrayList<Article> articles;
    ArticleArrayAdapter adapter;

    SearchFilter searchFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupViews();
    }

    // Menu icons are inflated just as they were with actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void setupViews() {
        etQuery = (EditText) findViewById(R.id.etSearch);
        gvResults = (GridView) findViewById(R.id.gvResults);
        btnSearch = (Button) findViewById(R.id.btnSearch);
        articles = new ArrayList<Article>();
        adapter = new ArticleArrayAdapter(this, articles);
        gvResults.setAdapter(adapter);

        gvResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                             @Override
                                             public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                                 //create an itent to display the article
                                                 Intent i = new Intent(getApplicationContext(), ArticleActivity.class);

                                                 //get the article to display
                                                 Article article = articles.get(position);

                                                 //pass the article to the intent
                                                 i.putExtra("article", article);

                                                 //launch the activity
                                                 startActivity(i);

                                             }
                                         }
        );

        gvResults.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                loadNextPage(page);
                return true; // ONLY if more data is actually being loaded; false otherwise.
            }
        });
    }


    public void onArticleSearch(View view) {

        if (articles.size() > 0) {
            articles.clear();
            adapter.notifyDataSetChanged();
        }

        String query = etQuery.getText().toString();

        AsyncHttpClient client = new AsyncHttpClient();
        String url = "https://api.nytimes.com/svc/search/v2/articlesearch.json";

        RequestParams params = NYTimesAPIUtils.formatParams(searchFilter, query, getString(R.string.nytimes_api_key), 0);

        makeAPICall(client, url, params);
    }

    public void loadNextPage(int pageNum) {

        String query = etQuery.getText().toString();

        AsyncHttpClient client = new AsyncHttpClient();
        String url = "https://api.nytimes.com/svc/search/v2/articlesearch.json";

        RequestParams params = NYTimesAPIUtils.formatParams(searchFilter, query, getString(R.string.nytimes_api_key), pageNum);

        makeAPICall(client, url, params);
    }

    public void makeAPICall(AsyncHttpClient client, String url, RequestParams params) {
        client.get(url, params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                JSONArray articleJsonResults = null;

                try {
                    articleJsonResults = response.getJSONObject("response").getJSONArray("docs");
                    adapter.addAll(Article.fromJsonArray(articleJsonResults));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.e("ERROR", errorResponse.toString());
            }
        });
    }

    public void showFilterSearchDialog(MenuItem item) {
        FragmentManager fm = getSupportFragmentManager();
        FilterSearchDialogFragment filterSearchDialogFragment = FilterSearchDialogFragment.newInstance("Filter Article Search");

        //set the current search preferences
        Bundle bundle = new Bundle();
        bundle.putSerializable("searchFilter", searchFilter);
        filterSearchDialogFragment.setArguments(bundle);
        filterSearchDialogFragment.show(fm, "fragment_filter_search");
    }


    @Override
    public void onFinishFilterSearchDialog(SearchFilter filter) {
        this.searchFilter = filter;
    }
}
