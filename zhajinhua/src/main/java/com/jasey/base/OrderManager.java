package com.jasey.base;

import com.jasey.base.card.Card;
import com.jasey.base.card.CardColor;
import com.jasey.base.rule.Rule;

import java.util.*;

import static com.jasey.base.card.Rate.*;

public class OrderManager {
    private static ThreadLocal<OrderManager> threadOrderManager = new ThreadLocal<OrderManager>();
    private static final int ORDER_END = 51;
    private static final int TOTAL_CARDS = ORDER_END + 1;
    private static final int PER_PLAYER_CARD_NUM = 3;
    private int order[] = new int[TOTAL_CARDS];
    private Card cards[] = new Card[TOTAL_CARDS];

    public static final int NUM = 13;
    public static void initCards(){
        Card[] cards = getOrderManager().getCards();
        for (int i = 0; i < NUM; i++){
            CardColor[] cardColors = CardColor.values();
            for (int j = 0; j <cardColors.length; j++){
                Card card = new Card();
                card.setNum(i);
                card.setColor(cardColors[j]);
                card.setDescription(translate(cardColors[j], i));
                cards[i * cardColors.length + j] = card;
            }
        }
    }

    public Card[] getCards() {
        return cards;
    }

    public int[] getOrder() {
        return order;
    }

    public void setOrder(int[] order) {
        this.order = order;
    }

    public static String translate(CardColor cardColor, int cardNum){
        String des = "";
        // 2 ~ 10 直译
        if (cardNum < 9){
            des = String.valueOf(cardNum + 2);
        }else if (cardNum == 9){
            des = "J";
        }else if (cardNum == 10){
            des = "Q";
        }else if (cardNum == 11){
            des = "K";
        }else if (cardNum == 12){
            des = "A";
        }else {
            des = "*";
        }
        return cardColor.getDes() + des;
    }

    public static void washCards(int washNum){
        for (int i = 0; i < washNum; i++){
            wash();
        }
    }

    public static OrderManager getOrderManager(){
        OrderManager orderManager = threadOrderManager.get();
        if (orderManager == null){
            orderManager = new OrderManager();
            threadOrderManager.set(orderManager);
            //初始化排序管理器
            int[] orders = orderManager.getOrder();
            for (int i = 0; i < orders.length; i++){
                orders[i] = i;
            }
        }
        return orderManager;
    }

    private static void wash(){

        //洗牌的第一个动作是切牌，切牌随即1~3次
        Random random = new Random();

        int cycleNum = random.nextInt(3) + 1;
        for (int i = 0; i < cycleNum ; i++) {
            int minOff = (int) Math.round(MIN_WASH_SPLIT_RATE * TOTAL_CARDS);
            int maxOff = (int) Math.round(MAX_WASH_SPLIT_RATE * TOTAL_CARDS);
            int splitIndex = random.nextInt(maxOff - minOff + 1) + minOff;//第一份，随即计算
            split(splitIndex);
        }

        //将牌随即分成两份
        int minOff = (int) Math.round(MIN_WASH_SPLIT_RATE * TOTAL_CARDS);
        int maxOff = (int) Math.round (MAX_WASH_SPLIT_RATE * TOTAL_CARDS);
        int first = random.nextInt(maxOff - minOff + 1) + minOff;//第一份，随即计算

        //其中一份的张数范围在17~35张之间(1/3 ~ 2/3)
        //洗牌的方式是一张叠一张，从上到下，第一份一张，第二份一张，如此类推
        int oldOrder[] = getOrderManager().getOrder();
        int newOrder[] = new int[TOTAL_CARDS];
        orderClone(newOrder, oldOrder);
        int firstIndex = 0;
        int secondIndex = first;
        for (int i = 0 ; i < TOTAL_CARDS; i++){
            if (firstIndex >= first || secondIndex >= TOTAL_CARDS){
                break;
            }
            newOrder[i] = oldOrder[firstIndex];
            newOrder[++i] = oldOrder[secondIndex];
            firstIndex++;
            secondIndex++;
        }
        if (first != TOTAL_CARDS - first) {
            int leafIndex = TOTAL_CARDS - (TOTAL_CARDS - (Math.min(first, TOTAL_CARDS - first)*2));
            for (int i = leafIndex; i < TOTAL_CARDS; i++) {
                if (first > TOTAL_CARDS - first) {
                    newOrder[i] = oldOrder[firstIndex];
                    firstIndex++;
                } else {
                    newOrder[i] = oldOrder[secondIndex];
                    secondIndex++;
                }
            }
        }

        if (isRightOrder(newOrder)){
            getOrderManager().setOrder(newOrder);
        }else {
            System.out.println("error wash cards !");
        }

    }

    private static void orderClone(int[] newOrder, int[] oldOrder){
        for (int i = 0; i < TOTAL_CARDS; i++){
            newOrder[i] = oldOrder[i];
        }
    }


    private static boolean isRightOrder(int[] cards){
        //去重
        Set<Integer> cardSet = new HashSet<Integer>();
        for(int i = 0; i < cards.length; i++){
            if (cardSet.contains(cards[i]) || cards[i] < 0 || cards[i] >= TOTAL_CARDS){
                return false;
            }
            cardSet.add(cards[i]);
        }

        return cardSet.size() == TOTAL_CARDS;
    }

    public static void splitCard(){
        Random random = new Random();
        int minOff = (int) Math.round(MIN_PATCH_SPLIT_RATE * TOTAL_CARDS);
        int maxOff = (int) Math.round (MAX_PATCH_SPLIT_RATE * TOTAL_CARDS);
        int splitIndex = random.nextInt(maxOff - minOff + 1) + minOff;//第一份，随即计算

        split(splitIndex);
    }

    private static void split(int splitIndex){

        int newOrder[] = new int[TOTAL_CARDS];
        int oldOrder[] = getOrderManager().getOrder();
        for (int i = 0; i < TOTAL_CARDS; i++){
            if (splitIndex >= TOTAL_CARDS){
                splitIndex = 0;
            }
            newOrder[i] = oldOrder[splitIndex];
            splitIndex++;
        }
        if (isRightOrder(newOrder)){
            getOrderManager().setOrder(newOrder);
        }else {
            System.out.println("error split cards");
        }
    }

    public static void patchCards(Player[] players){
        int playerIndex = getWinnerNext(players);
        int[] orders = getOrderManager().getOrder();
        for (int i = 0; i < players.length * PER_PLAYER_CARD_NUM; i++){
            players[playerIndex].patchedCard(orders[i]);
            playerIndex = (playerIndex + 1) % players.length;
        }
    }

    private static int getWinner(Player[] players){
        int winId = 0;
        for (int i = 0; i < players.length; i++){
            if (players[i].isWinner()){
                winId = players[i].getIndex();
            }
        }
        return winId;
    }

    private static int getWinnerNext(Player[] players){

        int winId = getWinner(players);

        int playerIndex = (winId + 1) % players.length;
        return playerIndex;
    }

    public static List<Card> getPlayerCards(List<Integer> orderIdList){
        //获取的只能是备份

        List<Card> cardList = new ArrayList<Card>();
        for (Integer orderId : orderIdList){
            cardList.add(getOrderManager().getCards()[orderId]);
        }
        return cardList;
    }

    public static void setWinner(Player[] players){
        //定输赢
        List<GameInfo> gameInfoList = new ArrayList<GameInfo>();
        for (int i = 0; i < players.length; i++){
            //先将所有人清空庄家
            players[i].setWinner(false);
            players[i].addTotalNum();
            GameInfo gameInfo = new GameInfo();
            gameInfo.setPlayerIndex(players[i].getIndex());
            gameInfo.setCards(getPlayerCards(players[i].getCardOrders()));
            gameInfoList.add(gameInfo);
        }
        int winnerIndex = Rule.getWinner(gameInfoList);
        players[winnerIndex].setWinner(true);
        players[winnerIndex].addWinNum();
    }

    public static void recoverCards(Player[] players){
        //收牌,一般都是放上面
        int playerIndex = getWinner(players);
        int[] orders = getOrderManager().getOrder();
        for (int i = 0; i < players.length; i++){
            //得到三张牌号码
            List<Integer> cardList = players[playerIndex].getCardOrders();
            for (int j = 0; j < cardList.size(); j++){
                orders[i * PER_PLAYER_CARD_NUM + j] = cardList.get(j);
            }
            players[playerIndex].recoverCard();
            playerIndex = (playerIndex + 1) % players.length;
        }
        if (!isRightOrder(orders)){
            System.out.println("recovery cards error");
        }
    }
}
