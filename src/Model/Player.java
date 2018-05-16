package Model;

import Model.Card.*;
import Model.Spell.GeneralizedSpell;
//import Model.PlayerHero;

import java.util.ArrayList;

public class Player {    // todo before and after some actions deuse and use of Aura cards must be called --> method for this
    private final int deckCapacity = 30;
    private ArrayList<Card> inventoryCards = new ArrayList<>();
    private ArrayList<Card> defaultDeckCards = new ArrayList<>(30);
    private ArrayList<Card> deckCards = new ArrayList<>();
    //private ArrayList<Integer> deckCardsSlotNumber = new ArrayList<>();//TODO whenever a card comes to deck, it's slot number must be added or updated (also when removed)
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
    private boolean isPlaying = false;

    public Player(){} //for now (human)

    public Player(String name, int playerHeroDefaultHP){
        this.name = name;
        playerHero = new PlayerHero(playerHeroDefaultHP, this);
    }

    public String getName() {
        return name;
    }

    public void setIsPlaying(boolean isPlaying){this.isPlaying = isPlaying;}
    public boolean getIsPlaying(){return isPlaying;}

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

    //-------------------------------------------buy and sell stuff from shop----------------------------------------------------
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
    //------------sell stuff------------
    //can improve by omitting the duplication
    public boolean sellStuff(TypeOfStuffToBuyAndSell typeOfStuffToBuyAndSell, String name, int numberToSell){/* 0:number more than in inventory(and not in deck) - 1:successful */
        int numberToSellCounter = numberToSell;
        int availableNumberOfThatCard = 0;
        int priceOfThatCard = 0;

        switch (typeOfStuffToBuyAndSell) {
            case CARD:
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
        gil += priceOfThatCard * numberToSell;
        return true;
    }
    //----------------------------------------------------add & remove card from Inventory to deck---------------------------------------------------------------

    public boolean addToDeck(String cardName, int slotNumber){
        for(Card card : inventoryCards){
            if(card.getName().equals(cardName)){
                removeFromDeck(slotNumber);
                card.transfer(deckCards);
                defaultDeckCards.set(slotNumber, card);
                //deckCardsSlotNumber.add(slotNumber);
                return true;
            }
        }
        return false;
    }

    public String removeFromDeck(int slotNumber){
        if(defaultDeckCards.get(slotNumber) == null)
            return null;
        Card wantedCard = defaultDeckCards.get(slotNumber);
        wantedCard.transfer(inventoryCards);
        //deckCards.remove(wantedCard);
        defaultDeckCards.set(slotNumber, null);
        return wantedCard.getName();
//        for(int i=0; i<deckCards.size(); i++){
//            if(deckCardsSlotNumber.get(i) == slotNumber){
//                String cardName = deckCards.get(i).getName();
//                deckCards.get(i).transfer(inventoryCards);
//                deckCardsSlotNumber.remove(i);
//                return cardName;
//            }
//        }
//        return null;
    }

    //-----------------------------------------------------------------------------------------------------------------------------

    public boolean equipAmulet(String amuletName){
        for(Amulet amulet : amulets){
            if(amulet.getName().equals(amuletName)){
                equippedAmulet = amulet;
                return true;
            }
        }
        return false;
    }

    public String removeEquippedAmulet(){
        if(equippedAmulet == null)
            return null;
        String amuletName = equippedAmulet.getName();
        equippedAmulet = null;
        return amuletName;
    }

    public boolean isDefenderPresent(){
        for (Card monsterCard: monsterFieldCards)
            if (monsterCard != null && ((MonsterCard)monsterCard).isDefender())
                return true;
        return false;
    }

//--------------------------------------------------------Aura Cards deuse and use---------------------------------------------------
    public void deuseAuraCards(){
        for (Card spellCard: spellFieldCards){
            if (spellCard != null && ((SpellCard) spellCard).getSpellCardType() == SpellCardType.AURA)
                ((SpellCard) spellCard).getSpell().deuse();
        }
    }

    public void useAuraCards(){
        for (Card spellCard: spellFieldCards){
            if (spellCard != null && ((SpellCard) spellCard).getSpellCardType() == SpellCardType.AURA)
                ((SpellCard) spellCard).getSpell().use();
        }
    }

//------------------------------------------------------------Start Turn, End Turn --------------------------------------------------
    public void startTurn(){
        for (Card spellCard: spellFieldCards){
            if (spellCard != null && ((SpellCard) spellCard).getSpellCardType() == SpellCardType.CONTINUOUS)
                ((SpellCard) spellCard).getSpell().use();
        }
        mana = ++maxMana;
        if (!deckCards.isEmpty()){    // else needed ??
            deckCards.get(0).transfer(handCards);
        }
    }

    public void endTurn(){
        awakeSleepingPlayedCards();
        resetHasAttackedCards();
    }

    //-----------------------------------toStrings---------------------------------------------------------------------------------
    public String deckToString(){
        String output = "";
//        for(int i=1; i<deckCapacity + 1; i++){
//            output += "Slot " + i + ": ";
//            int indexOfWantedCard = deckCardsSlotNumber.indexOf(i);
//            if(indexOfWantedCard == -1) {
//                output += "Empty\n";
//                continue;
//            }
//            output += deckCards.get(indexOfWantedCard) + "\n";
//        }
        for(int i=0; i<defaultDeckCards.size(); i++) {
            output += "Slot " + i + ": " + (defaultDeckCards.get(i) == null ? "Empty" : defaultDeckCards.get(i).getName()) + "\n";
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
