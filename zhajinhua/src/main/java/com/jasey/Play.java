package com.jasey;

import com.jasey.base.Player;
import com.jasey.base.OrderManager;


public class Play {
    public static void main(String args[]){
        //初始化52张牌
        OrderManager.initCards();
        //初始化玩家
        Player players[] = new Player[6];
        for (int i = 0; i < 6; i++){
            Player player = new Player();
            player.setIndex(i);
            players[i] = player;
        }
        //初始化庄家
        players[0].setWinner(true);

        for (int j = 0; j < 1000; j++) {
            for (int i = 0; i < 10; i++) {

                //洗牌
                OrderManager.washCards(5);

                //切牌
                OrderManager.splitCard();

                //发牌
                OrderManager.patchCards(players);

                //定输赢
                OrderManager.setWinner(players);

                //收牌
                OrderManager.recoverCards(players);

            }

            for (int k = 0; k < players.length; k++) {
                System.out.println(String.format("player %s 's win : %.2f", players[k].getIndex(), players[k].getWinRate()));
                players[k].clearLog();
            }

            System.out.println("==================================");
        }

        System.out.println("game over");

    }
}
