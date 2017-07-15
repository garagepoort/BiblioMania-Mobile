package com.bendani.bibliomania.books.domain;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Author implements Serializable {


    @SerializedName("id")
    private int id;
    @SerializedName("name")
    public Name name;
    @SerializedName("preferred")
    public boolean preferred;
    @SerializedName("dateOfBirth")
    public Date dateOfBirth;
    @SerializedName("dateOfDeath")
    public Date dateOfDeath;
    @SerializedName("imageName")
    private String imageName;

    public String getFirstname() {
        return name.getFirstname();
    }

    public String getInfix() {
        return name.getInfix();
    }

    public String getLastname() {
        return name.getLastname();
    }

    public String getImageName() {
        return imageName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public Date getDateOfDeath() {
        return dateOfDeath;
    }

    public int getId() {
        return id;
    }

    public boolean isPreferredAuthor() {
        return preferred;
    }

    public String getFullName() {
        if (name.getInfix() == null || name.getInfix().isEmpty()) {
            return name.getLastname() + " " + name.getFirstname();
        }
        return name.getLastname() + " " + name.getInfix() + " " + name.getFirstname();
    }

}
