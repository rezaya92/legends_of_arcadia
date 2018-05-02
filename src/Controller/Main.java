package Controller;

import Model.*;
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
        View.afterMatch();
        action = scanner.next();
        lastViewMethod = Class.forName("View.View").getMethod("afterMatch");
        helpHandler(lastViewMethod);
        switch (action){
            case "1":
                enterShop();
                break;
            case "2":

        }
    }

    private static void enterShop() throws Exception{
        View.enterShop();
        action = scanner.next();
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
        action = scanner.next();
        lastViewMethod = Class.forName("View.View").getMethod("cardShop");
        helpHandler(lastViewMethod);
        switch (action){
            case "1":
                //cardShop();
                break;
            case "2":
                //itemShop();
                break;
            case "3":
                //amuletShop();
                break;
            case "4":
                //afterMatch();
                break;
            case "5":
                enterShop();
            default:
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
            action = scanner.next();
        }
    }
}
