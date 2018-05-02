package Model;

/**
 * Created by msi-pc on 4/27/2018.
 */
public abstract class Card {

    private int cardID;
    private int manaCost;
    private int price;
    private CardPlace cardPlace;
    private String name;
    private Tribe tribe;
    private Player owner;

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

    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }

    public Tribe getTribe() {
        return tribe;
    }
    public void setTribe(Tribe tribe) {
        this.tribe = tribe;
    }

    public Player getOwner() {
        return owner;
    }
    public void setOwner(Player owner) {
        this.owner = owner;
    }


    public abstract void play(int slotNumber);

    public void Die(){
        cardPlace = CardPlace.GRAVEYARD;
    }
}
