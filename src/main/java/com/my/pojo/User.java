package com.my.pojo;

public class User {

    private Integer userId;

    private String username;

    private String password;

    private String phone;

    private String faceUrl;

    public User() {
    }

    public User(Integer userId, String username, String password, String phone, String faceUrl) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.faceUrl = faceUrl;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getFaceUrl() {
        return faceUrl;
    }

    public void setFaceUrl(String faceUrl) {
        this.faceUrl = faceUrl == null ? null : faceUrl.trim();
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", faceUrl='" + faceUrl + '\'' +
                '}';
    }
}