package Controller;

import Model.*;
import Model.Card.Card;
import Model.Card.CardPlace;
import Model.Card.SpellCard;
import View.*;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by msi-pc on 4/27/2018.
 */
public class Main {
    static public Player human = new Player();
    private static String action;
    private static Method lastViewMethod;
    private static Scanner scanner = new Scanner(System.in);
    private static ArrayList<Card> allCards = new ArrayList<>();//TODO add from constructors

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

    public void useContinuousSpellCards(){ //TODO enemy spellFieldCards needed
        for (SpellCard continuousSpellCard : human.getSpellFieldCards()) {
            ArrayList<SpellCastable> inputNeeded = continuousSpellCard.getSpell().inputNeeded();
            int index = 0;
            if (inputNeeded != null) {
                System.out.println("List of Targets:");
                CardPlace cardPlace = CardPlace.INVENTORY;
                for (SpellCastable spellCastable : inputNeeded) {
                    index++;
                    if (cardPlace != spellCastable.getCardPlace())
                        System.out.println(cardPlace + ":");
                    System.out.println(index + ".\t" + spellCastable.getName());
                    cardPlace = spellCastable.getCardPlace();
                }
                scanner.next();
                int choice = scanner.nextInt();
                continuousSpellCard.getSpell().use(inputNeeded.get(choice - 1));
            } else
                continuousSpellCard.getSpell().use();
        }
    }


    private static void afterMatch() throws Exception{
        //TODO player saveHuman = human.clone(); (for hourGlass)
        View.afterMatch();
        action = scanner.nextLine();//todo nextLine?
        lastViewMethod = Class.forName("View.View").getMethod("afterMatch");
        helpHandler(lastViewMethod);
        switch (action){
            case "1":
                enterShop();
                return;
            case "2":
                //TODO
                return;
            case "3":
                //TODO
                return;
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
                return;
            case "2":
                itemShop();
                return;
            case "3":
                amuletShop();
                return;
            case "4":
                afterMatch();
                return;
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
        if(action.equals("5") || action.equals("Exit") || action.equals("exit")) {
            enterShop();
            return;
        }
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
                if(!infoCard(cardName)){
                    throw new Exception();
                }
            } else if(action.equals("4") || action.equals("Edit deck") || action.equals("edit deck")){
                editDeck();
            }
        } catch (Exception e){
            View.invalidCommand();
            cardShop();
            return;
        }
        cardShop();
    }

    private static void itemShop() throws Exception{

    }

    private static void amuletShop() throws Exception{

    }

    private static void editDeck() throws Exception{
        View.editDeck();
        action = scanner.nextLine();
        lastViewMethod = Class.forName("View.View").getMethod("editDeck");
        helpHandler(lastViewMethod);
        //TODO
    }

    private static boolean infoCard(String cardName){
        for(Card card : allCards){
            if(card.getName().equals(cardName)){
                View.printCardInfo(card);
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
}
