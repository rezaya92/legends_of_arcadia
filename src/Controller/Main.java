package Controller;

import Model.*;
import View.View;

import java.lang.reflect.Method;
import java.util.Scanner;

/**
 * Created by msi-pc on 4/27/2018.
 */
public class Main {
    static public Player human = new Player();
    private static String action;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws Exception{
        Method lastViewMethod;

        int numberOfCards = 40;
        Card[] cards = new Card[numberOfCards];

        //todo: new cards
        // sout cards
        // scanner set deck cards (25 - 30)
        // shuffle deck cards  &&  put 4 cards in hand
        // while scanner != EndTurn    add max mp & a card to hand    scanner scans a card by id     hand to field || spell card || monsterField to enemy || spellCaster spell || use an Item
        // random play of opposite player(bot)


        View.afterMatch();
        action = scanner.next();
        lastViewMethod = Class.forName("View.View").getMethod("afterMatch");
        helpHandler(lastViewMethod);

        switch (action){
            case "1":
                View.shopEnter();
                break;
            case "2":

        }
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
