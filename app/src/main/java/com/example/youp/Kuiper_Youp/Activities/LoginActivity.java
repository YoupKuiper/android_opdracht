package com.example.youp.Kuiper_Youp.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.youp.Kuiper_Youp.Models.Authtoken;
import com.example.youp.Kuiper_Youp.Models.User;
import com.example.youp.Kuiper_Youp.R;
import com.example.youp.Kuiper_Youp.Webservice.LoginWebservice;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends Activity implements Callback<Authtoken> {

    LoginWebservice mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final Button button = findViewById(R.id.login_button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://inhollandbackend.azurewebsites.net/api/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                mService = retrofit.create(LoginWebservice.class);

                login();

            }
        });
    }

    private void login() {
        final TextView username = findViewById(R.id.username_input);
        final TextView password = findViewById(R.id.password_input);

        User user = new User();
        user.setUsername(username.getText().toString());
        user.setPassword(password.getText().toString());

        mService.login(user).enqueue(this);
    }

    @Override
    public void onResponse(Call<Authtoken> call, Response<Authtoken> response) {
        if(response.body() != null){
            Authtoken.getInstance().setAuthToken(response.body().getAuthToken());
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra("loggedIn", true);
            startActivity(intent);
        }
    }

    @Override
    public void onFailure(Call<Authtoken> call, Throwable t) {
        Snackbar.make(findViewById(R.id.root), "Error!", 600).show();

    }
}
