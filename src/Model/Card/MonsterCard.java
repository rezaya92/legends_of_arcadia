package Model.Card;

import Controller.Main;
import Model.HasHP;
import Model.Player;
import Model.Spell.GeneralizedSpell;
import Model.Spell.NoEffectableCardException;
import View.GameView.ConsoleView;
import View.GameView.GameView;
import javafx.beans.property.SimpleIntegerProperty;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import static Controller.Main.human;

/**
 * Created by msi-pc on 4/27/2018.
 */
public class MonsterCard extends Card implements HasHP, Cloneable, Serializable {

    final boolean isNimble;
    final boolean isDefender;
    private double damageReceivementRatio = 1;
    boolean isAwake = false;
    final int defaultHP;
    final int defaultAP;
    transient SimpleIntegerProperty ap = new SimpleIntegerProperty();
    transient SimpleIntegerProperty hp = new SimpleIntegerProperty();
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

    public void setHasUsedSpell(boolean hasUsedSpell) {
        this.hasUsedSpell = hasUsedSpell;
    }

    public boolean hasAttacked(){return hasAttacked;}
    public void setHasAttacked(boolean hasAttacked){this.hasAttacked = hasAttacked;}

    public GeneralizedSpell getBattleCry(){return  battleCry;}
    public GeneralizedSpell getSpellCasterSpell(){return spellCasterSpell;}
    public GeneralizedSpell getWill(){return will;}

    public MonsterCard(Tribe tribe, String name, int defaultHP, int defaultAP, int defaultManaCost, boolean isDefender, boolean isNimble, GeneralizedSpell battleCry, GeneralizedSpell spellCasterSpell, GeneralizedSpell will) {
        this.tribe = tribe;
        this.name = name;
        this.manaCost = this.defaultManaCost = defaultManaCost;
        this.hp.set(defaultHP);
        this.defaultHP = defaultHP;
        this.defaultAP = defaultAP;
        this.ap.set(this.defaultAP);
        this.battleCry = battleCry;
        this.spellCasterSpell = spellCasterSpell;
        this.will = will;
        if (spellCasterSpell != null)
            hasGotSpell = true;
        this.isNimble = isNimble;
        this.isDefender = isDefender;
        Main.allStuff.add(this);
    }

    public void attack(int slotNumber){
        MonsterCard monsterCard = (MonsterCard)owner.getOpponent().getMonsterFieldCards().get(slotNumber);  // casting a null object possible ?
        if (monsterCard != null){
            this.attack(monsterCard);
        }
        else {
            ConsoleView.slotIsEmpty(owner);
        }
    }
    public void attack(MonsterCard monsterCard) {   // todo return string
        if (isAwake && (monsterCard.isDefender || !monsterCard.owner.isDefenderPresent())) {
            if (!hasAttacked) {
                monsterCard.takeDamage(ap.get());
                this.takeDamage(monsterCard.ap.get());
                this.checkAlive();
                monsterCard.checkAlive();
                hasAttacked = true;
                owner.addHasAttackedCard(this);
                ConsoleView.clashWith(this.name, monsterCard.getName());
            }
            else
                ConsoleView.alreadyAttacked(owner);
        } else if (!isAwake){
            ConsoleView.cardIsSleep(owner);
        }
        else
            ConsoleView.defenderEnemyPresent(owner);
    }

    public Tribe getTribe() {
        return tribe;
    }
    public void setTribe(Tribe tribe) {
        this.tribe = tribe;
    }

    public boolean attackOpponentHero() {   // todo return string
        if (isAwake) {
            Player opponent = this.owner.getOpponent();
            if (!opponent.isDefenderPresent()) {
                if (!hasAttacked) {
                    opponent.getPlayerHero().takeDamage(ap.get());
                    hasAttacked = true;
                    owner.addHasAttackedCard(this);
                    ConsoleView.clashWith(this.name, owner.getOpponent().getName());
                    return opponent.getPlayerHero().checkAlive();
                }
                else
                    ConsoleView.alreadyAttacked(owner);
            } else if (!isAwake){
                ConsoleView.cardIsSleep(owner);
            }
            else
                ConsoleView.defenderEnemyPresent(owner);
        }
        return true;
    }

    public void takeDamage(int damageAmount){   // todo set if when ratio is 1 (double may cause change)
        if (damageReceivementRatio < 1.01 && damageReceivementRatio > 9.99)
            hp.set(hp.get() - damageAmount);
        else
            hp.set(hp.get() - (int)(damageAmount*damageReceivementRatio));
    }

    public void heal(int healAmount){
        hp.set(hp.get() + healAmount);
    }

    public boolean checkAlive() {
        if (hp.get() <= 0) {
            if (will != null) {
                try {
                    will.use(owner);
                } catch (NoEffectableCardException e) {
                    ConsoleView.noEffectableCard();
                }
            }
            this.transfer(owner.getGraveyardCards());
            return false;
        }
        else
            return true;
    }

    @Override
    public boolean play(int slotNumber) { // must be < 5   "just from hand to monsterField"
        if (manaCost <= owner.getMana()) {
            if (owner.getMonsterFieldCards().get(slotNumber) == null) {
                deuseAuraCards();
                cardPlace.remove(this);
                owner.getMonsterFieldCards().set(slotNumber, this);
                cardPlace = owner.getMonsterFieldCards();
                owner.setMana(owner.getMana() - manaCost);
                useAuraCards();
                GameView.updateFields();
                if (isNimble)
                    getAwake();
                else {
                    isAwake = false;                // because card may be played more than once
                }
                if (battleCry != null) {
                    try {
                        battleCry.use(owner);
                    } catch (NoEffectableCardException e) {
                        ConsoleView.noEffectableCard();
                    }
                }
                ConsoleView.playedInMonsterField(name);
                return true;                       // case battleCry didn't cast ?
            }
            else {
                ConsoleView.slotIsFull(owner);
            }
        }
        else {
            ConsoleView.insufficientMana(owner);
        }
        return false;
    }

    @Override
    public boolean play(){
        int slot = owner.getMonsterFieldCards().indexOf(null);
        if (slot != -1)
            return play(slot);
        else {
            ConsoleView.noEmptySlot();
            return false;
        }
    }

    public void castSpell() {
        if (spellCasterSpell != null) {
            if (!hasUsedSpell) {
                try {
                    spellCasterSpell.use(owner);   // else ??
                    hasUsedSpell = true;
                } catch (NoEffectableCardException e) {
                    ConsoleView.noEffectableCard();
                }
            }
            else if (owner == human)
                ConsoleView.spellHasBeenCasted();
        }
    }

    public int getAp() {
        return ap.get();
    }

    public SimpleIntegerProperty apProperty() {
        return  ap;
    }
    public void setAp(int ap) {
        this.ap.set(ap);
    }

    public int getHp() {
        return hp.get();
    }

    public void setHp(int hp) {
        this.hp.set(hp);
    }

    public SimpleIntegerProperty hpProperty() {return hp; }


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
        output += "Card Tribe: " + tribe.name() + "\n";
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
            isAwake = isNimble;
        }
    }

    @Override
    public void restoreValues(){
        hp.set(defaultHP);
        ap.set(defaultAP);
        manaCost = defaultManaCost;
        isAwake = false;
        damageReceivementRatio = 1;
    }

    public void changeDamageReceivementRatio(double coefficentofVariation) {
        this.damageReceivementRatio *= coefficentofVariation;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        Integer apInteger = ap.getValue();
        objectOutputStream.writeObject(apInteger);
        Integer hpInteger = hp.getValue();
        objectOutputStream.writeObject(hpInteger);
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException{
        objectInputStream.defaultReadObject();
        ap = new SimpleIntegerProperty((Integer)objectInputStream.readObject());
        hp = new SimpleIntegerProperty((Integer)objectInputStream.readObject());
    }

    @Override
    public Object clone() throws CloneNotSupportedException{
        MonsterCard cloned = (MonsterCard) super.clone();
        cloned.hp = new SimpleIntegerProperty();
        cloned.ap = new SimpleIntegerProperty();
        cloned.hp.set(this.hp.get());
        cloned.ap.set(this.ap.get());
        return cloned;
    }
}
