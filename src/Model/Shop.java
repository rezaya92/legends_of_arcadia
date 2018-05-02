package Model;

import Model.Card.Card;

import java.util.ArrayList;

public class Shop {
    private ArrayList<Card> cards;

    void add(Card card){
        cards.add(card);
    }

    void remove(Card card){
        cards.remove(card);
    }

    @Override
    public String toString(){
        String output = " Shop List:\n";
//        ArrayList<Card> uniqueCards = new ArrayList<>();
//        ArrayList<Integer> numberOfCards = new ArrayList<>();
//        myLabel1:
//        for(Card card : cards){
//            for(int i=0; i<uniqueCards.size(); i++){
//                if(card.equals(uniqueCards.get(i))){//todo equals card
//                    numberOfCards.set(i, numberOfCards.get(i) + 1);
//                    continue myLabel1;
//                }
//            }
//            uniqueCards.add(card);
//            numberOfCards.add(1);
//        }

        for(int i=0; i<cards.size(); i++){
            output += i + ". " + cards.get(i).getName() + " " + cards.get(i).getPrice() + " Gil\n";
        }
        return output;
    }
}
