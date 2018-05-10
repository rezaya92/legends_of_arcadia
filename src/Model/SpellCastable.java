package Model;

import Model.Card.CardPlace;
import Model.Card.Tribe;

public interface SpellCastable {
    CardPlace getCardPlace();
    Player getOwner();
    void setCardPlace(CardPlace cardPlace);
    Tribe getTribe();
    String getName();
}
