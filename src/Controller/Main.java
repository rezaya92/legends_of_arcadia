package Controller;

import Model.*;
import Model.Card.Card;
import Model.Card.CardPlace;
import Model.Card.SpellCard;
import View.*;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
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
    private static int turnNumber;

    public static void main(String[] args) throws Exception{
        int numberOfCards = 40;
        Card[] cards = new Card[numberOfCards];

        //todo: new cards
        // sout cards
        //startGameAgainst(opponent);

        afterMatch();

    }

    public static void startGameAgainst (Player opponent){
        opponent.setOpponent(human);
        human.setOpponent(opponent);
        System.out.println("Battle against " + opponent.getName() + " started!");
        Random random = new Random();
        int coin = random.nextInt(2);
        turnNumber = 0;

        human.setMaxMana(0);
        opponent.setMaxMana(0);
        Collections.shuffle(human.getDeckCards());
        Collections.shuffle(opponent.getDeckCards());

        System.out.print("Player drew ");
        for (int i = 0; i < 4; i++){
            Card drawingCard = human.getDeckCards().get(0);
            drawingCard.transfer(human.getHandCards());
            System.out.print(drawingCard.getName() + ", ");
            opponent.getDeckCards().get(0).transfer(opponent.getHandCards());
        }
        System.out.println("");     // could be a string from "Player drew"

        if (coin == 0)
            System.out.println(human.getName() + " starts the battle.");
        else
            System.out.println(opponent.getName() + " starts the battle.");

        if (coin == 0){
            while (true) { // todo correct
                humanPlayTurn();
                botPlayTurn(opponent);
            }
        }
        else {
            while (true){
                botPlayTurn(opponent);
                humanPlayTurn();
            }
        }
    }

    public static void humanPlayTurn(){
        System.out.println("Turn " + (++turnNumber) + " started!");
        System.out.println(human.getName() + "'s turn.");
        if (human.getDeckCards().isEmpty())
            View.emptyDeck();
        else
            System.out.println(human.getDeckCards().get(0).getName());
        View.showPlayerMana(human);

        human.startTurn();
        String action = scanner.next();
        while (action != "Done"){
            // todo
        }
        human.endTurn();
    }

    public static void botPlayTurn(Player bot){
        System.out.println("Turn " + (++turnNumber) + " started!");
        System.out.println(human.getName() + "'s turn.");

        bot.startTurn();
        // todo
        bot.endTurn();
    }
    /*
    public static void startGame (Player player1, Player player2){
        player1.setOpponent(player2);
        player2.setOpponent(player1);
        Player playingPlayer = player1;

        player1.setMaxMana(0);
        player2.setMaxMana(0);
        Collections.shuffle(player1.getDeckCards());
        Collections.shuffle(player2.getDeckCards());

        for (int i = 0; i < 4; i++){
            player1.getDeckCards().get(0).transfer(player1.getHandCards());
            player2.getDeckCards().get(0).transfer(player2.getHandCards());
        }

        while (true){
            playingPlayer.startTurn();
            String action = scanner.next();
            while (action != "Done"){
                // todo
            }
            playingPlayer.endTurn();
            playingPlayer = playingPlayer.getOpponent();
        }
    }*/

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
