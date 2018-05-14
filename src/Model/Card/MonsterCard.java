package Model.Card;

import Model.Player;
import Model.Spell.GeneralizedSpell;
import View.View;
import java.util.ArrayList;

/**
 * Created by msi-pc on 4/27/2018.
 */
public class MonsterCard extends Card {

    final boolean isNimble;
    final boolean isDefender;
    boolean isAwake = false;
    final int defaultHP;
    final int defaultAP;
    int ap;
    int hp;
    boolean hasSpell = false;
    boolean hasAttacked = false;
    GeneralizedSpell battleCry;
    GeneralizedSpell spellCasterSpell;
    GeneralizedSpell will;
    //todo set in constructor

    public boolean isNimble() {
        return isNimble;
    }

    public boolean isDefender() {
        return isDefender;
    }

    public boolean hasSpell(){return hasSpell;}

    public boolean hasAttacked(){return hasAttacked;}

    public GeneralizedSpell getBattleCry(){return  battleCry;}
    public GeneralizedSpell getSpellCasterSpell(){return spellCasterSpell;}
    public GeneralizedSpell getWill(){return will;}

    public MonsterCard(int defaultManaCost, int defaultHP, int defaultAP, ArrayList<Card> cardPlace, GeneralizedSpell battleCry, GeneralizedSpell spellCasterSpell, GeneralizedSpell will, boolean isNimble, boolean isDefender) {
        this.manaCost = this.defaultManaCost = defaultManaCost;
        this.hp = this.defaultHP = defaultHP;
        this.ap = this.defaultAP = defaultAP;
        this.cardPlace = cardPlace;
        cardPlace.add(this);
        this.battleCry = battleCry;
        this.spellCasterSpell = spellCasterSpell;
        this.will = will;
        if (spellCasterSpell != null)
            hasSpell = true;
        this.isNimble = isNimble;
        this.isDefender = isDefender;
    }

    public void Attack(MonsterCard monsterCard) {   // todo return string
        // defender magic case || secrets
        if (isAwake && (monsterCard.isDefender || !monsterCard.owner.isDefenderPresent())) {
            monsterCard.hp -= ap;
            hp -= monsterCard.ap;
            this.checkAlive();
            monsterCard.checkAlive();
        }
    }

    public void AttackOpponentHero() {   // todo return string
        if (isAwake) {
            Player opponent = this.owner.getOpponent();
            if (!opponent.isDefenderPresent()) {
                opponent.getPlayerHero().setHp(opponent.getPlayerHero().getHp() - ap);
                opponent.getPlayerHero().checkAlive();
                // this.checkAlive in case of weapon for playerHero
            }
        }
    }

    public void checkAlive() {
        // how about the player
        if (hp <= 0) {
            if (will != null) {
                will.use();
            }
            this.transfer(owner.getGraveyardCards());
        }
    }

    @Override
    public void play(int slotNumber) { // must be < 5   "just from hand to monsterField"
        if (manaCost <= owner.getMana()) {
            if (owner.getMonsterFieldCards().get(slotNumber) == null) {
                cardPlace.remove(this);
                owner.getMonsterFieldCards().set(slotNumber, this);
                cardPlace = owner.getMonsterFieldCards();
                owner.setMana(owner.getMana() - manaCost);
                if (isNimble)
                    getAwake();
                else
                    owner.addSleepingPlayedCard(this);
                if (battleCry != null)
                    battleCry.use();
            }
            else {
                View.slotIsFull();
            }
        }
        else {
            View.insufficientMana();
        }
    }

    public void castSpell() {
        if (spellCasterSpell != null)
            spellCasterSpell.use();   // else ??
    }

    public int getAp() {
        return ap;
    }

    public void setAp(int ap) {
        this.ap = ap;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    @Override
    public String toString() {
        String output = this.name + " Info:\n";
        String cardType = this.getClass().getName();
        cardType = cardType.substring(0, cardType.length() - 4);// not perfect
        output += "Name: " + this.name + "\n";
        output += "HP: " + this.defaultHP + "\n";
        output += "AP: " + this.defaultAP + "\n";
        output += "MP cost: " + this.defaultManaCost + "\n";
        output += "Card Type: " + cardType + "\n";
        output += "Card Tribe: " + tribe.name() + "\n";//TODO correct?
        output += "Is Defensive: " + isDefender + "\n";
        output += "Is Nimble: " + isNimble + "\n";
        if (battleCry != null)
            output += "BattleCry Details: " + battleCry.getDetail() + "\n";
        if (spellCasterSpell != null)
            output += "Spell Details: " + spellCasterSpell.getDetail() + "\n";
        if (will != null)
            output += "Will Details: " + will.getDetail() + "\n";
        return output;
    }

    public void getAwake() {
        isAwake = true;
    }

    public int getDefaultHP() {
        return defaultHP;
    }

    public int getDefaultAP() {
        return defaultAP;
    }

    @Override
    public void transfer(ArrayList<Card> destination){
        super.transfer(destination);
        if (isNimble)
            isAwake = true;
        else
            owner.addSleepingPlayedCard(this);
    }

    @Override
    public void restoreValues(){
        hp = defaultHP;
        ap = defaultAP;
        manaCost = defaultManaCost;
        isAwake = false;
    }
}
