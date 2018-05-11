package Model;

import Model.Card.*;
import Model.Card.PlayerHero;

import java.util.ArrayList;

public class Player {    // todo before and after some actions deuse and use of Aura cards must be called --> method for this   // also start/end turn methods
    private final int deckCapacity = 30;
    private ArrayList<Card> inventoryCards = new ArrayList<>();
    private ArrayList<Card> deckCards = new ArrayList<>();
    private ArrayList<Integer> deckCardsSlotNumber = new ArrayList<>();
    private ArrayList<Card> monsterFieldCards = new PlayAreaArrayList<>(5);
    private ArrayList<Card> spellFieldCards = new PlayAreaArrayList<>(3);
    private ArrayList<Card> graveyardCards;
    private ArrayList<Card> handCards;
    private Shop shop = new Shop();
    private int gil;
    private int mana;
    private int maxMana;
    private String name;
    private PlayerHero playerHero;
    private Player opponent;
    private ArrayList<MonsterCard> sleepingPlayedCards = new ArrayList<>();
    private ArrayList<SpellCard> continuousSpellCards = new ArrayList<>();

    public Player(){} //for now (human)

    public Player(String name, int playerHeroDefaultHP){
        this.name = name;
        playerHero = new PlayerHero(playerHeroDefaultHP, this);
    }

    public String getName() {
        return name;
    }

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

    public ArrayList<Card> getMonsterFieldCards() {
        return monsterFieldCards;
    }

    public ArrayList<Card> getSpellFieldCards() {
        return spellFieldCards;
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

    public PlayerHero getPlayerHero() {
        return playerHero;
    }

    public Player getOpponent() {
        return opponent;
    }
    public void setOpponent(Player opponent) {
        this.opponent = opponent;
    }

    //---------------------------------------------------------------------------------------------------------------
    public void addSleepingPlayedCard(MonsterCard sleepingPlayedCard){
        sleepingPlayedCards.add(sleepingPlayedCard);
    }

    public void awakeSleepingPlayedCards(){   // must be called when turn ends   "also consider being empty when next game starts"
        for (MonsterCard sleepingPlayedCard: sleepingPlayedCards){
            sleepingPlayedCard.getAwake();
        }
        sleepingPlayedCards.clear();
    }

    //---------------------------------------------------------------------------------------------------------------
    public void addContinuousSpellCard(SpellCard continuousSpellCard){
        continuousSpellCards.add(continuousSpellCard);
    }

    public void removeContinuousSpellCard(SpellCard continuousSpellCard){   // "consider being empty for next game"
        continuousSpellCards.remove(continuousSpellCard);
    }
    //---------------------------------------------------------------------------------------------------------------

    public int buyCard(String name, int numberToBuy){/* -1:insufficient Gil - 0:not available in shop - 1:successful */
        ArrayList<Card> shopCards = shop.getCards();
        int numberOfThatCardInShop = 0;
        int priceOfThatCard = 0;
        for(Card card : shopCards){
            if(card.getName().equals(name)){
                numberOfThatCardInShop ++;
                priceOfThatCard = card.getPrice();
            }
        }
        if(numberOfThatCardInShop < numberToBuy)
            return 0;
        if(priceOfThatCard * numberToBuy > gil)
            return -1;
        for(Card card : shopCards) {
            if (card.getName().equals(name) && numberToBuy > 0) {
                shop.remove(card);
                inventoryCards.add(card);
                numberToBuy--;
            }
        }
        gil -= priceOfThatCard * numberToBuy;
        return 1;
    }

    public boolean sellCard(String name, int numberToSell){/* 0:number more than in inventory(and not in deck) - 1:successful */
        ArrayList<Card> shopCards = shop.getCards();
        int availableNumberOfThatCard = 0;
        int priceOfThatCard = 0;
        for(Card card : inventoryCards){
            if(card.getName().equals(name) && !deckCards.contains(card)){
                availableNumberOfThatCard ++;
                priceOfThatCard = card.getPrice();
            }
        }
        if(availableNumberOfThatCard < numberToSell)
            return false;
        for(Card card : inventoryCards){
            if(card.getName().equals(name) && !deckCards.contains(card) && numberToSell > 0){
                shop.add(card);
                inventoryCards.remove(card);
                numberToSell --;
            }
        }
        gil += priceOfThatCard * numberToSell;
        return true;
    }

    public boolean isDefenderPresent(){
        for (Card monsterCard: monsterFieldCards)
            if (monsterCard != null && ((MonsterCard)monsterCard).isDefender())
                return true;
        return false;
    }

    public String deckToString(){
        String output = "";
        for(int i=1; i<deckCapacity + 1; i++){
            output += "Slot " + i + ": ";
            int indexOfWantedCard = deckCardsSlotNumber.indexOf(i);
            if(indexOfWantedCard == -1) {
                output += "Empty\n";
                continue;
            }
            output += deckCards.get(indexOfWantedCard) + "\n";
        }
        return output;
    }

    public String inventoryToString(){
        String output = "";
        ArrayList<Card> uniqueCards = new ArrayList<>();
        ArrayList<Integer> numberOfCards = new ArrayList<>();
        ArrayList<Integer> numberOnDeck = new ArrayList<>();
        myLabel1:
        for(Card card : inventoryCards){
            for(int i=0; i<uniqueCards.size(); i++){
                if(card.equalsInName(uniqueCards.get(i))){
                    numberOfCards.set(i, numberOfCards.get(i) + 1);
                    continue myLabel1;
                }
            }
            uniqueCards.add(card);
            numberOfCards.add(1);
            numberOnDeck.add(0);
            for(Card cardInDeck : deckCards) {
                if(card.equalsInName(cardInDeck)){
                    numberOnDeck.set(numberOnDeck.size() - 1, numberOnDeck.get(numberOnDeck.size() - 1) + 1);
                }
            }
        }
        for(int i=0; i<uniqueCards.size(); i++){
            output += i + 1 + ". " + numberOfCards.get(i) + " " + uniqueCards.get(i).getName() + " / " + numberOnDeck.get(i) + " on deck\n";
        }
        return output;
    }
}
