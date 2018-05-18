package Model.Card;

import Model.HasHP;
import Model.Player;
import Model.Spell.GeneralizedSpell;
import Model.Stuff;
import View.View;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by msi-pc on 4/27/2018.
 */
public class MonsterCard extends Card implements HasHP, Cloneable {

    final boolean isNimble;
    final boolean isDefender;
    private double damageReceivementRatio = 1;
    boolean isAwake = false;
    final int defaultHP;
    final int defaultAP;
    int ap;
    int hp;
    Tribe tribe;
    boolean hasGotSpell = false;
    boolean hasUsedSpell = false;
    boolean hasAttacked = false;
    GeneralizedSpell battleCry;
    GeneralizedSpell spellCasterSpell;
    GeneralizedSpell will;

    public boolean isNimble() {
        return isNimble;
    }
    public boolean isDefender() {
        return isDefender;
    }
    public boolean isAwake(){return isAwake;}

    public boolean hasGotSpell(){return hasGotSpell;}
    public boolean hasUsedSpell(){return hasUsedSpell;}

    public boolean hasAttacked(){return hasAttacked;}
    public void setHasAttacked(boolean hasAttacked){this.hasAttacked = hasAttacked;}

    public GeneralizedSpell getBattleCry(){return  battleCry;}
    public GeneralizedSpell getSpellCasterSpell(){return spellCasterSpell;}
    public GeneralizedSpell getWill(){return will;}

    public MonsterCard(Tribe tribe, String name, int defaultHP, int defaultAP, int defaultManaCost, boolean isDefender, boolean isNimble, GeneralizedSpell battleCry, GeneralizedSpell spellCasterSpell, GeneralizedSpell will) {
        this.tribe = tribe;
        this.name = name;
        this.manaCost = this.defaultManaCost = defaultManaCost;
        this.hp = this.defaultHP = defaultHP;
        this.ap = this.defaultAP = defaultAP;
        this.battleCry = battleCry;
        this.spellCasterSpell = spellCasterSpell;
        this.will = will;
        if (spellCasterSpell != null)
            hasGotSpell = true;
        this.isNimble = isNimble;
        this.isDefender = isDefender;
        Stuff.allStuff.add(this);
    }

    public void attack(int slotNumber){
        MonsterCard monsterCard = (MonsterCard)owner.getOpponent().getMonsterFieldCards().get(slotNumber);  // casting a null object possible ?
        if (monsterCard != null){
            this.attack(monsterCard);
        }
        else {
            View.slotIsEmpty(owner);
        }
    }
    public void attack(MonsterCard monsterCard) {   // todo return string
        if (isAwake && (monsterCard.isDefender || !monsterCard.owner.isDefenderPresent())) {
            if (!hasAttacked) {
                monsterCard.takeDamage(ap);
                this.takeDamage(monsterCard.ap);
                this.checkAlive();
                monsterCard.checkAlive();
                hasAttacked = true;
                owner.addHasAttackedCard(this);
                View.clashWith(this.name, monsterCard.getName());
            }
            else
                View.alreadyAttacked(owner);
        } else if (!isAwake){
            View.cardIsSleep(owner);
        }
        else
            View.defenderEnemyPresent(owner);
    }

    public Tribe getTribe() {
        return tribe;
    }
    public void setTribe(Tribe tribe) {
        this.tribe = tribe;
    }

    public void attackOpponentHero() {   // todo return string
        if (isAwake) {
            Player opponent = this.owner.getOpponent();
            if (!opponent.isDefenderPresent()) {
                if (!hasAttacked) {
                    opponent.getPlayerHero().takeDamage(ap);
                    opponent.getPlayerHero().checkAlive();
                    hasAttacked = true;
                    owner.addHasAttackedCard(this);
                    View.clashWith(this.name, owner.getOpponent().getName());
                }
                else
                    View.alreadyAttacked(owner);
            } else if (!isAwake){
                View.cardIsSleep(owner);
            }
            else
                View.defenderEnemyPresent(owner);
        }
    }

    public void takeDamage(int damageAmount){   // todo set if when ratio is 1 (double may cause change)
        hp -= (int)(damageAmount*damageReceivementRatio);
    }

    public void heal(int healAmount){
        hp += healAmount;
    }

    public boolean checkAlive() {
        if (hp <= 0) {
            if (will != null) {
                try {
                    will.use(owner);
                } catch (IOException ignored) {

                }
            }
            this.transfer(owner.getGraveyardCards());
            return false;
        }
        else
            return true;
    }

    @Override
    public void play(int slotNumber) { // must be < 5   "just from hand to monsterField"
        if (manaCost <= owner.getMana()) {
            if (owner.getMonsterFieldCards().get(slotNumber) == null) {
                deuseAuraCards();
                cardPlace.remove(this);
                owner.getMonsterFieldCards().set(slotNumber, this);
                cardPlace = owner.getMonsterFieldCards();
                owner.setMana(owner.getMana() - manaCost);
                useAuraCards();
                if (isNimble)
                    getAwake();
                else {
                    owner.addSleepingPlayedCard(this);
                    isAwake = false;                // because card may be played more than once
                }
                if (battleCry != null) {
                    try {
                        battleCry.use(owner);
                    } catch (IOException ignored) {

                    }
                }
            }
            else {
                View.slotIsFull(owner);
            }
        }
        else {
            View.insufficientMana(owner);
        }
    }

    public void castSpell() {
        if (spellCasterSpell != null) {
            if (!hasUsedSpell) {
                try {
                    spellCasterSpell.use(owner);   // else ??
                } catch (IOException ignored) {

                }
                hasUsedSpell = true;
            }
        }
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


    @Override
    public String toString() {
        String output = this.name + " Info:\n";
        String[] tmp = this.getClass().getName().split("\\.");
        String cardType = tmp[tmp.length-1];
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
    public void transfer(ArrayList<Card> destination){  // how about will ??
        super.transfer(destination);
        if (destination == owner.getMonsterFieldCards()) {  // how about enemy monsterFieldCards ?
            if (isNimble)
                isAwake = true;
            else {
                owner.addSleepingPlayedCard(this);
                isAwake = false;
            }
        }
    }

    @Override
    public void restoreValues(){
        hp = defaultHP;
        ap = defaultAP;
        manaCost = defaultManaCost;
        isAwake = false;
        damageReceivementRatio = 1;
    }

    public void changeDamageReceivementRatio(double coefficentofVariation) {
        this.damageReceivementRatio *= coefficentofVariation;
    }

    @Override
    public Object clone() throws CloneNotSupportedException{
        return super.clone();
    }
}
