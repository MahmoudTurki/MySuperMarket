package com.pentavalue.tomato.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.pentavalue.tomato.business.BusinessObject;

import java.util.List;

/**
 * @author Mahmoud Turki
 */
public class User extends BusinessObject {
    @SerializedName("FirstName")
    @Expose
    String firstName;
    @SerializedName("LastName")
    @Expose
    String lastName;
    @SerializedName("Email")
    @Expose
    String email;
    @SerializedName("Password")
    @Expose
    String password;
    @SerializedName("ConfirmPassword")
    @Expose
    String confirmPassword;
    @SerializedName("PhoneNumber")
    @Expose
    String phoneNumber;
    @SerializedName("gender")
    @Expose
    String gender;
    @SerializedName("DateOfBirth")
    @Expose
    String birthDate;
    @SerializedName("ShippingAddresses")
    @Expose
    List<ShippingAddresses> shippingAddresses;

    @SerializedName("access_token")
    @Expose
    String accessToken;
    @SerializedName("token_type")
    @Expose
    String tokenType;
    @SerializedName("expires_in")
    @Expose
    String expiresIn;
    @SerializedName("userName")
    @Expose
    String userName;
    @SerializedName(".issued")
    @Expose
    String issued;
    @SerializedName(".expires")
    @Expose
    String expires;
    @SerializedName("OldPassword")
    @Expose
    String oldPassword;
    @SerializedName("NewPassword")
    @Expose
    String newPassword;
    @SerializedName("error")
    @Expose
    String error;
    @SerializedName("errorMsg")
    @Expose
    String errorMsg;

    public User() {
        super(null, null, null);
    }

    public User(String accessToken, String tokenType, String expiresIn, String userName, String error, String errorMsg) {
        super(null, null, null);
        this.accessToken = accessToken;
        this.tokenType = tokenType;
        this.expiresIn = expiresIn;
        this.userName = userName;

        this.error = error;
        this.errorMsg = errorMsg;
    }

    public User(String userName, String password) {
        super(null, null, null);
        this.userName = userName;
        this.password = password;
    }

    public User(String oldPassword, String newPassword, String confirmPassword) {
        super(null, null, null);
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
        this.confirmPassword = confirmPassword;
    }

    public User(String firstName, String lastName, String email, String password, String confirmPassword, String phoneNumber, String gender, String birthDate) {
        super(null, null, null);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.birthDate = birthDate;
    }

    public User(String firstName, String lastName, String phoneNumber, String gender, String birthDate) {
        super(null, null, null);
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.birthDate = birthDate;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<ShippingAddresses> getShippingAddresses() {
        return shippingAddresses;
    }

    public void setShippingAddresses(List<ShippingAddresses> shippingAddresses) {
        this.shippingAddresses = shippingAddresses;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(String expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getIssued() {
        return issued;
    }

    public void setIssued(String issued) {
        this.issued = issued;
    }

    public String getExpires() {
        return expires;
    }

    public void setExpires(String expires) {
        this.expires = expires;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}