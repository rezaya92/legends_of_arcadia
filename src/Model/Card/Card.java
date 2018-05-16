package Model.Card;

import Model.Player;
import Model.SpellCastable;
import Model.Stuff;

import java.util.ArrayList;

/**
 * Created by msi-pc on 4/27/2018.
 */
public abstract class Card implements SpellCastable, Stuff {

    int defaultManaCost; // todo set final and initialize
    int manaCost;
    int price;
    String name;
    Player owner;    // "add default owner in case of opponent could take control of a card of player"
    ArrayList<Card> cardPlace;

    //TODO set owner for every spell in constructor

    public int getDefaultManaCost() {
        return defaultManaCost;
    }

    public void setManaCost(int manaCost) {
        this.manaCost = manaCost;
    }
    public int getManaCost(){
        return manaCost;
    }

    public void setCardPlace(ArrayList<Card> cardPlace) {
        this.cardPlace = cardPlace;
    }
    public ArrayList<Card> getCardPlace(){
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

    public Player getOwner() {
        return owner;
    }
    public void setOwner(Player owner) {
        this.owner = owner;
    }


    public abstract void play(int slotNumber);

    public boolean equalsInName(Object arg0){
        if(!(arg0 instanceof Card))
            return false;
        return (this.name.equals(((Card) arg0).name));
    }

    public void transfer (ArrayList<Card> destination){  // todo consider spell of spellCards   "also can be boolean"
        if (owner != null && owner.getIsPlaying())
            deuseAuraCards();
        if (cardPlace != null)
            cardPlace.remove(this);
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

    public abstract void restoreValues();
}

