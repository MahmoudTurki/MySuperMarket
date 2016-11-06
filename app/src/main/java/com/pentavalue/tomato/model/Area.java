package com.pentavalue.tomato.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.pentavalue.tomato.business.BusinessObject;

/**
 * @author Mahmoud Turki
 */
public class Area extends BusinessObject {
    @SerializedName("Id")
    @Expose
    String id;
    @SerializedName("Name")
    @Expose
    String name;

    public Area(String id, String name) {
        super(null, null, null);
        this.id = id;
        this.name = name;
    }

    public Area(String name) {
        super(null, null, null);
        this.name = name;
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
}
