package Controller;

import Model.Card.Card;
import Model.Card.MonsterCard;
import Model.Card.SpellCard;
import Model.Card.SpellCardType;
import Model.Item;
import Model.Player;
import Model.Spell.NoEffectableCardException;
import Model.Spell.Spell;
import Model.Spell.SpellCastable;
import View.GameView.ConsoleView;
import View.GameView.GameView;
import javafx.application.Platform;
import View.MenuView;
import javafx.application.Platform;
import javafx.stage.Stage;
import static Model.Stuff.getStuffByName;
import java.util.*;

import static Controller.LegendsOfArcadia.pStage;
import static Controller.Main.*;

/**
 * Created by msi-pc on 5/14/2018.
 */
public class Battle {
    private static int turnNumber;
    public static MonsterCard targetNeedingAttacker;
    public static SpellCastable target;
    public static Spell targetNeedingSpell;
    private static ArrayList<Card> humanDefaultDeckCardBeforeCustomization;
    private static ArrayList<Card> humanDeckCardBeforeCustomization;
    private static ArrayList<Item> humanItemsBeforeCustomization;
    private static ArrayList<Card> opponentDefaultDeckCardBeforeCustomization;
    private static ArrayList<Card> opponentDeckCardBeforeCustomization;
    private static ArrayList<Item> opponentItemsBeforeCustomization;
    private static ArrayList<Card> humanDefaultDeckCardBeforeMatch;
    private static ArrayList<Card> humanDeckCardBeforeMatch;
    private static Player opponent;
    public static boolean isMultiplayer;

    public static void startGameAgainst(Player opponent, int coin, boolean isMultiplayer) {
        processStage();
        human.restore();
        opponent.restore();
        Battle.opponent = opponent;
        Battle.isMultiplayer = isMultiplayer;
        humanDefaultDeckCardBeforeCustomization = new ArrayList<>(human.getDefaultDeckCards());
        humanDeckCardBeforeCustomization = new ArrayList<>(human.getDeckCards());
        humanItemsBeforeCustomization = new ArrayList<>(human.getItems());
        opponentDefaultDeckCardBeforeCustomization = new ArrayList<>(opponent.getDefaultDeckCards());
        opponentDeckCardBeforeCustomization = new ArrayList<>(opponent.getDeckCards());
        opponentItemsBeforeCustomization = new ArrayList<>(opponent.getItems());
        humanDefaultDeckCardBeforeMatch = new ArrayList<>(human.getDefaultDeckCards());
        humanDeckCardBeforeMatch = new ArrayList<>(human.getDeckCards());
        opponent.setOpponent(human);
        human.setOpponent(opponent);
        try {
            Main.afterMatch();
        } catch (Exception e) {
            e.printStackTrace();
        }
        GameView.prepare(pStage, human, opponent);
        ConsoleView.battleStarted(opponent);
        turnNumber = 0;
        human.setIsPlaying(true);
        opponent.setIsPlaying(true);
        Collections.shuffle(human.getDeckCards());
        Collections.shuffle(opponent.getDeckCards());
        try {
            if (human.getEquippedAmulet() != null)
                human.getEquippedAmulet().getEffect().use(human);
            if (opponent.getEquippedAmulet() != null)
                opponent.getEquippedAmulet().getEffect().use(opponent);
        } catch (NoEffectableCardException e) { // todo correct
            // todo view
            e.printStackTrace();
        }

        for (int i = 0; i < 4; i++) {
            Card drawingCard = human.getDeckCards().get(0);
            drawingCard.transfer(human.getHandCards());
            if (!isMultiplayer)
                opponent.getDeckCards().get(0).transfer(opponent.getHandCards());
        }
        if (isMultiplayer) {
            new Thread(cellTower).start();
            cellTower.transmitPlayerData(human);
        }

        if (coin == 0) {
            ConsoleView.announceBattleStarter(human.getName());
            humanPlayTurn();
        } else {
            ConsoleView.announceBattleStarter(opponent.getName());
            if (isMultiplayer)
                humanOpponentPlayTurn();
            else
                botPlayTurn(opponent);
        }
        prepareButtons();
    }

    static void humanPlayTurn() {
        Platform.runLater(() -> {
            GameView.showIdleScene();
            ConsoleView.turnAnnouncer(++turnNumber, human.getName());
            if (human.getDeckCards().isEmpty())
                ConsoleView.emptyDeck();
            human.startTurn();
            ConsoleView.showPlayerMana(human);
        });
    }

    private static void humanOpponentPlayTurn() {
        GameView.showOpponentTurnScene();
        ConsoleView.turnAnnouncer(++turnNumber, human.getOpponent().getName());
    }

    private static void botPlayTurn(Player bot) {
        GameView.showOpponentTurnScene();
        ConsoleView.turnAnnouncer(++turnNumber, bot.getName());
        int currentTurn = turnNumber;
        bot.startTurn();
        for (int i = 0; i < bot.getHandCards().size(); i++) {
            if (bot.getHandCards().get(i) != null && bot.getHandCards().get(i).play()) {
                i--;
            }
        }
        for (Card card : bot.getMonsterFieldCards()) {
            if (card != null)
                ((MonsterCard) card).castSpell();
            if (currentTurn != turnNumber)
                return;
        }
        for (Card card : bot.getMonsterFieldCards()) {
            if (card != null) {
                ((MonsterCard) card).attackOpponentHero();
                if (currentTurn != turnNumber)
                    return;
                for (int i = 0; i < bot.getOpponent().getMonsterFieldCards().size(); i++) {
                    ((MonsterCard) card).attack(i);
                }
            }
        }
        bot.endTurn();
        humanPlayTurn();
    }



    private static boolean spellCastingMenu(MonsterCard monsterCard) {   // returns false if opponent hero dies
        monsterCard.castSpell();
        return monsterCard.getOwner().getOpponent().getPlayerHero().checkAlive();
    }

    private static void prepareButtons() {
        GameView.getShowHandButton().setOnMouseClicked(event -> {
            GameView.showHand();
        });
        GameView.getShowItemsButton().setOnMouseClicked(event -> {
            GameView.showItems();
        });
        GameView.getEndTurnButton().setOnMouseClicked(event -> {
            human.endTurn();
            if (human.getDeckCards().isEmpty() && human.getHandCards().isEmpty() && human.getMonsterFieldCards().isEmpty() && human.getOpponent().getDeckCards().isEmpty() && human.getOpponent().getHandCards().isEmpty() && human.getOpponent().getMonsterFieldCards().isEmpty())
                gameEnded((human.getPlayerHero().getHp() > human.getOpponent().getPlayerHero().getHp()) ? human : human.getOpponent());
            if (isMultiplayer) {
                GameView.showOpponentTurnScene();
                cellTower.transmitText("End Turn");
            } else
                botPlayTurn(human.getOpponent());
        });
        human.getPlayerHero().hpProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.intValue() <= 0 && oldValue.intValue() > 0) {
                gameEnded(human.getOpponent());
            }
        });
        human.getOpponent().getPlayerHero().hpProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.intValue() <= 0) {
                gameEnded(human);
            }
        });
        GameView.getUseItemButton().setOnMouseClicked(event -> {
            Item item = (Item) GameView.getListView().getSelectionModel().getSelectedItem();
            if (item != null) {
                item.use(human);
                ConsoleView.spellCasted(item.getName(), item.getEffect());
                human.getItems().remove(item);
                GameView.showItems();
            } else
                ConsoleView.itemDontExist();
        });
        GameView.getPlayCardButton().setOnMouseClicked(event -> {
            if (GameView.getListView().getSelectionModel().getSelectedItem() == null)
                ConsoleView.noSlotSelected();
            else {
                boolean isInstantSpell = GameView.getListView().getSelectionModel().getSelectedItem() instanceof SpellCard && ((SpellCard) GameView.getListView().getSelectionModel().getSelectedItem()).getSpellCardType().equals(SpellCardType.INSTANT);
                ((Card) GameView.getListView().getSelectionModel().getSelectedItem()).play();
                if (!isInstantSpell)
                    GameView.showHand();
            }
        });
        GameView.getListView().getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null)
                GameView.getDetails().setText(newValue.toString());
        });
        GameView.getPlayerButton().setOnMouseClicked(event -> GameView.playerClicked(human));
        GameView.getOpponentButton().setOnMouseClicked(event -> GameView.playerClicked(human.getOpponent()));
        for (int i = 0; i < 5; i++) {
            int finalI = i;
            GameView.getPlayerMonsterField().getChildren().get(i).setOnMouseClicked(event -> GameView.fieldSelected(GameView.getPlayerMonsterField(), human.getMonsterFieldCards(), finalI));
            GameView.getOpponentMonsterField().getChildren().get(i).setOnMouseClicked(event -> GameView.fieldSelected(GameView.getOpponentMonsterField(), human.getOpponent().getMonsterFieldCards(), finalI));
        }
        for (int i = 0; i < 3; i++) {
            int finalI = i;
            GameView.getPlayerSpellField().getChildren().get(i).setOnMouseClicked(event -> GameView.fieldSelected(GameView.getPlayerSpellField(), human.getSpellFieldCards(), finalI));
            GameView.getOpponentSpellField().getChildren().get(i).setOnMouseClicked(event -> GameView.fieldSelected(GameView.getOpponentSpellField(), human.getOpponent().getSpellFieldCards(), finalI));
        }
        GameView.getPlayerGraveYard().setOnMouseClicked(event -> GameView.graveYardSelected(human));
        GameView.getOpponentGraveYard().setOnMouseClicked(event -> GameView.graveYardSelected(human.getOpponent()));
        GameView.getAttackButton().setOnMouseClicked(event -> {
            if (!targetNeedingAttacker.isAwake())
                ConsoleView.cardIsSleep(human);
            else if (targetNeedingAttacker.hasAttacked())
                ConsoleView.alreadyAttacked(human);
            else {
                GameView.showAttackScene();
            }
        });
        GameView.getCancelButton().setOnMouseClicked(event -> {
            targetNeedingAttacker = null;
            targetNeedingSpell = null;
            target = null;
            GameView.showIdleScene();
        });
        GameView.getChooseButton().setOnMouseClicked(event -> {
            if (GameView.getListView().getSelectionModel().getSelectedItem() != null)
                target = (SpellCastable) GameView.getListView().getSelectionModel().getSelectedItem();
            if (target == null)
                ConsoleView.noTargetChosen();
            else if (targetNeedingSpell != null) {
                targetNeedingSpell.apply(human, target);
                ConsoleView.spellTargeted(target);
                targetNeedingSpell = null;
                targetNeedingAttacker = null;
                target = null;
                GameView.showIdleScene();
            } else if (targetNeedingAttacker != null) {
                if (target.equals(human.getOpponent().getPlayerHero()))
                    targetNeedingAttacker.attackOpponentHero();
                else
                    targetNeedingAttacker.attack((MonsterCard) target);
                targetNeedingAttacker = null;
                targetNeedingSpell = null;
                target = null;
                GameView.showIdleScene();
            }
        });
        GameView.getUseSpellButton().setOnMouseClicked(event -> {
            targetNeedingAttacker.castSpell();
        });
    }

    private static void gameEnded(Player winner) {
        System.out.println(mysticHourGlass);
        human.setIsPlaying(false);
        human.getOpponent().setIsPlaying(false);
        if (winner == human) {
            human.setDefaultDeckCards(humanDefaultDeckCardBeforeMatch);
            human.setDeckCards(humanDeckCardBeforeMatch);
            LegendsOfArcadia.getMap().continueMap(Main.opponents.indexOf(human.getOpponent()) + 2);
        } else {
            if (mysticHourGlass > 0) {
                human.setDefaultDeckCards(humanDefaultDeckCardBeforeCustomization);
                human.setDeckCards(humanDeckCardBeforeCustomization);
                human.setItems(humanItemsBeforeCustomization);
                human.getOpponent().setDefaultDeckCards(opponentDefaultDeckCardBeforeCustomization);
                human.getOpponent().setDeckCards(opponentDeckCardBeforeCustomization);
                human.getOpponent().setItems(opponentItemsBeforeCustomization);
                mysticHourGlass--;
                ConsoleView.mysticHourGlassUsed();
                LegendsOfArcadia.getMap().continueMap();
            } else {
                ConsoleView.gameOver(human);
                //TODO Thread.sleep()
                mysticHourGlass = 3;   //todo other restorations ??
                MenuView.showMainMenu();
            }
        }
    }

    private static void processStage() {
        Stage primaryStage = LegendsOfArcadia.getPrimaryStage();
        primaryStage.setWidth(1500);
        primaryStage.setHeight(800);
        primaryStage.setTitle("Legends of Arcadia - Battle");
    }

    static void processReceivedPlayerData(Player player) throws CloneNotSupportedException {
        player.getPlayerHero().setHp(Integer.parseInt(cellTower.receiveText().split(":")[1]));
        player.setMana(Integer.parseInt(cellTower.receiveText().split(":")[1]));
        player.setMaxMana(Integer.parseInt(cellTower.receiveText().split(":")[1]));
        cellTower.receiveText();
        for (int i = 0; i < 5; i++) {
            String monsterCardName = cellTower.receiveText().split(":")[1];
            if (!monsterCardName.equals("NULL")) {
                player.getMonsterFieldCards().set(i, (Card) getStuffByName(monsterCardName).clone());
                ((MonsterCard) player.getMonsterFieldCards().get(i)).setHp(Integer.parseInt(cellTower.receiveText().split(":")[1]));
                ((MonsterCard) player.getMonsterFieldCards().get(i)).setAp(Integer.parseInt(cellTower.receiveText().split(":")[1]));
                player.getMonsterFieldCards().get(i).setCardPlace(player.getMonsterFieldCards());
                player.getMonsterFieldCards().get(i).setOwner(player);
            } else
                player.getMonsterFieldCards().set(i, null);
        }
        cellTower.receiveText();
        for (int i = 0; i < 3; i++) {
            String cardName = cellTower.receiveText().split(":")[1];
            if (!cardName.equals("NULL")) {
                player.getSpellFieldCards().set(i, (Card) getStuffByName(cardName).clone());
                player.getSpellFieldCards().get(i).setCardPlace(player.getSpellFieldCards());
                player.getSpellFieldCards().get(i).setOwner(player);
            }
            else
                player.getSpellFieldCards().set(i, null);
        }
        cellTower.receiveText();
        String command = cellTower.receiveText();
        player.getGraveyardCards().clear();
        while (!command.equals("Hand:")) {
            player.getGraveyardCards().add((Card) getStuffByName(command.split(":")[1]).clone());
            player.getGraveyardCards().get(player.getGraveyardCards().size() - 1).setOwner(player);
            player.getGraveyardCards().get(player.getGraveyardCards().size() - 1).setCardPlace(player.getGraveyardCards());
            command = cellTower.receiveText();
        }
        player.getHandCards().clear();
        command = cellTower.receiveText();
        while (!command.equals("Deck:")) {
            player.getHandCards().add((Card) getStuffByName(command.split(":")[1]).clone());
            player.getHandCards().get(player.getHandCards().size() - 1).setOwner(player);
            player.getHandCards().get(player.getHandCards().size() - 1).setCardPlace(player.getHandCards());
            command = cellTower.receiveText();
        }
        player.getDeckCards().clear();
        command = cellTower.receiveText();
        while (!command.equals("end of transmission")) {
            player.getDeckCards().add((Card) getStuffByName(command.split(":")[1]).clone());
            player.getDeckCards().get(player.getDeckCards().size() - 1).setOwner(player);
            player.getDeckCards().get(player.getDeckCards().size() - 1).setCardPlace(player.getDeckCards());
            command = cellTower.receiveText();
        }
        Platform.runLater(GameView::updateFields);
    }
}
