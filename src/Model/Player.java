package Model;

import Model.Card.Card;
import Model.Card.MonsterCard;

import java.util.ArrayList;

public class Player {
    private ArrayList<Card> inventoryCards;
    private ArrayList<Card> deckCards;
    private MonsterCard[] monsterFieldCards = new MonsterCard[5];
    private Card[] spellFieldCards = new Card[3];    //todo replace SpellCard
    private ArrayList<Card> graveyardCards;
    private ArrayList<Card> handCards;
    private Shop shop;
    private int gil;
    private int mana;
    private int maxMana;

    public ArrayList<Card> getInventoryCards() {
        return inventoryCards;
    }
    public void setInventoryCards(ArrayList<Card> inventoryCards) {
        this.inventoryCards = inventoryCards;
    }

    public ArrayList<Card> getDeckCards() {
        return deckCards;
    }
    public void setDeckCards(ArrayList<Card> deckCards) {
        this.deckCards = deckCards;
    }

    public MonsterCard[] getMonsterFieldCards() {
        return monsterFieldCards;
    }
    public void setMonsterFieldCards(MonsterCard monsterFieldCard, int slotNumber) {
        monsterFieldCards[slotNumber] = monsterFieldCard;
    }

    public Card[] getSpellFieldCards() {
        return spellFieldCards;
    }
    public void setSpellFieldCards(Card[] spellFieldCards) {
        this.spellFieldCards = spellFieldCards;
    }

    public ArrayList<Card> getGraveyardCards() {
        return graveyardCards;
    }
    public void setGraveyardCards(ArrayList<Card> graveyardCards) {
        this.graveyardCards = graveyardCards;
    }

    public ArrayList<Card> getHandCards() {
        return handCards;
    }
    public void setHandCards(ArrayList<Card> handCards) {
        this.handCards = handCards;
    }

    public Shop getShop() {
        return shop;
    }
    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public int getGil() {
        return gil;
    }
    public void setGil(int gil) {
        this.gil = gil;
    }

    public int getMana() {
        return mana;
    }
    public void setMana(int mana) {
        this.mana = mana;
    }

    public int getMaxMana() {
        return maxMana;
    }
    public void setMaxMana(int maxMana) {
        this.maxMana = maxMana;
    }

    public boolean isDefenderPresent(){
        for (MonsterCard monsterCard: monsterFieldCards)
            if (monsterCard != null && monsterCard.isDefender())
                return true;
        return false;
    }
}
