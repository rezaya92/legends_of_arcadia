package Model;

import Model.Spell.ListShowable;

public interface SpellCastable extends ListShowable {
    //ArrayList<Card> getCardPlace();
    Player getOwner();
    //void setCardPlace(ArrayList<Card> cardPlace);
    String getName();

    public Object clone() throws CloneNotSupportedException;
}
