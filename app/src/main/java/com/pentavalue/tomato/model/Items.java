package com.pentavalue.tomato.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.pentavalue.tomato.business.BusinessObject;

import java.io.Serializable;

/**
 * @author Mahmoud Turki
 */
public class Items extends BusinessObject implements Serializable {

    @SerializedName("Id")
    @Expose
    int id;
    @SerializedName("Name")
    @Expose
    String name;
    @SerializedName("Image")
    @Expose
    String image;
    @SerializedName("Price")
    @Expose
    String price;
    @SerializedName("Description")
    @Expose
    String description;
    @SerializedName("Count")
    @Expose
    int count;


    public Items() {
        super(null, null, null);
    }

    public Items(int id, String name, String image, String price, String description, int count) {
        super(null, null, null);
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
        this.description = description;
        this.count = count;
    }
    public Items(int id, String name, int count) {
        super(null, null, null);
        this.id = id;
        this.name = name;
        this.count = count;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}