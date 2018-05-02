package Model.Card;

import Model.CardPlace;

import java.util.ArrayList;

/**
 * Created by msi-pc on 4/27/2018.
 */
public abstract class MonsterCard extends Card {
    
    boolean isNimble;
    boolean isDefender;
    int ap;
    int hp;
    Spell battleCry;
    Spell spellCast;
    Spell will;
    //todo set in constructor

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
        monsterCard.hp -= ap;
        hp -= monsterCard.ap;
        this.checkAlive();
        monsterCard.checkAlive();
    }

    public void checkAlive(){
        // how about the player
        if (hp <= 0){
            if (will != null){
                //todo will.use();
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
            }
        }
    }

    // to consider sleeping
}
