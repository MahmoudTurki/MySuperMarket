package com.pentavalue.tomato.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.pentavalue.tomato.business.BusinessObject;

import java.io.Serializable;

/**
 * @author Mahmoud Turki
 */
public class Orders extends BusinessObject implements Serializable {

    @SerializedName("Id")
    @Expose
    int id;

    @SerializedName("deliveryTime")
    @Expose
    String deliveryTime;

    @SerializedName("orderPrice")
    @Expose
    String orderPrice;
    @SerializedName("orderNumber")
    @Expose
    String orderNumber;

    @SerializedName("orderStatus")
    @Expose
    int orderStatus;

    public Orders() {
        super(null, null, null);
    }

    public Orders(int id, String orderNumber, String deliveryTime, String orderPrice, int orderStatus) {
        super(null, null, null);
        this.id = id;
        this.deliveryTime = deliveryTime;
        this.orderNumber = orderNumber;
        this.orderPrice = orderPrice;
        this.orderStatus = orderStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(String orderPrice) {
        this.orderPrice = orderPrice;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }
}