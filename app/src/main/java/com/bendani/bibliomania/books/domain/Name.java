package com.bendani.bibliomania.books.domain;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

class Name implements Serializable {

    @SerializedName("firstname")
    public String firstname;
    @SerializedName("infix")
    public String infix;
    @SerializedName("lastname")
    public String lastname;

    public String getFirstname() {
        return firstname;
    }

    public String getInfix() {
        return infix;
    }

    public String getLastname() {
        return lastname;
    }
}
