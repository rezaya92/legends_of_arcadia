package Model;

import Model.Card.Card;
import Model.Card.CardPlace;
import Model.Card.Tribe;

import java.util.ArrayList;

public interface SpellCastable {
    ArrayList<Card> getCardPlace();
    Player getOwner();
    void setCardPlace(ArrayList<Card> cardPlace);
    Tribe getTribe();
    String getName();
}
