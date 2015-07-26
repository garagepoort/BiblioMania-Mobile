package com.bendani.bibliomania.books.domain;

import com.google.gson.annotations.SerializedName;

public class Publisher {

    @SerializedName("name")
    private String name;

    public String getName() {
        return name;
    }
}
