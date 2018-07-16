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

import java.util.*;

import static Controller.LegendsOfArcadia.pStage;
import static Controller.Main.human;
import static Controller.Main.lucifer;
import static Controller.Main.mysticHourGlass;

/**
 * Created by msi-pc on 5/14/2018.
 */
public class Battle {
    private static int turnNumber;
    private static Scanner scanner = new Scanner(System.in);
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

    public static void startGameAgainst(Player opponent) {
        human.restore();
        opponent.restore();
        Battle.opponent = opponent;
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
        GameView.prepare(pStage,human,opponent);
        ConsoleView.battleStarted(opponent);
        Random random = new Random();
        int coin = random.nextInt(2);
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
        } catch (NoEffectableCardException e){ // todo correct
            // todo view
        }

        for (int i = 0; i < 4; i++) {
            Card drawingCard = human.getDeckCards().get(0);
            drawingCard.transfer(human.getHandCards());
            opponent.getDeckCards().get(0).transfer(opponent.getHandCards());
        }

        if (coin == 0) {
            ConsoleView.announceBattleStarter(human.getName());
            humanPlayTurn();
        }
        else {
            ConsoleView.announceBattleStarter(opponent.getName());
            botPlayTurn(opponent);
        }
        prepareButtons();
    }

    private static void humanPlayTurn() {
        GameView.showIdleScene();
        ConsoleView.turnAnnouncer(++turnNumber, human.getName());
        if (human.getDeckCards().isEmpty())
            ConsoleView.emptyDeck();
        human.startTurn();
        ConsoleView.showPlayerMana(human);
    }


    private static void botPlayTurn(Player bot) {
        GameView.showOpponentTurnScene();
        ConsoleView.turnAnnouncer(++turnNumber,bot.getName());
        int currentTurn = turnNumber;
        bot.startTurn();
        for (int i = 0; i < bot.getHandCards().size(); i++){
            if (bot.getHandCards().get(i) != null && bot.getHandCards().get(i).play()) {
                i--;
            }
        }
        for (Card card: bot.getMonsterFieldCards()){
            if (card != null)
                ((MonsterCard)card).castSpell();
            if (currentTurn != turnNumber)
                return;
        }
        for (Card card: bot.getMonsterFieldCards()){
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
                            ConsoleView.noSlotSelected();
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

    private static void prepareButtons(){
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
            botPlayTurn(human.getOpponent());
        });
        human.getPlayerHero().hpProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.intValue() <= 0){
                gameEnded(human.getOpponent());
            }
        });
        human.getOpponent().getPlayerHero().hpProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.intValue() <= 0){
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
            }
            else
                ConsoleView.itemDontExist();
        });
        GameView.getPlayCardButton().setOnMouseClicked(event -> {
            if (GameView.getListView().getSelectionModel().getSelectedItem() == null)
                ConsoleView.noSlotSelected();
            else {
                boolean isInstantSpell = GameView.getListView().getSelectionModel().getSelectedItem() instanceof SpellCard && ((SpellCard)GameView.getListView().getSelectionModel().getSelectedItem()).getSpellCardType().equals(SpellCardType.INSTANT);
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
            GameView.getPlayerMonsterField().getChildren().get(i).setOnMouseClicked(event -> GameView.fieldSelected(GameView.getPlayerMonsterField(),human.getMonsterFieldCards(),finalI));
            GameView.getOpponentMonsterField().getChildren().get(i).setOnMouseClicked(event -> GameView.fieldSelected(GameView.getOpponentMonsterField(),human.getOpponent().getMonsterFieldCards(),finalI));
        }
        for (int i = 0; i < 3; i++) {
            int finalI = i;
            GameView.getPlayerSpellField().getChildren().get(i).setOnMouseClicked(event -> GameView.fieldSelected(GameView.getPlayerSpellField(),human.getSpellFieldCards(),finalI));
            GameView.getOpponentSpellField().getChildren().get(i).setOnMouseClicked(event -> GameView.fieldSelected(GameView.getOpponentSpellField(),human.getOpponent().getSpellFieldCards(),finalI));
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
                target = (SpellCastable)GameView.getListView().getSelectionModel().getSelectedItem();
            if (target == null)
                ConsoleView.noTargetChosen();
            else if (targetNeedingSpell != null){
                targetNeedingSpell.apply(human,target);
                ConsoleView.spellTargeted(target);
                targetNeedingSpell = null;
                targetNeedingAttacker = null;
                target = null;
                GameView.showIdleScene();
            }
            else if (targetNeedingAttacker != null){
                if (target.equals(human.getOpponent().getPlayerHero()))
                    targetNeedingAttacker.attackOpponentHero();
                else
                    targetNeedingAttacker.attack((MonsterCard)target);
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
        //ToDo change to map.enter
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

        if (opponent == lucifer) {
            ConsoleView.wholeWinner();
            pStage.close();
        }
        else {
            pStage.close();
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
