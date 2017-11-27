package com.example.youp.Kuiper_Youp.Activities;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.youp.Kuiper_Youp.Adapters.NewsItemsAdapter;
import com.example.youp.Kuiper_Youp.Models.Authtoken;
import com.example.youp.Kuiper_Youp.Models.Result;
import com.example.youp.Kuiper_Youp.Models.RootObject;
import com.example.youp.Kuiper_Youp.R;
import com.example.youp.Kuiper_Youp.Webservice.MainWebservice;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity implements Callback<RootObject>, NewsItemsAdapter.NewsItemListener {

    private RecyclerView mRecyclerView;
    private NewsItemsAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private MainWebservice mService;
    private ActionBarDrawerToggle toggle;
    private View refresh_button;

    private String[] mMenuItems;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private int nextId;
    private final int AMOUNT_OF_ARTICLES_TO_GET = 20;

    private int previousTotal = 0;
    private boolean loading = true;
    private int visibleThreshold = 5;
    int firstVisibleItem, visibleItemCount, totalItemCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.newsitems_list);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                visibleItemCount = mRecyclerView.getChildCount();
                totalItemCount = mLayoutManager.getItemCount();
                firstVisibleItem = mLayoutManager.findFirstVisibleItemPosition();

                if (loading) {
                    if (totalItemCount > previousTotal) {
                        loading = false;
                        previousTotal = totalItemCount;
                    }
                }
                if (!loading && (totalItemCount - visibleItemCount)
                        <= (firstVisibleItem + visibleThreshold)) {

                    fetchMoreContent();

                    loading = true;
                }
            }
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://inhollandbackend.azurewebsites.net/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mService = retrofit.create(MainWebservice.class);

        mMenuItems = new String[]{"Login", "Register", "Liked", "Log out"};
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toggle = new ActionBarDrawerToggle
                (
                        this,
                        mDrawerLayout,
                        R.string.navigation_drawer_open,
                        R.string.navigation_drawer_close
                )
        {
        };
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();


        // Set the adapter for the list view
        mDrawerList.setAdapter(new ArrayAdapter<>(this,
                R.layout.drawer_list_item, mMenuItems));

        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        mAdapter = new NewsItemsAdapter(this, new ArrayList<Result>(), this);
        mRecyclerView.setAdapter(mAdapter);
        fetchContent();


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (toggle.onOptionsItemSelected(item))
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        Intent intent = new Intent(MainActivity.this, MainActivity.class);
        finish();
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_bar, menu);

        return super.onCreateOptionsMenu(menu);
    }

    private void fetchContent() {
        if(Authtoken.getInstance().getAuthToken() == null){
            mService.getArticles().enqueue(this);
        }else{
            mService.getArticlesForLoggedInUser(Authtoken.getInstance().getAuthToken()).enqueue(this);
        }
    }

    private void fetchMoreContent() {
        if(Authtoken.getInstance().getAuthToken() == null){
            mService.getMoreArticles(nextId, AMOUNT_OF_ARTICLES_TO_GET).enqueue(this);
        }else{
            mService.getMoreArticlesForLoggedInUser(Authtoken.getInstance().getAuthToken(), nextId, AMOUNT_OF_ARTICLES_TO_GET).enqueue(this);
        }
    }

    private void fetchLikedArticles() {
        if(Authtoken.getInstance().getAuthToken() != null){
            mService.getAllLikedArticles(Authtoken.getInstance().getAuthToken()).enqueue(this);
        }else{

        }
    }

    @Override
    public void onResponse(Call<RootObject> call, Response<RootObject> response) {
        if (response.isSuccessful() && response.body() != null) {
            RootObject res = response.body();

            if(nextId != res.getNextId()){
                nextId = res.getNextId();

                mAdapter.setmItems(res.getResults());

                runOnUiThread(new Runnable() {
                    public void run() {
                        mAdapter.notifyDataSetChanged();
                    }
                });
            }
        }
    }

    @Override
    public void onFailure(Call<RootObject> call, Throwable t) {
        //something went wrong
        Log.e("Exercise6", "Could net fetch data", t);
    }

    public boolean refreshArticles(MenuItem item){
        Intent intent = new Intent(MainActivity.this, MainActivity.class);
        finish();
        startActivity(intent);
        return true;
    }

    @Override
    public void onItemClick(View view, Result content) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(DetailActivity.CONTENT, content);
        ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(
                this,
                // Now we provide a list of Pair items which contain the view we can transitioning
                // from, and the name of the view it is transitioning to, in the launched activity
                new Pair<>(view.findViewById(R.id.listitem_image),
                        DetailActivity.VIEW_NAME_HEADER_IMAGE),
                new Pair<>(view.findViewById(R.id.listitem_title),
                        DetailActivity.VIEW_NAME_HEADER_TITLE),
                new Pair<>(view.findViewById(R.id.listitem_description),
                        DetailActivity.VIEW_NAME_HEADER_DESCRIPTION));


        startActivity(intent, activityOptions.toBundle());
    }


    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            TextView v = (TextView) view;
            selectItem(v.getText().toString());
        }
    }

    private void selectItem(String viewText) {

        Intent intent;
        switch(viewText) {

            case "Login":
                intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                break;
            case "Register":
                intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
            case "Liked":
                mAdapter.clearList();
                runOnUiThread(new Runnable() {
                    public void run() {
                        mAdapter.notifyDataSetChanged();
                    }
                });
                fetchLikedArticles();
                break;
            case "Log out":
                Authtoken.getInstance().setAuthToken(null);
                intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
                break;
            default:
                intent = null;
        }
    }

}
