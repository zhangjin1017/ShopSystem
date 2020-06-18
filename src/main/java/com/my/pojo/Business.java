package com.my.pojo;

public class Business {
    private Integer businessId;

    private String username;

    private String password;

    private String shopName;

    private String photoUrl;

    private String phone;

    public Business() {
    }

    public Business(Integer businessId, String username, String password, String shopName, String photoUrl, String phone) {
        this.businessId = businessId;
        this.username = username;
        this.password = password;
        this.shopName = shopName;
        this.photoUrl = photoUrl;
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Business{" +
                "businessId=" + businessId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", shopName='" + shopName + '\'' +
                ", photoUrl='" + photoUrl + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }

    public Integer getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName == null ? null : shopName.trim();
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl == null ? null : photoUrl.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }
}