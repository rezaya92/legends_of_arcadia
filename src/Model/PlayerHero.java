package Model;

import Model.Spell.GeneralizedSpell;
import Model.Spell.NoEffectableCardException;
import View.ConsoleView;


// description: this could be in Player class, brought here for code to be more clear.
/**
 * Created by msi-pc on 5/7/2018.
 */
public class PlayerHero implements HasHP, Cloneable {
    private int defaultHP;
    private int hp;
    private double damageReceivementRatio = 1;
    private String name = "Player";
    private Player owner;

    public PlayerHero(int defaultHP, Player owner){
        this.hp = this.defaultHP = defaultHP;
        this.owner = owner;
    }

    public int getHp() {
        return hp;
    }
    //public void setHp(int hp) {
    //    this.hp = hp;
    //}

    public void restore(){
        hp = defaultHP;
    }

    public Player getOwner(){return owner;}

    public void takeDamage(int damageAmount){
        hp -= (int)(damageAmount*damageReceivementRatio);
    }

    public void heal(int healAmount){
        hp += healAmount;
    }

    public void useItem(GeneralizedSpell item){ // must be in items (is check needed?)
        try {
            item.use(owner);
        }catch (NoEffectableCardException e){
            ConsoleView.noEffectableCard();
        }
    }

    public boolean checkAlive(){
        if (hp <= 0){
            ConsoleView.battleOver(owner);  // owner = loser
            return false;
        }
        return true;
    }

    @Override
    public String getName() {
        return name;
    }

    public void changeDamageReceivementRatio(double coefficentofVariation) {
        this.damageReceivementRatio *= coefficentofVariation;
    }

    @Override
    public Object clone() throws CloneNotSupportedException{
        return super.clone();
    }
}
