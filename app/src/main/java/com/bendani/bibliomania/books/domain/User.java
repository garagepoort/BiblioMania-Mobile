package com.bendani.bibliomania.books.domain;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("username")
    private String username;
    @SerializedName("email")
    private String email;

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }
}
