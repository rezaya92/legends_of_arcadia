package Controller;

import Model.*;
import View.*;
import View.GameView.ConsoleView;
import javafx.util.Pair;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

import static Model.Stuff.getStuffByName;

/**
 * Created by msi-pc on 4/27/2018.
 */
public class Main {
    public static ArrayList<Stuff> allStuff = new ArrayList<>();
    public static Player human = new Player("human", 10000);
    static Player goblinChieftain = new Player("Goblin Chieftain", 10000);
    static Player ogreWarlord = new Player("Ogre Warlord", 10000);
    static Player vampireLord = new Player("Vampire Lord", 10000);
    static Player lucifer = new Player("Lucifer", 10000);
    static int mysticHourGlass = 3;  //change place?
    public static ArrayList<Player> opponents = new ArrayList<>(Arrays.asList(goblinChieftain,ogreWarlord,vampireLord,lucifer));
    public static CellTower cellTower;

    public static void afterMatch() {
        enterShop();//TODO CHANGE
    }

    public static void enterShop() {
        MenuView.showShop();
        //TODO get onMouseClicked actions in one place

    }

//    public static void cardShop() {
//        MenuView.showStuffShop(TypeOfStuffToBuyAndSell.CARD, human.getShop().getCards(), human.getInventoryCards());
//    }
//
//    public static void itemShop() {
//        MenuView.showStuffShop(TypeOfStuffToBuyAndSell.ITEM, human.getShop().getItems(), human.getItems());
//    }
//
//    public static void amuletShop() {
//        MenuView.showStuffShop(TypeOfStuffToBuyAndSell.AMULET, human.getShop().getAmulets(), human.getAmulets());
//    }
//
//    public static void editInventory() {
//        MenuView.showInventoryMenu();
//    }

    public static void editDeck(String action, boolean nextIsBattle) {
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
        //editDeck(nextIsBattle);
    }

    public static void editAmulet(String action) {
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
        //editAmulet();
        //MenuView.showEditAmulet();
    }

    public static void buyThingsProcessor(TypeOfStuffToBuyAndSell typeOfStuffToBuyAndSell, String command) throws Exception{
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

    public static void sellThingsProcessor(TypeOfStuffToBuyAndSell typeOfStuffToBuyAndSell, String command) throws Exception{
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
        for(Stuff stuff : allStuff){
            if(stuff.getName().equalsIgnoreCase(stuffName)){
                ConsoleView.printStuffInfo(stuff);
                return true;
            }
        }
        return false;
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

    static void hostGame(int portNumber) throws IOException {
        human.setIsPlaying(true);
        ServerSocket serverSocket = new ServerSocket(portNumber);
        Socket socket = serverSocket.accept();
        int coin = new Random().nextInt(2);
        cellTower = new CellTower(socket,serverSocket);
        cellTower.transmitInitials(coin);
        Player opponent = new Player(cellTower.receiveText().split(":")[1],10000);
        String amuletName = cellTower.receiveText().split(":")[1];
        if (!amuletName.equals("NULL"))
            opponent.setEquippedAmulet((Amulet)getStuffByName(amuletName));
        Battle.startGameAgainst(opponent, coin, true);
    }

    static void joinGame(String ip, int portNumber) throws IOException {
        human.setIsPlaying(true);
        Socket socket = new Socket(ip, portNumber);
        cellTower = new CellTower(socket);
        Player opponent = new Player(cellTower.receiveText().split(":")[1],1000);
        String amuletName = cellTower.receiveText().split(":")[1];
        cellTower.transmitInitials();
        if (!amuletName.equals("NULL"))
            opponent.setEquippedAmulet((Amulet)getStuffByName(amuletName));
        cellTower.transmitPlayerData(human);
        Battle.startGameAgainst(opponent, 1 - Integer.parseInt(cellTower.receiveText().split(":")[1]),true);
    }
}
