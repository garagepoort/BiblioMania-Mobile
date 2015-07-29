package com.bendani.bibliomania.books.domain;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PersonalBookInfo implements Serializable{

    @SerializedName("rating")
    private int rating;

    public int getRating() {
        return rating;
    }
}
