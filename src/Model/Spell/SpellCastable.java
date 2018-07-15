package Model.Spell;

import Model.ListShowable;
import Model.Player;

public interface SpellCastable extends ListShowable {
    //ArrayList<Card> getCardPlace();
    Player getOwner();
    //void setCardPlace(ArrayList<Card> cardPlace);
    String getName();

    public Object clone() throws CloneNotSupportedException;
}
