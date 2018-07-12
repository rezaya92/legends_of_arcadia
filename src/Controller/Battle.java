package Controller;

import Model.Card.Card;
import Model.Card.MonsterCard;
import Model.Item;
import Model.Player;
import Model.Spell.NoEffectableCardException;
import View.GameView.ConsoleView;
import View.GameView.GameView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import java.util.*;

import static Controller.Main.opponents;
import static Controller.LegendsOfArcadia.pStage;
import static Controller.Main.human;
import static Controller.Main.mysticHourGlass;
import static Controller.Main.opponentNumber;

/**
 * Created by msi-pc on 5/14/2018.
 */
public class Battle {
    private static int turnNumber;
    private static Scanner scanner = new Scanner(System.in);

    public static void startGameAgainst(Player opponent, ArrayList<Card> humanDefaultDeckCardBeforeCustomization, ArrayList<Card> humanDeckCardBeforeCustomization ,ArrayList<Item> humanItemsBeforeCustomization, ArrayList<Card> opponentDefaultDeckCardBeforeCustomization ,ArrayList<Card> opponentDeckCardBeforeCustomization,ArrayList<Item> opponentItemsBeforeCustomization,ArrayList<Card> humanDefaultDeckCardBeforeMatch, ArrayList<Card> humanDeckCardBeforeMatch) {
        opponent.setOpponent(human);
        human.setOpponent(opponent);
        GameView.prepare(pStage,human,opponent);
        ConsoleView.battleStarted(opponent);
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

        for (int i = 0; i < 4; i++) {
            Card drawingCard = human.getDeckCards().get(0);
            drawingCard.transfer(human.getHandCards());
            opponent.getDeckCards().get(0).transfer(opponent.getHandCards());
        }

        if (coin == 0)
            ConsoleView.announceBattleStarter(human.getName());
        else {
            ConsoleView.announceBattleStarter(opponent.getName());
            botPlayTurn(opponent);
        }
        prepareButtons(humanDefaultDeckCardBeforeCustomization, humanDeckCardBeforeCustomization , humanItemsBeforeCustomization, opponentDefaultDeckCardBeforeCustomization , opponentDeckCardBeforeCustomization, opponentItemsBeforeCustomization, humanDefaultDeckCardBeforeMatch, humanDeckCardBeforeMatch);
        humanPlayTurn();
    }

    private static void humanPlayTurn() {
        GameView.showIdleScene();
        ConsoleView.turnAnnouncer(++turnNumber, human.getName());
        if (human.getDeckCards().isEmpty())
            ConsoleView.emptyDeck();
        human.startTurn();
        ConsoleView.showPlayerMana(human);
        /*String action = scanner.next();
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
                        ConsoleView.printStuffInfo(card);
                    else
                        ConsoleView.invalidCardName();
                    break;
                default:
                    ConsoleView.invalidCommand();
            }
            action = scanner.next();
        }*/
        human.endTurn();
    }


    private static void botPlayTurn(Player bot) {
        GameView.showOpponentTurnScene();
        ConsoleView.turnAnnouncer(++turnNumber,bot.getName());

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
                ((MonsterCard) card).attackOpponentHero();
                for (int i = 0; i < bot.getOpponent().getMonsterFieldCards().size(); i++) {
                    ((MonsterCard) card).attack(i);
                }
            }
        }
        bot.endTurn();
        humanPlayTurn();
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
                    ConsoleView.printStuffInfo(monsterCard);
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

    private static void prepareButtons(ArrayList<Card> humanDefaultDeckCardBeforeCustomization, ArrayList<Card> humanDeckCardBeforeCustomization ,ArrayList<Item> humanItemsBeforeCustomization, ArrayList<Card> opponentDefaultDeckCardBeforeCustomization ,ArrayList<Card> opponentDeckCardBeforeCustomization,ArrayList<Item> opponentItemsBeforeCustomization,ArrayList<Card> humanDefaultDeckCardBeforeMatch, ArrayList<Card> humanDeckCardBeforeMatch){
        GameView.getShowHandButton().setOnMouseClicked(event -> {
            GameView.showHand();
        });
        GameView.getShowItemsButton().setOnMouseClicked(event -> {
            GameView.showItems();
        });
        GameView.getEndTurnButton().setOnMouseClicked(event -> {
            human.endTurn();
            if (human.getDeckCards().isEmpty() && human.getHandCards().isEmpty() && human.getMonsterFieldCards().isEmpty() && human.getOpponent().getDeckCards().isEmpty() && human.getOpponent().getHandCards().isEmpty() && human.getOpponent().getMonsterFieldCards().isEmpty())
                gameEnded((human.getPlayerHero().getHp() > human.getOpponent().getPlayerHero().getHp()) ? human : human.getOpponent(), humanDefaultDeckCardBeforeCustomization, humanDeckCardBeforeCustomization , humanItemsBeforeCustomization, opponentDefaultDeckCardBeforeCustomization , opponentDeckCardBeforeCustomization, opponentItemsBeforeCustomization, humanDefaultDeckCardBeforeMatch, humanDeckCardBeforeMatch);
            botPlayTurn(human.getOpponent());
        });
        human.getPlayerHero().hpProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.intValue() < 0){
                gameEnded(human.getOpponent(), humanDefaultDeckCardBeforeCustomization, humanDeckCardBeforeCustomization , humanItemsBeforeCustomization, opponentDefaultDeckCardBeforeCustomization , opponentDeckCardBeforeCustomization, opponentItemsBeforeCustomization, humanDefaultDeckCardBeforeMatch, humanDeckCardBeforeMatch);
            }
        });
        human.getOpponent().getPlayerHero().hpProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.intValue() <= 0){
                gameEnded(human, humanDefaultDeckCardBeforeCustomization, humanDeckCardBeforeCustomization , humanItemsBeforeCustomization, opponentDefaultDeckCardBeforeCustomization , opponentDeckCardBeforeCustomization, opponentItemsBeforeCustomization, humanDefaultDeckCardBeforeMatch, humanDeckCardBeforeMatch);
            }
        });
        GameView.getUseItemButton().setOnMouseClicked(event -> {
            Item item = (Item) GameView.getListView().getSelectionModel().getSelectedItem();
            if (item != null) {
                item.use(human);
                ConsoleView.spellCasted(item.getName(), item.getEffect());
                human.getItems().remove(item);
                GameView.showItems();
            }
            else
                ConsoleView.itemDontExist();
        });
        GameView.getPlayCardButton().setOnMouseClicked(event -> {

        });

    }

    private static void gameEnded(Player winner, ArrayList<Card> humanDefaultDeckCardBeforeCustomization, ArrayList<Card> humanDeckCardBeforeCustomization ,ArrayList<Item> humanItemsBeforeCustomization, ArrayList<Card> opponentDefaultDeckCardBeforeCustomization ,ArrayList<Card> opponentDeckCardBeforeCustomization,ArrayList<Item> opponentItemsBeforeCustomization,ArrayList<Card> humanDefaultDeckCardBeforeMatch, ArrayList<Card> humanDeckCardBeforeMatch) {
        //ToDo change to map
        //pStage.close();
        human.setIsPlaying(false);
        human.getOpponent().setIsPlaying(false);
        if (winner == human) {
            //ArrayList<Item> humanItemsAfterMatch = human.getItems();
            //human = humanBeforeMatch;
            human.setDefaultDeckCards(humanDefaultDeckCardBeforeMatch);
            human.setDeckCards(humanDeckCardBeforeMatch);
            //human.setItems(humanItemsAfterMatch);
        } else {
            if (mysticHourGlass > 0) {
                //System.out.println(humanBeforeCustomize.getDeckCards().size());
                //human = humanBeforeCustomize;
                human.setDefaultDeckCards(humanDefaultDeckCardBeforeCustomization);
                human.setDeckCards(humanDeckCardBeforeCustomization);
                human.setItems(humanItemsBeforeCustomization);
                human.getOpponent().setDefaultDeckCards(opponentDefaultDeckCardBeforeCustomization);
                human.getOpponent().setDeckCards(opponentDeckCardBeforeCustomization);
                human.getOpponent().setItems(opponentItemsBeforeCustomization);
                mysticHourGlass--;
                ConsoleView.mysticHourGlassUsed();
            } else {
                ConsoleView.gameOver(human);
                //TODO end game
            }
        }

        //TODO go to map

        if (opponentNumber == opponents.size()) {
            ConsoleView.wholeWinner();
        }
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
