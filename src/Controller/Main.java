package Controller;

import Model.*;
import Model.Card.*;
import Model.Spell.*;
import View.*;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by msi-pc on 4/27/2018.
 */
public class Main {
    static public Player human = new Player();
    private static String action;
    private static Method lastViewMethod;
    private static Scanner scanner = new Scanner(System.in);
    private static ArrayList<Card> allCards = new ArrayList<>();//TODO add from constructors
//    private static ArrayList<Card> allCards = new ArrayList<>();//TODO add from constructors
//    private static ArrayList<Item> allItems = new ArrayList<>();//TODO add from constructors
//    private static ArrayList<Amulet> allAmulets = new ArrayList<>();//TODO add from constructors
    private static ArrayList<Stuff> allStuff = new ArrayList<>();//TODO add from constructors

    public static void main(String[] args) throws Exception{
        int numberOfCards = 40;
        Card[] cards = new Card[numberOfCards];

        //todo: new cards
        // sout cards
        //startGameAgainst(opponent);

        afterMatch();
    }

    /*public void useContinuousSpellCards(){ //TODO enemy spellFieldCards needed
    for (SpellCard continuousSpellCard : human.getSpellFieldCards()) {
        ArrayList<SpellCastable> choiceList = continuousSpellCard.getSpell().inputNeeded();
        int index = 0;
        if (choiceList != null) {
            System.out.println("List of Targets:");
            CardPlace cardPlace = CardPlace.INVENTORY;
            for (SpellCastable spellCastable : choiceList) {
                index++;
                if (cardPlace != spellCastable.getCardPlace())
                    System.out.println(cardPlace + ":");
                System.out.println(index + ".\t" + spellCastable.getName());
                cardPlace = spellCastable.getCardPlace();
            }
            scanner.next();
            int choice = scanner.nextInt();
            continuousSpellCard.getSpell().use(choiceList.get(choice - 1));
        } else
            continuousSpellCard.getSpell().use();
        }
    }
    */

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
                buyThingsProcessor(TypeOfStuffToBuyAndSell.CARD, action);
            } else if(action.startsWith("Sell ") || action.startsWith("sell ")){
                sellThingsProcessor(TypeOfStuffToBuyAndSell.CARD, action);
            } else if(action.startsWith("Info ") || action.startsWith("info ")){
                String cardName = action.substring(5);
                if(!printInfoStuff(cardName)){
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

    public static void buyThingsProcessor(TypeOfStuffToBuyAndSell typeOfStuffToBuyAndSell, String command) throws Exception{
        int numberToBuy = Integer.parseInt(action.split(" - ")[1]);
        if(numberToBuy <= 0)
            throw new Exception();//TODO check if executes correctly
        String thingName = action.split(" - ")[0].substring(4);
        int status = human.buyStuff(typeOfStuffToBuyAndSell, thingName, numberToBuy);
        switch (status){
            case -1:
                View.insufficientGil();
                break;
            case 0:
                View.notAvailableInShop();
                break;
            case 1:
                View.successfulBuy(thingName, numberToBuy);
        }
    }

    public static void sellThingsProcessor(TypeOfStuffToBuyAndSell typeOfStuffToBuyAndSell, String command) throws Exception{
        int numberToSell = Integer.parseInt(action.split(" - ")[1]);
        if(numberToSell <= 0)
            throw new Exception();//TODO check if executes correctly
        String cardName = action.split(" - ")[0].substring(5);
        if(human.sellStuff(typeOfStuffToBuyAndSell, cardName, numberToSell))
            View.successfulSell(cardName, numberToSell);
        else
            View.notEnoughCards();
    }

    private static void itemShop() throws Exception{
        View.itemShop();
        action = scanner.nextLine();
        lastViewMethod = Class.forName("View.View").getMethod("itemShop");
        helpHandler(lastViewMethod);
        //TODO
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

    private static boolean printInfoStuff(String stuffName){
        for(Stuff stuff : allStuff){
            if(stuff.getName().equals(stuffName)){
                View.printStuffInfo(stuff);
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

    private void instantiateSpells(){
        GeneralizedSpell throwKnives = new GeneralizedSpell(new Spell[]{
                new HPSpell(
                        EnumSet.of(SpellArea.ENEMY_MONSTERFIELD,SpellArea.ENEMY_PLAYER),
                        new Class[]{PlayerHero.class,MonsterCard.class},
                        EnumSet.of(Tribe.DEMONIC,Tribe.ATLANTIAN,Tribe.DRAGONBREED,Tribe.ELVEN),SpellChoiceType.SELECT,-500)
        },
                "Deal 500 damage to a selected enemy monster card on the field or to enemy player","Throw Knives");
        GeneralizedSpell poisonousCauldron = new GeneralizedSpell(new Spell[]{
                new APSpell(
                        EnumSet.of(SpellArea.ENEMY_MONSTERFIELD,SpellArea.ENEMY_PLAYER),
                        new Class[]{PlayerHero.class,MonsterCard.class},
                        EnumSet.of(Tribe.DEMONIC,Tribe.ATLANTIAN,Tribe.DRAGONBREED,Tribe.ELVEN),SpellChoiceType.SELECT,-500)
        },
                "Deal 100 damage to all enemy monster cards and enemy player","Poisonous Cauldron");
        GeneralizedSpell firstAidKit = new GeneralizedSpell(new Spell[]{
                new APSpell(
                        EnumSet.of(SpellArea.ENEMY_MONSTERFIELD,SpellArea.ENEMY_PLAYER),
                        new Class[]{PlayerHero.class,MonsterCard.class},
                        EnumSet.of(Tribe.DEMONIC,Tribe.ATLANTIAN,Tribe.DRAGONBREED,Tribe.ELVEN),SpellChoiceType.SELECT,-500)
        },
                "Increase HP of a selected friendly monster card or player by 500","First Aid Kit");
        GeneralizedSpell reapersScythe = new GeneralizedSpell(new Spell[]{
                new APSpell(
                        EnumSet.of(SpellArea.ENEMY_MONSTERFIELD,SpellArea.ENEMY_PLAYER),
                        new Class[]{PlayerHero.class,MonsterCard.class},
                        EnumSet.of(Tribe.DEMONIC,Tribe.ATLANTIAN,Tribe.DRAGONBREED,Tribe.ELVEN),SpellChoiceType.SELECT,-500)
        },
                "Send an enemy monster or spell card from field to graveyard","Reaper’s Scythe");
        GeneralizedSpell meteorShower = new GeneralizedSpell(new Spell[]{
                new APSpell(
                        EnumSet.of(SpellArea.ENEMY_MONSTERFIELD,SpellArea.ENEMY_PLAYER),
                        new Class[]{PlayerHero.class,MonsterCard.class},
                        EnumSet.of(Tribe.DEMONIC,Tribe.ATLANTIAN,Tribe.DRAGONBREED,Tribe.ELVEN),SpellChoiceType.SELECT,-500)
        },
                "Deal 800 damage to a random enemy monster card on field or player","Meteor Shower");
        GeneralizedSpell lunarBlessing = new GeneralizedSpell(new Spell[]{
                new APSpell(
                        EnumSet.of(SpellArea.ENEMY_MONSTERFIELD,SpellArea.ENEMY_PLAYER),
                        new Class[]{PlayerHero.class,MonsterCard.class},
                        EnumSet.of(Tribe.DEMONIC,Tribe.ATLANTIAN,Tribe.DRAGONBREED,Tribe.ELVEN),SpellChoiceType.SELECT,-500)
        },
                "Increase AP and HP of friendly Elven monster cards by 300","Lunar Blessing");
        GeneralizedSpell strategicRetreat = new GeneralizedSpell(new Spell[]{
                new APSpell(
                        EnumSet.of(SpellArea.ENEMY_MONSTERFIELD,SpellArea.ENEMY_PLAYER),
                        new Class[]{PlayerHero.class,MonsterCard.class},
                        EnumSet.of(Tribe.DEMONIC,Tribe.ATLANTIAN,Tribe.DRAGONBREED,Tribe.ELVEN),SpellChoiceType.SELECT,-500)
        },
                "Select and move a monster card from field to hand and draw one card from deck","Strategic Retreat");
        GeneralizedSpell warDrum = new GeneralizedSpell(new Spell[]{
                new APSpell(
                        EnumSet.of(SpellArea.ENEMY_MONSTERFIELD,SpellArea.ENEMY_PLAYER),
                        new Class[]{PlayerHero.class,MonsterCard.class},
                        EnumSet.of(Tribe.DEMONIC,Tribe.ATLANTIAN,Tribe.DRAGONBREED,Tribe.ELVEN),SpellChoiceType.SELECT,-500)
        },
                "Increase all friendly monster cards’ AP by 300","War Drum");
        GeneralizedSpell healingWard = new GeneralizedSpell(new Spell[]{
                new APSpell(
                        EnumSet.of(SpellArea.ENEMY_MONSTERFIELD,SpellArea.ENEMY_PLAYER),
                        new Class[]{PlayerHero.class,MonsterCard.class},
                        EnumSet.of(Tribe.DEMONIC,Tribe.ATLANTIAN,Tribe.DRAGONBREED,Tribe.ELVEN),SpellChoiceType.SELECT,-500)
        },
                "Increase all friendly monster cards’ HP by 200","Healing Ward");
        GeneralizedSpell bloodFeast = new GeneralizedSpell(new Spell[]{
                new APSpell(
                        EnumSet.of(SpellArea.ENEMY_MONSTERFIELD,SpellArea.ENEMY_PLAYER),
                        new Class[]{PlayerHero.class,MonsterCard.class},
                        EnumSet.of(Tribe.DEMONIC,Tribe.ATLANTIAN,Tribe.DRAGONBREED,Tribe.ELVEN),SpellChoiceType.SELECT,-500)
        },
                "Deal 500 damage to enemy player and heal your player for 500 HP","Blood Feast");
        GeneralizedSpell tsunami = new GeneralizedSpell(new Spell[]{
                new APSpell(
                        EnumSet.of(SpellArea.ENEMY_MONSTERFIELD,SpellArea.ENEMY_PLAYER),
                        new Class[]{PlayerHero.class,MonsterCard.class},
                        EnumSet.of(Tribe.DEMONIC,Tribe.ATLANTIAN,Tribe.DRAGONBREED,Tribe.ELVEN),SpellChoiceType.SELECT,-500)
        },
                "Deal 500 damage to all non-Atlantian monster cards on both sides of field","Tsunami");
        GeneralizedSpell takeAllYouCan = new GeneralizedSpell(new Spell[]{
                new APSpell(
                        EnumSet.of(SpellArea.ENEMY_MONSTERFIELD,SpellArea.ENEMY_PLAYER),
                        new Class[]{PlayerHero.class,MonsterCard.class},
                        EnumSet.of(Tribe.DEMONIC,Tribe.ATLANTIAN,Tribe.DRAGONBREED,Tribe.ELVEN),SpellChoiceType.SELECT,-500)
        },
                "Increase all friendly normal monster cards’ HP and AP by 400","Take All You Can");
        GeneralizedSpell arcaneBolt = new GeneralizedSpell(new Spell[]{
                new APSpell(
                        EnumSet.of(SpellArea.ENEMY_MONSTERFIELD,SpellArea.ENEMY_PLAYER),
                        new Class[]{PlayerHero.class,MonsterCard.class},
                        EnumSet.of(Tribe.DEMONIC,Tribe.ATLANTIAN,Tribe.DRAGONBREED,Tribe.ELVEN),SpellChoiceType.SELECT,-500)
        },
                "Deal 500 damage to enemy player and select and move an enemy spell card from field to graveyard","Arcane Bolt");
        GeneralizedSpell greaterPurge = new GeneralizedSpell(new Spell[]{
                new APSpell(
                        EnumSet.of(SpellArea.ENEMY_MONSTERFIELD,SpellArea.ENEMY_PLAYER),
                        new Class[]{PlayerHero.class,MonsterCard.class},
                        EnumSet.of(Tribe.DEMONIC,Tribe.ATLANTIAN,Tribe.DRAGONBREED,Tribe.ELVEN),SpellChoiceType.SELECT,-500)
        },
                "Remove all spell cards on field from both sides and move them to hand","Greater Purge");
        GeneralizedSpell magicSeal = new GeneralizedSpell(new Spell[]{
                new APSpell(
                        EnumSet.of(SpellArea.ENEMY_MONSTERFIELD,SpellArea.ENEMY_PLAYER),
                        new Class[]{PlayerHero.class,MonsterCard.class},
                        EnumSet.of(Tribe.DEMONIC,Tribe.ATLANTIAN,Tribe.DRAGONBREED,Tribe.ELVEN),SpellChoiceType.SELECT,-500)
        },
                "Remove all enemy spell cards from field and move them to graveyard","Magic Seal");
        GeneralizedSpell rejuvenation = new GeneralizedSpell(new Spell[]{
                new APSpell(
                        EnumSet.of(SpellArea.ENEMY_MONSTERFIELD,SpellArea.ENEMY_PLAYER),
                        new Class[]{PlayerHero.class,MonsterCard.class},
                        EnumSet.of(Tribe.DEMONIC,Tribe.ATLANTIAN,Tribe.DRAGONBREED,Tribe.ELVEN),SpellChoiceType.SELECT,-500)
        },
                "Increase a selected friendly monster card’s HP by 500 and AP by 300","Rejuvenation");
        GeneralizedSpell arcaneExplosion = new GeneralizedSpell(new Spell[]{
                new APSpell(
                        EnumSet.of(SpellArea.ENEMY_MONSTERFIELD,SpellArea.ENEMY_PLAYER),
                        new Class[]{PlayerHero.class,MonsterCard.class},
                        EnumSet.of(Tribe.DEMONIC,Tribe.ATLANTIAN,Tribe.DRAGONBREED,Tribe.ELVEN),SpellChoiceType.SELECT,-500)
        },
                "Deal 400 damage to all enemy monster cards and remove a random spell card from enemy field and move it to graveyard.","Arcane Explosion");
        GeneralizedSpell purge = new GeneralizedSpell(new Spell[]{
                new APSpell(
                        EnumSet.of(SpellArea.ENEMY_MONSTERFIELD,SpellArea.ENEMY_PLAYER),
                        new Class[]{PlayerHero.class,MonsterCard.class},
                        EnumSet.of(Tribe.DEMONIC,Tribe.ATLANTIAN,Tribe.DRAGONBREED,Tribe.ELVEN),SpellChoiceType.SELECT,-500)
        },
                "Remove all enemy spell cards on the field and move them to hand","Purge");
        GeneralizedSpell noblePurge = new GeneralizedSpell(new Spell[]{
                new APSpell(
                        EnumSet.of(SpellArea.ENEMY_MONSTERFIELD,SpellArea.ENEMY_PLAYER),
                        new Class[]{PlayerHero.class,MonsterCard.class},
                        EnumSet.of(Tribe.DEMONIC,Tribe.ATLANTIAN,Tribe.DRAGONBREED,Tribe.ELVEN),SpellChoiceType.SELECT,-500)
        },
                "Increase a random friendly Elven monster card on the field’s HP by 800 and AP by 600","Noble Purge");
        GeneralizedSpell reviveAllies = new GeneralizedSpell(new Spell[]{
                new APSpell(
                        EnumSet.of(SpellArea.ENEMY_MONSTERFIELD,SpellArea.ENEMY_PLAYER),
                        new Class[]{PlayerHero.class,MonsterCard.class},
                        EnumSet.of(Tribe.DEMONIC,Tribe.ATLANTIAN,Tribe.DRAGONBREED,Tribe.ELVEN),SpellChoiceType.SELECT,-500)
        },
                "move two random cards from your graveyard to hand","Revive Allies");
        GeneralizedSpell divineBlessing = new GeneralizedSpell(new Spell[]{
                new APSpell(
                        EnumSet.of(SpellArea.ENEMY_MONSTERFIELD,SpellArea.ENEMY_PLAYER),
                        new Class[]{PlayerHero.class,MonsterCard.class},
                        EnumSet.of(Tribe.DEMONIC,Tribe.ATLANTIAN,Tribe.DRAGONBREED,Tribe.ELVEN),SpellChoiceType.SELECT,-500)
        },
                "Increase HP of a friendly monster card or player by 2500","Divine Blessing");
        GeneralizedSpell burstofLight = new GeneralizedSpell(new Spell[]{
                new APSpell(
                        EnumSet.of(SpellArea.ENEMY_MONSTERFIELD,SpellArea.ENEMY_PLAYER),
                        new Class[]{PlayerHero.class,MonsterCard.class},
                        EnumSet.of(Tribe.DEMONIC,Tribe.ATLANTIAN,Tribe.DRAGONBREED,Tribe.ELVEN),SpellChoiceType.SELECT,-500)
        },
                "Increase HP of all friendly monster cards and player by 500 and increase AP of all friendly monster cards by 200","Burst of Light");
        GeneralizedSpell magicalFire = new GeneralizedSpell(new Spell[]{
                new APSpell(
                        EnumSet.of(SpellArea.ENEMY_MONSTERFIELD,SpellArea.ENEMY_PLAYER),
                        new Class[]{PlayerHero.class,MonsterCard.class},
                        EnumSet.of(Tribe.DEMONIC,Tribe.ATLANTIAN,Tribe.DRAGONBREED,Tribe.ELVEN),SpellChoiceType.SELECT,-500)
        },
                "Move an enemy monster card from field to graveyard","Magical Fire");
        GeneralizedSpell lavaSpit = new GeneralizedSpell(new Spell[]{
                new APSpell(
                        EnumSet.of(SpellArea.ENEMY_MONSTERFIELD,SpellArea.ENEMY_PLAYER),
                        new Class[]{PlayerHero.class,MonsterCard.class},
                        EnumSet.of(Tribe.DEMONIC,Tribe.ATLANTIAN,Tribe.DRAGONBREED,Tribe.ELVEN),SpellChoiceType.SELECT,-500)
        },
                "Deal 500 damage to an enemy monster card and reduce its AP by 500","Lava Spit");
        GeneralizedSpell Devour = new GeneralizedSpell(new Spell[]{
                new APSpell(
                        EnumSet.of(SpellArea.ENEMY_MONSTERFIELD,SpellArea.ENEMY_PLAYER),
                        new Class[]{PlayerHero.class,MonsterCard.class},
                        EnumSet.of(Tribe.DEMONIC,Tribe.ATLANTIAN,Tribe.DRAGONBREED,Tribe.ELVEN),SpellChoiceType.SELECT,-500)
        },
                "Send a random enemy monster card from field to graveyard","Devour");
        GeneralizedSpell dragonsCall = new GeneralizedSpell(new Spell[]{
                new APSpell(
                        EnumSet.of(SpellArea.ENEMY_MONSTERFIELD,SpellArea.ENEMY_PLAYER),
                        new Class[]{PlayerHero.class,MonsterCard.class},
                        EnumSet.of(Tribe.DEMONIC,Tribe.ATLANTIAN,Tribe.DRAGONBREED,Tribe.ELVEN),SpellChoiceType.SELECT,-500)
        },
                "draw two cards from deck to hand","Dragon’s Call");
        GeneralizedSpell kingsGrace = new GeneralizedSpell(new Spell[]{
                new APSpell(
                        EnumSet.of(SpellArea.ENEMY_MONSTERFIELD,SpellArea.ENEMY_PLAYER),
                        new Class[]{PlayerHero.class,MonsterCard.class},
                        EnumSet.of(Tribe.DEMONIC,Tribe.ATLANTIAN,Tribe.DRAGONBREED,Tribe.ELVEN),SpellChoiceType.SELECT,-500)
        },
                "Send all non-Hero monster cards on both sides of field to their graveyards","King’s Grace");
        GeneralizedSpell kingsWingSlash = new GeneralizedSpell(new Spell[]{
                new APSpell(
                        EnumSet.of(SpellArea.ENEMY_MONSTERFIELD,SpellArea.ENEMY_PLAYER),
                        new Class[]{PlayerHero.class,MonsterCard.class},
                        EnumSet.of(Tribe.DEMONIC,Tribe.ATLANTIAN,Tribe.DRAGONBREED,Tribe.ELVEN),SpellChoiceType.SELECT,-500)
        },
                "Deal 600 damage to all enemy monster cards and player","King’s Wing Slash");
        GeneralizedSpell kingsWail = new GeneralizedSpell(new Spell[]{
                new APSpell(
                        EnumSet.of(SpellArea.ENEMY_MONSTERFIELD,SpellArea.ENEMY_PLAYER),
                        new Class[]{PlayerHero.class,MonsterCard.class},
                        EnumSet.of(Tribe.DEMONIC,Tribe.ATLANTIAN,Tribe.DRAGONBREED,Tribe.ELVEN),SpellChoiceType.SELECT,-500)
        },
                "Decrease all enemy monster cards’ AP by 400","King’s Wail");
        GeneralizedSpell songoftheSiren = new GeneralizedSpell(new Spell[]{
                new APSpell(
                        EnumSet.of(SpellArea.ENEMY_MONSTERFIELD,SpellArea.ENEMY_PLAYER),
                        new Class[]{PlayerHero.class,MonsterCard.class},
                        EnumSet.of(Tribe.DEMONIC,Tribe.ATLANTIAN,Tribe.DRAGONBREED,Tribe.ELVEN),SpellChoiceType.SELECT,-500)
        },
                "Increase HP of all friendly monster cards by 300 and their AP by 200","Song of the Siren");
        GeneralizedSpell SerpentsBite = new GeneralizedSpell(new Spell[]{
                new APSpell(
                        EnumSet.of(SpellArea.ENEMY_MONSTERFIELD,SpellArea.ENEMY_PLAYER),
                        new Class[]{PlayerHero.class,MonsterCard.class},
                        EnumSet.of(Tribe.DEMONIC,Tribe.ATLANTIAN,Tribe.DRAGONBREED,Tribe.ELVEN),SpellChoiceType.SELECT,-500)
        },
                "Deal 1000 damage to an enemy monster card or player","Serpent’s Bite");
        GeneralizedSpell titansPresence = new GeneralizedSpell(new Spell[]{
                new APSpell(
                        EnumSet.of(SpellArea.ENEMY_MONSTERFIELD,SpellArea.ENEMY_PLAYER),
                        new Class[]{PlayerHero.class,MonsterCard.class},
                        EnumSet.of(Tribe.DEMONIC,Tribe.ATLANTIAN,Tribe.DRAGONBREED,Tribe.ELVEN),SpellChoiceType.SELECT,-500)
        },
                "Return one random enemy monster card from field to hand and reduce all enemy monsters’ AP by 200","Titan’s Presence");
        GeneralizedSpell titansFall = new GeneralizedSpell(new Spell[]{
                new APSpell(
                        EnumSet.of(SpellArea.ENEMY_MONSTERFIELD,SpellArea.ENEMY_PLAYER),
                        new Class[]{PlayerHero.class,MonsterCard.class},
                        EnumSet.of(Tribe.DEMONIC,Tribe.ATLANTIAN,Tribe.DRAGONBREED,Tribe.ELVEN),SpellChoiceType.SELECT,-500)
        },
                "Deal 400 damage to all enemy monster cards and player","Titan’s Fall");
        GeneralizedSpell calltoArms = new GeneralizedSpell(new Spell[]{
                new APSpell(
                        EnumSet.of(SpellArea.ENEMY_MONSTERFIELD,SpellArea.ENEMY_PLAYER),
                        new Class[]{PlayerHero.class,MonsterCard.class},
                        EnumSet.of(Tribe.DEMONIC,Tribe.ATLANTIAN,Tribe.DRAGONBREED,Tribe.ELVEN),SpellChoiceType.SELECT,-500)
        },
                "Select and move a card from hand to play field","Call to Arms");
        GeneralizedSpell tridentBeam = new GeneralizedSpell(new Spell[]{
                new APSpell(
                        EnumSet.of(SpellArea.ENEMY_MONSTERFIELD,SpellArea.ENEMY_PLAYER),
                        new Class[]{PlayerHero.class,MonsterCard.class},
                        EnumSet.of(Tribe.DEMONIC,Tribe.ATLANTIAN,Tribe.DRAGONBREED,Tribe.ELVEN),SpellChoiceType.SELECT,-500)
        },
                "Deal 800 damage to three random enemy monster cards or player","Trident Beam");
        GeneralizedSpell oceansCry = new GeneralizedSpell(new Spell[]{
                new APSpell(
                        EnumSet.of(SpellArea.ENEMY_MONSTERFIELD,SpellArea.ENEMY_PLAYER),
                        new Class[]{PlayerHero.class,MonsterCard.class},
                        EnumSet.of(Tribe.DEMONIC,Tribe.ATLANTIAN,Tribe.DRAGONBREED,Tribe.ELVEN),SpellChoiceType.SELECT,-500)
        },
                "Deal 400 damage to all enemy monster cards and player and increase all friendly Atlantian monster cards’ AP by 500","Ocean’s Cry");
        GeneralizedSpell mend = new GeneralizedSpell(new Spell[]{
                new APSpell(
                        EnumSet.of(SpellArea.ENEMY_MONSTERFIELD,SpellArea.ENEMY_PLAYER),
                        new Class[]{PlayerHero.class,MonsterCard.class},
                        EnumSet.of(Tribe.DEMONIC,Tribe.ATLANTIAN,Tribe.DRAGONBREED,Tribe.ELVEN),SpellChoiceType.SELECT,-500)
        },
                "Increase a friendly monster card or player’s HP by 400","Mend");
        GeneralizedSpell enrage = new GeneralizedSpell(new Spell[]{
                new APSpell(
                        EnumSet.of(SpellArea.ENEMY_MONSTERFIELD,SpellArea.ENEMY_PLAYER),
                        new Class[]{PlayerHero.class,MonsterCard.class},
                        EnumSet.of(Tribe.DEMONIC,Tribe.ATLANTIAN,Tribe.DRAGONBREED,Tribe.ELVEN),SpellChoiceType.SELECT,-500)
        },
                "Increase a friendly monster card’s AP by 400","Enrage");
        GeneralizedSpell warStomp = new GeneralizedSpell(new Spell[]{
                new APSpell(
                        EnumSet.of(SpellArea.ENEMY_MONSTERFIELD,SpellArea.ENEMY_PLAYER),
                        new Class[]{PlayerHero.class,MonsterCard.class},
                        EnumSet.of(Tribe.DEMONIC,Tribe.ATLANTIAN,Tribe.DRAGONBREED,Tribe.ELVEN),SpellChoiceType.SELECT,-500)
        },
                "Deal 400 damage to all enemy monster cards and player","War Stomp");
        GeneralizedSpell lastOrder = new GeneralizedSpell(new Spell[]{
                new APSpell(
                        EnumSet.of(SpellArea.ENEMY_MONSTERFIELD,SpellArea.ENEMY_PLAYER),
                        new Class[]{PlayerHero.class,MonsterCard.class},
                        EnumSet.of(Tribe.DEMONIC,Tribe.ATLANTIAN,Tribe.DRAGONBREED,Tribe.ELVEN),SpellChoiceType.SELECT,-500)
        },
                "Increase all friendly monster cards’ AP by 300","Last Order");
        GeneralizedSpell curse = new GeneralizedSpell(new Spell[]{
                new APSpell(
                        EnumSet.of(SpellArea.ENEMY_MONSTERFIELD,SpellArea.ENEMY_PLAYER),
                        new Class[]{PlayerHero.class,MonsterCard.class},
                        EnumSet.of(Tribe.DEMONIC,Tribe.ATLANTIAN,Tribe.DRAGONBREED,Tribe.ELVEN),SpellChoiceType.SELECT,-500)
        },
                "Reduce an enemy monster card’s AP by 500","Curse");
        GeneralizedSpell blackWave = new GeneralizedSpell(new Spell[]{
                new APSpell(
                        EnumSet.of(SpellArea.ENEMY_MONSTERFIELD,SpellArea.ENEMY_PLAYER),
                        new Class[]{PlayerHero.class,MonsterCard.class},
                        EnumSet.of(Tribe.DEMONIC,Tribe.ATLANTIAN,Tribe.DRAGONBREED,Tribe.ELVEN),SpellChoiceType.SELECT,-500)
        },
                "Deal 300 damage to all enemy monster cards and heal all friendly monster cards for 300 HP","Black Wave");
        GeneralizedSpell fear = new GeneralizedSpell(new Spell[]{
                new APSpell(
                        EnumSet.of(SpellArea.ENEMY_MONSTERFIELD,SpellArea.ENEMY_PLAYER),
                        new Class[]{PlayerHero.class,MonsterCard.class},
                        EnumSet.of(Tribe.DEMONIC,Tribe.ATLANTIAN,Tribe.DRAGONBREED,Tribe.ELVEN),SpellChoiceType.SELECT,-500)
        },
                "Return two random enemy monster cards from field to hand","Fear");
        GeneralizedSpell darkness = new GeneralizedSpell(new Spell[]{
                new APSpell(
                        EnumSet.of(SpellArea.ENEMY_MONSTERFIELD,SpellArea.ENEMY_PLAYER),
                        new Class[]{PlayerHero.class,MonsterCard.class},
                        EnumSet.of(Tribe.DEMONIC,Tribe.ATLANTIAN,Tribe.DRAGONBREED,Tribe.ELVEN),SpellChoiceType.SELECT,-500)
        },
                "Reduce all enemy monster cards’ AP by 200 and increase friendly monster cards AP by 200","Darkness");
        GeneralizedSpell evilGaze = new GeneralizedSpell(new Spell[]{
                new APSpell(
                        EnumSet.of(SpellArea.ENEMY_MONSTERFIELD,SpellArea.ENEMY_PLAYER),
                        new Class[]{PlayerHero.class,MonsterCard.class},
                        EnumSet.of(Tribe.DEMONIC,Tribe.ATLANTIAN,Tribe.DRAGONBREED,Tribe.ELVEN),SpellChoiceType.SELECT,-500)
        },
                "Deal 800 damage to all enemy monster cards and player","Evil Gaze");
        GeneralizedSpell raiseDead = new GeneralizedSpell(new Spell[]{
                new APSpell(
                        EnumSet.of(SpellArea.ENEMY_MONSTERFIELD,SpellArea.ENEMY_PLAYER),
                        new Class[]{PlayerHero.class,MonsterCard.class},
                        EnumSet.of(Tribe.DEMONIC,Tribe.ATLANTIAN,Tribe.DRAGONBREED,Tribe.ELVEN),SpellChoiceType.SELECT,-500)
        },
                "Move a random card from your graveyard to hand","Raise Dead");
        GeneralizedSpell sacrifice = new GeneralizedSpell(new Spell[]{
                new APSpell(
                        EnumSet.of(SpellArea.ENEMY_MONSTERFIELD,SpellArea.ENEMY_PLAYER),
                        new Class[]{PlayerHero.class,MonsterCard.class},
                        EnumSet.of(Tribe.DEMONIC,Tribe.ATLANTIAN,Tribe.DRAGONBREED,Tribe.ELVEN),SpellChoiceType.SELECT,-500)
        },
                "Select and move a card from hand to graveyard","Sacrifice");
        GeneralizedSpell loyalty = new GeneralizedSpell(new Spell[]{
                new APSpell(
                        EnumSet.of(SpellArea.ENEMY_MONSTERFIELD,SpellArea.ENEMY_PLAYER),
                        new Class[]{PlayerHero.class,MonsterCard.class},
                        EnumSet.of(Tribe.DEMONIC,Tribe.ATLANTIAN,Tribe.DRAGONBREED,Tribe.ELVEN),SpellChoiceType.SELECT,-500)
        },
                "Heal your player for 1000 HP","Loyalty");
        GeneralizedSpell opentheGate = new GeneralizedSpell(new Spell[]{
                new APSpell(
                        EnumSet.of(SpellArea.ENEMY_MONSTERFIELD,SpellArea.ENEMY_PLAYER),
                        new Class[]{PlayerHero.class,MonsterCard.class},
                        EnumSet.of(Tribe.DEMONIC,Tribe.ATLANTIAN,Tribe.DRAGONBREED,Tribe.ELVEN),SpellChoiceType.SELECT,-500)
        },
                "Draw three cards from deck to hand","Open the Gate");
        GeneralizedSpell hellfire = new GeneralizedSpell(new Spell[]{
                new APSpell(
                        EnumSet.of(SpellArea.ENEMY_MONSTERFIELD,SpellArea.ENEMY_PLAYER),
                        new Class[]{PlayerHero.class,MonsterCard.class},
                        EnumSet.of(Tribe.DEMONIC,Tribe.ATLANTIAN,Tribe.DRAGONBREED,Tribe.ELVEN),SpellChoiceType.SELECT,-500)
        },
                "Deal 300 damage to all enemy monster cards and Increase HP and AP of all friendly\n" +
                        "monster cards by 300","Hellfire");
        GeneralizedSpell revengeoftheTwoHeads = new GeneralizedSpell(new Spell[]{
                new APSpell(
                        EnumSet.of(SpellArea.ENEMY_MONSTERFIELD,SpellArea.ENEMY_PLAYER),
                        new Class[]{PlayerHero.class,MonsterCard.class},
                        EnumSet.of(Tribe.DEMONIC,Tribe.ATLANTIAN,Tribe.DRAGONBREED,Tribe.ELVEN),SpellChoiceType.SELECT,-500)
        },
                "Send two random enemy monster cards from field to garveyard","Revenge of the Two Heads");
        GeneralizedSpell smallHpPotion = new GeneralizedSpell(new Spell[]{
                new APSpell(
                        EnumSet.of(SpellArea.ENEMY_MONSTERFIELD,SpellArea.ENEMY_PLAYER),
                        new Class[]{PlayerHero.class,MonsterCard.class},
                        EnumSet.of(Tribe.DEMONIC,Tribe.ATLANTIAN,Tribe.DRAGONBREED,Tribe.ELVEN),SpellChoiceType.SELECT,-500)
        },
                "Increase Player’s HP by 500","Small HP Potion");
        GeneralizedSpell mediumHpPotion = new GeneralizedSpell(new Spell[]{
                new APSpell(
                        EnumSet.of(SpellArea.ENEMY_MONSTERFIELD,SpellArea.ENEMY_PLAYER),
                        new Class[]{PlayerHero.class,MonsterCard.class},
                        EnumSet.of(Tribe.DEMONIC,Tribe.ATLANTIAN,Tribe.DRAGONBREED,Tribe.ELVEN),SpellChoiceType.SELECT,-500)
        },
                "Increase Player’s HP by 1000","Medium HP Potion");
        GeneralizedSpell largeHpPotion = new GeneralizedSpell(new Spell[]{
                new APSpell(
                        EnumSet.of(SpellArea.ENEMY_MONSTERFIELD,SpellArea.ENEMY_PLAYER),
                        new Class[]{PlayerHero.class,MonsterCard.class},
                        EnumSet.of(Tribe.DEMONIC,Tribe.ATLANTIAN,Tribe.DRAGONBREED,Tribe.ELVEN),SpellChoiceType.SELECT,-500)
        },
                "Increase Player’s HP by 2000","Large HP Potion");
        GeneralizedSpell smallMpPotion = new GeneralizedSpell(new Spell[]{
                new APSpell(
                        EnumSet.of(SpellArea.ENEMY_MONSTERFIELD,SpellArea.ENEMY_PLAYER),
                        new Class[]{PlayerHero.class,MonsterCard.class},
                        EnumSet.of(Tribe.DEMONIC,Tribe.ATLANTIAN,Tribe.DRAGONBREED,Tribe.ELVEN),SpellChoiceType.SELECT,-500)
        },
                "Increase Player’s MP by 2","Small MP Potion");
        GeneralizedSpell mediumMpPotion = new GeneralizedSpell(new Spell[]{
                new APSpell(
                        EnumSet.of(SpellArea.ENEMY_MONSTERFIELD,SpellArea.ENEMY_PLAYER),
                        new Class[]{PlayerHero.class,MonsterCard.class},
                        EnumSet.of(Tribe.DEMONIC,Tribe.ATLANTIAN,Tribe.DRAGONBREED,Tribe.ELVEN),SpellChoiceType.SELECT,-500)
        },
                "Increase Player’s MP by 4","Medium MP Potion");
        GeneralizedSpell largeMPPotion = new GeneralizedSpell(new Spell[]{
                new APSpell(
                        EnumSet.of(SpellArea.ENEMY_MONSTERFIELD,SpellArea.ENEMY_PLAYER),
                        new Class[]{PlayerHero.class,MonsterCard.class},
                        EnumSet.of(Tribe.DEMONIC,Tribe.ATLANTIAN,Tribe.DRAGONBREED,Tribe.ELVEN),SpellChoiceType.SELECT,-500)
        },
                "Increase Player’s MP by 8","Large MP Potion");
        GeneralizedSpell lesserRestorative = new GeneralizedSpell(new Spell[]{
                new APSpell(
                        EnumSet.of(SpellArea.ENEMY_MONSTERFIELD,SpellArea.ENEMY_PLAYER),
                        new Class[]{PlayerHero.class,MonsterCard.class},
                        EnumSet.of(Tribe.DEMONIC,Tribe.ATLANTIAN,Tribe.DRAGONBREED,Tribe.ELVEN),SpellChoiceType.SELECT,-500)
        },
                "Increase Player’s HP by 500 and MP by 2","Lesser Restorative");
        GeneralizedSpell greaterRestorative = new GeneralizedSpell(new Spell[]{
                new APSpell(
                        EnumSet.of(SpellArea.ENEMY_MONSTERFIELD,SpellArea.ENEMY_PLAYER),
                        new Class[]{PlayerHero.class,MonsterCard.class},
                        EnumSet.of(Tribe.DEMONIC,Tribe.ATLANTIAN,Tribe.DRAGONBREED,Tribe.ELVEN),SpellChoiceType.SELECT,-500)
        },
                "Increase Player’s HP by 1000 and MP by 4","Greater Restorative");
        GeneralizedSpell ironPendant = new GeneralizedSpell(new Spell[]{
                new APSpell(
                        EnumSet.of(SpellArea.ENEMY_MONSTERFIELD,SpellArea.ENEMY_PLAYER),
                        new Class[]{PlayerHero.class,MonsterCard.class},
                        EnumSet.of(Tribe.DEMONIC,Tribe.ATLANTIAN,Tribe.DRAGONBREED,Tribe.ELVEN),SpellChoiceType.SELECT,-500)
        },
                "Increase Player’s Max HP by 500","Iron Pendant");
        GeneralizedSpell goldPendant = new GeneralizedSpell(new Spell[]{
                new APSpell(
                        EnumSet.of(SpellArea.ENEMY_MONSTERFIELD,SpellArea.ENEMY_PLAYER),
                        new Class[]{PlayerHero.class,MonsterCard.class},
                        EnumSet.of(Tribe.DEMONIC,Tribe.ATLANTIAN,Tribe.DRAGONBREED,Tribe.ELVEN),SpellChoiceType.SELECT,-500)
        },
                "Increase Player’s Max HP by 1000","Gold Pendant");
        GeneralizedSpell diamondPendant = new GeneralizedSpell(new Spell[]{
                new APSpell(
                        EnumSet.of(SpellArea.ENEMY_MONSTERFIELD,SpellArea.ENEMY_PLAYER),
                        new Class[]{PlayerHero.class,MonsterCard.class},
                        EnumSet.of(Tribe.DEMONIC,Tribe.ATLANTIAN,Tribe.DRAGONBREED,Tribe.ELVEN),SpellChoiceType.SELECT,-500)
        },
                "Increase Player’s Max HP by 2000","Diamond Pendant");
        GeneralizedSpell ironRing = new GeneralizedSpell(new Spell[]{
                new APSpell(
                        EnumSet.of(SpellArea.ENEMY_MONSTERFIELD,SpellArea.ENEMY_PLAYER),
                        new Class[]{PlayerHero.class,MonsterCard.class},
                        EnumSet.of(Tribe.DEMONIC,Tribe.ATLANTIAN,Tribe.DRAGONBREED,Tribe.ELVEN),SpellChoiceType.SELECT,-500)
        },
                "Increase Player’s Max MP by 1","Iron Ring");
        GeneralizedSpell goldRing = new GeneralizedSpell(new Spell[]{
                new APSpell(
                        EnumSet.of(SpellArea.ENEMY_MONSTERFIELD,SpellArea.ENEMY_PLAYER),
                        new Class[]{PlayerHero.class,MonsterCard.class},
                        EnumSet.of(Tribe.DEMONIC,Tribe.ATLANTIAN,Tribe.DRAGONBREED,Tribe.ELVEN),SpellChoiceType.SELECT,-500)
        },
                "Increase Player’s Max MP by 2","Gold Ring");
        GeneralizedSpell diamondRing = new GeneralizedSpell(new Spell[]{
                new APSpell(
                        EnumSet.of(SpellArea.ENEMY_MONSTERFIELD,SpellArea.ENEMY_PLAYER),
                        new Class[]{PlayerHero.class,MonsterCard.class},
                        EnumSet.of(Tribe.DEMONIC,Tribe.ATLANTIAN,Tribe.DRAGONBREED,Tribe.ELVEN),SpellChoiceType.SELECT,-500)
        },
                "Increase Player’s Max MP by 3","Diamond Ring");
    }
}
