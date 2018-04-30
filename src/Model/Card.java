package Model;

/**
 * Created by msi-pc on 4/27/2018.
 */
public abstract class Card {

    int manaCost;
    CardPlace cardPlace;

    public void Die(){
        cardPlace = CardPlace.GRAVEYARD;
    }
}
