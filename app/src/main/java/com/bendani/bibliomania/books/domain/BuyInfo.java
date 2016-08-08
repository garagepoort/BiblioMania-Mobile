package com.bendani.bibliomania.books.domain;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BuyInfo implements Serializable{
    @SerializedName("buyPrice")
    private Price buyPrice;

    @SerializedName("shop")
    private String shop;
    @SerializedName("reason")
    private String reason;
    @SerializedName("cityShop")
    private String cityShop;
    @SerializedName("countryShop")
    private String countryShop;
    @SerializedName("buyDate")
    private Date buyDate;

    public Price getBuyPrice() {
        return buyPrice;
    }

    public String getShop() {
        return shop;
    }

    public String getReason() {
        return reason;
    }

    public String getCityShop() {
        return cityShop;
    }

    public String getCountryShop() {
        return countryShop;
    }

    public Date getBuyDate() {
        return buyDate;
    }
}
