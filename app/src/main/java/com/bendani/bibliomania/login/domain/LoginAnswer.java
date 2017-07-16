package com.bendani.bibliomania.login.domain;

import com.google.gson.annotations.SerializedName;

public class LoginAnswer {

    @SerializedName("token")
    private String token;
    @SerializedName("user")
    private com.bendani.bibliomania.books.domain.User user;

    public String getToken() {
        return token;
    }

    public com.bendani.bibliomania.books.domain.User getUser() {
        return user;
    }
}
