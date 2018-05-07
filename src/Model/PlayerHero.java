package Model;

import Model.Spell.GeneralizedSpell;

import java.util.ArrayList;

// description: this could be in Player class, brought here for code to be more clear.
/**
 * Created by msi-pc on 5/7/2018.
 */
public class PlayerHero {
    private int defaultHP;
    private int hp;
    private ArrayList<GeneralizedSpell> items;
    private Player owner;

    public PlayerHero(int defaultHP, Player owner){
        this.hp = this.defaultHP = defaultHP;
        this.owner = owner;
    }

    public int getHp() {
        return hp;
    }
    public void setHp(int hp) {
        this.hp = hp;
    }

    public Player getOwner(){return owner;}

    public void useItem(GeneralizedSpell item){ // must be in items (is check needed?)
        item.use();
    }

    public void checkAlive(){
        if (hp <= 0){
            // todo game ends
        }
    }
}
