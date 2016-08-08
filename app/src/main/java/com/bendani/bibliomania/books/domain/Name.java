package com.bendani.bibliomania.books.domain;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Name implements Serializable {
    @SerializedName("firstname")
    public String firstname;
    @SerializedName("infix")
    public String infix;
    @SerializedName("lastname")
    public String lastname;

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getInfix() {
        return infix;
    }

    public void setInfix(String infix) {
        this.infix = infix;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String name) {
        this.lastname = name;
    }

    public String getFullName() {
        if (infix == null || infix.isEmpty()) {
            return lastname + " " + firstname;
        }
        return lastname + " " + infix + " " + firstname;
    }

}
