package Model;

/**
 * Created by msi-pc on 4/27/2018.
 */
public abstract class Card {

    int manaCost;
    CardPlace cardPlace;

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

    public void Die(){
        cardPlace = CardPlace.GRAVEYARD;
    }
}
