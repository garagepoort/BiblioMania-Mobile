package com.bendani.bibliomania.books.domain;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GiftInfo implements Serializable {

    @SerializedName("from")
    private String from;
    @SerializedName("occasion")
    private String occasion;
    @SerializedName("reason")
    private String reason;
    @SerializedName("giftDate")
    private Date giftDate;

    public String getFrom() {
        return from;
    }

    public String getOccasion() {
        return occasion;
    }

    public String getReason() {
        return reason;
    }

    public Date getGiftDate() {
        return giftDate;
    }
}
