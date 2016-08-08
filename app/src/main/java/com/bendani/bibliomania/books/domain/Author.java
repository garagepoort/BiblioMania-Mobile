package com.bendani.bibliomania.books.domain;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Author implements Serializable {

    @SerializedName("id")
    public int id;
    @SerializedName("name")
    public Name name;
    @SerializedName("preferred")
    public boolean preferred;

    public boolean isPreferredAuthor() {
        return preferred;
    }

    public String getFullName() {
        if(name != null){
            return name.getFullName();
        }
        return "";
    }

}
