package Controller;

import Model.Card.Card;
import Model.Card.MonsterCard;
import Model.Item;
import Model.Player;
import Model.Stuff;
import View.View;

import java.util.Collections;
import java.util.InputMismatchException;
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
        human.setIsPlaying(true);
        opponent.setIsPlaying(true);

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
        else {
            System.out.println(opponent.getName() + " starts the battle.");
            botPlayTurn(opponent);
        }

        while (!(human.getDeckCards().isEmpty() && human.getHandCards().isEmpty() && human.getMonsterFieldCards().isEmpty() && opponent.getDeckCards().isEmpty() && opponent.getHandCards().isEmpty() && opponent.getMonsterFieldCards().isEmpty())) {
            if (!humanPlayTurn()){
                winner = human;
                break;
            }
            if (!botPlayTurn(opponent)){
                winner = opponent;
                break;
            }
        }

        human.setIsPlaying(false);
        opponent.setIsPlaying(false);
        if (winner == null)
            winner = (human.getPlayerHero().getHp() > opponent.getPlayerHero().getHp()) ? human : opponent;
        return winner;
    }


    private static boolean humanPlayTurn() {
        System.out.println("Turn " + (++turnNumber) + " started!");
        System.out.println(human.getName() + "'s turn.");
        if (human.getDeckCards().isEmpty())
            View.emptyDeck();
        else
            System.out.println(human.getDeckCards().get(0).getName());
        View.showPlayerMana(human);

        human.startTurn();
        String action = scanner.next();
        while (!action.equals("Done")) {
            int slotNumber;
            switch (action) {
                case "Help":
                    View.battleHelp();
                    break;
                case "Use":
                    String s = scanner.next();
                    if (s.equals("Item")){
                        if (!itemUseMenu())
                            return false;
                    } else {
                        try {
                            slotNumber = Integer.parseInt(s);
                            MonsterCard monsterCard = (MonsterCard) human.getMonsterFieldCards().get(slotNumber);
                            if (monsterCard != null) {
                                if (!monsterCardUseMenu(monsterCard)) {
                                    return false;
                                }
                            } else
                                View.slotIsEmpty(human);
                        } catch (NumberFormatException e){
                            View.invalidCommand();
                        } catch (IndexOutOfBoundsException e){
                            View.slotIsEmpty(human);
                        }
                    }
                    break;
                case "Set":                // todo correct for instant spells
                    try {
                        int handIndex = scanner.nextInt() - 1;
                        scanner.next();
                        slotNumber = scanner.nextInt();
                        human.getHandCards().get(handIndex).play(slotNumber);  // handIndex should be less than hand size.
                    }// catch (IndexOutOfBoundsException e){
                    //    View.slotIsEmpty(human);
                    //}
                    catch (InputMismatchException e){
                        View.invalidCommand();
                    }
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
                    String cardName = scanner.nextLine().substring(1);  // must be a card name (??)
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
                case "Again":
                    View.usingMonsterCardInfo(monsterCard);
                    break;
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
                    else {
                        try {
                            monsterCard.attack(Integer.parseInt(beingAttackedSlot));  //must be < 5
                        } catch (NumberFormatException e){
                            View.invalidCommand();
                        }
                    }
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
        monsterCard.castSpell();  // todo is this enough ??
        return true;
    }


    public static boolean itemUseMenu(){
        View.availableItems(human);
        String action = scanner.next();
        while (!action.equals("Exit")){
            switch (action){
                case "Again":
                    View.availableItems(human);
                    break;
                case "Help":
                    View.itemHelp();
                    break;
                case "Use":
                    String itemName = scanner.nextLine();
                    for (Item item: human.getItems()){          // invalid input ?
                        if (item.getName().equals(itemName)){
                            item.use(human);
                            human.getItems().remove(item);
                            if (!human.getOpponent().getPlayerHero().checkAlive())
                                return false;
                            break;
                        }
                    }
                    break;
                case "Info":
                    itemName = scanner.nextLine();
                    if (!Main.printInfoStuff(itemName))
                        View.itemDontExist();
                    break;
                default:
                    View.invalidCommand();
            }
        }
        return true;
    }
}
