package Model;

public interface SpellCastable extends Cloneable {
    //ArrayList<Card> getCardPlace();
    Player getOwner();
    //void setCardPlace(ArrayList<Card> cardPlace);
    String getName();

    public Object clone() throws CloneNotSupportedException;
}
