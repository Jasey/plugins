import com.jasey.base.GameInfo;
import com.jasey.base.card.Card;
import com.jasey.base.card.CardColor;
import com.jasey.base.rule.Rule;

import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args){
        List<GameInfo> gameInfoList = new ArrayList<GameInfo>();

        GameInfo gameInfo1= new GameInfo();
        gameInfo1.setPlayerIndex(0);
        List<Card> cardList1 = new ArrayList<Card>();
        Card card11 = new Card();
        card11.setNum(12);
        card11.setColor(CardColor.BLACK_ClUB);
        Card card12 = new Card();
        card12.setNum(0);
        card12.setColor(CardColor.BLACK_ClUB);
        Card card13 = new Card();
        card13.setNum(1);
        card13.setColor(CardColor.BLACK_ClUB);
        cardList1.add(card11);
        cardList1.add(card12);
        cardList1.add(card13);
        gameInfo1.setCards(cardList1);

        GameInfo gameInfo2= new GameInfo();
        gameInfo2.setPlayerIndex(1);
        List<Card> cardList2 = new ArrayList<Card>();

        Card card21 = new Card();
        card21.setNum(11);
        card21.setColor(CardColor.BLACK_HEART);

        Card card22 = new Card();
        card22.setNum(1);
        card22.setColor(CardColor.BLACK_HEART);

        Card card23 = new Card();
        card23.setNum(5);
        card23.setColor(CardColor.BLACK_HEART);
        cardList2.add(card21);
        cardList2.add(card22);
        cardList2.add(card23);

        gameInfo2.setCards(cardList2);

        gameInfoList.add(gameInfo2);
        gameInfoList.add(gameInfo1);


        Rule.getWinner(gameInfoList);
    }
}
