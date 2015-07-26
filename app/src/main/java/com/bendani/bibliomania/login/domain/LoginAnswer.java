package com.bendani.bibliomania.login.domain;

import com.google.gson.annotations.SerializedName;

public class LoginAnswer {

    @SerializedName("token")
    private String token;

    public String getToken() {
        return token;
    }
}
