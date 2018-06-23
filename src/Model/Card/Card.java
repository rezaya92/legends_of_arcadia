package Model.Card;

import Model.Player;
import Model.SpellCastable;
import Model.Stuff;
import javafx.collections.ObservableList;

import java.util.ArrayList;

/**
 * Created by msi-pc on 4/27/2018.
 */
public abstract class Card implements SpellCastable, Stuff, Cloneable {

    int defaultManaCost;
    int manaCost;
    int price;
    String name;
    Player owner;    // "add default owner in case of opponent could take control of a card of player"
    ObservableList<Card> cardPlace;

    public int getDefaultManaCost() {
        return defaultManaCost;
    }

    public void setManaCost(int manaCost) {
        this.manaCost = manaCost;
    }
    public int getManaCost(){
        return manaCost;
    }

    public void setCardPlace(ObservableList<Card> cardPlace) {
        this.cardPlace = cardPlace;
    }
    public ObservableList<Card> getCardPlace(){
        return cardPlace;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }

    public int getPriceToSell() {
        return price/2;
    }

    public Player getOwner() {
        return owner;
    }
    public void setOwner(Player owner) {
        this.owner = owner;
    }


    public abstract boolean play(int slotNumber);
    public abstract boolean play();    // greedy way

    public boolean equalsInName(Object arg0){
        if(!(arg0 instanceof Card))
            return false;
        return (this.name.equals(((Card) arg0).name));
    }

    public void transfer (ObservableList<Card> destination){  // "also can be boolean"
        if (owner != null && owner.getIsPlaying())
            deuseAuraCards();
        if (cardPlace != null)
            cardPlace.remove(this);
        if(destination != null)
            destination.add(this);
        cardPlace = destination;
        this.restoreValues();   // is correct ??  "if there exist a spell that change hand card values no"
        if (owner != null && owner.getIsPlaying())
            useAuraCards();
    }

    public void deuseAuraCards(){
        owner.deuseAuraCards();
        owner.getOpponent().deuseAuraCards();
    }
    public void useAuraCards(){
        owner.useAuraCards();
        owner.getOpponent().useAuraCards();
    }

    public String getCardPlacebyName(){
            if (cardPlace == owner.getMonsterFieldCards())
                return "Monster Field";
            else if (cardPlace == owner.getDeckCards())
                return "Deck";
            else if (cardPlace == owner.getHandCards())
                return "Hand";
            else if (cardPlace == owner.getSpellFieldCards()) {
                return "Spell Field";
            } else if (cardPlace == owner.getGraveyardCards()) {
                return "Graveyard";
            } else if (cardPlace == owner.getInventoryCards())
                return "Inventory";
            else
                return "nowhere!";
    }

    public abstract void restoreValues();

//    @Override
//    public Stuff clone() throws CloneNotSupportedException{
//        return (Stuff)super.clone();
//    }

    @Override
    public Object clone() throws CloneNotSupportedException{
        return super.clone();
    }
}
