package com.jasey.base.card;

public enum  CardColor {
    READ_HEART("红桃"),
    READ_REC("方片"),
    BLACK_HEART("黑桃"),
    BLACK_ClUB("梅花");

    private String des;

    CardColor(String des) {
        this.des = des;
    }

    public String getDes() {
        return des;
    }
}
