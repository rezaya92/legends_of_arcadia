package Model;

import Model.Card.Card;

import java.util.ArrayList;

public class Shop {
    private ArrayList<Card> cards = new ArrayList<>();

    void add(Card card){
        cards.add(card);
    }

    void remove(Card card){
        cards.remove(card);
    }

    @Override
    public String toString(){
        String output = " Shop List:\n";
        for(int i=0; i<cards.size(); i++){
            output += i + 1 + ". " + cards.get(i).getName() + " " + cards.get(i).getPrice() + " Gil\n";//i + 1 okaye?
        }
        return output;
    }
}
