package com.my.pojo;

import java.util.Date;

public class Person {
    private Integer personId;

    private Integer userId;

    private String name;

    private String sex;

    private Date birth;

    public Person() {
    }

    public Person(Integer personId, Integer userId, String name, String sex, Date birth) {
        this.personId = personId;
        this.userId = userId;
        this.name = name;
        this.sex = sex;
        this.birth = birth;
    }

    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    @Override
    public String toString() {
        return "Person{" +
                "personId=" + personId +
                ", userId=" + userId +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", birth=" + birth +
                '}';
    }
}