package com.pentavalue.tomato.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.pentavalue.tomato.business.BusinessObject;

/**
 * @author Mahmoud Turki
 */
public class Notification extends BusinessObject {
    @SerializedName("Id")
    @Expose
    String id;
    @SerializedName("Name")
    @Expose
    String name;

    @SerializedName("Date")
    @Expose
    String date;

    public Notification(String id, String name, String date) {
        super(null, null, null);
        this.id = id;
        this.name = name;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
