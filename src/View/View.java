package View;

import Controller.*;
import Model.Card.Card;
import Model.Player;
import Model.Stuff;

import static Controller.Main.human;

abstract public class View {
    public static void afterMatch(){
        System.out.println("1. Enter Shop: To enter shop and buy or sell cards and items");
        System.out.println("2. Edit Inventory: To edit your amulet or deck");
        System.out.println("3. Next: To go to deck and amulet customization");
    }

    public static void afterMatchHelp(){
        afterMatch();
    }

    public static void enterShop(){
        System.out.println("Remaining Gil: " + human.getGil() + " Gil");
        System.out.println("1. Card Shop");
        System.out.println("2. Item Shop");
        System.out.println("3. Amulet Shop");
        System.out.println("4. Exit");
    }

    public static void enterShopHelp(){
        enterShop();
    }

    public static void cardShop(){
        System.out.println("Remaining Gil: " + human.getGil() + " Gil");
        System.out.println(" Shop List:");
        System.out.println(human.getShop().cardToString() + " Card Inventory:\n" + human.inventoryToString());
    }

    public static void cardShopHelp(){
        System.out.println("Remaining Gil: " + human.getGil() + " Gil");
        System.out.println("1. Buy \"Card Name\" - #NumberToBuy: To buy a certain number of a card from shop");
        System.out.println("2. Sell \"Card Name\" - #NumberToSell: To sell a certain number of a card from inventory");
        System.out.println("3. Info \"Card Name\": To get more information about a card");
        System.out.println("4. Edit Deck: To edit deck and remove and add cards to it");
        System.out.println("5. Exit: To return to shop menu");
    }

    public static void itemShop(){
        System.out.println("Remaining Gil: " + human.getGil() + " Gil");
        System.out.println(" Shop List:");
        System.out.println(human.getShop().itemToString() + " Item Inventory:\n" + human.itemToString());
    }

    public static void itemShopHelp(){
        System.out.println("Remaining Gil: " + human.getGil() + " Gil");
        System.out.println("1. Buy \"Item Name\" - #NumberToBuy: To buy an item from the shop");
        System.out.println("2. Sell \"Item Name\" - #NumberToSell: To sell an item from your item inventory");
        System.out.println("3. Info \"Item Name\": To view the full information of the item");
        System.out.println("4. Exit: To return to shop menu");
    }

    public static void amuletShop(){
        System.out.println("Remaining Gil: " + human.getGil() + " Gil");
        System.out.println(" Shop List:");
        System.out.println(human.getShop().amuletToString() + "Equipped Amulet: " + (human.getEquippedAmulet() == null ? "-" : human.getEquippedAmulet().getName()));
        System.out.println("Amulet Inventory:\n" + human.amuletToString());
    }

    public static void amuletShopHelp(){
        System.out.println("Remaining Gil: " + human.getGil() + " Gil");
        System.out.println("1. Buy \"Amulet Name\" - #NumberToBuy: To buy a number of an amulet from the shop");
        System.out.println("2. Sell \"Amulet Name\" - #NumberToSell: To sell a number of an amulet from amulet inventory");
        System.out.println("3. Info \"Amulet Name\": To get full info on an amulet");
        System.out.println("4. Edit Amulets: To equip or remove your hero's amulet");
        System.out.println("5. Exit: To return to shop menu");
    }

    public static void editDeck(){
        System.out.println("Deck:");
        System.out.println(human.deckToString());
        System.out.println("Other Cards:");
        System.out.println(human.inventoryToString());
    }

    public static void editDeckHelp(){
        System.out.println("1. Add \"Card Name\" #CardSlotNum: To add cards to your deck");
        System.out.println("2. Remove \"Card Name\" #CardSlotNum: To remove cards from your deck");
        System.out.println("3. Info \"Card Name\": To get more information about a specific card");
        System.out.println("4. Exit: To return to the previous section");
    }

    public static void insufficientGil(){
        System.out.println("Not enough Gil!");
    }

    public static void notAvailableInShop(){
        System.out.println("Wanted stuff not available in shop.");
    }

    public static void successfulBuy(String boughtThingName, int numberToBuy){
        System.out.println("Successfully bought " + numberToBuy + " of " + boughtThingName + "!");
    }

    public static void notEnoughCards(){
        System.out.println("Not enough cards!");
    }

    public static void successfulSell(String soldThingName, int numberToSell){
        System.out.println("Successfully sold " + numberToSell + " of " + soldThingName + "!");
    }

    public static void printStuffInfo(Stuff stuff){
        System.out.println(stuff);
    }

    public static void invalidCommand(){
        System.out.println("Invalid command. Type Help for more information.");
    }

    public static void showPlayerMana(Player player){   // mana and maxMana
        System.out.println(player.getMana() + " - " + player.getMaxMana());
    }

    public static void emptyDeck(){
        System.out.println("Deck is empty!");
    }

    public static void battleHelp(){
        System.out.println("\n1. Use #SlotNum: To use a specific card which is on the Monster Field");
        System.out.println("2. Set #HandIndex to #SlotNum: To set a card which is on the hand, in the field");
        System.out.println("3. View Hand: To view the cards in your hand");
        System.out.println("4. View Graveyard: To view the cards in your graveyard");
        System.out.println("5. View SpellField: To view the cards in both ’players spell fields");
        System.out.println("6. View MonsterField: To view the cards in both ’players monster fields");
        System.out.println("7. Info \"Card Name\": To view full information about a card");
        System.out.println("8. Done: To end your turn\n");
    }
}
