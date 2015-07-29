package com.bendani.bibliomania.books.domain;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AuthorBookPivot implements Serializable {
    @SerializedName("preferred")
    private int preferred;

    public boolean isPreferred() {
        return preferred == 1;
    }
}
