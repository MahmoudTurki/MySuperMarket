package com.pentavalue.tomato.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.pentavalue.tomato.business.BusinessObject;

import java.util.List;

/**
 * @author Mahmoud Turki
 */
public class Location extends BusinessObject {
    @SerializedName("Id")
    @Expose
    String id;
    @SerializedName("Name")
    @Expose
    String name;
    @SerializedName("Areas")
    @Expose
    List<Area> areaList;

    public Location() {
        super(null, null, null);
    }

    public Location(String id, String name, List<Area> areaList) {
        super(null, null, null);
        this.id = id;
        this.name = name;
        this.areaList = areaList;
    }

    public Location(String name, List<Area> areaList) {
        super(null, null, null);
        this.name = name;
        this.areaList = areaList;
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

    public List<Area> getAreaList() {
        return areaList;
    }

    public void setAreaList(List<Area> areaList) {
        this.areaList = areaList;
    }
}
