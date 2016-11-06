package com.pentavalue.tomato.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.pentavalue.tomato.business.BusinessObject;

import java.io.Serializable;

/**
 * @author Mahmoud Turki
 */
public class Supermarket extends BusinessObject implements Serializable{

    @SerializedName("Id")
    @Expose
    int id;
    @SerializedName("Name")
    @Expose
    String name;
    @SerializedName("Logo")
    @Expose
    String logo;
    @SerializedName("OpeningTime")
    @Expose
    String openingTime;
    @SerializedName("ClosingTime")
    @Expose
    String closingTime;
    @SerializedName("AvailabilityStatus")
    @Expose
    String availabilityStatus;
    @SerializedName("MinimumOrder")
    @Expose
    int minimumOrder;
    @SerializedName("DeliveryCharges")
    @Expose
    int deliveryCharges;
    @SerializedName("DeliveryTime")
    @Expose
    String deliveryTime;
    @SerializedName("PaymentOptions")
    @Expose
    int paymentOptions;
    @SerializedName("AreaId")
    @Expose
    int areaId;
    @SerializedName("CityId")
    @Expose
    int cityId;
    @SerializedName("Area")
    @Expose
    String area;
    @SerializedName("City")
    @Expose
    String city;
    @SerializedName("Rating")
    @Expose
    String rating;

    public Supermarket() {
        super(null, null, null);
    }

    public Supermarket(int id, String name, String logo,
                       String openingTime, String closingTime, String availabilityStatus,
                       int minimumOrder, int deliveryCharges, String deliveryTime,
                       int paymentOptions, int areaId, int cityId,
                       String area, String city, String rating) {
        super(null, null, null);
        this.id = id;
        this.name = name;
        this.logo = logo;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
        this.availabilityStatus = availabilityStatus;
        this.minimumOrder = minimumOrder;
        this.deliveryCharges = deliveryCharges;
        this.deliveryTime = deliveryTime;
        this.paymentOptions = paymentOptions;
        this.areaId = areaId;
        this.cityId = cityId;
        this.area = area;
        this.city = city;
        this.rating = rating;
    }

/*    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.logo);
        dest.writeString(this.openingTime);
        dest.writeString(this.closingTime);
        dest.writeString(this.availabilityStatus);
        dest.writeString(this.deliveryTime);
        dest.writeString(this.area);
        dest.writeString(this.city);
        dest.writeString(this.rating);
        dest.writeInt(this.id);
        dest.writeInt(this.minimumOrder);
        dest.writeInt(this.deliveryCharges);
        dest.writeInt(this.paymentOptions);
        dest.writeInt(this.areaId);
        dest.writeInt(this.cityId);
    }

    protected Supermarket(Parcel in) {
        super(null, null, null);
        this.id = in.readInt();
        this.name = in.readString();
        this.logo = in.readString();
        this.openingTime = in.readString();
        this.closingTime = in.readString();
        this.availabilityStatus = in.readString();
        this.minimumOrder = in.readInt();
        this.deliveryCharges = in.readInt();
        this.deliveryTime = in.readString();
        this.paymentOptions = in.readInt();
        this.areaId = in.readInt();
        this.cityId = in.readInt();
        this.area = in.readString();
        this.city = in.readString();
        this.rating = in.readString();
    }

    public static final Parcelable.Creator<Supermarket> CREATOR = new Parcelable.Creator<Supermarket>() {
        @Override
        public Supermarket createFromParcel(Parcel source) {
            return new Supermarket(source);
        }

        @Override
        public Supermarket[] newArray(int size) {
            return new Supermarket[size];
        }
    };

    @Override
    public String toString() {
        return "Supermarket{" +
                "id='" + id + '\'' +
                "name='" + name + '\'' +
                "logo='" + logo + '\'' +
                "openingTime='" + openingTime + '\'' +
                "closingTime='" + closingTime + '\'' +
                "availabilityStatus='" + availabilityStatus + '\'' +
                "minimumOrder='" + minimumOrder + '\'' +
                "deliveryCharges='" + deliveryCharges + '\'' +
                "deliveryTime='" + deliveryTime + '\'' +
                "paymentOptions='" + paymentOptions + '\'' +
                "areaId='" + areaId + '\'' +
                "cityId='" + cityId + '\'' +
                "area='" + area + '\'' +
                "city='" + city + '\'' +
                ", rating=" + rating +
                '}';
    }*/

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

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(String openingTime) {
        this.openingTime = openingTime;
    }

    public String getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(String closingTime) {
        this.closingTime = closingTime;
    }

    public String getAvailabilityStatus() {
        return availabilityStatus;
    }

    public void setAvailabilityStatus(String availabilityStatus) {
        this.availabilityStatus = availabilityStatus;
    }

    public int getMinimumOrder() {
        return minimumOrder;
    }

    public void setMinimumOrder(int minimumOrder) {
        this.minimumOrder = minimumOrder;
    }

    public int getDeliveryCharges() {
        return deliveryCharges;
    }

    public void setDeliveryCharges(int deliveryCharges) {
        this.deliveryCharges = deliveryCharges;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public int getPaymentOptions() {
        return paymentOptions;
    }

    public void setPaymentOptions(int paymentOptions) {
        this.paymentOptions = paymentOptions;
    }

    public int getAreaId() {
        return areaId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
/*
    public static Creator<Supermarket> getCREATOR() {
        return CREATOR;
    }*/
}