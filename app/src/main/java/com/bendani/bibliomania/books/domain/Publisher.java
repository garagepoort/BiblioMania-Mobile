package com.bendani.bibliomania.books.domain;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Publisher implements Serializable {

    @SerializedName("name")
    private String name;

    public String getName() {
        return name;
    }
}
