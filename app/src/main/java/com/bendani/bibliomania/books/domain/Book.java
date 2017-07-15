package com.bendani.bibliomania.books.domain;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Book implements Serializable {

    @SerializedName("id")
    private int id;
    @SerializedName("title")
    private String title;
    @SerializedName("subtitle")
    private String subtitle;
    @SerializedName("authors")
    private List<Author> authors;
    @SerializedName("publisher")
    private String publisher;
    @SerializedName("personalBookInfo")
    private PersonalBookInfo personalBookInfo;
    @SerializedName("isbn")
    private String isbn;
    @SerializedName("imageName")
    private String imageName;

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        if(subtitle==null){
            return "";
        }
        return subtitle;
    }

    public Author getPreferredAuthor() {
        if (authors.isEmpty()) {
            return null;
        }
        for (Author author : authors) {
            if (author.isPreferredAuthor()) {
                return author;
            }
        }
        return authors.get(0);
    }

    public String getPublisher() {
        return publisher;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public PersonalBookInfo getPersonalBookInfo() {
        return personalBookInfo;
    }

    public String getISBN() {
        return isbn;
    }

    public int getId() {
        return id;
    }

    public String getImageName() {
        return imageName;
    }
}
