package com.my.pojo;

public class VipCard {
    private String cardId;

    private Integer userId;

    private Double money;

    public VipCard() {
    }

    public VipCard(String cardId, Integer userId, Double money) {
        this.cardId = cardId;
        this.userId = userId;
        this.money = money;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId == null ? null : cardId.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }
}