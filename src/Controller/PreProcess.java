package Controller;

import Model.*;
import Model.Card.*;
import Model.Card.MonsterCard;
import Model.Card.SpellCard;
import Model.Card.SpellCardType;
import Model.Card.Tribe;
import Model.Spell.*;
import Model.Spell.SpellArea;

import static Controller.Main.*;
import static Model.Spell.GeneralizedSpell.*;
import static Model.Stuff.allStuff;
import static Model.Stuff.getStuffByName;
import static Model.TypeOfStuffToBuyAndSell.getTypeOfStuffByName;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.EnumSet;

public class PreProcess {
    static void preProcess() throws Exception{
        instantiateSpells();
        instantiateSpellCards();
        instantiateMonsterCards();
        instantiateItems();
        instantiateAmulets();
        instantiateHumanShop();
        instantiatePlayerInventory(human, "database/Inventories/HumanInventory.txt");
        instantiatePlayerInventory(goblinChieftain, "database/Inventories/GoblinChieftainInventory.txt");
        instantiatePlayerInventory(ogreWarlord, "database/Inventories/OgreWarlordInventory.txt");
        instantiatePlayerInventory(vampireLord, "database/Inventories/VampireLordInventory.txt");
        instantiatePlayerInventory(lucifer, "database/Inventories/LuciferInventory.txt");
    }

    private static void instantiateSpells(){
        GeneralizedSpell throwingKnives = new GeneralizedSpell(new Spell[]{
                new HPSpell(
                        EnumSet.of(SpellArea.ENEMY_MONSTERFIELD,SpellArea.ENEMY_PLAYER),
                        new Class[]{PlayerHero.class,MonsterCard.class},
                         SpellChoiceType.SELECT,-500)
        },
                "Deal 500 damage to a selected enemy monster card on the field or to enemy player","Throwing Knives");
        GeneralizedSpell poisonousCauldron = new GeneralizedSpell(new Spell[]{
                new HPSpell(
                        EnumSet.of(SpellArea.ENEMY_MONSTERFIELD,SpellArea.ENEMY_PLAYER),
                        new Class[]{PlayerHero.class,MonsterCard.class},
                        SpellChoiceType.ALL,-100)
        },
                "Deal 100 damage to all enemy monster cards and enemy player","Poisonous Cauldron");
        GeneralizedSpell firstAidKit = new GeneralizedSpell(new Spell[]{
                new HPSpell(
                        EnumSet.of(SpellArea.FRIENDLY_MONSTERFIELD,SpellArea.FRIENDLY_PLAYER),
                        new Class[]{PlayerHero.class,MonsterCard.class},
                        SpellChoiceType.SELECT,500)
        },
                "Increase HP of a selected friendly monster card or player by 500","First Aid Kit");
        GeneralizedSpell reapersScythe = new GeneralizedSpell(new Spell[]{
                new MoveSpell(
                        EnumSet.of(SpellArea.ENEMY_MONSTERFIELD,SpellArea.ENEMY_SPELLFIELD),
                        new Class[]{SpellCard.class,MonsterCard.class},
                        SpellChoiceType.SELECT,SpellArea.ENEMY_GRAVEYARD)
        },
                "Send an enemy monster or spell card from field to graveyard","Reaper's Scythe");
        GeneralizedSpell meteorShower = new GeneralizedSpell(new Spell[]{
                new HPSpell(
                        EnumSet.of(SpellArea.ENEMY_MONSTERFIELD,SpellArea.ENEMY_PLAYER),
                        new Class[]{PlayerHero.class,MonsterCard.class},
                        SpellChoiceType.RANDOM,-800)
        },
                "Deal 800 damage to a random enemy monster card on field or player","Meteor Shower");
        GeneralizedSpell lunarBlessing = new GeneralizedSpell(new Spell[]{
                new APSpell(
                        EnumSet.of(SpellArea.FRIENDLY_MONSTERFIELD),
                        new Class[]{MonsterCard.class},
                        EnumSet.of(Tribe.ELVEN),SpellChoiceType.ALL,300),
                new HPSpell(
                        EnumSet.of(SpellArea.FRIENDLY_MONSTERFIELD),
                        new Class[]{MonsterCard.class},
                        EnumSet.of(Tribe.ELVEN),SpellChoiceType.ALL,300)
        },
                "Increase AP and HP of friendly Elven monster cards by 300","Lunar Blessing");
        GeneralizedSpell strategicRetreat = new GeneralizedSpell(new Spell[]{
                new MoveSpell(
                        EnumSet.of(SpellArea.FRIENDLY_MONSTERFIELD),
                        new Class[]{MonsterCard.class},
                        SpellChoiceType.SELECT,SpellArea.FRIENDLY_HAND),
                new MoveSpell(
                        EnumSet.of(SpellArea.FRIENDLY_DECK),
                        new Class[]{Card.class},
                        SpellChoiceType.RANDOM,SpellArea.FRIENDLY_HAND)
        },
                "Select and move a monster card from field to hand and draw one card from deck","Strategic Retreat");
        GeneralizedSpell warDrum = new GeneralizedSpell(new Spell[]{
                new APSpell(
                        EnumSet.of(SpellArea.FRIENDLY_MONSTERFIELD),
                        new Class[]{MonsterCard.class},
                        SpellChoiceType.ALL,300)
        },
                "Increase all friendly monster cards’ AP by 300","War Drum");
        GeneralizedSpell healingWard = new GeneralizedSpell(new Spell[]{
                new HPSpell(
                        EnumSet.of(SpellArea.FRIENDLY_MONSTERFIELD),
                        new Class[]{MonsterCard.class},
                        SpellChoiceType.ALL,200)
        },
                "Increase all friendly monster cards’ HP by 200","Healing Ward");
        GeneralizedSpell bloodFeast = new GeneralizedSpell(new Spell[]{
                new HPSpell(
                        EnumSet.of(SpellArea.ENEMY_PLAYER),
                        new Class[]{PlayerHero.class},
                        SpellChoiceType.ALL,-500),
                new HPSpell(
                        EnumSet.of(SpellArea.FRIENDLY_PLAYER),
                        new Class[]{PlayerHero.class},
                        SpellChoiceType.ALL,500)
        },
                "Deal 500 damage to enemy player and heal your player for 500 HP","Blood Feast");
        GeneralizedSpell tsunami = new GeneralizedSpell(new Spell[]{
                new HPSpell(
                        EnumSet.of(SpellArea.ENEMY_MONSTERFIELD,SpellArea.FRIENDLY_MONSTERFIELD),
                        new Class[]{MonsterCard.class},
                        EnumSet.of(Tribe.DEMONIC,Tribe.DRAGONBREED,Tribe.ELVEN),SpellChoiceType.ALL,-500)
        },
                "Deal 500 damage to all non-Atlantian monster cards on both sides of field","Tsunami");
        GeneralizedSpell takeAllYouCan = new GeneralizedSpell(new Spell[]{
                new APSpell(
                        EnumSet.of(SpellArea.FRIENDLY_MONSTERFIELD),
                        new Class[]{NormalCard.class},
                        SpellChoiceType.ALL,400),
                new HPSpell(
                        EnumSet.of(SpellArea.FRIENDLY_MONSTERFIELD),
                        new Class[]{NormalCard.class},
                        SpellChoiceType.ALL,400)
        },
                "Increase all friendly normal monster cards’ HP and AP by 400","Take All You Can");
        GeneralizedSpell arcaneBolt = new GeneralizedSpell(new Spell[]{
                new HPSpell(
                        EnumSet.of(SpellArea.ENEMY_PLAYER),
                        new Class[]{PlayerHero.class},
                        SpellChoiceType.ALL,-500),
                new MoveSpell(
                        EnumSet.of(SpellArea.ENEMY_SPELLFIELD),
                        new Class[]{SpellCard.class},
                        SpellChoiceType.SELECT,SpellArea.ENEMY_GRAVEYARD)
        },
                "Deal 500 damage to enemy player and select and move an enemy spell card from field to graveyard","Arcane Bolt");
        GeneralizedSpell greaterPurge = new GeneralizedSpell(new Spell[]{
                new MoveSpell(
                        EnumSet.of(SpellArea.ENEMY_SPELLFIELD,SpellArea.FRIENDLY_SPELLFIELD),
                        new Class[]{SpellCard.class},
                        SpellChoiceType.ALL,SpellArea.FRIENDLY_HAND)
        },
                "Remove all spell cards on field from both sides and move them to hand","Greater Purge");
        GeneralizedSpell magicSeal = new GeneralizedSpell(new Spell[]{
                new MoveSpell(
                        EnumSet.of(SpellArea.ENEMY_SPELLFIELD),
                        new Class[]{SpellCard.class},
                        SpellChoiceType.ALL,SpellArea.ENEMY_GRAVEYARD)
        },
                "Remove all enemy spell cards from field and move them to graveyard","Magic Seal");
        GeneralizedSpell rejuvenation = new GeneralizedSpell(new Spell[]{
                new HPSpell(
                        EnumSet.of(SpellArea.FRIENDLY_MONSTERFIELD),
                        new Class[]{MonsterCard.class},
                        SpellChoiceType.SELECT,500),
                new APSpell(
                        EnumSet.of(SpellArea.FRIENDLY_MONSTERFIELD),
                        new Class[]{MonsterCard.class},
                        SpellChoiceType.SELECT,300)
        },
                "Increase a selected friendly monster card’s HP by 500 and AP by 300","Rejuvenation", true);
        GeneralizedSpell arcaneExplosion = new GeneralizedSpell(new Spell[]{
                new HPSpell(
                        EnumSet.of(SpellArea.ENEMY_MONSTERFIELD),
                        new Class[]{MonsterCard.class},
                        SpellChoiceType.ALL,-400),
                new MoveSpell(
                        EnumSet.of(SpellArea.ENEMY_SPELLFIELD),
                        new Class[]{SpellCard.class},
                        SpellChoiceType.RANDOM,SpellArea.ENEMY_GRAVEYARD)
        },
                "Deal 400 damage to all enemy monster cards and remove a random spell card from enemy field and move it to graveyard.","Arcane Explosion");
        GeneralizedSpell purge = new GeneralizedSpell(new Spell[]{
                new MoveSpell(
                        EnumSet.of(SpellArea.ENEMY_SPELLFIELD),
                        new Class[]{SpellCard.class},
                        SpellChoiceType.ALL,SpellArea.FRIENDLY_HAND)
        },
                "Remove all enemy spell cards on the field and move them to hand","Purge");
        GeneralizedSpell noblePurge = new GeneralizedSpell(new Spell[]{
                new APSpell(
                        EnumSet.of(SpellArea.FRIENDLY_MONSTERFIELD),
                        new Class[]{MonsterCard.class},
                        EnumSet.of(Tribe.ELVEN),SpellChoiceType.RANDOM,600),
                new HPSpell(
                        EnumSet.of(SpellArea.FRIENDLY_MONSTERFIELD),
                        new Class[]{MonsterCard.class},
                        EnumSet.of(Tribe.ELVEN),SpellChoiceType.RANDOM,800)
        },
                "Increase a random friendly Elven monster card on the field’s HP by 800 and AP by 600","Noble Purge",true);//this spell has no name in doc!
        GeneralizedSpell reviveAllies = new GeneralizedSpell(new Spell[]{
                new MoveSpell(
                        EnumSet.of(SpellArea.FRIENDLY_GRAVEYARD),
                        new Class[]{Card.class},
                        SpellChoiceType.RANDOM,SpellArea.FRIENDLY_HAND),
                new MoveSpell(
                        EnumSet.of(SpellArea.FRIENDLY_GRAVEYARD),
                        new Class[]{Card.class},
                        SpellChoiceType.RANDOM,SpellArea.FRIENDLY_HAND)
        },
                "move two random cards from your graveyard to hand","Revive Allies");
        GeneralizedSpell divineBlessing = new GeneralizedSpell(new Spell[]{
                new HPSpell(
                        EnumSet.of(SpellArea.FRIENDLY_MONSTERFIELD,SpellArea.FRIENDLY_PLAYER),
                        new Class[]{PlayerHero.class,MonsterCard.class},
                        SpellChoiceType.SELECT,2500)
        },
                "Increase HP of a friendly monster card or player by 2500","Divine Blessing");
        GeneralizedSpell burstofLight = new GeneralizedSpell(new Spell[]{
                new HPSpell(
                        EnumSet.of(SpellArea.FRIENDLY_MONSTERFIELD,SpellArea.FRIENDLY_PLAYER),
                        new Class[]{PlayerHero.class,MonsterCard.class},
                        SpellChoiceType.ALL,200),
                new APSpell(
                        EnumSet.of(SpellArea.FRIENDLY_MONSTERFIELD),
                        new Class[]{MonsterCard.class},
                        SpellChoiceType.ALL,200)
        },
                "Increase HP of all friendly monster cards and player by 500 and increase AP of all friendly monster cards by 200","Burst of Light");
        GeneralizedSpell magicalFire = new GeneralizedSpell(new Spell[]{
                new MoveSpell(
                        EnumSet.of(SpellArea.ENEMY_MONSTERFIELD),
                        new Class[]{MonsterCard.class},
                        SpellChoiceType.SELECT,SpellArea.ENEMY_GRAVEYARD)
        },
                "Move an enemy monster card from field to graveyard","Magical Fire");
        GeneralizedSpell lavaSpit = new GeneralizedSpell(new Spell[]{
                new HPSpell(
                        EnumSet.of(SpellArea.ENEMY_MONSTERFIELD),
                        new Class[]{MonsterCard.class},
                        SpellChoiceType.SELECT,-500),
                new APSpell(
                        EnumSet.of(SpellArea.ENEMY_MONSTERFIELD),
                        new Class[]{MonsterCard.class},
                        SpellChoiceType.SELECT,-500)
        },
                "Deal 500 damage to an enemy monster card and reduce its AP by 500","Lava Spit",true);
        GeneralizedSpell Devour = new GeneralizedSpell(new Spell[]{
                new MoveSpell(
                        EnumSet.of(SpellArea.ENEMY_MONSTERFIELD),
                        new Class[]{MonsterCard.class},
                        SpellChoiceType.RANDOM,SpellArea.ENEMY_GRAVEYARD)
        },
                "Send a random enemy monster card from field to graveyard","Devour");
        GeneralizedSpell dragonsCall = new GeneralizedSpell(new Spell[]{
                new MoveSpell(
                        EnumSet.of(SpellArea.FRIENDLY_DECK),
                        new Class[]{Card.class},
                        SpellChoiceType.RANDOM,SpellArea.FRIENDLY_HAND),
                new MoveSpell(
                        EnumSet.of(SpellArea.FRIENDLY_DECK),
                        new Class[]{Card.class},
                        SpellChoiceType.RANDOM,SpellArea.FRIENDLY_HAND)
        },
                "draw two cards from deck to hand","Dragon's Call");
        GeneralizedSpell kingsGrace = new GeneralizedSpell(new Spell[]{
                new MoveSpell(
                        EnumSet.of(SpellArea.ENEMY_MONSTERFIELD),
                        new Class[]{NormalCard.class,GeneralCard.class,SpellcasterCard.class},
                        SpellChoiceType.ALL,SpellArea.ENEMY_GRAVEYARD),
                new MoveSpell(
                        EnumSet.of(SpellArea.FRIENDLY_MONSTERFIELD),
                        new Class[]{NormalCard.class,GeneralCard.class,SpellcasterCard.class},
                        SpellChoiceType.ALL,SpellArea.FRIENDLY_GRAVEYARD)
        },
                "Send all non-Hero monster cards on both sides of field to their graveyards","King's Grace");
        GeneralizedSpell kingsWingSlash = new GeneralizedSpell(new Spell[]{
                new HPSpell(
                        EnumSet.of(SpellArea.ENEMY_MONSTERFIELD,SpellArea.ENEMY_PLAYER),
                        new Class[]{PlayerHero.class,MonsterCard.class},
                        SpellChoiceType.ALL,-600)
        },
                "Deal 600 damage to all enemy monster cards and player","King's Wing Slash");
        GeneralizedSpell kingsWail = new GeneralizedSpell(new Spell[]{
                new APSpell(
                        EnumSet.of(SpellArea.ENEMY_MONSTERFIELD),
                        new Class[]{MonsterCard.class},
                        SpellChoiceType.ALL,-400)
        },
                "Decrease all enemy monster cards’ AP by 400","King's Wail");
        GeneralizedSpell songoftheSiren = new GeneralizedSpell(new Spell[]{
                new APSpell(
                        EnumSet.of(SpellArea.FRIENDLY_MONSTERFIELD),
                        new Class[]{MonsterCard.class},
                        SpellChoiceType.ALL,200),
                new HPSpell(
                        EnumSet.of(SpellArea.FRIENDLY_MONSTERFIELD),
                        new Class[]{MonsterCard.class},
                        SpellChoiceType.ALL,300)
        },
                "Increase HP of all friendly monster cards by 300 and their AP by 200","Song of the Siren");
        GeneralizedSpell SerpentsBite = new GeneralizedSpell(new Spell[]{
                new HPSpell(
                        EnumSet.of(SpellArea.ENEMY_MONSTERFIELD,SpellArea.ENEMY_PLAYER),
                        new Class[]{PlayerHero.class,MonsterCard.class},
                        SpellChoiceType.SELECT,-1000)
        },
                "Deal 1000 damage to an enemy monster card or player","Serpent's Bite");
        GeneralizedSpell titansPresence = new GeneralizedSpell(new Spell[]{
                new MoveSpell(
                        EnumSet.of(SpellArea.ENEMY_MONSTERFIELD),
                        new Class[]{MonsterCard.class},
                        SpellChoiceType.RANDOM,SpellArea.ENEMY_HAND),
                new APSpell(
                        EnumSet.of(SpellArea.ENEMY_MONSTERFIELD),
                        new Class[]{MonsterCard.class},
                        SpellChoiceType.SELECT,-200)
        },
                "Return one random enemy monster card from field to hand and reduce all enemy monsters AP by 200","Titan's Presence");
        GeneralizedSpell titansFall = new GeneralizedSpell(new Spell[]{
                new HPSpell(
                        EnumSet.of(SpellArea.ENEMY_MONSTERFIELD,SpellArea.ENEMY_PLAYER),
                        new Class[]{PlayerHero.class,MonsterCard.class},
                        SpellChoiceType.ALL,-400)
        },
                "Deal 400 damage to all enemy monster cards and player","Titan's Fall");
        GeneralizedSpell calltoArms = new GeneralizedSpell(new Spell[]{
                new MoveSpell(
                        EnumSet.of(SpellArea.FRIENDLY_HAND),
                        new Class[]{MonsterCard.class},
                        SpellChoiceType.SELECT,SpellArea.FRIENDLY_MONSTERFIELD)
        },
                "Select and move a monster card from hand to monster field","Call to Arms");//changed a little bit
        GeneralizedSpell tridentBeam = new GeneralizedSpell(new Spell[]{
                new HPSpell(
                        EnumSet.of(SpellArea.ENEMY_MONSTERFIELD,SpellArea.ENEMY_PLAYER),
                        new Class[]{PlayerHero.class,MonsterCard.class},
                        SpellChoiceType.RANDOM,-800),
                new HPSpell(
                        EnumSet.of(SpellArea.ENEMY_MONSTERFIELD,SpellArea.ENEMY_PLAYER),
                        new Class[]{PlayerHero.class,MonsterCard.class},
                        SpellChoiceType.RANDOM,-800),
                new HPSpell(
                        EnumSet.of(SpellArea.ENEMY_MONSTERFIELD,SpellArea.ENEMY_PLAYER),
                        new Class[]{PlayerHero.class,MonsterCard.class},
                        SpellChoiceType.RANDOM,-800)
        },
                "Deal 800 damage to three random enemy monster cards or player","Trident Beam");
        GeneralizedSpell oceansCry = new GeneralizedSpell(new Spell[]{
                new HPSpell(
                        EnumSet.of(SpellArea.ENEMY_MONSTERFIELD,SpellArea.ENEMY_PLAYER),
                        new Class[]{PlayerHero.class,MonsterCard.class},
                        SpellChoiceType.ALL,-400),
                new APSpell(
                        EnumSet.of(SpellArea.FRIENDLY_MONSTERFIELD),
                        new Class[]{MonsterCard.class},
                        EnumSet.of(Tribe.ATLANTIAN),SpellChoiceType.ALL,500)
        },
                "Deal 400 damage to all enemy monster cards and player and increase all friendly Atlantian monster cards’ AP by 500","Ocean's Cry");
        GeneralizedSpell mend = new GeneralizedSpell(new Spell[]{
                new HPSpell(
                        EnumSet.of(SpellArea.FRIENDLY_MONSTERFIELD,SpellArea.FRIENDLY_PLAYER),
                        new Class[]{PlayerHero.class,MonsterCard.class},
                        SpellChoiceType.SELECT,400)
        },
                "Increase a friendly monster card or player’s HP by 400","Mend");
        GeneralizedSpell enrage = new GeneralizedSpell(new Spell[]{
                new APSpell(
                        EnumSet.of(SpellArea.FRIENDLY_MONSTERFIELD),
                        new Class[]{MonsterCard.class},
                        SpellChoiceType.SELECT,400)
        },
                "Increase a friendly monster card’s AP by 400","Enrage");
        GeneralizedSpell warStomp = new GeneralizedSpell(new Spell[]{
                new HPSpell(
                        EnumSet.of(SpellArea.ENEMY_MONSTERFIELD,SpellArea.ENEMY_PLAYER),
                        new Class[]{PlayerHero.class,MonsterCard.class},
                        SpellChoiceType.ALL,-400)
        },
                "Deal 400 damage to all enemy monster cards and player","War Stomp");
        GeneralizedSpell lastOrder = new GeneralizedSpell(new Spell[]{
                new APSpell(
                        EnumSet.of(SpellArea.FRIENDLY_MONSTERFIELD),
                        new Class[]{MonsterCard.class},
                        SpellChoiceType.ALL,300)
        },
                "Increase all friendly monster cards’ AP by 300","Last Order");
        GeneralizedSpell curse = new GeneralizedSpell(new Spell[]{
                new APSpell(
                        EnumSet.of(SpellArea.ENEMY_MONSTERFIELD),
                        new Class[]{MonsterCard.class},
                        SpellChoiceType.SELECT,-500)
        },
                "Reduce an enemy monster card’s AP by 500","Curse");
        GeneralizedSpell blackWave = new GeneralizedSpell(new Spell[]{
                new HPSpell(
                        EnumSet.of(SpellArea.ENEMY_MONSTERFIELD),
                        new Class[]{MonsterCard.class},
                        SpellChoiceType.ALL,-300),
                new HPSpell(
                        EnumSet.of(SpellArea.FRIENDLY_MONSTERFIELD),
                        new Class[]{MonsterCard.class},
                        SpellChoiceType.ALL,300)
        },
                "Deal 300 damage to all enemy monster cards and heal all friendly monster cards for 300 HP","Black Wave");
        GeneralizedSpell fear = new GeneralizedSpell(new Spell[]{
                new MoveSpell(
                        EnumSet.of(SpellArea.ENEMY_MONSTERFIELD),
                        new Class[]{MonsterCard.class},
                        SpellChoiceType.RANDOM,SpellArea.ENEMY_HAND),
                new MoveSpell(
                        EnumSet.of(SpellArea.ENEMY_MONSTERFIELD),
                        new Class[]{MonsterCard.class},
                        SpellChoiceType.RANDOM,SpellArea.ENEMY_HAND)
        },
                "Return two random enemy monster cards from field to hand","Fear");
        GeneralizedSpell darkness = new GeneralizedSpell(new Spell[]{
                new APSpell(
                        EnumSet.of(SpellArea.ENEMY_MONSTERFIELD),
                        new Class[]{MonsterCard.class},
                        SpellChoiceType.ALL,-200),
                new APSpell(
                        EnumSet.of(SpellArea.FRIENDLY_MONSTERFIELD),
                        new Class[]{MonsterCard.class},
                        SpellChoiceType.ALL,200)
        },
                "Reduce all enemy monster cards’ AP by 200 and increase friendly monster cards AP by 200","Darkness");
        GeneralizedSpell evilGaze = new GeneralizedSpell(new Spell[]{
                new HPSpell(
                        EnumSet.of(SpellArea.ENEMY_MONSTERFIELD,SpellArea.ENEMY_PLAYER),
                        new Class[]{PlayerHero.class,MonsterCard.class},
                        SpellChoiceType.ALL,-800)
        },
                "Deal 800 damage to all enemy monster cards and player","Evil Gaze");
        GeneralizedSpell raiseDead = new GeneralizedSpell(new Spell[]{
                new MoveSpell(
                        EnumSet.of(SpellArea.FRIENDLY_GRAVEYARD),
                        new Class[]{Card.class},
                        SpellChoiceType.RANDOM,SpellArea.FRIENDLY_HAND)
        },
                "Move a random card from your graveyard to hand","Raise Dead");
        GeneralizedSpell sacrifice = new GeneralizedSpell(new Spell[]{
                new MoveSpell(
                        EnumSet.of(SpellArea.FRIENDLY_HAND),
                        new Class[]{Card.class},
                        SpellChoiceType.SELECT,SpellArea.FRIENDLY_GRAVEYARD)
        },
                "Select and move a card from hand to graveyard","Sacrifice");
        GeneralizedSpell loyalty = new GeneralizedSpell(new Spell[]{
                new HPSpell(
                        EnumSet.of(SpellArea.FRIENDLY_PLAYER),
                        new Class[]{PlayerHero.class},
                        SpellChoiceType.ALL,1000)
        },
                "Heal your player for 1000 HP","Loyalty");
        GeneralizedSpell opentheGate = new GeneralizedSpell(new Spell[]{
                new MoveSpell(
                        EnumSet.of(SpellArea.FRIENDLY_DECK),
                        new Class[]{Card.class},
                        SpellChoiceType.RANDOM,SpellArea.FRIENDLY_HAND),
                new MoveSpell(
                        EnumSet.of(SpellArea.FRIENDLY_DECK),
                        new Class[]{Card.class},
                        SpellChoiceType.RANDOM,SpellArea.FRIENDLY_HAND),
                new MoveSpell(
                        EnumSet.of(SpellArea.FRIENDLY_DECK),
                        new Class[]{Card.class},
                        SpellChoiceType.RANDOM,SpellArea.FRIENDLY_HAND)
        },
                "Draw three cards from deck to hand","Open the Gate");
        GeneralizedSpell hellfire = new GeneralizedSpell(new Spell[]{
                new HPSpell(
                        EnumSet.of(SpellArea.ENEMY_MONSTERFIELD),
                        new Class[]{MonsterCard.class},
                        SpellChoiceType.ALL,-300),
                new APSpell(
                        EnumSet.of(SpellArea.FRIENDLY_MONSTERFIELD),
                        new Class[]{MonsterCard.class},
                        SpellChoiceType.ALL,300),
                new HPSpell(
                        EnumSet.of(SpellArea.FRIENDLY_MONSTERFIELD),
                        new Class[]{MonsterCard.class},
                        SpellChoiceType.ALL,300)
        },
                "Deal 300 damage to all enemy monster cards and Increase HP and AP of all friendly monster cards by 300","Hellfire");
        GeneralizedSpell revengeoftheTwoHeads = new GeneralizedSpell(new Spell[]{
                new MoveSpell(
                        EnumSet.of(SpellArea.ENEMY_MONSTERFIELD),
                        new Class[]{MonsterCard.class},
                        SpellChoiceType.RANDOM,SpellArea.ENEMY_GRAVEYARD),
                new MoveSpell(
                        EnumSet.of(SpellArea.ENEMY_MONSTERFIELD),
                        new Class[]{MonsterCard.class},
                        SpellChoiceType.RANDOM,SpellArea.ENEMY_GRAVEYARD)
        },
                "Send two random enemy monster cards from field to garveyard","Revenge of the Two Heads");
        GeneralizedSpell smallHpPotion = new GeneralizedSpell(new Spell[]{
                new HPSpell(
                        EnumSet.of(SpellArea.FRIENDLY_PLAYER),
                        new Class[]{PlayerHero.class},
                        SpellChoiceType.ALL,500)
        },
                "Increase Player’s HP by 500","Small HP Potion");
        GeneralizedSpell mediumHpPotion = new GeneralizedSpell(new Spell[]{
                new HPSpell(
                        EnumSet.of(SpellArea.FRIENDLY_PLAYER),
                        new Class[]{PlayerHero.class},
                        SpellChoiceType.ALL,1000)
        },
                "Increase Player’s HP by 1000","Medium HP Potion");
        GeneralizedSpell largeHpPotion = new GeneralizedSpell(new Spell[]{
                new HPSpell(
                        EnumSet.of(SpellArea.FRIENDLY_PLAYER),
                        new Class[]{PlayerHero.class},
                        SpellChoiceType.ALL,2000)
        },
                "Increase Player’s HP by 2000","Large HP Potion");
        GeneralizedSpell smallMpPotion = new GeneralizedSpell(new Spell[]{
                new MPSpell(
                        EnumSet.of(SpellArea.FRIENDLY_PLAYER),
                        new Class[]{PlayerHero.class},
                        SpellChoiceType.ALL,2)
        },
                "Increase Player’s MP by 2","Small MP Potion");
        GeneralizedSpell mediumMpPotion = new GeneralizedSpell(new Spell[]{
                new MPSpell(
                        EnumSet.of(SpellArea.FRIENDLY_PLAYER),
                        new Class[]{PlayerHero.class},
                        SpellChoiceType.ALL,4)
        },
                "Increase Player’s MP by 4","Medium MP Potion");
        GeneralizedSpell largeMPPotion = new GeneralizedSpell(new Spell[]{
                new MPSpell(
                        EnumSet.of(SpellArea.FRIENDLY_PLAYER),
                        new Class[]{PlayerHero.class},
                        SpellChoiceType.ALL,8)
        },
                "Increase Player’s MP by 8","Large MP Potion");
        GeneralizedSpell lesserRestorative = new GeneralizedSpell(new Spell[]{
                new HPSpell(
                        EnumSet.of(SpellArea.FRIENDLY_PLAYER),
                        new Class[]{PlayerHero.class},
                        SpellChoiceType.ALL,500),
                new MPSpell(
                        EnumSet.of(SpellArea.FRIENDLY_PLAYER),
                        new Class[]{PlayerHero.class},
                        SpellChoiceType.ALL,2)
        },
                "Increase Player’s HP by 500 and MP by 2","Lesser Restorative");
        GeneralizedSpell greaterRestorative = new GeneralizedSpell(new Spell[]{
                new HPSpell(
                        EnumSet.of(SpellArea.FRIENDLY_PLAYER),
                        new Class[]{PlayerHero.class},
                        SpellChoiceType.ALL,1000),
                new MPSpell(
                        EnumSet.of(SpellArea.FRIENDLY_PLAYER),
                        new Class[]{PlayerHero.class},
                        SpellChoiceType.ALL,4)
        },
                "Increase Player’s HP by 1000 and MP by 4","Greater Restorative");
        GeneralizedSpell ironPendant = new GeneralizedSpell(new Spell[]{
                new HPSpell(
                        EnumSet.of(SpellArea.FRIENDLY_PLAYER),
                        new Class[]{PlayerHero.class},
                        SpellChoiceType.ALL,500)
        },
                "Increase Player’s Max HP by 500","Iron Pendant");
        GeneralizedSpell goldPendant = new GeneralizedSpell(new Spell[]{
                new HPSpell(
                        EnumSet.of(SpellArea.FRIENDLY_PLAYER),
                        new Class[]{PlayerHero.class},
                        SpellChoiceType.ALL,1000)
        },
                "Increase Player’s Max HP by 1000","Gold Pendant");
        GeneralizedSpell diamondPendant = new GeneralizedSpell(new Spell[]{
                new HPSpell(
                        EnumSet.of(SpellArea.FRIENDLY_PLAYER),
                        new Class[]{PlayerHero.class},
                        SpellChoiceType.ALL,2000)
        },
                "Increase Player’s Max HP by 2000","Diamond Pendant");
        GeneralizedSpell ironRing = new GeneralizedSpell(new Spell[]{
                new Spell(
                        EnumSet.of(SpellArea.FRIENDLY_PLAYER),
                        new Class[]{PlayerHero.class}, SpellChoiceType.ALL) {
                    @Override
                    protected void apply(Player owner) {
                        for (SpellCastable card: getEffectableCard()) {
                            if (card instanceof PlayerHero) {
                                PlayerHero current = (PlayerHero) card;
                                current.getOwner().setMaxMana(current.getOwner().getMaxMana() + 1);
                            } else
                                return;
                        }
                    }

                    @Override
                    protected void deuse(Player owner) {
                        setEffectableCards(owner);
                        for (SpellCastable card: getEffectableCard()) {
                            PlayerHero current = (PlayerHero) card;
                            current.getOwner().setMana(current.getOwner().getMana() - 1);
                        }
                        //        effectedCard.clear();
                        getEffectableCard().clear();
                    }
                }
        },
                "Increase Player’s Max MP by 1","Iron Ring");
        GeneralizedSpell goldRing = new GeneralizedSpell(new Spell[]{
                new Spell(
                        EnumSet.of(SpellArea.FRIENDLY_PLAYER),
                        new Class[]{PlayerHero.class}, SpellChoiceType.ALL) {
                    @Override
                    protected void apply(Player owner) {
                        for (SpellCastable card: getEffectableCard()) {
                            if (card instanceof PlayerHero) {
                                PlayerHero current = (PlayerHero) card;
                                current.getOwner().setMaxMana(current.getOwner().getMaxMana() + 2);
                            } else
                                return;
                        }
                    }

                    @Override
                    protected void deuse(Player owner) {
                        setEffectableCards(owner);
                        for (SpellCastable card: getEffectableCard()) {
                            PlayerHero current = (PlayerHero) card;
                            current.getOwner().setMana(current.getOwner().getMana() - 2);
                        }
                        //        effectedCard.clear();
                        getEffectableCard().clear();
                    }
                }
        },
                "Increase Player’s Max MP by 2","Gold Ring");
        GeneralizedSpell diamondRing = new GeneralizedSpell(new Spell[]{
                new Spell(
                        EnumSet.of(SpellArea.FRIENDLY_PLAYER),
                        new Class[]{PlayerHero.class}, SpellChoiceType.ALL) {
                    @Override
                    protected void apply(Player owner) {
                        for (SpellCastable card: getEffectableCard()) {
                            if (card instanceof PlayerHero) {
                                PlayerHero current = (PlayerHero) card;
                                current.getOwner().setMaxMana(current.getOwner().getMaxMana() + 3);
                            } else
                                return;
                        }
                    }

                    @Override
                    protected void deuse(Player owner) {
                        setEffectableCards(owner);
                        for (SpellCastable card: getEffectableCard()) {
                            PlayerHero current = (PlayerHero) card;
                            current.getOwner().setMana(current.getOwner().getMana() - 3);
                        }
                        //        effectedCard.clear();
                        getEffectableCard().clear();
                    }
                }
        },
                "Increase Player’s Max MP by 3","Diamond Ring");
        GeneralizedSpell DemonKingsCrown = new GeneralizedSpell(new Spell[]{
                new RatioSpell(
                        EnumSet.of(SpellArea.FRIENDLY_MONSTERFIELD,SpellArea.FRIENDLY_PLAYER),
                        new Class[]{PlayerHero.class,MonsterCard.class},
                        SpellChoiceType.ALL,0.8)
        },
                "Decrease All Incoming Damages by 20%","Demon King's Crown");
    }



    private static void instantiateSpellCards() {
        try {
            FileReader fileReader = new FileReader("database/SpellCards.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = null;
            while((line = bufferedReader.readLine()) != null){
                String[] constructorInputs = line.split(" - ");
                GeneralizedSpell wantedSpell = getSpellByName(constructorInputs[0]);
                int manaCost = Integer.parseInt(constructorInputs[1]);
                SpellCardType spellCardType = SpellCardType.nameToSpellCardType(constructorInputs[2]);
                new SpellCard(wantedSpell, manaCost, spellCardType);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void instantiateMonsterCards(){
        try {
            FileReader fileReader = new FileReader("database/MonsterCards.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = null;
            Tribe tribeOfSection = null;
            while((line = bufferedReader.readLine()) != null){
                if(!line.contains("-")){
                    tribeOfSection = Tribe.getTribeByName(line);
                    continue;
                }
                String[] constructorInputs = line.split(" - ");
                int defaultHP = Integer.parseInt(constructorInputs[1]);
                int defaultAP = Integer.parseInt(constructorInputs[2]);
                int defaultManaCost = Integer.parseInt(constructorInputs[3]);
                boolean isDefender = (constructorInputs[5].equalsIgnoreCase("Defender") || constructorInputs[5].equalsIgnoreCase("Both"));
                boolean isNimble = (constructorInputs[5].equalsIgnoreCase("Nimble") || constructorInputs[5].equalsIgnoreCase("Both"));
                if(constructorInputs[4].equalsIgnoreCase("Normal")){
                    new NormalCard(tribeOfSection, constructorInputs[0], defaultHP, defaultAP, defaultManaCost, isDefender, isNimble);
                }else if(constructorInputs[4].equalsIgnoreCase("Spell Caster")){
                    GeneralizedSpell spell = getSpellByName(constructorInputs[6]);
                    new SpellcasterCard(tribeOfSection, constructorInputs[0], defaultHP, defaultAP, defaultManaCost, isDefender, isNimble, spell);
                }else if(constructorInputs[4].equalsIgnoreCase("General")){
                    GeneralizedSpell battleCry = getSpellByName(constructorInputs[6]);
                    GeneralizedSpell will = getSpellByName(constructorInputs[7]);
                    new GeneralCard(tribeOfSection, constructorInputs[0], defaultHP, defaultAP, defaultManaCost, isDefender, isNimble, battleCry, will);
                }else if(constructorInputs[4].equalsIgnoreCase("Hero")){
                    GeneralizedSpell battleCry = getSpellByName(constructorInputs[6]);
                    GeneralizedSpell spell = getSpellByName(constructorInputs[7]);
                    GeneralizedSpell will = getSpellByName(constructorInputs[8]);
                    new HeroCard(tribeOfSection, constructorInputs[0], defaultHP, defaultAP, defaultManaCost, isDefender, isNimble, battleCry, spell, will);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void instantiateItems(){
        try {
            FileReader fileReader = new FileReader("database/Items.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = null;
            while((line = bufferedReader.readLine()) != null){
                String[] constructorInputs = line.split(" - ");
                GeneralizedSpell wantedSpell = getSpellByName(constructorInputs[0]);
                int price = Integer.parseInt(constructorInputs[1]);
                new Item(wantedSpell, price);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void instantiateAmulets(){
        try {
            FileReader fileReader = new FileReader("database/Amulets.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = null;
            while((line = bufferedReader.readLine()) != null){
                String[] constructorInputs = line.split(" - ");
                GeneralizedSpell wantedSpell = getSpellByName(constructorInputs[0]);
                int price = Integer.parseInt(constructorInputs[1]);
                new Amulet(wantedSpell, price);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void instantiateHumanShop() throws Exception{
        for(Stuff stuff : allStuff){
            if(stuff instanceof MonsterCard && ((MonsterCard)stuff).getTribe().equals(Tribe.DEMONIC))
                continue;
            if(stuff instanceof Item){
                human.getShop().addItem((Item)stuff.clone());
            }else if(stuff instanceof Amulet && !stuff.getName().equalsIgnoreCase("Demon King's Crown")){
                human.getShop().addAmulet((Amulet)stuff.clone());
            }else if(stuff instanceof Card){
                ((Card)stuff).setOwner(human);
                if(stuff instanceof SpellCard && !stuff.getName().matches("Blood Feast|Reaper's Scythe|Meteor Shower")){
                    if(((SpellCard) stuff).getDefaultManaCost() < 6)
                        human.getShop().addCard((SpellCard)stuff.clone(), 3);
                    else
                        human.getShop().addCard((SpellCard)stuff.clone(), 2);
                }else if(stuff instanceof NormalCard){
                    human.getShop().addCard((NormalCard)stuff.clone(), 4);
                }else if(stuff instanceof SpellcasterCard || stuff instanceof GeneralCard){
                    human.getShop().addCard((MonsterCard)stuff.clone(), 2);//TODO casting to MonsterCard is no problem?
                }else if (stuff instanceof HeroCard){
                    human.getShop().addCard((HeroCard)stuff.clone());
                }
                ((Card)stuff).setOwner(null);
            }
        }
    }

    private static void instantiatePlayerInventory(Player player, String fileDestination) throws Exception{
        try {
            FileReader fileReader = new FileReader(fileDestination);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = null;
            TypeOfStuffToBuyAndSell currentStuff = TypeOfStuffToBuyAndSell.CARD;
            while((line = bufferedReader.readLine()) != null){
                if(!line.contains("-")){
                    currentStuff = getTypeOfStuffByName(line);
                    continue;
                }
                String[] constructorInputs = line.split(" - ");
                Stuff stuff = getStuffByName(constructorInputs[0]);
                int number = Integer.parseInt(constructorInputs[1]);
                switch (currentStuff){
                    case CARD:
                        ((Card)stuff).setOwner(player);
                        player.addInitialInventoryCard((Card)stuff.clone(), number);
                        ((Card)stuff).setOwner(null);
                        break;
                    case ITEM:
                        player.addInitialItems((Item)stuff.clone(), number);
                        break;
                    case AMULET:
                        player.addInitialAmulets((Amulet)stuff.clone());
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
