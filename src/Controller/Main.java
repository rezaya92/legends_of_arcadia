package Controller;

import Model.*;
import Model.Card.Card;
import View.*;
import java.lang.reflect.Method;
import java.util.Scanner;

/**
 * Created by msi-pc on 4/27/2018.
 */
public class Main {
    static public Player human = new Player();
    private static String action;
    private static Method lastViewMethod;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws Exception{
        int numberOfCards = 40;
        Card[] cards = new Card[numberOfCards];

        //todo: new cards
        // sout cards
        // scanner set deck cards (25 - 30)
        // shuffle deck cards  &&  put 4 cards in hand
        // while scanner != EndTurn    add max mp & a card to hand    scanner scans a card by id     hand to field || spell card || monsterField to enemy || spellCaster spell || use an Item
        // random play of opposite player(bot)

        afterMatch();

    }

    private static void afterMatch() throws Exception{
        //TODO player saveHuman = heman.clone(); (for hourGlass)
        View.afterMatch();
        action = scanner.nextLine();//todo nextLine?
        lastViewMethod = Class.forName("View.View").getMethod("afterMatch");
        helpHandler(lastViewMethod);
        switch (action){
            case "1":
                enterShop();
                break;
            case "2":
                break;
            case "3":
                break;
                default:
                    View.invalidCommand();
                    afterMatch();
        }
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
                afterMatch();
                break;
                default:
                    View.invalidCommand();
                    enterShop();
        }
    }

    private static void cardShop() throws Exception{
        View.cardShop();
        action = scanner.nextLine();
        lastViewMethod = Class.forName("View.View").getMethod("cardShop");
        helpHandler(lastViewMethod);
        try {
            if (action.startsWith("Buy ") || action.startsWith("buy ")) {
                int numberToBuy = Integer.parseInt(action.split(" - ")[1]);
                if(numberToBuy <= 0)
                    throw new Exception();//TODO check if executes correctly
                String cardName = action.split(" - ")[0].substring(4);
                int status = human.buyCard(cardName, numberToBuy);
                switch (status){
                    case -1:
                        View.insufficientGil();
                        break;
                    case 0:
                        View.notAvailableInShop();
                        break;
                    case 1:
                        View.successfulBuy(cardName, numberToBuy);
                }
            } else if(action.startsWith("Sell ") || action.startsWith("sell ")){
                int numberToSell = Integer.parseInt(action.split(" - ")[1]);
                if(numberToSell <= 0)
                    throw new Exception();//TODO check if executes correctly
                String cardName = action.split(" - ")[0].substring(5);
                if(human.sellCard(cardName, numberToSell))
                    View.successfulSell(cardName, numberToSell);
                else
                    View.notEnoughCards();
            } else if(action.startsWith("Info ") || action.startsWith("info ")){
                String cardName = action.substring(5);
                //TODO this case
            }//TODO other cases
        } catch (Exception e){
            View.invalidCommand();
            cardShop();
        }
    }

    private static void itemShop() throws Exception{

    }

    private static void amuletShop() throws Exception{

    }

    private static void helpHandler(Method lastViewMethod) throws Exception{
        while(action.equals("Help") || action.equals("Again")){
            switch (action) {
                case "Help":
                    Class.forName("View.View").getMethod(lastViewMethod.getName() + "Help").invoke(null);
                    break;
                case "Again":
                    lastViewMethod.invoke(null);
            }
            action = scanner.nextLine();
        }
    }
}
