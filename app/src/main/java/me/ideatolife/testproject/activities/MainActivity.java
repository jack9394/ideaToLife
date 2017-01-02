package me.ideatolife.testproject.activities;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.support.v7.widget.Toolbar;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import java.util.ArrayList;

import me.ideatolife.testproject.AppController;
import me.ideatolife.testproject.R;
import me.ideatolife.testproject.Utils;
import me.ideatolife.testproject.adapters.GridViewAdapter;
import me.ideatolife.testproject.base.BaseActivity;
import me.ideatolife.testproject.handlers.FlickerHandler;
import me.ideatolife.testproject.utils.GridSpacingItemDecoration;
import me.ideatolife.testproject.volley.PerformeAction;

public class MainActivity extends BaseActivity {
    private ArrayList flickrObjectArrayList;
    RecyclerView recyclerView;
    private LinearLayoutManager gridLayoutManager;
    private PerformeAction performeAction;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFeeds();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getFeeds();
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_to_refresh);
        setTitle("Flicker Feeds");
    }

    private void getFeeds() {
        performeAction = new PerformeAction.Builder<>()
                .context(this)
                .showLoader(false)
                .url("https://api.flickr.com/services/feeds/photos_public.gne?format=json&nojsoncallback=1")
                .asJson(true)
                .type(PerformeAction.GET)
                .errorListener(new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                })
                .listener(new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        FlickerHandler flickerHandler = new FlickerHandler(response);
                        flickrObjectArrayList = flickerHandler.getItems();
                        setfeedList();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                })
                .build();

        performeAction.run();
    }

    private void setfeedList() {
        GridViewAdapter adapter = new GridViewAdapter(this, flickrObjectArrayList);
        gridLayoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(gridLayoutManager);
//        recyclerView.addItemDecoration(new GridSpacingItemDecoration(Utils.dipToPixels(this, 5)));
        recyclerView.setAdapter(adapter);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppController.getmInstance().getRequestQueue().cancelAll(performeAction.getUrl());
    }
}
