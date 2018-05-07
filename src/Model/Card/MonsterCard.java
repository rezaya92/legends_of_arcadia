package Model.Card;

import Model.Player;
import Model.Spell.GeneralizedSpell;

/**
 * Created by msi-pc on 4/27/2018.
 */
public class MonsterCard extends Card{
    
    final boolean isNimble;
    final boolean isDefender;
    boolean isAwake = false;
    final int defualtHP;
    final int defaultAP;
    int ap;
    int hp;
    GeneralizedSpell battleCry;
    GeneralizedSpell spellCasterSpell;
    GeneralizedSpell will;
    //todo set in constructor

    public boolean isNimble(){
        return isNimble;
    }
    public boolean isDefender(){
        return isDefender;
    }

    public MonsterCard(int defaultManaCost, int defaultHP, int defaultAP, CardPlace cardPlace, boolean isNimble, boolean isDefender) {
        this.manaCost = this.defaultManaCost = defaultManaCost;
        this.hp = this.defualtHP = defaultHP;
        this.ap = this.defaultAP = defaultAP;
        this.cardPlace = cardPlace;
        this.isNimble = isNimble;
        this.isDefender = isDefender;
    }

    public void Attack(MonsterCard monsterCard){   // todo return string
        // defender magic case || secrets
        if (isAwake && (monsterCard.isDefender || !monsterCard.owner.isDefenderPresent())) {
            monsterCard.hp -= ap;
            hp -= monsterCard.ap;
            this.checkAlive();
            monsterCard.checkAlive();
        }
    }

    public void AttackOpponentHero(){   // todo return string
        if (isAwake){
            Player opponent = this.owner.getOpponent();
            if (!opponent.isDefenderPresent()){
                opponent.getPlayerHero().setHp(opponent.getPlayerHero().getHp() - ap);
                opponent.getPlayerHero().checkAlive();
                // this.checkAlive in case of weapon for playerHero
            }
        }
    }

    public void checkAlive(){
        // how about the player
        if (hp <= 0){
            if (will != null){
                will.use();
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
            if (owner.getHandCards().remove(this)) {      // todo card may get played from deck --> fix this
                owner.setMonsterFieldCards(this, slotNumber);
                owner.setMana(owner.getMana() - manaCost);
                if (isNimble)
                    getAwake();
                else
                    owner.addSleepingPlayedCard(this);
                if (battleCry != null)
                    battleCry.use();
            }
        }
    }

    public void castSpell(){
        if (spellCasterSpell != null);
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
    public String toString(){
        String output = this.name + " Info:\n";
        String cardType = this.getClass().getName();
        cardType = cardType.substring(0, cardType.length()-4);// not perfect
        output += "Name: " + this.name + "\n";
        output += "HP: " + this.defualtHP + "\n";
        output += "AP: " + this.defaultAP + "\n";
        output += "MP cost: " + this.defaultManaCost + "\n";
        output += "Card Type: " + cardType + "\n";
        output += "Card Tribe: " + tribe.name() + "\n";//TODO correct?
        output += "Is Defencive: " + isDefender + "\n";
        output += "Is Nimble: " + isNimble + "\n";
        return output;
    }

    public void getAwake(){
        isAwake = true;
    }

    public int getDefualtHP() {
        return defualtHP;
    }

    public int getDefaultAP() {
        return defaultAP;
    }
}
