package Model;

import Model.Card.Card;
import Model.Spell.GeneralizedSpell;

import java.util.ArrayList;

public class Shop implements Cloneable{
    private ArrayList<Card> cards = new ArrayList<>();
    private ArrayList<Item> items = new ArrayList<>();
    private ArrayList<Amulet> amulets = new ArrayList<>();

    public ArrayList<Card> getCards() {
        return cards;
    }
    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }

    public ArrayList<Item> getItems() {
        return items;
    }
    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public ArrayList<Amulet> getAmulets() {
        return amulets;
    }
    public void setAmulets(ArrayList<Amulet> amulets) {
        this.amulets = amulets;
    }

    void addCard(Card card){
        cards.add(card);
    }
    void removeCard(Card card){
        cards.remove(card);
    }

    void addItem(Item item){
        items.add(item);
    }
    void removeItem(Item item){
        items.remove(item);
    }

    void addAmulet(Amulet amulet){
        amulets.add(amulet);
    }
    void removeAmulet(Amulet amulet){
        amulets.remove(amulet);
    }

    public String cardToString(){
        String output = "";
        for(int i=0; i<cards.size(); i++){
            output += i + 1 + ". " + cards.get(i).getName() + " " + cards.get(i).getPrice() + " Gil\n";
        }
        return output;
    }

    public String itemToString(){
        String output = "";
        for(int i=0; i<items.size(); i++){
            output += i + 1 + ". " + items.get(i).getName() + " " + items.get(i).getPrice() + " Gil\n";
        }
        return output;
    }

    public String amuletToString(){
        String output = "";
        for(int i=0; i<amulets.size(); i++){
            output += i + 1 + ". " + amulets.get(i).getName() + " " + amulets.get(i).getPrice() + " Gil\n";
        }
        return output;
    }

    @Override
    public Object clone() throws CloneNotSupportedException{
        return super.clone();
    }
}
