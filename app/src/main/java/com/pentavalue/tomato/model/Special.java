package com.pentavalue.tomato.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.pentavalue.tomato.business.BusinessObject;

import java.io.Serializable;

/**
 * @author Mahmoud Turki
 */
public class Special extends BusinessObject implements Serializable {

    @SerializedName("Id")
    @Expose
    int id;
    @SerializedName("Name")
    @Expose
    String name;
    @SerializedName("Image")
    @Expose
    String image;


    public Special() {
        super(null, null, null);
    }

    public Special(int id, String name, String image) {
        super(null, null, null);
        this.id = id;
        this.name = name;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}