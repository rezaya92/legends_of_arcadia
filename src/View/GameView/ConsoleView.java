package View.GameView;

import Model.*;
import Model.Card.Card;
import Model.Card.MonsterCard;
import Model.Spell.GeneralizedSpell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextArea;

import java.util.ArrayList;

import static Controller.Main.human;

abstract public class ConsoleView {

    static private TextArea console;

    public static void setConsole(TextArea console) {
        ConsoleView.console = console;
    }

    public static void afterMatch(){
        console.appendText("1. Enter Shop: To enter shop and buy or sell cards and items" + "\n");
        console.appendText("2. Edit Inventory: To edit your amulet or deck" + "\n");
        console.appendText("3. Next: To go to deck and amulet customization" + "\n");
    }

    public static void afterMatchHelp(){
        afterMatch();
    }

    public static void enterShop(){
        console.appendText("Remaining Gil: " + human.getGil() + " Gil" + "\n");
        console.appendText("1. Card Shop" + "\n");
        console.appendText("2. Item Shop" + "\n");
        console.appendText("3. Amulet Shop" + "\n");
        console.appendText("4. Exit" + "\n");
    }

    public static void enterShopHelp(){
        enterShop();
    }

    public static void cardShop(){
        console.appendText("Remaining Gil: " + human.getGil() + " Gil" + "\n");
        console.appendText(" Shop List:" + "\n");
        console.appendText(human.getShop().cardToString() + " Card Inventory:\n" + human.inventoryToString() + "\n");
    }

    public static void cardShopHelp(){
        console.appendText("Remaining Gil: " + human.getGil() + " Gil" + "\n");
        console.appendText("1. Buy \"Card Name\" - #NumberToBuy: To buy a certain number of a card from shop" + "\n");
        console.appendText("2. Sell \"Card Name\" - #NumberToSell: To sell a certain number of a card from inventory" + "\n");
        console.appendText("3. Info \"Card Name\": To get more information about a card" + "\n");
        console.appendText("4. Edit Deck: To edit deck and remove and add cards to it" + "\n");
        console.appendText("5. Exit: To return to shop menu" + "\n");
    }

    public static void itemShop(){
        console.appendText("Remaining Gil: " + human.getGil() + " Gil" + "\n");
        console.appendText(" Shop List:" + "\n");
        console.appendText(human.getShop().itemToString() + " Item Inventory:\n" + human.itemToString() + "\n");
    }

    public static void itemShopHelp(){
        console.appendText("Remaining Gil: " + human.getGil() + " Gil" + "\n");
        console.appendText("1. Buy \"Item Name\" - #NumberToBuy: To buy an item from the shop" + "\n");
        console.appendText("2. Sell \"Item Name\" - #NumberToSell: To sell an item from your item inventory" + "\n");
        console.appendText("3. Info \"Item Name\": To view the full information of the item" + "\n");
        console.appendText("4. Exit: To return to shop menu" + "\n");
    }

    public static void amuletShop(){
        console.appendText("Remaining Gil: " + human.getGil() + " Gil" + "\n");
        console.appendText(" Shop List:" + "\n");
        console.appendText(human.getShop().amuletToString() + "Equipped Amulet: " + (human.getEquippedAmulet() == null ? "-" : human.getEquippedAmulet().getName()) + "\n");
        console.appendText("Amulet Inventory:\n" + human.amuletToString() + "\n");
    }

    public static void amuletShopHelp(){
        console.appendText("Remaining Gil: " + human.getGil() + " Gil" + "\n");
        console.appendText("1. Buy \"Amulet Name\" - #NumberToBuy: To buy a number of an amulet from the shop" + "\n");
        console.appendText("2. Sell \"Amulet Name\" - #NumberToSell: To sell a number of an amulet from amulet inventory" + "\n");
        console.appendText("3. Info \"Amulet Name\": To get full info on an amulet" + "\n");
        console.appendText("4. Edit Amulets: To equip or remove your hero's amulet" + "\n");
        console.appendText("5. Exit: To return to shop menu" + "\n");
    }

    public static void editInventory(){
        console.appendText("1. Card Inventory: To view your cards" + "\n");
        console.appendText("2. Item Inventory: To view your items" + "\n");
        console.appendText("3. Amulet Inventory: To view your amulets" + "\n");
        console.appendText("4. Edit Deck: To edit your card deck" + "\n");
        console.appendText("5. Edit Amulets: To equip or remove your amulets" + "\n");
        console.appendText("6. Exit: to return to previous menu" + "\n");
    }

    public static void editInventoryHelp(){
        editInventory();
    }

    public static void stuffInventory(TypeOfStuffToBuyAndSell typeOfStuffToBuyAndSell){
        switch (typeOfStuffToBuyAndSell){
            case CARD:
                console.appendText("Card Inventory:" + "\n");
                console.appendText(human.inventoryToString() + "\n");
                break;
            case ITEM:
                console.appendText("Item Inventory:" + "\n");
                console.appendText(human.itemToString() + "\n");
                break;
                default:
                    console.appendText("Amulet Inventory:" + "\n");
                    console.appendText(human.amuletToString() + "\n");
        }
    }

    public static void stuffInventoryHelp(TypeOfStuffToBuyAndSell typeOfStuffToBuyAndSell){
        console.appendText("1. Info \"" + typeOfStuffToBuyAndSell.name() + " name\": to get more information about a specific " + typeOfStuffToBuyAndSell.name() + "\n");
        console.appendText("2. Exit: to return to previous section" + "\n");
    }

    public static void editDeck(){
        console.appendText("Deck:" + "\n");
        console.appendText(human.deckToString() + "\n");
        console.appendText("Other Cards:" + "\n");
        console.appendText(human.inventoryToString() + "\n");
    }

    public static void editDeckHelp(boolean nextIsBattle){
        console.appendText("1. Add \"Card Name\" #CardSlotNum: To add cards to your deck" + "\n");
        console.appendText("2. Remove #CardSlotNum: To remove cards from your deck" + "\n");
        console.appendText("3. Info \"Card Name\": To get more information about a specific card" + "\n");
        if(nextIsBattle)
            console.appendText("4. Next: To go to battlefield" + "\n");
        else
            console.appendText("4. Exit: To return to the previous section" + "\n");
    }

    public static void editAmulet(){
        console.appendText("Amulets:" + "\n");
        console.appendText(human.amuletToString() + "\n");
        if(human.getEquippedAmulet() != null)
            console.appendText("Player is equipped with " + human.getEquippedAmulet().getName() + "\n");
    }

    public static void editAmuletHelp(){
        console.appendText("1. Equip \"Amulet name\": To equip the player with an amulet" + "\n");
        console.appendText("2. Remove Amulet: To remove the equipped amulet of the player (if the player is equipped with one)" + "\n");
        console.appendText("3. Info \"Amulet Name\": To get more information about a specific amulet" + "\n");
        console.appendText("4. Exit: to return to the previous section" + "\n");
    }

    public static void insufficientGil(){
        console.appendText("Not enough Gil!" + "\n");
    }

    public static void notAvailableInShop(){
        console.appendText("Wanted stuff not available in shop." + "\n");
    }

    public static void successfulBuy(String boughtThingName, int numberToBuy){
        console.appendText("Successfully bought " + numberToBuy + " of " + boughtThingName + "!" + "\n");
    }

    public static void notEnoughStuffs(TypeOfStuffToBuyAndSell typeOfStuffToBuyAndSell){
        console.appendText("Not enough " + typeOfStuffToBuyAndSell.name().toLowerCase() + "s!" + "\n");
    }

    public static void notEnoughCardsToInitiateBattle(){
        console.appendText("Not enough cards to initiate a battle. please add more cards to your deck." + "\n");
    }

    public static void successfulSell(String soldThingName, int numberToSell){
        console.appendText("Successfully sold " + numberToSell + " of " + soldThingName + "!" + "\n");
    }

    public static void printStuffInfo(Stuff stuff){
        console.appendText(stuff + "\n");
    }

    public static void invalidCommand(){
        console.appendText("Invalid command. Type Help for more information." + "\n");
    }

    public static void showPlayerMana(Player player){   // mana and maxMana
        console.appendText(player.getMana() + " - " + player.getMaxMana() + "\n");
    }

    public static void emptyDeck(){
        console.appendText("Deck is empty!" + "\n");
    }

    public static void successfulAddToDeck(String cardName, int slotNumber){
        console.appendText(cardName + " was add to slot " + slotNumber + "\n");
    }

    public static void successfulRemoveFromDeck(String cardName, int slotNumber){
        console.appendText(cardName + " was removed from slot " + slotNumber + "\n");
    }

    public static void successfulAmuletEquip(String amuletName){
        console.appendText(amuletName + " was equipped on the player." + "\n");
    }

    public static void successfulRemoveEquippedAmulet(String amuletName){
        console.appendText(amuletName + " was removed!" + "\n");
    }

    public static void slotIsFull(Player player){
        if (player == human)
            console.appendText("\nSlot is full!\n" + "\n");
    }

    public static void insufficientMana(Player player){
        if (player == human)
            console.appendText("\nNot enough MP!\n" + "\n");
    }

    public static void invalidCardName(){
        console.appendText("Card does not exist!" + "\n");
    }

    public static void slotIsEmpty(Player player){
        if (player == human)
            console.appendText("Slot is empty!" + "\n");
    }

    public static void clashWith(String attackerCard, String attackedCard){
        console.appendText(attackerCard + " clashed with " + attackedCard + "\n");
    }

    public static void alreadyAttacked(Player player){
        if (player == human)
            console.appendText("The card has already attacked!" + "\n");
    }

    public static void cardIsSleep(Player player){
        if (player == human)
            console.appendText("The selected card is sleep!" + "\n");
    }

    public static void defenderEnemyPresent(Player player){
        if (player == human)
            console.appendText("There's a defender enemy present!" + "\n");
    }

//--------------------------------------------------------------------------------- usingMonsterCard
    public static void usingMonsterCardInfo(MonsterCard monsterCard){
        console.appendText("Using " + monsterCard.getName() + ":" + "\n");
        console.appendText("HP: " + monsterCard.getHp() + "  AP: " + monsterCard.getAp() + "\n");
        console.appendText("Is Sleeping: " + !monsterCard.isAwake() + "\n");
        console.appendText("Can Attack: " + (!monsterCard.hasAttacked() && monsterCard.isAwake()) + "\n");
        if (monsterCard.hasGotSpell()){
            console.appendText("Can Cast: " + !monsterCard.hasUsedSpell() + "\n");
        }
    }

    public static void usingMonsterCardHelp(boolean hasSpell){
        console.appendText("1. Attack #EnemyMonsterSlot / Player: To attack the card on that slot of enemy MonsterField or the enemy player" + "\n");
        if (!hasSpell){
            console.appendText("2. Info: To get full information on card" + "\n");
            console.appendText("3. Exit: To go back to Play Menu" + "\n");
        }
        else{
            console.appendText("2. Cast Spell: To cast the card's spell " + "\n");
            console.appendText("3. Info: To get full information on card" + "\n");
            console.appendText("4. Exit: To go back to Play Menu" + "\n");
        }
    }

//--------------------------------------------------------------------------------- view hand,graveyard,spellField,monsterField
    public static void viewHand(Player player){
        console.appendText("Your Hand:" + "\n");
        int i = 1;
        for (Card card: player.getHandCards()){
            console.appendText(i + ". " + card.getName() + " - " + card.getManaCost() + "\n");
            i++;
        }
        if (i == 1)
            console.appendText("Hand empty!" + "\n");
    }

    public static void viewGraveyard(Player player){
        console.appendText("Your Graveyard:" + "\n");
        int i = 1;
        for (Card card: player.getGraveyardCards()){
            console.appendText(i + ". " + card.getName() + "\n");
            i++;
        }
        if (i == 1)
            console.appendText("Graveyard empty!" + "\n");

        console.appendText("\n");
        console.appendText("Enemy's Graveyard:" + "\n");
        int j = 1;
        for (Card card: player.getOpponent().getGraveyardCards()){
            console.appendText(j + ". " + card.getName() + "\n");
            j++;
        }
        if (j == 1)
            console.appendText("Graveyard empty!" + "\n");
    }

    public static void viewSpellField(Player player){
        console.appendText("Your SpellField:" + "\n");
        int i =1;
        for (Card card: player.getSpellFieldCards()){
            console.appendText("Slot" + i + ": ");
            if (card == null)
                console.appendText("Empty" + "\n");
            else
                console.appendText(card.getName() + "\n");
            i++;
        }

        console.appendText("\n");
        console.appendText("Enemy's SpellField:" + "\n");
        int j =1;
        for (Card card: player.getOpponent().getSpellFieldCards()){
            console.appendText("Slot" + j + ": ");
            if (card == null)
                console.appendText("Empty" + "\n");
            else
                console.appendText(card.getName() + "\n");
            i++;
        }
    }



    public static void viewMonsterField(Player player){
        console.appendText("Your MonsterField:" + "\n");
        int i = 1;
        for (Card card: player.getMonsterFieldCards()){
            console.appendText("Slot" + i + ": ");
            if (card == null)
                console.appendText("Empty");
            else{
                MonsterCard monsterCard = (MonsterCard)card;
                console.appendText(card.getName() + "  HP:" + monsterCard.getHp() + " AP:" + monsterCard.getAp());
                if (monsterCard.isDefender())
                    console.appendText(" Defensive");
                if (monsterCard.isNimble())
                    console.appendText(" Nimble");
                if (monsterCard.hasGotSpell()){
                    if (monsterCard.hasUsedSpell())
                        console.appendText(" UsedSpell");
                    else
                        console.appendText(" HasSpell");
                }
            }
            console.appendText("\n");
            i++;
        }

        console.appendText("\n");
        console.appendText("Enemy's MonsterField:" + "\n");
        int j = 1;
        for (Card card: player.getOpponent().getMonsterFieldCards()){
            console.appendText("Slot" + j + ": ");
            if (card == null)
                console.appendText("Empty");
            else{
                MonsterCard monsterCard = (MonsterCard)card;
                console.appendText(card.getName() + "  HP:" + monsterCard.getHp() + " AP:" + monsterCard.getAp());
                if (monsterCard.isDefender())
                    console.appendText(" Defensive");
                if (monsterCard.isNimble())
                    console.appendText(" Nimble");
                if (monsterCard.hasGotSpell()){
                    if (monsterCard.hasUsedSpell())
                        console.appendText(" UsedSpell");
                    else
                        console.appendText(" HasSpell" + "\n");
                }
            }
            console.appendText("\n");
            j++;
        }
    }

    public static void viewHeroes(Player player){
        console.appendText("Your Hero: " + player.getPlayerHero().getHp() + "\n");
        console.appendText("Opponent Hero: " + player.getOpponent().getPlayerHero().getHp() + "\n");
    }


    //------------------------------------------------------------------------------------------Spell
    public static void viewSpellEffectableCards(ArrayList<SpellCastable> effectableCards){
        int index =  1;
        console.appendText("List‬‬ ‫‪of‬‬ ‫‪Targets‬‬ ‫‪:" + "\n");
        boolean friendlinessChanged = false;
        ArrayList<String> cardPlaceNames = new ArrayList<>();
        ArrayList<Card> currentPlace = new ArrayList<>();
        for (SpellCastable spellCastable: effectableCards){
            if (spellCastable instanceof Card && currentPlace != ((Card)spellCastable).getCardPlace()){
                currentPlace = ((Card)spellCastable).getCardPlace();
                if (cardPlaceNames.contains(((Card)spellCastable).getCardPlacebyName()))
                    friendlinessChanged = true;
                else
                    cardPlaceNames.add(((Card)spellCastable).getCardPlacebyName());
                if (friendlinessChanged)
                    console.appendText("Enemy " + ((Card)spellCastable).getCardPlacebyName() + ":" + "\n");
                else
                    console.appendText(((Card)spellCastable).getCardPlacebyName() + ":" + "\n");
            }
            else if (spellCastable instanceof PlayerHero){
                currentPlace = new ArrayList<>();
                if (cardPlaceNames.contains("Player"))
                    friendlinessChanged = true;
                else
                    cardPlaceNames.add("Player");
                if (friendlinessChanged)
                    console.appendText("Enemy Player:" + "\n");
                else
                    console.appendText("Player:" + "\n");
            }
            console.appendText(index + "." + spellCastable.getName() + "\n");
            index++;
        }
    }

    public static void spellCastHelp(){
        console.appendText("1. Target #TargetNum To cast the spell on the specified target" + "\n");
        console.appendText("2. Exit: To skip spell casting" + "\n");
    }

    public static void spellTargeted(SpellCastable target){
        console.appendText(target.getName() + " has been targeted." + "\n");
    }

    public static void noTargetChosen(){
        console.appendText("No Target was chosen. Such a waste ..." + "\n");
    }

    public static void noValidTarget(Player player){
        if (player == human){
            console.appendText("There's no valid target. Spell didn't cast!" + "\n");
        }
    }

    public static void spellCasted(String caster, GeneralizedSpell spell){
        console.appendText(caster + " has cast a spell:" + "\n");
        console.appendText(spell.getName() + ": " + spell.getDetail() + "\n");
    }

    public static void noEffectableCard(){
        console.appendText("OOPS! There was no one to cast the spell on." + "\n");
    }


//------------------------------------------------------------------------------------------
    public static void battleStarted(Player opponent){
        console.appendText("Battle against " + opponent.getName() + " started!" + "\n");
    }

    public static void announceBattleStarter(String name){
        console.appendText(name + " starts the battle" + "\n");
    }

    public  static void turnAnnouncer(int turnNumber, String starter){
        console.appendText("turn " + turnNumber + " started" + "\n");
        console.appendText(starter + "'s turn" + "\n");
    }
    public static void battleHelp(){
        console.appendText("0. Use Item: To use an item of your items" + "\n");
        console.appendText("1. Use #SlotNum: To use a specific card which is on the Monster Field" + "\n");
        console.appendText("2. Set #HandIndex to #SlotNum: To set a card which is on the hand, in the field" + "\n");
        console.appendText("3. ConsoleView Hand: To view the cards in your hand" + "\n");
        console.appendText("4. ConsoleView Graveyard: To view the cards in your graveyard" + "\n");
        console.appendText("5. ConsoleView SpellField: To view the cards in both ’players spell fields" + "\n");
        console.appendText("6. ConsoleView MonsterField: To view the cards in both ’players monster fields" + "\n");
        console.appendText("7. Info \"Card Name\": To view full information about a card" + "\n");
        console.appendText("8. Done: To end your turn" + "\n");
    }

    public static void battleOver(Player loser){
        console.appendText("Battle Ended!" + "\n");
        if (loser == human){
            console.appendText("You Lost!" + "\n");
        }
        else
            console.appendText("You Won!" + "\n");
    }

    public static void mysticHourGlassUsed(){
        console.appendText("Mystic Hourglass Used!" + "\n");
    }

    public static void gameOver(Player player){
        if (player == human) {
            console.appendText("You are out of Mystic Hourglass" + "\n");
            console.appendText("Game Over!" + "\n");
        }
    }

    public static void wholeWinner(){
        console.appendText("You won all the games!" + "\n");
        console.appendText("Game over!" + "\n");
    }

//--------------------------------------Items menu-------------------------------------------------------
    public static void availableItems(Player player){
        console.appendText("Available Items:" + "\n");
        int i = 0;
        for (Item item: player.getItems()){
            console.appendText((++i) + ". " + item.getName() + "\n");
        }
        if (i == 0)
            console.appendText("There's no available item!" + "\n");
    }

    public static void itemHelp(){
        console.appendText("1. Use \"Item Name\": To cast the spell of the item" + "\n");
        console.appendText("2. Info \"Item Name\": To view full information about the item" + "\n");
        console.appendText("2. Exit" + "\n");
    }

    public static void itemDontExist(){
        console.appendText("No item selected!" + "\n");
    }

//-------------------------------------------------------exception-----------------------------------
    public static void indexOutOfBound(){
        console.appendText("Selected slot is not in range!" + "\n");
    }

//------------------------------------------------------Played cards-------------------------------------
    public static void cardDrawn(String name) {
        console.appendText("You drew " + name + "\n");
    }

    public static void playedInMonsterField(String name){
        console.appendText(name + "  played in MonsterField" + "\n");
    }

    public static void playedInSpellField(String name){
        console.appendText(name + "  played in SpellField" + "\n");
    }

    public static void spellCardCasted(String name){
        console.appendText(name + "  casted and went to Graveyard" + "\n");
    }
}
