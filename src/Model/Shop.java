package Model;

import Model.Card.Card;
import Model.Spell.GeneralizedSpell;

import java.io.Serializable;
import java.util.ArrayList;

public class Shop implements Cloneable, Serializable{//TODO (not vital): add a sentence to tell that the price in selling divides by 2
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

    public void addCard(Card card, int number) throws Exception{
        for(int i=0; i<number; i++)
            addCard((Card)card.clone());
    }
    public void addCard(Card card){
        cards.add(card);
    }
    public void removeCard(Card card){
        cards.remove(card);
    }

    public void addItem(Item item){
        items.add(item);
    }
    public void removeItem(Item item){
        items.remove(item);
    }

    public void addAmulet(Amulet amulet){
        amulets.add(amulet);
    }
    public void removeAmulet(Amulet amulet){
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
        Shop cloned = (Shop) super.clone();
        cloned.amulets = new ArrayList<>(this.amulets);
        cloned.cards = new ArrayList<>(this.cards);
        cloned.items = new ArrayList<>(this.items);
        return cloned;
    }
}
