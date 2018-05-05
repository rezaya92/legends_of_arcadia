package Model.Card;

import Model.CardPlace;
import Model.Spell.Spell;

import java.util.ArrayList;

/**
 * Created by msi-pc on 4/27/2018.
 */
public abstract class MonsterCard extends Card{
    
    boolean isNimble;
    boolean isDefender;
    boolean isAwake = false;
    int ap;
    int hp;
    Spell battleCry;
    Spell spellCast;
    Spell will;
    //todo set in constructor

    public boolean isNimble(){
        return isNimble;
    }
    public boolean isDefender(){
        return isDefender;
    }

    public MonsterCard(int manaCost, int hp, int ap, CardPlace cardPlace, boolean isNimble, boolean isDefender) {
        this.manaCost = manaCost;
        this.hp = hp;
        this.ap = ap;
        this.cardPlace = cardPlace;
        this.isNimble = isNimble;
        this.isDefender = isDefender;
    }

    public void Attack(MonsterCard monsterCard){
        // defender magic case || secrets
        if (isAwake && (monsterCard.isDefender || !monsterCard.owner.isDefenderPresent())) {
            monsterCard.hp -= ap;
            hp -= monsterCard.ap;
            this.checkAlive();
            monsterCard.checkAlive();
        }
    }

    public void checkAlive(){
        // how about the player
        if (hp <= 0){
            if (will != null){
                will.use(); // todo consider
            }
            for (int i = 0; i < 5; i++){
                if (owner.getMonsterFieldCards()[i] == this){
                    owner.setMonsterFieldCards(null, i);
                    owner.getGraveyardCards().add(this); // cut to "die" in cards?
                }
            }
        }
    }

    @Override
    public void play(int slotNumber){ // must be < 5
        if (manaCost <= owner.getMana() && owner.getMonsterFieldCards()[slotNumber] == null){
            if (owner.getHandCards().remove(this)) {
                owner.setMonsterFieldCards(this, slotNumber);
                owner.setMana(owner.getMana() - manaCost);
                if (isNimble)
                    getAwake();
                if (battleCry != null)
                    battleCry.use(); // todo consider
            }
        }
    }

    public void castSpell(){
        if (spellCast != null)
            //spellCast.use();
    }

    @Override
    public String toString(){
        String output = this.name + " Info:\n";
        String cardType = this.getClass().getName();
        cardType = cardType.substring(0, cardType.length()-4);// not perfect
        output += "Name: " + this.name + "\n";
        output += "HP: " + this.hp + "\n";
        output += "AP: " + this.ap + "\n";
        output += "MP cost: " + this.manaCost + "\n";
        output += "Card Type: " + cardType + "\n";
        output += "Card Tribe: " + tribe.name() + "\n";//TODO correct?
        output += "Is Defencive: " + isDefender + "\n";
        output += "Is Nimble: " + isNimble + "\n";
        return output;
    }

    public void getAwake(){
        isAwake = true;
    }
    // to consider sleeping
}
