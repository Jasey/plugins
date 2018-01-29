package com.jasey.base.card;

public class Card {
    private int num; //纸牌的数字(0~12,分别表示2~A)
    private String description; //纸牌的直观描述 (红桃A)
    private CardColor color; //纸牌的花色

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CardColor getColor() {
        return color;
    }

    public void setColor(CardColor color) {
        this.color = color;
    }
}
