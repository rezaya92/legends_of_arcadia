package Model;

import Model.Card.Card;
import Model.Spell.GeneralizedSpell;
import Model.Spell.NoEffectableCardException;
import View.GameView.ConsoleView;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;


// description: this could be in Player class, brought here for code to be more clear.
/**
 * Created by msi-pc on 5/7/2018.
 */
public class PlayerHero implements HasHP, Cloneable {
    private int defaultHP;
    private SimpleIntegerProperty hp = new SimpleIntegerProperty();
    private double damageReceivementRatio = 1;
    private String name = "Player";
    private Player owner;

    public PlayerHero(int defaultHP, Player owner){
        this.defaultHP = defaultHP;
        this.hp.set(defaultHP);
        this.owner = owner;
    }

    private ObservableList<Card> a = FXCollections.observableArrayList(new ArrayList<>());
    public int getHp(){
        return hp.get();
    }
    public SimpleIntegerProperty hpProperty() {
        return hp;
    }
    //public void setHp(int hp) {
    //    this.hp = hp;
    //}

    public void restore(){
        hp.set(defaultHP);
    }

    public Player getOwner(){return owner;}

    public void takeDamage(int damageAmount){
        hp.set(hp.get() -(int)(damageAmount*damageReceivementRatio));
    }

    public void heal(int healAmount){
        hp.set(hp.get() + healAmount);
    }

    public void useItem(GeneralizedSpell item){ // must be in items (is check needed?)
        try {
            item.use(owner);
        }catch (NoEffectableCardException e){
            ConsoleView.noEffectableCard();
        }
    }

    public boolean checkAlive(){
        if (hp.get() < 0){
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
