package com.bendani.bibliomania.books.domain;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Author implements Serializable {

    @SerializedName("firstname")
    public String firstname;
    @SerializedName("infix")
    public String infix;
    @SerializedName("name")
    public String name;
    @SerializedName("pivot")
    public AuthorBookPivot authorBookPivot;

    public String getFirstname() {
        return firstname;
    }

    public String getInfix() {
        return infix;
    }

    public String getName() {
        return name;
    }

    public static Builder newAuthor() {
        return new Builder();
    }

    public boolean isPreferredAuthor() {
        return authorBookPivot.isPreferred();
    }

    public String getFullName() {
        if (infix == null || infix.isEmpty()) {
            return name + " " + firstname;
        }
        return name + " " + infix + " " + firstname;
    }

    public static class Builder {
        private Author author;

        public Builder() {
            this.author = new Author();
        }

        public Author build() {
            return author;
        }

        public Builder withName(String name) {
            author.name = name;
            return this;
        }

        public Builder withSurame(String surname) {
            author.firstname = surname;
            return this;
        }

        public Builder withInfix(String infix) {
            author.infix = infix;
            return this;
        }
    }
}
