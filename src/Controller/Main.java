package Controller;

import Model.*;
import Model.Card.*;
import View.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.lang.reflect.Method;
import java.util.*;
import static Controller.PreProcess.*;

/**
 * Created by msi-pc on 4/27/2018.
 */
public class Main {
    public static Player human = new Player("human", 10000);
    public static Player goblinChieftain = new Player("Goblin Chieftain", 10000);
    public static Player ogreWarlord = new Player("Ogre Warlord", 10000);
    public static Player vampireLord = new Player("Vampire Lord", 10000);
    public static Player lucifer = new Player("Lucifer", 10000);
    private static String action;
    private static Method lastViewMethod;
    private static Scanner scanner = new Scanner(System.in);
    private static ArrayList<Player> opponents = new ArrayList<>();
    private static int mysticHourGlass = 3;  //change place?
    private static Stage primaryStage = LegendsOfArcadia.getPrimaryStage();//TODO correct?

    public static void startOfOperations() throws Exception{
//        Player humanBeforeCustomize;
//        Player humanBeforeMatch;
        int opponentNumber = 0;

        preProcess();

        opponents.add(goblinChieftain);
        opponents.add(ogreWarlord);
        opponents.add(vampireLord);
        opponents.add(lucifer);

        //Stuff stuff = (Stuff)allStuff.get(0).clone();
        //((Card)allStuff.get(0)).setName("23");//.setName("23");
        //System.out.println(allSpells);
        //System.out.println(allStuff.get(0));
//        for(Stuff stuff : allStuff)
//            if(stuff instanceof NormalCard)
//                System.out.println(stuff);
        //System.out.println(stuff);
        //System.out.println(allStuff);
        //System.out.println(allStuff.get(1));
        //System.out.println(human.getShop().cardToString());
        //System.out.println(human.inventoryToString());
        //System.out.println(human.deckToString());////
        //System.out.println(lucifer.getEquippedAmulet());

        while (opponentNumber < opponents.size()){
            Player opponent = opponents.get(opponentNumber);
            //humanBeforeCustomize = (Player)human.clone();
            human.restore();
            opponent.restore();
            ArrayList<Card> humanDefaultDeckCardBeforeCustomization = new ArrayList<>(human.getDefaultDeckCards());
            ArrayList<Card> humanDeckCardBeforeCustomization = human.getDeckCards();
            ArrayList<Item> humanItemsBeforeCustomization = new ArrayList<>(human.getItems());
            ArrayList<Card> opponentDefaultDeckCardBeforeCustomization = new ArrayList<>(opponent.getDefaultDeckCards());
            ArrayList<Card> opponentDeckCardBeforeCustomization = opponent.getDeckCards();
            ArrayList<Item> opponentItemsBeforeCustomization = new ArrayList<>(opponent.getItems());
            afterMatch();
            //humanBeforeMatch = (Player)human.clone();
            ArrayList<Card> humanDefaultDeckCardBeforeMatch = new ArrayList<>(human.getDefaultDeckCards());
            ArrayList<Card> humanDeckCardBeforeMatch = human.getDeckCards();
            Player winner = Battle.startGameAgainst(opponent);
            if (winner == human){
                opponentNumber++;
                //ArrayList<Item> humanItemsAfterMatch = human.getItems();
                //human = humanBeforeMatch;
                human.setDefaultDeckCards(humanDefaultDeckCardBeforeMatch);
                human.setDeckCards(humanDeckCardBeforeMatch);
                //human.setItems(humanItemsAfterMatch);
            }
            else{
                if (mysticHourGlass > 0){
                    //System.out.println(humanBeforeCustomize.getDeckCards().size());
                    //human = humanBeforeCustomize;
                    human.setDefaultDeckCards(humanDefaultDeckCardBeforeCustomization);
                    human.setDeckCards(humanDeckCardBeforeCustomization);
                    human.setItems(humanItemsBeforeCustomization);
                    opponent.setDefaultDeckCards(opponentDefaultDeckCardBeforeCustomization);
                    opponent.setDeckCards(opponentDeckCardBeforeCustomization);
                    opponent.setItems(opponentItemsBeforeCustomization);
                    mysticHourGlass--;
                    ConsoleView.mysticHourGlassUsed();
                }
                else{
                    ConsoleView.gameOver(human);
                    break;
                }
            }
        }

        if (opponentNumber == opponents.size()){
            ConsoleView.wholeWinner();
        }
    }

    /*public void useContinuousSpellCards(){
    for (SpellCard continuousSpellCard : human.getSpellFieldCards()) {

    }
    */

    //executing with while improves performance a lot?
    static void afterMatch() throws Exception{
        /*ConsoleView.afterMatch();
        action = scanner.nextLine();
        lastViewMethod = Class.forName("View.ConsoleView").getMethod("afterMatch");
        helpHandler(lastViewMethod);
        switch (action){
            case "1":
                enterShop();
                break;
            case "2":
                editInventory();
                break;
            case "3":
                editDeck(true);
                return;
                default:
                    ConsoleView.invalidCommand();
        }
        afterMatch();*/
        enterShop();//TODO CHANGE
    }

    private static void enterShop() throws Exception{
        /*ConsoleView.enterShop();
        action = scanner.nextLine();
        lastViewMethod = Class.forName("View.ConsoleView").getMethod("enterShop");
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
                    ConsoleView.invalidCommand();
        }
        enterShop();*/
        MenuView.showShop();
        MenuView.getCardShopButton().setOnMouseClicked(event -> {
            try {
                cardShop();
            }catch (Exception e){
                e.printStackTrace();
            }
        });
    }

    private static void cardShop() throws Exception{
        /*ConsoleView.cardShop();
        action = scanner.nextLine();
        lastViewMethod = Class.forName("View.ConsoleView").getMethod("cardShop");
        helpHandler(lastViewMethod);
        try {
            if (action.startsWith("Buy ") || action.startsWith("buy "))
                buyThingsProcessor(TypeOfStuffToBuyAndSell.CARD, action);
            else if(action.startsWith("Sell ") || action.startsWith("sell "))
                sellThingsProcessor(TypeOfStuffToBuyAndSell.CARD, action);
            else if(action.startsWith("Info ") || action.startsWith("info "))
                infoProcessor(action);
            else if(action.equals("4") || action.equalsIgnoreCase("Edit deck"))
                editDeck(false);
            else if(action.equals("5") || action.equalsIgnoreCase("Exit"))
                return;
            else
                throw new Exception();
        } catch (Exception e){
            ConsoleView.invalidCommand();
        }
        cardShop();*/
        ArrayList<Pair<Card, Integer>> uniqueCardsWithNumber = getUniqueWithNumber(human.getShop().getCards());
        //uniqueCardsWithNumber.get(0).getKey();
        MenuView.showCardShop(human.getShop().getCards(), human.getDeckCards());
    }

    private static void itemShop() throws Exception{
        ConsoleView.itemShop();
        action = scanner.nextLine();
        lastViewMethod = Class.forName("View.ConsoleView").getMethod("itemShop");
        helpHandler(lastViewMethod);
        try {
            if (action.startsWith("Buy ") || action.startsWith("buy "))
                buyThingsProcessor(TypeOfStuffToBuyAndSell.ITEM, action);
            else if(action.startsWith("Sell ") || action.startsWith("sell "))
                sellThingsProcessor(TypeOfStuffToBuyAndSell.ITEM, action);
            else if(action.startsWith("Info ") || action.startsWith("info "))
                infoProcessor(action);
            else if(action.equals("4") || action.equalsIgnoreCase("Exit"))
                return;
            else
                throw new Exception();
        } catch (Exception e){
            ConsoleView.invalidCommand();
        }
        itemShop();
    }

    private static void amuletShop() throws Exception{
        ConsoleView.amuletShop();
        action = scanner.nextLine();
        lastViewMethod = Class.forName("View.ConsoleView").getMethod("amuletShop");
        helpHandler(lastViewMethod);
        try {
            if (action.startsWith("Buy ") || action.startsWith("buy "))
                buyThingsProcessor(TypeOfStuffToBuyAndSell.AMULET, action);
            else if(action.startsWith("Sell ") || action.startsWith("sell "))
                sellThingsProcessor(TypeOfStuffToBuyAndSell.AMULET, action);
            else if(action.startsWith("Info ") || action.startsWith("info "))
                infoProcessor(action);
            else if(action.equals("4") || action.equalsIgnoreCase("Edit Amulets"))
                editAmulet();
            else if(action.equals("5") || action.equalsIgnoreCase("Exit"))
                return;
            else
                throw new Exception();
        } catch (Exception e){
            ConsoleView.invalidCommand();
        }
        amuletShop();
    }

    private static void editInventory() throws Exception{
        ConsoleView.editInventory();
        action = scanner.nextLine();
        lastViewMethod = Class.forName("View.ConsoleView").getMethod("editInventory");
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
                editDeck(false);
                break;
            case "5":
                editAmulet();
                break;
            case "6":
                return;
                default:
                    ConsoleView.invalidCommand();
        }
        editInventory();
    }

    private static void stuffInventory(TypeOfStuffToBuyAndSell typeOfStuffToBuyAndSell) throws Exception{
        ConsoleView.stuffInventory(typeOfStuffToBuyAndSell);
        action = scanner.nextLine();
        lastViewMethod = Class.forName("View.ConsoleView").getMethod("stuffInventory", TypeOfStuffToBuyAndSell.class);
        helpHandler(lastViewMethod, typeOfStuffToBuyAndSell);
        if(action.equals("2") || action.equalsIgnoreCase("Exit"))
            return;
        try{
            if(!(action.startsWith("Info ") || action.startsWith("info")))
                throw new Exception();
            infoProcessor(action);
        } catch (Exception e){
            ConsoleView.invalidCommand();
        }
        stuffInventory(typeOfStuffToBuyAndSell);
    }

    private static void editDeck(boolean nextIsBattle) throws Exception{
        ConsoleView.editDeck();
        action = scanner.nextLine();
        lastViewMethod = Class.forName("View.ConsoleView").getMethod("editDeck");
        helpHandler(lastViewMethod, nextIsBattle);
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
                    ConsoleView.successfulAddToDeck(cardName, slotNumber);
                else
                    throw new Exception();
            }else if(action.startsWith("Remove ") || action.startsWith("remove ")){
                int slotNumber = Integer.parseInt(action.substring(7));
                String cardName = human.removeFromDeck(slotNumber);
                if(cardName == null)
                    throw new Exception();
                else
                    ConsoleView.successfulRemoveFromDeck(cardName, slotNumber);
            }else if(action.startsWith("Info ") || action.startsWith("info ")){
                infoProcessor(action);
            }else if(action.equals("4") || action.equalsIgnoreCase(nextIsBattle ? "Next" : "Exit")){
                if(nextIsBattle && human.getDeckCards().size() < 25)
                    ConsoleView.notEnoughCardsToInitiateBattle();
                else
                    return;
            }else{
                throw new Exception();
            }
        }catch (Exception e){
            ConsoleView.invalidCommand();
        }
        editDeck(nextIsBattle);
    }

    private static void editAmulet() throws Exception{
        ConsoleView.editAmulet();
        action = scanner.nextLine();
        lastViewMethod = Class.forName("View.ConsoleView").getMethod("editAmulet");
        helpHandler(lastViewMethod);
        try{
            if(action.startsWith("Equip ") || action.startsWith("equip ")){
                String amuletName = action.substring(6);
                if(human.equipAmulet(amuletName))
                    ConsoleView.successfulAmuletEquip(amuletName);
                else
                    throw new Exception();
            }else if(action.equals("2") || action.equalsIgnoreCase("Remove Amulet")){
                String amuletName = human.removeEquippedAmulet();
                if(amuletName == null)
                    throw new Exception();
                ConsoleView.successfulRemoveEquippedAmulet(amuletName);
            }else if(action.startsWith("Info ") || action.startsWith("info ")){
                infoProcessor(action);
            }else if(action.equals("4") || action.equalsIgnoreCase("Exit")){
                return;
            }else{
                throw new Exception();
            }
        }catch(Exception e){
            ConsoleView.invalidCommand();
        }
        editAmulet();
    }

    private static void buyThingsProcessor(TypeOfStuffToBuyAndSell typeOfStuffToBuyAndSell, String command) throws Exception{
        int numberToBuy = Integer.parseInt(command.split(" - ")[1]);
        if(numberToBuy <= 0)
            throw new Exception();
        String thingName = command.split(" - ")[0].substring(4);
        int status = human.buyStuff(typeOfStuffToBuyAndSell, thingName, numberToBuy);
        switch (status){
            case -1:
                ConsoleView.insufficientGil();
                break;
            case 0:
                ConsoleView.notAvailableInShop();
                break;
            case 1:
                ConsoleView.successfulBuy(thingName, numberToBuy);
        }
    }

    private static void sellThingsProcessor(TypeOfStuffToBuyAndSell typeOfStuffToBuyAndSell, String command) throws Exception{
        int numberToSell = Integer.parseInt(command.split(" - ")[1]);
        if(numberToSell <= 0)
            throw new Exception();
        String stuffName = command.split(" - ")[0].substring(5);
        if(human.sellStuff(typeOfStuffToBuyAndSell, stuffName, numberToSell))
            ConsoleView.successfulSell(stuffName, numberToSell);
        else
            ConsoleView.notEnoughStuffs(typeOfStuffToBuyAndSell);
    }

    private static void infoProcessor(String command) throws Exception{
        String stuffName = command.substring(5);
        if(!printInfoStuff(stuffName)){
            throw new Exception();
        }
    }

    static boolean printInfoStuff(String stuffName){
        for(Stuff stuff : Stuff.allStuff){
            if(stuff.getName().equalsIgnoreCase(stuffName)){
                ConsoleView.printStuffInfo(stuff);
                return true;
            }
        }
        return false;
    }

    private static void helpHandler(Method lastViewMethod) throws Exception{
        while(true){
            if(action.equalsIgnoreCase("Help"))
                Class.forName("View.ConsoleView").getMethod(lastViewMethod.getName() + "Help").invoke(null);
            else if(action.equalsIgnoreCase("Again"))
                lastViewMethod.invoke(null);
            else
                break;
            action = scanner.nextLine();
        }
    }

    //for stuffInventory(TypeOfStuffToBuyAndSell)
    private static void helpHandler(Method lastViewMethod, TypeOfStuffToBuyAndSell typeOfStuffToBuyAndSell) throws Exception{
        while(true){
            if(action.equalsIgnoreCase("Help"))
                Class.forName("View.ConsoleView").getMethod(lastViewMethod.getName() + "Help", TypeOfStuffToBuyAndSell.class).invoke(null, typeOfStuffToBuyAndSell);
            else if(action.equalsIgnoreCase("Again"))
                lastViewMethod.invoke(null);
            else
                break;
            action = scanner.nextLine();
        }
    }

    //for editDeck(boolean)
    private static void helpHandler(Method lastViewMethod, boolean nextIsBattle) throws Exception{
        while(true){
            if(action.equalsIgnoreCase("Help"))
                Class.forName("View.ConsoleView").getMethod(lastViewMethod.getName() + "Help", boolean.class).invoke(null, nextIsBattle);
            else if(action.equalsIgnoreCase("Again"))
                lastViewMethod.invoke(null);
            else
                break;
            action = scanner.nextLine();
        }
    }

    public static <T extends Stuff> ArrayList<Pair<T, Integer>> getUniqueWithNumber(ArrayList<T> stuffs){
        Boolean[] repetitious = new Boolean[stuffs.size()];
        ArrayList<Pair<T, Integer>> output = new ArrayList<>();
        for(int i=0; i<stuffs.size(); i++){
            if(repetitious[i] != null)//TODO check correct
                continue;
            repetitious[i] = true;//new Boolean(true);
            int numberOfStuff = 1;
            for(int j=i+1; j<stuffs.size(); j++){
                if(stuffs.get(i).getName().equals(stuffs.get(j).getName())){
                    repetitious[j] = true;
                    numberOfStuff++;
                }
            }
            output.add(new Pair<T, Integer>(stuffs.get(i), numberOfStuff));
        }
        return output;
    }
}
