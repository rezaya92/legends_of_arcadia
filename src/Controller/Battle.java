package Controller;

import Model.Card.Card;
import Model.Card.MonsterCard;
import Model.Item;
import Model.Player;
import Model.Spell.NoEffectableCardException;
import Model.Stuff;
import View.GameView.ConsoleView;

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

    public static Player startGameAgainst(Player opponent) {
        opponent.setOpponent(human);
        human.setOpponent(opponent);
        System.out.println("Battle against " + opponent.getName() + " started!");
        Random random = new Random();
        int coin = random.nextInt(2);
        turnNumber = 0;
        Player winner = null;
        human.setIsPlaying(true);
        opponent.setIsPlaying(true);
//        human.setMaxMana(0);
//        opponent.setMaxMana(0);
        Collections.shuffle(human.getDeckCards());
        Collections.shuffle(opponent.getDeckCards());
        try {
            if (human.getEquippedAmulet() != null)
                human.getEquippedAmulet().getEffect().use(human);
            if (opponent.getEquippedAmulet() != null)
                opponent.getEquippedAmulet().getEffect().use(opponent);
        } catch (NoEffectableCardException e){ // todo correct
            // todo view
        }

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
            ConsoleView.emptyDeck();
        else
            System.out.println(human.getDeckCards().get(0).getName());

        human.startTurn();
        ConsoleView.showPlayerMana(human);
        String action = scanner.next();
        while (!action.equalsIgnoreCase("Done")) {
            int slotNumber;
            switch (action) {
                case "Help":
                case "help":
                    ConsoleView.battleHelp();
                    break;
                case "Use":
                case "use":
                    String s = scanner.next();
                    if (s.equalsIgnoreCase("Item")){
                        if (!itemUseMenu())
                            return false;
                        ConsoleView.showPlayerMana(human);
                    } else {
                        try {
                            slotNumber = Integer.parseInt(s);
                            MonsterCard monsterCard = (MonsterCard) human.getMonsterFieldCards().get(slotNumber -1);
                            if (monsterCard != null) {
                                if (!monsterCardUseMenu(monsterCard)) {
                                    return false;
                                }
                                ConsoleView.showPlayerMana(human);
                            } else
                                ConsoleView.slotIsEmpty(human);
                        } catch (NumberFormatException e){
                            ConsoleView.invalidCommand();
                        } catch (IndexOutOfBoundsException e){
                            ConsoleView.indexOutOfBound();
                        }
                    }
                    break;
                case "Set":                // todo correct for instant spells
                case "set":
                    try {
                        int handIndex = scanner.nextInt() - 1;
                        scanner.next();
                        slotNumber = scanner.nextInt() - 1;
                        human.getHandCards().get(handIndex).play(slotNumber);  // handIndex should be less than hand size.
                    } catch (IndexOutOfBoundsException e){
                        ConsoleView.indexOutOfBound();
                    }
                    catch (InputMismatchException e){
                        ConsoleView.invalidCommand();
                    }
                    break;
                case "View":
                case "view":
                    String place = scanner.next();
                    switch (place){
                        case "Hand":
                        case "hand":
                            ConsoleView.showPlayerMana(human);
                            ConsoleView.viewHand(human);
                            break;
                        case "Graveyard":
                        case "graveyard":
                            ConsoleView.viewGraveyard(human);
                            break;
                        case "SpellField":
                        case "spellfield":
                            ConsoleView.viewSpellField(human);
                            break;
                        case "MonsterField":
                        case "monsterfield":
                            ConsoleView.viewMonsterField(human);
                            break;
                        case "Heroes":
                        case "heroes":
                            ConsoleView.viewHeroes(human);
                            break;
                        default:
                            ConsoleView.invalidCommand();
                    }
                    break;
                case "Info":
                case "info":
                    String cardName = scanner.nextLine().substring(1);  // must be a card name (??)
                    Stuff card = Stuff.getStuffByName(cardName);
                    if (card != null)
                        System.out.println(card);
                    else
                        ConsoleView.invalidCardName();
                    break;
                default:
                    ConsoleView.invalidCommand();
            }
            action = scanner.next();
        }
        human.endTurn();
        return true;
    }


    private static boolean botPlayTurn(Player bot) {
        System.out.println("Turn " + (++turnNumber) + " started!");
        System.out.println(bot.getName() + "'s turn.");

        bot.startTurn();

        for (int i = 0; i < bot.getHandCards().size(); i++){
            if (bot.getHandCards().get(i) != null && bot.getHandCards().get(i).play()) {
                i--;
            }
        }
        for (Card card: bot.getMonsterFieldCards()){
            if (card != null)
                ((MonsterCard)card).castSpell();
        }
        for (Card card: bot.getMonsterFieldCards()){
            if (card != null) {
                if (!((MonsterCard) card).attackOpponentHero())
                    return false;
                for (int i = 0; i < bot.getOpponent().getMonsterFieldCards().size(); i++) {
                    ((MonsterCard) card).attack(i);
                }
            }
        }

        bot.endTurn();
        return true;
    }


    private static boolean monsterCardUseMenu(MonsterCard monsterCard){  // returns false if opponent hero dies
        ConsoleView.usingMonsterCardInfo(monsterCard);
        String action = scanner.next();
        while (!action.equalsIgnoreCase("Exit")){
            switch (action){
                case "Again":
                case "again":
                    ConsoleView.usingMonsterCardInfo(monsterCard);
                    break;
                case "Help":
                case "help":
                    ConsoleView.usingMonsterCardHelp(monsterCard.hasGotSpell());
                    break;
                case "Info":
                case "info":
                    System.out.println(monsterCard);
                    break;
                case "Attack":
                case "attack":
                    String beingAttackedSlot = scanner.next();
                    if (beingAttackedSlot.equals("Player")) {
                        if (!monsterCard.attackOpponentHero())
                            return false;
                    }
                    else {
                        try {
                            monsterCard.attack(Integer.parseInt(beingAttackedSlot) - 1);  //must be < 5
                        } catch (NumberFormatException e){
                            ConsoleView.invalidCommand();
                        } catch (IndexOutOfBoundsException e){
                            ConsoleView.indexOutOfBound();
                        }
                    }
                    return true;
                case "Cast":
                case "cast":
                    String s = scanner.next();
                    if (!monsterCard.hasGotSpell() || !s.equals("Spell"))
                        ConsoleView.invalidCommand();
                    else {
                        if (!spellCastingMenu(monsterCard))
                            return false;
                    }
                    return true;
                default:
                    ConsoleView.invalidCommand();
            }
            action = scanner.next();
        }
        return true;
    }


    private static boolean spellCastingMenu(MonsterCard monsterCard){   // returns false if opponent hero dies
        monsterCard.castSpell();  // todo is this enough ??
        return monsterCard.getOwner().getOpponent().getPlayerHero().checkAlive();
    }


    private static boolean itemUseMenu(){
        ConsoleView.availableItems(human);
        String action = scanner.next();
        while (!action.equalsIgnoreCase("Exit")){
            switch (action){
                case "Again":
                case "again":
                    ConsoleView.availableItems(human);
                    break;
                case "Help":
                case "help":
                    ConsoleView.itemHelp();
                    break;
                case "Use":
                case "use":
                    String itemName = scanner.nextLine().substring(1);
                    for (Item item: human.getItems()){          // invalid input ?
                        if (item.getName().equals(itemName)){
                            item.use(human);
                            ConsoleView.spellCasted(itemName,item.getEffect());
                            human.getItems().remove(item);
                            return human.getOpponent().getPlayerHero().checkAlive();
                        }
                    }
                    ConsoleView.itemDontExist();
                    break;
                case "Info":
                    itemName = scanner.nextLine();
                    if (!Main.printInfoStuff(itemName))
                        ConsoleView.itemDontExist();
                    break;
                default:
                    ConsoleView.invalidCommand();
            }
            action =scanner.next();
        }
        return true;
    }
}
