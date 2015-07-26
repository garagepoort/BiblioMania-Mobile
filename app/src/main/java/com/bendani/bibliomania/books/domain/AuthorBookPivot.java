package com.bendani.bibliomania.books.domain;

import com.google.gson.annotations.SerializedName;

public class AuthorBookPivot {
    @SerializedName("preferred")
    private int preferred;

    public boolean isPreferred() {
        return preferred == 1;
    }
}
