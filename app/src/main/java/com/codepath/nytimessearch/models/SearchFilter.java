package com.codepath.nytimessearch.models;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Created by michaelsignorotti on 9/24/17.
 */

public class SearchFilter implements Serializable {

    private Calendar beginDate;
    private String sortOrder;
    private boolean art;
    private boolean fashionAndStyle;
    private boolean sports;

    public SearchFilter(Calendar beginDate, String sortOrder, boolean art, boolean fashionAndStyle, boolean sports) {
        this.beginDate = beginDate;
        this.sortOrder = sortOrder;
        this.art = art;
        this.fashionAndStyle = fashionAndStyle;
        this.sports = sports;
    }

    public Calendar getBeginDate() {
        return beginDate;
    }

    public int getBeginDateDay() {
        return beginDate.get(Calendar.DAY_OF_MONTH);
    }

    public int getBeginDateMonth() {
        return beginDate.get(Calendar.MONTH) + 1;
    }

    public int getBeginDateYear() {
        return beginDate.get(Calendar.YEAR);
    }

    public String getBeginDateString() {
        return getBeginDateMonth() + "/" + getBeginDateDay() + "/" + getBeginDateYear();
    }

    public String getBeginDateParam() {
        String month = Integer.toString(getBeginDateMonth());
        if (month.length() == 1) {
            month = "0" + month;
        }
        String day = Integer.toString(getBeginDateDay());
        if (day.length() == 1) {
            day = "0" + day;
        }
        return getBeginDateYear() + month + day;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public boolean isArt() {
        return art;
    }

    public boolean isFashionAndStyle() {
        return fashionAndStyle;
    }

    public boolean isSports() {
        return sports;
    }
}
