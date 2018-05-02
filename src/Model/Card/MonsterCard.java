package Model.Card;

import Model.CardPlace;

/**
 * Created by msi-pc on 4/27/2018.
 */
public abstract class MonsterCard extends Card {
    
    boolean isNimble;
    boolean isDefender;
    int ap;
    int hp;

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

    }
}
