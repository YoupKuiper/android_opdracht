package com.example.youp.Kuiper_Youp.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.youp.Kuiper_Youp.Models.RegisterResponse;
import com.example.youp.Kuiper_Youp.Models.User;
import com.example.youp.Kuiper_Youp.R;
import com.example.youp.Kuiper_Youp.Webservice.RegisterWebservice;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by youp on 10/9/2017.
 */

public class RegisterActivity extends Activity implements Callback<RegisterResponse>{

    RegisterWebservice mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final Button button = findViewById(R.id.register_button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                final TextView username = findViewById(R.id.username_input);
                final TextView password = findViewById(R.id.password_input);

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://inhollandbackend.azurewebsites.net/api/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                mService = retrofit.create(RegisterWebservice.class);

                User user = new User();
                user.setUsername(username.getText().toString());
                user.setPassword(password.getText().toString());
                mService.register(user).enqueue(new Callback<RegisterResponse>() {
                    @Override
                    public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {

                        if(response.isSuccessful()) {
                            Snackbar.make(findViewById(R.id.root), response.body().getMessage(), 600).show();
                        }else{
                            Snackbar.make(findViewById(R.id.root), response.body().getMessage(), 600).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<RegisterResponse> call, Throwable t) {
                        Snackbar.make(findViewById(R.id.root), "Error!", 600).show();
                    }
                });
            }
        });
    }

    @Override
    public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {

    }

    @Override
    public void onFailure(Call<RegisterResponse> call, Throwable t) {

    }
}
