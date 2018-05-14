package Model;

import Model.Card.*;
import Model.Spell.GeneralizedSpell;
//import Model.PlayerHero;

import java.util.ArrayList;

public class Player {    // todo before and after some actions deuse and use of Aura cards must be called --> method for this   // also start/end turn methods
    private final int deckCapacity = 30;
    private ArrayList<Card> inventoryCards = new ArrayList<>();
    private ArrayList<Card> deckCards = new ArrayList<>();
    private ArrayList<Integer> deckCardsSlotNumber = new ArrayList<>();//TODO whenever a card comes to deck, it's slot number must be added or updated (also when removed)
    private ArrayList<Card> monsterFieldCards = new PlayAreaArrayList<>(5);
    private ArrayList<Card> spellFieldCards = new PlayAreaArrayList<>(3);
    private ArrayList<Card> graveyardCards;
    private ArrayList<Card> handCards;
    private ArrayList<Item> items = new ArrayList<>();
    private ArrayList<Amulet> amulets = new ArrayList<>();
    private Amulet equippedAmulet;
    private Shop shop = new Shop();
    private int gil;
    private int mana;
    private int maxMana;
    private String name;
    private PlayerHero playerHero;
    private Player opponent;
    private ArrayList<MonsterCard> sleepingPlayedCards = new ArrayList<>();
    private ArrayList<MonsterCard> hasAttackedCards = new ArrayList<>();
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

    public Amulet getEquippedAmulet() {
        return equippedAmulet;
    }
    public void setEquippedAmulet(Amulet equippedAmulet) {
        this.equippedAmulet = equippedAmulet;
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
    public void addHasAttackedCard(MonsterCard monsterCard){
        hasAttackedCards.add(monsterCard);
    }

    public void resetHasAttackedCards(){
        for (MonsterCard monsterCard: hasAttackedCards){
            monsterCard.setHasAttacked(false);
        }
        hasAttackedCards.clear();
    }

    //---------------------------------------------------------------------------------------------------------------
    public void addContinuousSpellCard(SpellCard continuousSpellCard){
        continuousSpellCards.add(continuousSpellCard);
    }

    public void removeContinuousSpellCard(SpellCard continuousSpellCard){   // "consider being empty for next game"
        continuousSpellCards.remove(continuousSpellCard);
    }
    //---------------------------------------------------------------------------------------------------------------

    public int buyStuff(TypeOfStuffToBuyAndSell typeOfStuffToBuyAndSell, String name, int numberToBuy){/* -1:insufficient Gil - 0:not available in shop - 1:successful */
        int numberToBuyCounter = numberToBuy;
        ArrayList<? extends Stuff> shopStuff;
        int numberOfThatCardInShop = 0;
        int priceOfThatCard = 0;

        switch (typeOfStuffToBuyAndSell) {
            case CARD:
                shopStuff = shop.getCards();
                break;
            case ITEM:
                shopStuff = shop.getItems();
                break;
            //case AMULET:
                default:
                shopStuff = shop.getAmulets();
        }

        for(Stuff stuff : shopStuff){
            if(stuff.getName().equals(name)){
                numberOfThatCardInShop ++;
                priceOfThatCard = stuff.getPrice();
            }
        }
        if(numberOfThatCardInShop < numberToBuy)
            return 0;
        if(priceOfThatCard * numberToBuy > gil)
            return -1;
        for(Stuff stuff : shopStuff) {
            if (stuff.getName().equals(name) && numberToBuyCounter > 0) {
                switch (typeOfStuffToBuyAndSell) {
                    case CARD:
                        shop.removeCard((Card) stuff);
                        inventoryCards.add((Card) stuff);
                        break;
                    case ITEM:
                        shop.removeItem((Item) stuff);
                        items.add((Item) stuff);
                        break;
                    default:
                        shop.removeAmulet((Amulet) stuff);
                        amulets.add((Amulet) stuff);
                }
                numberToBuyCounter--;
            }
        }
        gil -= priceOfThatCard * numberToBuy;
        return 1;
    }

    //can improve by omitting the duplication
    public boolean sellStuff(TypeOfStuffToBuyAndSell typeOfStuffToBuyAndSell, String name, int numberToSell){/* 0:number more than in inventory(and not in deck) - 1:successful */
        int numberToSellCounter = numberToSell;
        ArrayList<? extends Stuff> playerStuff;
        int availableNumberOfThatCard = 0;
        int priceOfThatCard = 0;

        switch (typeOfStuffToBuyAndSell) {
            case CARD:
                //playerStuff = inventoryCards;
                for(Card card : inventoryCards){
                    if(card.getName().equals(name) && !deckCards.contains(card)){
                        availableNumberOfThatCard ++;
                        priceOfThatCard = card.getPrice();
                    }
                }
                if(availableNumberOfThatCard < numberToSell)
                    return false;
                for(Card card : inventoryCards){
                    if(card.getName().equals(name) && !deckCards.contains(card) && numberToSellCounter > 0){
                        shop.addCard(card);
                        inventoryCards.remove(card);
                        numberToSellCounter --;
                    }
                }
                break;
            case ITEM:
                //playerStuff = items;
                for(Item item : items){
                    if(item.getName().equals(name)){
                        availableNumberOfThatCard ++;
                        priceOfThatCard = item.getPrice();
                    }
                }
                if(availableNumberOfThatCard < numberToSell)
                    return false;
                for(Item item : items){
                    if(item.getName().equals(name) && numberToSellCounter > 0){
                        shop.addItem(item);
                        items.remove(item);
                        numberToSellCounter --;
                    }
                }
                break;
            default:
                //playerStuff = amulets;
                for(Amulet amulet : amulets){
                    if(amulet.getName().equals(name) && !equippedAmulet.equals(amulet)){
                        availableNumberOfThatCard ++;
                        priceOfThatCard = amulet.getPrice();
                    }
                }
                if(availableNumberOfThatCard < numberToSell)
                    return false;
                for(Amulet amulet : amulets){
                    if(amulet.getName().equals(name) && !equippedAmulet.equals(amulet) && numberToSellCounter > 0){
                        shop.addAmulet(amulet);
                        amulets.remove(amulet);
                        numberToSellCounter --;
                    }
                }
        }
/*
        for(Stuff stuff : playerStuff){
            if(stuff.getName().equals(name) && !deckCards.contains(card)){
                availableNumberOfThatCard ++;
                priceOfThatCard = card.getPrice();
            }
        }
        if(availableNumberOfThatCard < numberToSell)
            return false;
        for(Card card : inventoryCards){
            if(card.getName().equals(name) && !deckCards.contains(card) && numberToSellCounter > 0){
                shop.addCard(card);
                inventoryCards.remove(card);
                numberToSellCounter --;
            }
        }*/
        gil += priceOfThatCard * numberToSell;
        return true;
    }







//
//    //duplicated code can be improved?
//    public int buyCard(String name, int numberToBuy){/* -1:insufficient Gil - 0:not available in shop - 1:successful */
//        int numberToBuyCounter = numberToBuy;
//        ArrayList<Card> shopCards = shop.getCards();
//        int numberOfThatCardInShop = 0;
//        int priceOfThatCard = 0;
//        for(Card card : shopCards){
//            if(card.getName().equals(name)){
//                numberOfThatCardInShop ++;
//                priceOfThatCard = card.getPrice();
//            }
//        }
//        if(numberOfThatCardInShop < numberToBuy)
//            return 0;
//        if(priceOfThatCard * numberToBuy > gil)
//            return -1;
//        for(Card card : shopCards) {
//            if (card.getName().equals(name) && numberToBuyCounter > 0) {
//                shop.removeCard(card);
//                inventoryCards.add(card);
//                numberToBuyCounter--;
//            }
//        }
//        gil -= priceOfThatCard * numberToBuy;
//        return 1;
//    }
//
//    public boolean sellCard(String name, int numberToSell){/* 0:number more than in inventory(and not in deck) - 1:successful */
//        int numberToSellCounter = numberToSell;
//        int availableNumberOfThatCard = 0;
//        int priceOfThatCard = 0;
//        for(Card card : inventoryCards){
//            if(card.getName().equals(name) && !deckCards.contains(card)){
//                availableNumberOfThatCard ++;
//                priceOfThatCard = card.getPrice();
//            }
//        }
//        if(availableNumberOfThatCard < numberToSell)
//            return false;
//        for(Card card : inventoryCards){
//            if(card.getName().equals(name) && !deckCards.contains(card) && numberToSellCounter > 0){
//                shop.addCard(card);
//                inventoryCards.remove(card);
//                numberToSellCounter --;
//            }
//        }
//        gil += priceOfThatCard * numberToSell;
//        return true;
//    }
//
//    public int buyItem(String name, int numberToBuy){/* -1:insufficient Gil - 0:not available in shop - 1:successful */
//        int numberToBuyCounter = numberToBuy;
//        ArrayList<Item> shopItems = shop.getItems();
//        int numberOfThatCardInShop = 0;
//        int priceOfThatCard = 0;
//        for(Item item : shopItems){
//            if(item.getName().equals(name)){
//                numberOfThatCardInShop ++;
//                priceOfThatCard = item.getPrice();
//            }
//        }
//        if(numberOfThatCardInShop < numberToBuy)
//            return 0;
//        if(priceOfThatCard * numberToBuy > gil)
//            return -1;
//        for(Item item : shopItems) {
//            if (item.getName().equals(name) && numberToBuyCounter > 0) {
//                shop.removeItem(item);
//                items.add(item);
//                numberToBuyCounter--;
//            }
//        }
//        gil -= priceOfThatCard * numberToBuy;
//        return 1;
//    }
//
//    public boolean sellItem(String name, int numberToSell){/* 0:number more than in inventory(and not in deck) - 1:successful */
//        int numberToSellCounter = numberToSell;
//        int availableNumberOfThatCard = 0;
//        int priceOfThatCard = 0;
//        for(Item item : items){
//            if(item.getName().equals(name)){
//                availableNumberOfThatCard ++;
//                priceOfThatCard = item.getPrice();
//            }
//        }
//        if(availableNumberOfThatCard < numberToSell)
//            return false;
//        for(Item item : items){
//            if(item.getName().equals(name) && numberToSellCounter > 0){
//                shop.addItem(item);
//                items.remove(item);
//                numberToSellCounter --;
//            }
//        }
//        gil += priceOfThatCard * numberToSell;
//        return true;
//    }
//
//    public int buyAmulet(String name, int numberToBuy){/* -1:insufficient Gil - 0:not available in shop - 1:successful */
//        int numberToBuyCounter = numberToBuy;
//        ArrayList<Amulet> shopAmulets = shop.getAmulets();
//        int numberOfThatCardInShop = 0;
//        int priceOfThatCard = 0;
//        for(Amulet amulet : shopAmulets){
//            if(amulet.getName().equals(name)){
//                numberOfThatCardInShop ++;
//                priceOfThatCard = amulet.getPrice();
//            }
//        }
//        if(numberOfThatCardInShop < numberToBuy)
//            return 0;
//        if(priceOfThatCard * numberToBuy > gil)
//            return -1;
//        for(Amulet amulet : shopAmulets) {
//            if (amulet.getName().equals(name) && numberToBuyCounter > 0) {
//                shop.removeAmulet(amulet);
//                amulets.add(amulet);
//                numberToBuyCounter--;
//            }
//        }
//        gil -= priceOfThatCard * numberToBuy;
//        return 1;
//    }
//
//    public boolean sellAmulet(String name, int numberToSell){/* 0:number more than in inventory(and not in deck) - 1:successful */
//        int numberToSellCounter = numberToSell;
//        int availableNumberOfThatCard = 0;
//        int priceOfThatCard = 0;
//        for(Amulet amulet : amulets){
//            if(amulet.getName().equals(name) && !equippedAmulet.equals(amulet)){
//                availableNumberOfThatCard ++;
//                priceOfThatCard = amulet.getPrice();
//            }
//        }
//        if(availableNumberOfThatCard < numberToSell)
//            return false;
//        for(Amulet amulet : amulets){
//            if(amulet.getName().equals(name) && !equippedAmulet.equals(amulet) && numberToSellCounter > 0){
//                shop.addAmulet(amulet);
//                amulets.remove(amulet);
//                numberToSellCounter --;
//            }
//        }
//        gil += priceOfThatCard * numberToSell;
//        return true;
//    }

    public boolean isDefenderPresent(){
        for (Card monsterCard: monsterFieldCards)
            if (monsterCard != null && ((MonsterCard)monsterCard).isDefender())
                return true;
        return false;
    }

//---------------------------------------------------------------------------------------------------------------------
    public void startTurn(){
        for (Card spellCard: spellFieldCards){
            if (((SpellCard) spellCard).getSpellCardType() == SpellCardType.CONTINUOUS)
                ((SpellCard) spellCard).getSpell().use();
        }
        mana = ++maxMana;
        if (!deckCards.isEmpty()){   // deck should be shuffled in the first    // else needed ??
            deckCards.get(0).transfer(handCards);
        }
    }

    public void endTurn(){
        awakeSleepingPlayedCards();
        resetHasAttackedCards();
    }

//---------------------------------------------------------------------------------------------------------------------
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

    //duplicated code could maybe be corrected
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

    public String itemToString(){
        String output = "";
        ArrayList<Item> uniqueItems = new ArrayList<>();
        ArrayList<Integer> numberOfItems = new ArrayList<>();
        //ArrayList<Integer> numberOnDeck = new ArrayList<>();
        myLabel1:
        for(Item item : items){
            for(int i=0; i<uniqueItems.size(); i++){
                if(item.equalsInName(uniqueItems.get(i))){
                    numberOfItems.set(i, numberOfItems.get(i) + 1);
                    continue myLabel1;
                }
            }
            uniqueItems.add(item);
            numberOfItems.add(1);
        }
        for(int i=0; i<uniqueItems.size(); i++){
            output += i + 1 + ". " + numberOfItems.get(i) + " " + uniqueItems.get(i).getName();
        }
        return output;
    }

    public String amuletToString(){
        String output = "";
        ArrayList<Amulet> uniqueAmulet = new ArrayList<>();
        ArrayList<Integer> numberOfAmulets = new ArrayList<>();
        //ArrayList<Integer> numberOnDeck = new ArrayList<>();
        myLabel1:
        for(Amulet amulet : amulets){
            for(int i=0; i<uniqueAmulet.size(); i++){
                if(amulet.equalsInName(uniqueAmulet.get(i))){
                    numberOfAmulets.set(i, numberOfAmulets.get(i) + 1);
                    continue myLabel1;
                }
            }
            uniqueAmulet.add(amulet);
            numberOfAmulets.add(1);
        }
        for(int i=0; i<uniqueAmulet.size(); i++){
            output += i + 1 + ". " + numberOfAmulets.get(i) + " " + uniqueAmulet.get(i).getName();
        }
        return output;
    }
}
