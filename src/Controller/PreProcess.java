package Controller;

import Model.Card.*;
import Model.Card.MonsterCard;
import Model.Card.SpellCard;
import Model.Card.SpellCardType;
import Model.Card.Tribe;
import Model.PlayerHero;
import Model.Spell.*;
import Model.Spell.SpellArea;
import static Model.Spell.GeneralizedSpell.*;
import static Model.Stuff.allStuff;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.EnumSet;

public class PreProcess {
    static void instantiateSpells(){
        GeneralizedSpell throwingKnives = new GeneralizedSpell(new Spell[]{
                new HPSpell(
                        EnumSet.of(SpellArea.ENEMY_MONSTERFIELD,SpellArea.ENEMY_PLAYER),
                        new Class[]{PlayerHero.class,MonsterCard.class},
                        EnumSet.of(Tribe.DEMONIC,Tribe.ATLANTIAN,Tribe.DRAGONBREED,Tribe.ELVEN), SpellChoiceType.SELECT,-500)
        },
                "Deal 500 damage to a selected enemy monster card on the field or to enemy player","Throwing Knives");
        GeneralizedSpell poisonousCauldron = new GeneralizedSpell(new Spell[]{
                new HPSpell(
                        EnumSet.of(SpellArea.ENEMY_MONSTERFIELD,SpellArea.ENEMY_PLAYER),
                        new Class[]{PlayerHero.class,MonsterCard.class},
                        EnumSet.of(Tribe.DEMONIC,Tribe.ATLANTIAN,Tribe.DRAGONBREED,Tribe.ELVEN),SpellChoiceType.ALL,-100)
        },
                "Deal 100 damage to all enemy monster cards and enemy player","Poisonous Cauldron");
        GeneralizedSpell firstAidKit = new GeneralizedSpell(new Spell[]{
                new HPSpell(
                        EnumSet.of(SpellArea.FRIENDLY_MONSTERFIELD,SpellArea.FRIENDLY_PLAYER),
                        new Class[]{PlayerHero.class,MonsterCard.class},
                        EnumSet.of(Tribe.DEMONIC,Tribe.ATLANTIAN,Tribe.DRAGONBREED,Tribe.ELVEN),SpellChoiceType.SELECT,500)
        },
                "Increase HP of a selected friendly monster card or player by 500","First Aid Kit");
        GeneralizedSpell reapersScythe = new GeneralizedSpell(new Spell[]{
                new MoveSpell(
                        EnumSet.of(SpellArea.ENEMY_MONSTERFIELD,SpellArea.ENEMY_SPELLFIELD),
                        new Class[]{SpellCard.class,MonsterCard.class},
                        EnumSet.of(Tribe.DEMONIC,Tribe.ATLANTIAN,Tribe.DRAGONBREED,Tribe.ELVEN),SpellChoiceType.SELECT,SpellArea.ENEMY_GRAVEYARD)
        },
                "Send an enemy monster or spell card from field to graveyard","Reaper's Scythe");
        GeneralizedSpell meteorShower = new GeneralizedSpell(new Spell[]{
                new HPSpell(
                        EnumSet.of(SpellArea.ENEMY_MONSTERFIELD,SpellArea.ENEMY_PLAYER),
                        new Class[]{PlayerHero.class,MonsterCard.class},
                        EnumSet.of(Tribe.DEMONIC,Tribe.ATLANTIAN,Tribe.DRAGONBREED,Tribe.ELVEN),SpellChoiceType.RANDOM,-800)
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
                        EnumSet.of(Tribe.DEMONIC,Tribe.ATLANTIAN,Tribe.DRAGONBREED,Tribe.ELVEN),SpellChoiceType.SELECT,SpellArea.FRIENDLY_HAND),
                new MoveSpell(
                        EnumSet.of(SpellArea.FRIENDLY_DECK),
                        new Class[]{Card.class},
                        EnumSet.of(Tribe.DEMONIC,Tribe.ATLANTIAN,Tribe.DRAGONBREED,Tribe.ELVEN),SpellChoiceType.RANDOM,SpellArea.FRIENDLY_HAND)
        },
                "Select and move a monster card from field to hand and draw one card from deck","Strategic Retreat");
        GeneralizedSpell warDrum = new GeneralizedSpell(new Spell[]{
                new APSpell(
                        EnumSet.of(SpellArea.FRIENDLY_MONSTERFIELD),
                        new Class[]{MonsterCard.class},
                        EnumSet.of(Tribe.DEMONIC,Tribe.ATLANTIAN,Tribe.DRAGONBREED,Tribe.ELVEN),SpellChoiceType.ALL,300)
        },
                "Increase all friendly monster cards’ AP by 300","War Drum");
        GeneralizedSpell healingWard = new GeneralizedSpell(new Spell[]{
                new HPSpell(
                        EnumSet.of(SpellArea.FRIENDLY_MONSTERFIELD),
                        new Class[]{MonsterCard.class},
                        EnumSet.of(Tribe.DEMONIC,Tribe.ATLANTIAN,Tribe.DRAGONBREED,Tribe.ELVEN),SpellChoiceType.ALL,200)
        },
                "Increase all friendly monster cards’ HP by 200","Healing Ward");
        GeneralizedSpell bloodFeast = new GeneralizedSpell(new Spell[]{
                new HPSpell(
                        EnumSet.of(SpellArea.ENEMY_PLAYER),
                        new Class[]{PlayerHero.class},
                        EnumSet.of(Tribe.DEMONIC,Tribe.ATLANTIAN,Tribe.DRAGONBREED,Tribe.ELVEN),SpellChoiceType.ALL,-500),
                new HPSpell(
                        EnumSet.of(SpellArea.FRIENDLY_PLAYER),
                        new Class[]{PlayerHero.class},
                        EnumSet.of(Tribe.DEMONIC,Tribe.ATLANTIAN,Tribe.DRAGONBREED,Tribe.ELVEN),SpellChoiceType.ALL,500)
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
                        EnumSet.of(Tribe.DEMONIC,Tribe.ATLANTIAN,Tribe.DRAGONBREED,Tribe.ELVEN),SpellChoiceType.ALL,400),
                new HPSpell(
                        EnumSet.of(SpellArea.FRIENDLY_MONSTERFIELD),
                        new Class[]{NormalCard.class},
                        EnumSet.of(Tribe.DEMONIC,Tribe.ATLANTIAN,Tribe.DRAGONBREED,Tribe.ELVEN),SpellChoiceType.ALL,400)
        },
                "Increase all friendly normal monster cards’ HP and AP by 400","Take All You Can");
        GeneralizedSpell arcaneBolt = new GeneralizedSpell(new Spell[]{
                new HPSpell(
                        EnumSet.of(SpellArea.ENEMY_PLAYER),
                        new Class[]{PlayerHero.class},
                        EnumSet.of(Tribe.DEMONIC,Tribe.ATLANTIAN,Tribe.DRAGONBREED,Tribe.ELVEN),SpellChoiceType.ALL,-500),
                new MoveSpell(
                        EnumSet.of(SpellArea.ENEMY_SPELLFIELD),
                        new Class[]{SpellCard.class},
                        EnumSet.of(Tribe.DEMONIC,Tribe.ATLANTIAN,Tribe.DRAGONBREED,Tribe.ELVEN),SpellChoiceType.SELECT,SpellArea.ENEMY_GRAVEYARD)
        },
                "Deal 500 damage to enemy player and select and move an enemy spell card from field to graveyard","Arcane Bolt");
        GeneralizedSpell greaterPurge = new GeneralizedSpell(new Spell[]{
                new MoveSpell(
                        EnumSet.of(SpellArea.ENEMY_SPELLFIELD,SpellArea.FRIENDLY_SPELLFIELD),
                        new Class[]{SpellCard.class},
                        EnumSet.of(Tribe.DEMONIC,Tribe.ATLANTIAN,Tribe.DRAGONBREED,Tribe.ELVEN),SpellChoiceType.ALL,SpellArea.FRIENDLY_HAND)
        },
                "Remove all spell cards on field from both sides and move them to hand","Greater Purge");
        GeneralizedSpell magicSeal = new GeneralizedSpell(new Spell[]{
                new MoveSpell(
                        EnumSet.of(SpellArea.ENEMY_SPELLFIELD),
                        new Class[]{SpellCard.class},
                        EnumSet.of(Tribe.DEMONIC,Tribe.ATLANTIAN,Tribe.DRAGONBREED,Tribe.ELVEN),SpellChoiceType.ALL,SpellArea.ENEMY_GRAVEYARD)
        },
                "Remove all enemy spell cards from field and move them to graveyard","Magic Seal");
        GeneralizedSpell rejuvenation = new GeneralizedSpell(new Spell[]{
                new HPSpell(
                        EnumSet.of(SpellArea.FRIENDLY_MONSTERFIELD),
                        new Class[]{MonsterCard.class},
                        EnumSet.of(Tribe.DEMONIC,Tribe.ATLANTIAN,Tribe.DRAGONBREED,Tribe.ELVEN),SpellChoiceType.SELECT,500),
                new APSpell(
                        EnumSet.of(SpellArea.FRIENDLY_MONSTERFIELD),
                        new Class[]{MonsterCard.class},
                        EnumSet.of(Tribe.DEMONIC,Tribe.ATLANTIAN,Tribe.DRAGONBREED,Tribe.ELVEN),SpellChoiceType.SELECT,300)
        },
                "Increase a selected friendly monster card’s HP by 500 and AP by 300","Rejuvenation", true);
        GeneralizedSpell arcaneExplosion = new GeneralizedSpell(new Spell[]{
                new HPSpell(
                        EnumSet.of(SpellArea.ENEMY_MONSTERFIELD),
                        new Class[]{MonsterCard.class},
                        EnumSet.of(Tribe.DEMONIC,Tribe.ATLANTIAN,Tribe.DRAGONBREED,Tribe.ELVEN),SpellChoiceType.ALL,-400),
                new MoveSpell(
                        EnumSet.of(SpellArea.ENEMY_SPELLFIELD),
                        new Class[]{SpellCard.class},
                        EnumSet.of(Tribe.DEMONIC,Tribe.ATLANTIAN,Tribe.DRAGONBREED,Tribe.ELVEN),SpellChoiceType.RANDOM,SpellArea.ENEMY_GRAVEYARD)
        },
                "Deal 400 damage to all enemy monster cards and remove a random spell card from enemy field and move it to graveyard.","Arcane Explosion");
        GeneralizedSpell purge = new GeneralizedSpell(new Spell[]{
                new MoveSpell(
                        EnumSet.of(SpellArea.ENEMY_SPELLFIELD),
                        new Class[]{SpellCard.class},
                        EnumSet.of(Tribe.DEMONIC,Tribe.ATLANTIAN,Tribe.DRAGONBREED,Tribe.ELVEN),SpellChoiceType.ALL,SpellArea.FRIENDLY_HAND)
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
                "Increase a random friendly Elven monster card on the field’s HP by 800 and AP by 600","Noble Purge",true);
        GeneralizedSpell reviveAllies = new GeneralizedSpell(new Spell[]{
                new MoveSpell(
                        EnumSet.of(SpellArea.FRIENDLY_GRAVEYARD),
                        new Class[]{Card.class},
                        EnumSet.of(Tribe.DEMONIC,Tribe.ATLANTIAN,Tribe.DRAGONBREED,Tribe.ELVEN),SpellChoiceType.RANDOM,SpellArea.FRIENDLY_HAND),
                new MoveSpell(
                        EnumSet.of(SpellArea.FRIENDLY_GRAVEYARD),
                        new Class[]{Card.class},
                        EnumSet.of(Tribe.DEMONIC,Tribe.ATLANTIAN,Tribe.DRAGONBREED,Tribe.ELVEN),SpellChoiceType.RANDOM,SpellArea.FRIENDLY_HAND)
        },
                "move two random cards from your graveyard to hand","Revive Allies");
        GeneralizedSpell divineBlessing = new GeneralizedSpell(new Spell[]{
                new HPSpell(
                        EnumSet.of(SpellArea.FRIENDLY_MONSTERFIELD,SpellArea.FRIENDLY_PLAYER),
                        new Class[]{PlayerHero.class,MonsterCard.class},
                        EnumSet.of(Tribe.DEMONIC,Tribe.ATLANTIAN,Tribe.DRAGONBREED,Tribe.ELVEN),SpellChoiceType.SELECT,2500)
        },
                "Increase HP of a friendly monster card or player by 2500","Divine Blessing");
        GeneralizedSpell burstofLight = new GeneralizedSpell(new Spell[]{
                new HPSpell(
                        EnumSet.of(SpellArea.FRIENDLY_MONSTERFIELD,SpellArea.FRIENDLY_PLAYER),
                        new Class[]{PlayerHero.class,MonsterCard.class},
                        EnumSet.of(Tribe.DEMONIC,Tribe.ATLANTIAN,Tribe.DRAGONBREED,Tribe.ELVEN),SpellChoiceType.ALL,200),
                new APSpell(
                        EnumSet.of(SpellArea.FRIENDLY_MONSTERFIELD),
                        new Class[]{MonsterCard.class},
                        EnumSet.of(Tribe.DEMONIC,Tribe.ATLANTIAN,Tribe.DRAGONBREED,Tribe.ELVEN),SpellChoiceType.ALL,200)
        },
                "Increase HP of all friendly monster cards and player by 500 and increase AP of all friendly monster cards by 200","Burst of Light");
        GeneralizedSpell magicalFire = new GeneralizedSpell(new Spell[]{
                new MoveSpell(
                        EnumSet.of(SpellArea.ENEMY_MONSTERFIELD),
                        new Class[]{MonsterCard.class},
                        EnumSet.of(Tribe.DEMONIC,Tribe.ATLANTIAN,Tribe.DRAGONBREED,Tribe.ELVEN),SpellChoiceType.SELECT,SpellArea.ENEMY_GRAVEYARD)
        },
                "Move an enemy monster card from field to graveyard","Magical Fire");
        GeneralizedSpell lavaSpit = new GeneralizedSpell(new Spell[]{
                new HPSpell(
                        EnumSet.of(SpellArea.ENEMY_MONSTERFIELD),
                        new Class[]{MonsterCard.class},
                        EnumSet.of(Tribe.DEMONIC,Tribe.ATLANTIAN,Tribe.DRAGONBREED,Tribe.ELVEN),SpellChoiceType.SELECT,-500),
                new APSpell(
                        EnumSet.of(SpellArea.ENEMY_MONSTERFIELD),
                        new Class[]{MonsterCard.class},
                        EnumSet.of(Tribe.DEMONIC,Tribe.ATLANTIAN,Tribe.DRAGONBREED,Tribe.ELVEN),SpellChoiceType.SELECT,-500)
        },
                "Deal 500 damage to an enemy monster card and reduce its AP by 500","Lava Spit",true);
        GeneralizedSpell Devour = new GeneralizedSpell(new Spell[]{
                new MoveSpell(
                        EnumSet.of(SpellArea.ENEMY_MONSTERFIELD),
                        new Class[]{MonsterCard.class},
                        EnumSet.of(Tribe.DEMONIC,Tribe.ATLANTIAN,Tribe.DRAGONBREED,Tribe.ELVEN),SpellChoiceType.RANDOM,SpellArea.ENEMY_GRAVEYARD)
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









        allSpells.add(throwingKnives);
        allSpells.add(poisonousCauldron);
        allSpells.add(firstAidKit);
        allSpells.add(reapersScythe);
        allSpells.add(meteorShower);
        allSpells.add(lunarBlessing);
        allSpells.add(strategicRetreat);
        allSpells.add(warDrum);
        allSpells.add(healingWard);
        allSpells.add(bloodFeast);
        allSpells.add(tsunami);
        allSpells.add(takeAllYouCan);
        allSpells.add(arcaneBolt);
        allSpells.add(greaterPurge);
        allSpells.add(magicSeal);

    }



    static void instantiateSpellCards() {
        try {
            FileReader fileReader = new FileReader("SpellCards.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = null;
            while((line = bufferedReader.readLine()) != null){
                String[] constructorInputs = line.split(" - ");
                GeneralizedSpell wantedSpell = null;
                for(GeneralizedSpell generalizedSpell : allSpells){
                    if(constructorInputs[0].equals(generalizedSpell.getName())){
                        wantedSpell = generalizedSpell;
                        break;
                    }
                }
                int manaCost = Integer.parseInt(constructorInputs[1]);
                SpellCardType spellCardType = SpellCardType.nameToSpellCardType(constructorInputs[2]);
                allStuff.add(new SpellCard(wantedSpell, manaCost, spellCardType));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
