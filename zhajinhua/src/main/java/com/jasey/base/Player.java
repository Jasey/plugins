package com.jasey.base;


import com.jasey.base.card.Card;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private int index; //座次
    private boolean isWinner; //是否是庄家
    private int winNum; //胜利了多少次
    private int totalNum; //一共玩了多少把
    private List<Integer> cardOrders = new ArrayList(); //当前手上牌

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean isWinner() {
        return isWinner;
    }

    public void setWinner(boolean winner) {
        isWinner = winner;
    }

    public int getWinNum() {
        return winNum;
    }

    public void addWinNum() {
        this.winNum++;
    }

    public int getTotalNum() {
        return totalNum;
    }

    public void addTotalNum() {
        this.totalNum++;
    }

    public void patchedCard(int cardOrder) {
        cardOrders.add(cardOrder);
    }

    public List<Integer> getCardOrders() {
        return cardOrders;
    }

    public void recoverCard(){
        cardOrders = new ArrayList();
    }

    public double getWinRate(){
        return ((double)winNum / (double)totalNum)*100;
    }

    public void clearLog(){
        winNum = 0;
        totalNum = 0;
    }
}
