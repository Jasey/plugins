package com.jasey.base;

import com.jasey.base.card.Card;

import java.util.List;

public class GameInfo {
    private int playerIndex;
    private List<Card> cards;

    public int getPlayerIndex() {
        return playerIndex;
    }

    public void setPlayerIndex(int playerIndex) {
        this.playerIndex = playerIndex;
    }

    public List<Card> getCards() {
        return cards;
    }

    public String getCardsDes(){
        StringBuffer des = new StringBuffer("");
        for (Card card : cards){
            des.append(card.getDescription() + " ");
        }
        return des.toString();
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }
}
