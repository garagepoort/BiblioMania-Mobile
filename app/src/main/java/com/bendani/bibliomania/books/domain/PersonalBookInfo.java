package com.bendani.bibliomania.books.domain;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class PersonalBookInfo implements Serializable {

    @SerializedName("id")
    private int id;
    @SerializedName("rating")
    private int rating;
    @SerializedName("readingDates")
    private List<ReadingDate> readingDates;

    public int getRating() {
        return rating;
    }

    public int getId() {
        return id;
    }

    public List<ReadingDate> getReadingDates() {
        return readingDates;
    }
}
