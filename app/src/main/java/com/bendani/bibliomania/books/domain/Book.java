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
    private Publisher publisher;
    @SerializedName("personal_book_info")
    private PersonalBookInfo personalBookInfo;
    @SerializedName("ISBN")
    private String isbn;
    @SerializedName("coverImage")
    private String coverImage;

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
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

    public Publisher getPublisher() {
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

    public String getCoverImage() {
        return coverImage;
    }
}
