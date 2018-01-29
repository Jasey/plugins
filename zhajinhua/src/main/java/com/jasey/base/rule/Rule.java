package com.jasey.base.rule;

import com.jasey.base.GameInfo;
import com.jasey.base.card.Card;
import com.jasey.base.card.CardColor;

import java.util.*;

public class Rule {
    public static int getWinner(List<GameInfo> gameInfoList){

        Map<Integer, GameInfo> gameInfoMap = new HashMap<Integer, GameInfo>();
        for (int i = 0; i < gameInfoList.size(); i++){
            gameInfoMap.put(gameInfoList.get(i).getPlayerIndex(), gameInfoList.get(i));
        }

        //根据规则判断输赢
        //输赢平台
        Map<CardLevel, List<GameInfo>> winMap = new HashMap();
        for (GameInfo gameInfo : gameInfoList){
            CardLevel cardLevel = getCardLevel(gameInfo.getCards());
            if (winMap.containsKey(cardLevel)){
                List<GameInfo> gameList = winMap.get(cardLevel);
                gameList.add(gameInfo);
            }else {
                List<GameInfo> gameList = new ArrayList<GameInfo>();
                gameList.add(gameInfo);
                winMap.put(cardLevel, gameList);
            }
        }

        //牌气
        /*for (CardLevel cardLevel : winMap.keySet()){
            System.out.println(cardLevel.getDes() + ":");
            List<GameInfo> gameInfos = winMap.get(cardLevel);
            for(GameInfo gameInfo : gameInfos){
                System.out.println(gameInfo.getCardsDes());
            }
        }*/

        //名次
        int winIndex = 0;
        int[] winOrder = new int[gameInfoList.size()];
        if (winMap.containsKey(CardLevel.SAME_NUM)){
            List<GameInfo> gameList = winMap.get(CardLevel.SAME_NUM);
            Collections.sort(gameList, new Comparator<GameInfo>() {
                public int compare(GameInfo o1, GameInfo o2) {
                    return 0 - compareCard(o1.getCards().get(0), o2.getCards().get(0));
                }
            });

            for(int i = 0; i < gameList.size(); i++){
                winOrder[winIndex] = gameList.get(i).getPlayerIndex();
                winIndex++;
            }
        }
        if (winMap.containsKey(CardLevel.SAME_ORDER)){
            List<GameInfo> gameList = winMap.get(CardLevel.SAME_ORDER);
            Collections.sort(gameList, new Comparator<GameInfo>() {
                public int compare(GameInfo o1, GameInfo o2) {
                    Card card1 = getMinCardIndex(o1.getCards());
                    Card card2 = getMinCardIndex(o2.getCards());
                    return 0 - compareCard(card1, card2);
                }
            });

            for(int i = 0; i < gameList.size(); i++){
                winOrder[winIndex] = gameList.get(i).getPlayerIndex();
                winIndex++;
            }
        }
        if (winMap.containsKey(CardLevel.SAME_COLOR)){
            List<GameInfo> gameList = winMap.get(CardLevel.SAME_COLOR);
            Collections.sort(gameList, new Comparator<GameInfo>() {
                public int compare(GameInfo o1, GameInfo o2) {
                    List<Card> cardList1 = o1.getCards();
                    List<Card> cardList2 = o2.getCards();
                    return 0 - compareCards(cardList1, cardList2);
                }
            });

            for(int i = 0; i < gameList.size(); i++){
                winOrder[winIndex] = gameList.get(i).getPlayerIndex();
                winIndex++;
            }
        }

        if (winMap.containsKey(CardLevel.ORDER)){
            List<GameInfo> gameList = winMap.get(CardLevel.ORDER);
            Collections.sort(gameList, new Comparator<GameInfo>() {
                public int compare(GameInfo o1, GameInfo o2) {
                    List<Card> cardList1 = o1.getCards();
                    List<Card> cardList2 = o2.getCards();
                    return 0 - compareCards(cardList1, cardList2);
                }
            });

            for(int i = 0; i < gameList.size(); i++){
                winOrder[winIndex] = gameList.get(i).getPlayerIndex();
                winIndex++;
            }
        }
        if (winMap.containsKey(CardLevel.COUPLE)){
            List<GameInfo> gameList = winMap.get(CardLevel.COUPLE);
            Collections.sort(gameList, new Comparator<GameInfo>() {
                public int compare(GameInfo o1, GameInfo o2) {
                    int num1 = getFixCountCardNum(o1.getCards(), 2);
                    int num2 = getFixCountCardNum(o1.getCards(), 2);
                    if (num1 == num2){
                        return 0 - compareCard(getSingleCard(o1.getCards()), getSingleCard(o2.getCards()));
                    }else {
                        return 0 - (num1 - num2);
                    }
                }
            });

            for(int i = 0; i < gameList.size(); i++){
                winOrder[winIndex] = gameList.get(i).getPlayerIndex();
                winIndex++;
            }
        }
        if (winMap.containsKey(CardLevel.SINGLE)){
            List<GameInfo> gameList = winMap.get(CardLevel.SINGLE);
            Collections.sort(gameList, new Comparator<GameInfo>() {
                public int compare(GameInfo o1, GameInfo o2) {
                    List<Card> cardList1 = o1.getCards();
                    List<Card> cardList2 = o2.getCards();
                    return 0 - compareCards(cardList1, cardList2);
                }
            });

            for(int i = 0; i < gameList.size(); i++){
                winOrder[winIndex] = gameList.get(i).getPlayerIndex();
                winIndex++;
            }
        }

        //显示排名
        for (int i = 0; i < winOrder.length; i++){
            int playerIndex = winOrder[i];
            GameInfo gameInfo = gameInfoMap.get(playerIndex);
            Collections.sort(gameInfo.getCards(), new Comparator<Card>() {
                public int compare(Card o1, Card o2) {
                    return 0 - compareCard(o1, o2);
                }
            });
            System.out.println(String.format("order : %s, player : %s, card : %s", i+1, playerIndex, gameInfo.getCardsDes()));
        }
        System.out.println("");
        return winOrder[0];
    }

    private static int getFixCountCardNum(List<Card> cardList, int fixCount){
        Map<Integer, Integer> cardCountMap = new HashMap<Integer, Integer>();
        for (Card card : cardList){
            if (cardCountMap.containsKey(card.getNum())){
                int cardCount = cardCountMap.get(card.getNum());
                cardCount++;
                cardCountMap.put(card.getNum(), cardCount);
            }else {
                cardCountMap.put(card.getNum(), 1);
            }
        }

        for (Integer cardNum : cardCountMap.keySet()){
            int count = cardCountMap.get(cardNum);
            if (count == fixCount){
                return cardNum;
            }
        }
        return cardList.get(0).getNum();
    }

    private static Card getSingleCard(List<Card> cardList){
        Map<Integer, List<Card>> cardMap = new HashMap<Integer, List<Card>>();
        for (int i = 0; i < cardList.size(); i++){
            Card card = cardList.get(i);
            if (cardMap.containsKey(card.getNum())){
                List<Card> cards = cardMap.get(card.getNum());
                cards.add(card);
            }else {
                List<Card> cards = new ArrayList<Card>();
                cards.add(card);
                cardMap.put(card.getNum(), cards);
            }
        }

        for (Integer cardNum : cardMap.keySet()){
            List<Card> cards = cardMap.get(cardNum);
            if (cards.size() == 1){
                return cards.get(0);
            }
        }
        return cardList.get(0);
    }


    private static Card getMinCardIndex(List<Card> cards){
        Card card = cards.get(0);
        for (int i = 1; i < cards.size(); i++){
            if (cards.get(i).getNum() < card.getNum()){
                card = cards.get(i);
            }
        }
        return card;
    }

    private static int compareCards(List<Card> cardList1, List<Card> cardList2){
        //从大到小排序
        Collections.sort(cardList1, new Comparator<Card>() {
            public int compare(Card o1, Card o2) {
                return 0 - compareCard(o1, o2);
            }
        });
        Collections.sort(cardList2, new Comparator<Card>() {
            public int compare(Card o1, Card o2) {
                return 0 - compareCard(o1, o2);
            }
        });
        //第一轮比较大小
        for (int i = 0; i < cardList1.size(); i++){
            Card card1 = cardList1.get(i);
            Card card2 = cardList2.get(i);
            if (card1.getNum() > card2.getNum()){
                return 1;
            }else if (card1.getNum() < card2.getNum()){
                return -1;
            }
        }
        //第二轮比较花色
        for (int i = 0; i < cardList1.size(); i++){
            Card card1 = cardList1.get(i);
            Card card2 = cardList2.get(i);
            if (card1.getColor().ordinal() > card2.getColor().ordinal()){
                return 1;
            }else if (card1.getColor().ordinal() < card2.getColor().ordinal()){
                return -1;
            }
        }
        return 0;
    }

    private static int compareCard(Card card1, Card card2){
        int num = card1.getNum() - card2.getNum();
        int color = card1.getColor().ordinal() - card2.getColor().ordinal();
        return num == 0 ? color : num;
    }

    private static CardLevel getCardLevel(List<Card> cards){
        Set<Integer> numSet = new HashSet<Integer>();
        Set<CardColor> colorSet = new HashSet<CardColor>();
        for (Card card : cards){
            numSet.add(card.getNum());
            colorSet.add(card.getColor());
        }

        if (numSet.size() == 1){
            return CardLevel.SAME_NUM;
        }
        if (numSet.size() == 3 && CardLevel.isOrderCard(numSet)){
            if (colorSet.size() == 1){
                return CardLevel.SAME_ORDER;
            }else {
                return CardLevel.ORDER;
            }
        }
        if (colorSet.size() == 1){
            return CardLevel.SAME_COLOR;
        }
        if(numSet.size() == 2){
            return CardLevel.COUPLE;
        }else {
            return CardLevel.SINGLE;
        }
    }
}
