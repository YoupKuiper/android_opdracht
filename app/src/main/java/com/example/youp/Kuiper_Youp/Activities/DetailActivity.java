package com.example.youp.Kuiper_Youp.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.youp.Kuiper_Youp.Models.Authtoken;
import com.example.youp.Kuiper_Youp.Models.Result;
import com.example.youp.Kuiper_Youp.R;
import com.example.youp.Kuiper_Youp.Webservice.MainWebservice;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by youp on 10/5/2017.
 */

public class DetailActivity extends Activity implements Callback {

    public static final String CONTENT = "8b:CONTENT";
    public static final String VIEW_NAME_HEADER_IMAGE = "8b:image";
    public static final String VIEW_NAME_HEADER_TITLE = "8b:title";
    public static final String VIEW_NAME_HEADER_DESCRIPTION = "8b:description";

    private MainWebservice mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        final Result content = getIntent().getParcelableExtra(CONTENT);

        ImageView image = findViewById(R.id.article_image);
        TextView name = findViewById(R.id.article_name);
        TextView description = findViewById(R.id.article_description);
        TextView url = findViewById(R.id.article_url);
        CheckBox isLiked = findViewById(R.id.article_isLiked);

        if(Authtoken.getInstance().getAuthToken() == null){
            isLiked.setVisibility(View.INVISIBLE);
        }else{
            isLiked.setVisibility(View.VISIBLE);
            isLiked.setChecked(content.getIsLiked());
        }

        url.setText(content.getUrl());

        image.setTransitionName(VIEW_NAME_HEADER_IMAGE);
        name.setTransitionName(VIEW_NAME_HEADER_TITLE);

        Glide.with(this).load(content.getImage()).centerCrop().dontAnimate().into(image);
        name.setText(content.getTitle());
        description.setText(content.getSummary());

        isLiked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isLiked) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://inhollandbackend.azurewebsites.net/api/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                mService = retrofit.create(MainWebservice.class);

                if(isLiked){
                    likeArticle(content.getId());
                }else{
                    noLongerLikeArticle(content.getId());
                }

            }
        });
    }

    private void likeArticle(int id){
        mService.likeArticle(Authtoken.getInstance().getAuthToken(), id).enqueue(this);
    }

    private void noLongerLikeArticle(int id){
        mService.noLongerLikeArticle(Authtoken.getInstance().getAuthToken(), id).enqueue(this);
    }

    @Override
    public void onResponse(Call call, Response response) {
        String a = "a";
    }

    @Override
    public void onFailure(Call call, Throwable t) {
        String b = "b";
    }
}

