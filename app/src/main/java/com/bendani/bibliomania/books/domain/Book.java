package com.bendani.bibliomania.books.domain;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Book {

    @SerializedName("title")
    private String title;
    @SerializedName("subtitle")
    private String subtitle;
    @SerializedName("authors")
    private List<Author> authors;
    @SerializedName("publisher")
    private Publisher publisher;

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public Author getPreferredAuthor(){
        if(authors.isEmpty()){
            return null;
        }
        for (Author author : authors) {
            if(author.isPreferredAuthor()){
                return author;
            }
        }
        return authors.get(0);
    }

    public Publisher getPublisher() {
        return publisher;
    }
}
