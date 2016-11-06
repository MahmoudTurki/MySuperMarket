package com.pentavalue.tomato.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.pentavalue.tomato.business.BusinessObject;

/**
 * @author Mahmoud Turki
 */
public class ShippingAddresses extends BusinessObject {

    @SerializedName("AddressName")
    @Expose
    String addressName;
    @SerializedName("Id")
    @Expose
    int id;
    @SerializedName("Type")
    @Expose
    String type;
    @SerializedName("BlockNumber")
    @Expose
    String blockNumber;
    @SerializedName("StreetName")
    @Expose
    String streetName;
    @SerializedName("BuildingNumber")
    @Expose
    String buildingNumber;
    @SerializedName("Floor")
    @Expose
    String floor;
    @SerializedName("ExtraDirections")
    @Expose
    String extraDirections;
    @SerializedName("AppartmentNumber")
    @Expose
    String appartmentNumber;
    @SerializedName("addressDescription")
    @Expose
    String addressDescription;

    public ShippingAddresses() {
        super(null, null, null);
    }

    public ShippingAddresses(String addressName, String type, String blockNumber, String streetName, String buildingNumber, String floor, String appartmentNumber, String extraDirections, String addressDescription) {
        super(null, null, null);
        this.addressName = addressName;
        this.type = type;
        this.blockNumber = blockNumber;
        this.streetName = streetName;
        this.buildingNumber = buildingNumber;
        this.floor = floor;
        this.extraDirections = extraDirections;
        this.appartmentNumber = appartmentNumber;
        this.addressDescription = addressDescription;
    }

    public ShippingAddresses(String addressName, String type, String blockNumber, String streetName, String buildingNumber, String floor, String appartmentNumber, String extraDirections) {
        super(null, null, null);
        this.addressName = addressName;
        this.type = type;
        this.blockNumber = blockNumber;
        this.streetName = streetName;
        this.buildingNumber = buildingNumber;
        this.floor = floor;
        this.extraDirections = extraDirections;
        this.appartmentNumber = appartmentNumber;
    }

    public ShippingAddresses(String addressName, String addressDescription) {
        super(null, null, null);
        this.addressName = addressName;
        this.addressDescription = addressDescription;
    }

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBlockNumber() {
        return blockNumber;
    }

    public void setBlockNumber(String blockNumber) {
        this.blockNumber = blockNumber;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getBuildingNumber() {
        return buildingNumber;
    }

    public void setBuildingNumber(String buildingNumber) {
        this.buildingNumber = buildingNumber;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getExtraDirections() {
        return extraDirections;
    }

    public void setExtraDirections(String extraDirections) {
        this.extraDirections = extraDirections;
    }

    public String getAppartmentNumber() {
        return appartmentNumber;
    }

    public void setAppartmentNumber(String appartmentNumber) {
        this.appartmentNumber = appartmentNumber;
    }

    public String getAddressDescription() {
        return addressDescription;
    }

    public void setAddressDescription(String addressDescription) {
        this.addressDescription = addressDescription;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ShippingAddresses other = (ShippingAddresses) obj;
        if (id != other.id)
            return false;
        return true;
    }
}
