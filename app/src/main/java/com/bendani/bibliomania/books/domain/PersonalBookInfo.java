package com.bendani.bibliomania.books.domain;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class PersonalBookInfo implements Serializable {

    @SerializedName("acquirement")
    private String acquirement;
    @SerializedName("read")
    private boolean read;
    @SerializedName("buyInfo")
    private BuyInfo buyInfo;
    @SerializedName("giftInfo")
    private GiftInfo giftInfo;
    @SerializedName("readingDates")
    private List<ReadingDate> readingDates;

    public List<ReadingDate> getReadingDates() {
        return readingDates;
    }

    public String getAcquirement() {
        return acquirement;
    }

    public BuyInfo getBuyInfo() {
        return buyInfo;
    }

    public GiftInfo getGiftInfo() {
        return giftInfo;
    }
}
