package Model.Card;

import Model.CardPlace;
import Model.Tribe;

/**
 * Created by msi-pc on 4/27/2018.
 */
public abstract class Card {

    int cardID;
    int manaCost;
    CardPlace cardPlace;
    String name;
    Tribe tribe;

    public int getCardID() {
        return cardID;
    }
    public void setCardID(int cardID) {
        this.cardID = cardID;
    }

    public void setManaCost(int manaCost) {
        this.manaCost = manaCost;
    }
    public int getManaCost(){
        return manaCost;
    }

    public void setCardPlace(CardPlace cardPlace) {
        this.cardPlace = cardPlace;
    }
    public CardPlace getCardPlace(){
        return cardPlace;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }


    public void Die(){
        cardPlace = CardPlace.GRAVEYARD;
    }

    public Tribe getTribe() {
        return tribe;
    }
}
