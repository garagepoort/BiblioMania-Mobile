package com.bendani.bibliomania.books.domain;

import java.io.Serializable;

public class ReadingDate implements Serializable {

    private Date date;
    private String review;
    private int rating;
    private int personalBookInfoId;

    public ReadingDate(Date date, String review, int rating, int personalBookInfoId) {
        this.date = date;
        this.review = review;
        this.rating = rating;
        this.personalBookInfoId = personalBookInfoId;
    }

    public Date getDate() {
        return date;
    }

    public String getReview() {
        return review;
    }

    public int getRating() {
        return rating;
    }

    public int getPersonalBookInfoId() {
        return personalBookInfoId;
    }
}
