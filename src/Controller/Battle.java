package Controller;

import Model.Card.Card;
import Model.Card.MonsterCard;
import Model.Player;
import Model.Stuff;
import View.View;

import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

import static Controller.Main.human;

/**
 * Created by msi-pc on 5/14/2018.
 */
public class Battle {
    private static int turnNumber;
    private static Scanner scanner = new Scanner(System.in);

    public static Player startGameAgainst(Player opponent) {   // todo use amulets
        opponent.setOpponent(human);
        human.setOpponent(opponent);
        System.out.println("Battle against " + opponent.getName() + " started!");
        Random random = new Random();
        int coin = random.nextInt(2);
        turnNumber = 0;
        Player winner = null;

        human.setMaxMana(0);
        opponent.setMaxMana(0);
        Collections.shuffle(human.getDeckCards());
        Collections.shuffle(opponent.getDeckCards());

        System.out.print("Player drew ");
        for (int i = 0; i < 4; i++) {
            Card drawingCard = human.getDeckCards().get(0);
            drawingCard.transfer(human.getHandCards());
            System.out.print(drawingCard.getName() + ", ");
            opponent.getDeckCards().get(0).transfer(opponent.getHandCards());
        }
        System.out.println();     // could be a string from "Player drew"

        if (coin == 0)
            System.out.println(human.getName() + " starts the battle.");
        else
            System.out.println(opponent.getName() + " starts the battle.");

        if (coin == 0) {
            while (true) { // todo correct
                if (!humanPlayTurn()){
                    winner = human;
                    break;
                }
                if (!botPlayTurn(opponent)){
                    winner = opponent;
                    break;
                }
            }
        } else {
            while (true) {
                if (!botPlayTurn(opponent)){
                    winner = opponent;
                    break;
                }
                if (!humanPlayTurn()){
                    winner = human;
                    break;
                }
            }
        }

        return winner;
    }


    public static boolean humanPlayTurn() {
        System.out.println("Turn " + (++turnNumber) + " started!");
        System.out.println(human.getName() + "'s turn.");
        if (human.getDeckCards().isEmpty())
            View.emptyDeck();
        else
            System.out.println(human.getDeckCards().get(0).getName());
        View.showPlayerMana(human);

        human.startTurn();
        String action = scanner.next();
        while (action != "Done") {
            int slotNumber;
            switch (action) {
                case "Help":
                    View.battleHelp();
                    break;
                case "Use":
                    slotNumber = scanner.nextInt();
                    MonsterCard monsterCard = (MonsterCard)human.getMonsterFieldCards().get(slotNumber);
                    if (monsterCard != null)
                        if (!monsterCardUseMenu(monsterCard)){
                            return false;
                        }
                    else
                        View.slotIsEmpty(human);
                    break;
                case "Set":
                    int handIndex = scanner.nextInt();
                    scanner.next();
                    slotNumber = scanner.nextInt();
                    human.getHandCards().get(handIndex).play(slotNumber);  // handIndex should be less than hand size.
                    break;
                case "View":
                    String place = scanner.next();
                    switch (place){
                        case "Hand":
                            View.viewHand(human);
                            break;
                        case "Graveyard":
                            View.viewGraveyard(human);
                            break;
                        case "SpellField":
                            View.viewSpellField(human);
                            break;
                        case "MonsterField":
                            View.viewMonsterField(human);
                            break;
                        default:
                            View.invalidCommand();
                    }
                    break;
                case "Info":
                    String cardName = scanner.nextLine();  // must be a card name (??)
                    Stuff card = Stuff.getStuffByName(cardName);
                    if (card != null)
                        System.out.println(card);
                    else
                        View.invalidCardName();
                    break;
                default:
                    View.invalidCommand();
            }
            action = scanner.next();
        }
        human.endTurn();
        return true;
    }


    public static boolean botPlayTurn(Player bot) {
        System.out.println("Turn " + (++turnNumber) + " started!");
        System.out.println(bot.getName() + "'s turn.");

        bot.startTurn();
        // todo
        bot.endTurn();
        return true;
    }


    public static boolean monsterCardUseMenu(MonsterCard monsterCard){  // returns false if opponent hero dies
        View.usingMonsterCardInfo(monsterCard);
        String action = scanner.next();
        while (!action.equals("Exit")){
            switch (action){
                case "Help":
                    View.usingMonsterCardHelp(monsterCard.hasGotSpell());
                    break;
                case "Info":
                    System.out.println(monsterCard);
                    break;
                case "Attack":
                    String beingAttackedSlot = scanner.next();
                    if (beingAttackedSlot.equals("Player")) {
                        monsterCard.attackOpponentHero();
                        if (!monsterCard.getOwner().getOpponent().getPlayerHero().checkAlive())
                            return false;
                    }
                    else
                        monsterCard.attack(Integer.parseInt(beingAttackedSlot));  // bug: must surround with try/catch  also must be < 5
                    break;
                case "Cast":
                    String s = scanner.next();
                    if (!monsterCard.hasGotSpell() || !s.equals("Spell"))
                        View.invalidCommand();
                    else {
                        if (!spellCastingMenu(monsterCard))
                            return false;
                    }
                    break;
                default:
                    View.invalidCommand();
            }
            action = scanner.next();
        }
        return true;
    }

    public static boolean spellCastingMenu(MonsterCard monsterCard){   // returns false if opponent hero dies
        // todo
        return true;
    }
}