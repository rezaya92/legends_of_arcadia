package Controller;

import Model.*;
import Model.Card.*;
import Model.Spell.*;
import View.*;
import java.lang.reflect.Method;
import java.util.*;
import static Controller.PreProcess.*;
import static Model.Spell.GeneralizedSpell.allSpells;
import static Model.Stuff.*;

/**
 * Created by msi-pc on 4/27/2018.
 */
public class Main {
    static public Player human = new Player();
    private static String action;
    private static Method lastViewMethod;
    private static Scanner scanner = new Scanner(System.in);
    private static ArrayList<Card> allCards = new ArrayList<>();//TODO add from constructors
//    private static ArrayList<Card> allCards = new ArrayList<>();//TODO add from constructors
//    private static ArrayList<Item> allItems = new ArrayList<>();//TODO add from constructors
//    private static ArrayList<Amulet> allAmulets = new ArrayList<>();//TODO add from constructors

    public static void main(String[] args) throws Exception{
        int numberOfCards = 40;
        Card[] cards = new Card[numberOfCards];

        //Player winner = startGameAgainst(opponent);

        instantiateSpells();
        instantiateSpellCards();
        //System.out.println(allSpells);
        System.out.println(allStuff);
        //System.out.println(allStuff.get(0));
        //System.out.println(allStuff.get(1));
        afterMatch();
    }

    /*public void useContinuousSpellCards(){
    for (SpellCard continuousSpellCard : human.getSpellFieldCards()) {

    }
    */

    //executing with while improves performance a lot?
    private static void afterMatch() throws Exception{
        //TODO player saveHuman = human.clone(); (for hourGlass)
        View.afterMatch();
        action = scanner.nextLine();//todo nextLine?
        lastViewMethod = Class.forName("View.View").getMethod("afterMatch");
        helpHandler(lastViewMethod);
        switch (action){
            case "1":
                enterShop();
                break;
            case "2":
                editInventory();
                break;
            case "3":
                return;
                default:
                    View.invalidCommand();
        }
        afterMatch();
    }

    private static void enterShop() throws Exception{
        View.enterShop();
        action = scanner.nextLine();
        lastViewMethod = Class.forName("View.View").getMethod("enterShop");
        helpHandler(lastViewMethod);
        switch (action){
            case "1":
                cardShop();
                break;
            case "2":
                itemShop();
                break;
            case "3":
                amuletShop();
                break;
            case "4":
                return;
                default:
                    View.invalidCommand();
        }
        enterShop();
    }

    private static void cardShop() throws Exception{
        View.cardShop();
        action = scanner.nextLine();
        lastViewMethod = Class.forName("View.View").getMethod("cardShop");
        helpHandler(lastViewMethod);
        try {
            if (action.startsWith("Buy ") || action.startsWith("buy "))
                buyThingsProcessor(TypeOfStuffToBuyAndSell.CARD, action);
            else if(action.startsWith("Sell ") || action.startsWith("sell "))
                sellThingsProcessor(TypeOfStuffToBuyAndSell.CARD, action);
            else if(action.startsWith("Info ") || action.startsWith("info "))
                infoProcessor(action);
            else if(action.equals("4") || action.equals("Edit deck") || action.equals("edit deck"))
                editDeck();
            else if(action.equals("5") || action.equals("Exit") || action.equals("exit"))
                return;
            else
                throw new Exception();
        } catch (Exception e){
            View.invalidCommand();
        }
        cardShop();
    }

    private static void itemShop() throws Exception{
        View.itemShop();
        action = scanner.nextLine();
        lastViewMethod = Class.forName("View.View").getMethod("itemShop");
        helpHandler(lastViewMethod);
        try {
            if (action.startsWith("Buy ") || action.startsWith("buy "))
                buyThingsProcessor(TypeOfStuffToBuyAndSell.ITEM, action);
            else if(action.startsWith("Sell ") || action.startsWith("sell "))
                sellThingsProcessor(TypeOfStuffToBuyAndSell.ITEM, action);
            else if(action.startsWith("Info ") || action.startsWith("info "))
                infoProcessor(action);
            else if(action.equals("4") || action.equals("Exit") || action.equals("exit"))
                return;
            else
                throw new Exception();
        } catch (Exception e){
            View.invalidCommand();
        }
        itemShop();
    }

    private static void amuletShop() throws Exception{
        View.amuletShop();
        action = scanner.nextLine();
        lastViewMethod = Class.forName("View.View").getMethod("amuletShop");
        helpHandler(lastViewMethod);
        try {
            if (action.startsWith("Buy ") || action.startsWith("buy "))
                buyThingsProcessor(TypeOfStuffToBuyAndSell.AMULET, action);
            else if(action.startsWith("Sell ") || action.startsWith("sell "))
                sellThingsProcessor(TypeOfStuffToBuyAndSell.AMULET, action);
            else if(action.startsWith("Info ") || action.startsWith("info "))
                infoProcessor(action);
            else if(action.equals("4") || action.equals("Edit amulet") || action.equals("edit Amulet"))
                editAmulet();
            else if(action.equals("5") || action.equals("Exit") || action.equals("exit"))
                return;
            else
                throw new Exception();
        } catch (Exception e){
            View.invalidCommand();
        }
        amuletShop();
    }

    private static void editInventory() throws Exception{
        View.editInventory();
        action = scanner.nextLine();
        lastViewMethod = Class.forName("View.View").getMethod("editInventory");
        helpHandler(lastViewMethod);
        switch (action){
            case "1":
                stuffInventory(TypeOfStuffToBuyAndSell.CARD);
                break;
            case "2":
                stuffInventory(TypeOfStuffToBuyAndSell.ITEM);
                break;
            case "3":
                stuffInventory(TypeOfStuffToBuyAndSell.AMULET);
                break;
            case "4":
                editDeck();
            case "5":
                editAmulet();
            case "6":
                return;
                default:
                    View.invalidCommand();
        }
        editInventory();
    }

    private static void stuffInventory(TypeOfStuffToBuyAndSell typeOfStuffToBuyAndSell) throws Exception{
        View.stuffInventory(typeOfStuffToBuyAndSell);
        action = scanner.nextLine();
        lastViewMethod = Class.forName("View.View").getMethod("stuffInventory", TypeOfStuffToBuyAndSell.class);
        helpHandler(lastViewMethod, typeOfStuffToBuyAndSell);
        if(action.equals("2") || action.equals("Exit") || action.equals("exit"))
            return;
        try{
            if(!(action.startsWith("Info ") || action.startsWith("info")))
                throw new Exception();
            infoProcessor(action);
        } catch (Exception e){
            View.invalidCommand();
        }
        stuffInventory(typeOfStuffToBuyAndSell);
    }

    private static void editDeck() throws Exception{
        View.editDeck();
        action = scanner.nextLine();
        lastViewMethod = Class.forName("View.View").getMethod("editDeck");
        helpHandler(lastViewMethod);
        try {
            if (action.startsWith("Add ") || action.startsWith("add ")) {
                int splitIndex = 0;
                for (int i = action.length() - 1; i >= 0; i--) {
                    if (action.charAt(i) == ' ') {
                        splitIndex = i;
                        break;
                    }
                }
                String cardName = action.substring(4, splitIndex);
                int slotNumber = Integer.parseInt(action.substring(splitIndex + 1));
                if (human.addToDeck(cardName, slotNumber))
                    View.successfulAddToDeck(cardName, slotNumber);
                else
                    throw new Exception();
            }else if(action.startsWith("Remove ") || action.startsWith("remove ")){
                int slotNumber = Integer.parseInt(action.substring(7));
                String cardName = human.removeFromDeck(slotNumber);
                if(cardName == null)
                    throw new Exception();
                else
                    View.successfulRemoveFromDeck(cardName, slotNumber);
            }else if(action.startsWith("Info ") || action.startsWith("info ")){
                infoProcessor(action);
            }else if(action.equals("4") || action.equals("Exit") || action.equals("exit")){
                return;
            }else{
                throw new Exception();
            }
        }catch (Exception e){
            View.invalidCommand();
        }
        editDeck();
    }

    private static void editAmulet() throws Exception{
        View.editAmulet();
        action = scanner.nextLine();
        lastViewMethod = Class.forName("View.View").getMethod("editAmulet");
        helpHandler(lastViewMethod);
        try{
            if(action.startsWith("Equip ") || action.startsWith("equip ")){
                String amuletName = action.substring(6);
                if(human.equipAmulet(amuletName))
                    View.successfulAmuletEquip(amuletName);
                else
                    throw new Exception();
            }else if(action.equals("2") || action.matches("[rR]emove [aA]mulet")){
                String amuletName = human.removeEquippedAmulet();
                if(amuletName == null)
                    throw new Exception();
                View.successfulRemoveEquippedAmulet(amuletName);
            }else if(action.startsWith("Info ") || action.startsWith("info")){
                infoProcessor(action);
            }else if(action.equals("4") || action.matches("[Ee]xit")){
                return;
            }else{
                throw new Exception();
            }
        }catch(Exception e){
            View.invalidCommand();
        }
        editAmulet();
    }

    private static void buyThingsProcessor(TypeOfStuffToBuyAndSell typeOfStuffToBuyAndSell, String command) throws Exception{
        int numberToBuy = Integer.parseInt(action.split(" - ")[1]);
        if(numberToBuy <= 0)
            throw new Exception();//TODO check if executes correctly
        String thingName = action.split(" - ")[0].substring(4);
        int status = human.buyStuff(typeOfStuffToBuyAndSell, thingName, numberToBuy);
        switch (status){
            case -1:
                View.insufficientGil();
                break;
            case 0:
                View.notAvailableInShop();
                break;
            case 1:
                View.successfulBuy(thingName, numberToBuy);
        }
    }

    private static void sellThingsProcessor(TypeOfStuffToBuyAndSell typeOfStuffToBuyAndSell, String command) throws Exception{
        int numberToSell = Integer.parseInt(command.split(" - ")[1]);
        if(numberToSell <= 0)
            throw new Exception();//TODO check if executes correctly
        String stuffName = command.split(" - ")[0].substring(5);
        if(human.sellStuff(typeOfStuffToBuyAndSell, stuffName, numberToSell))
            View.successfulSell(stuffName, numberToSell);
        else
            View.notEnoughStuffs(typeOfStuffToBuyAndSell);
    }

    private static void infoProcessor(String command) throws Exception{
        String stuffName = command.substring(5);
        if(!printInfoStuff(stuffName)){
            throw new Exception();
        }
    }

    static boolean printInfoStuff(String stuffName){
        for(Stuff stuff : Stuff.allStuff){
            if(stuff.getName().equals(stuffName)){
                View.printStuffInfo(stuff);
                return true;
            }
        }
        return false;
    }

    private static void helpHandler(Method lastViewMethod) throws Exception{
        while(action.equals("Help") || action.equals("help") || action.equals("Again") || action.equals("again")){
            switch (action) {
                case "Help":
                case "help":
                    Class.forName("View.View").getMethod(lastViewMethod.getName() + "Help").invoke(null);
                    break;
                case "Again":
                case "again":
                    lastViewMethod.invoke(null);
            }
            action = scanner.nextLine();
        }
    }

    private static void helpHandler(Method lastViewMethod, TypeOfStuffToBuyAndSell typeOfStuffToBuyAndSell) throws Exception{
        while(action.equals("Help") || action.equals("help") || action.equals("Again") || action.equals("again")){
            switch (action) {
                case "Help":
                case "help":
                    Class.forName("View.View").getMethod(lastViewMethod.getName() + "Help", TypeOfStuffToBuyAndSell.class).invoke(null, typeOfStuffToBuyAndSell);
                    break;
                case "Again":
                case "again":
                    lastViewMethod.invoke(null);
            }
            action = scanner.nextLine();
        }
    }
}
