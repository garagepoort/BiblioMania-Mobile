package com.bendani.bibliomania.books.domain;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Date implements Serializable {

    @SerializedName("day")
    public Integer day;
    @SerializedName("month")
    public Integer month;
    @SerializedName("year")
    public Integer year;

    public Date(Integer day, Integer month, Integer year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public String getFullDate(){
        if(day != null && month != null && year != null){
            return day + "-" + month + "-" + year;
        }
        if(month != null && year != null){
            return month + "-" + year;
        }
        if(year != null){
            return String.valueOf(year);
        }
        return "";
    }
}
