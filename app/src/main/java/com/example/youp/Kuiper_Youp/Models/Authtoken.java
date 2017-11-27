package com.example.youp.Kuiper_Youp.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by youp on 10/11/2017.
 */

public class Authtoken {
    private static final Authtoken ourInstance = new Authtoken();

    public static Authtoken getInstance() {
        return ourInstance;
    }

    @SerializedName("AuthToken")
    @Expose
    private String authToken;

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    private Authtoken() {
    }
}
