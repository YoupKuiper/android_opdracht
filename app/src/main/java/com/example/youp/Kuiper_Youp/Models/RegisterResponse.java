package com.example.youp.Kuiper_Youp.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by youp on 10/10/2017.
 */

public class RegisterResponse {
    @SerializedName("Success")
    @Expose
    private Boolean success;
    @SerializedName("Message")
    @Expose
    private String message;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
