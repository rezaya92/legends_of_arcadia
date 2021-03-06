package Model;

import Model.Card.Card;
import Model.Spell.GeneralizedSpell;
import Model.Spell.NoEffectableCardException;
import View.GameView.ConsoleView;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;


// description: this could be in Player class, brought here for code to be more clear.
/**
 * Created by msi-pc on 5/7/2018.
 */
public class PlayerHero implements HasHP, Cloneable, Serializable {
    private int defaultHP;
    private transient SimpleIntegerProperty hp = new SimpleIntegerProperty();
    private double damageReceivementRatio = 1;
    private Player owner;

    public PlayerHero(int defaultHP, Player owner){
        this.defaultHP = defaultHP;
        this.hp.set(defaultHP);
        this.owner = owner;
    }

    public int getHp(){
        return hp.get();
    }

    public void setHp(int hp) {
        this.hp.set(hp);
    }

    public SimpleIntegerProperty hpProperty() {
        return hp;
    }
    //public void setHp(int hp) {
    //    this.hp = hp;
    //}

    public void restore(){
        hp = new SimpleIntegerProperty(defaultHP);
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
        return owner.getName();
    }

    public void changeDamageReceivementRatio(double coefficentofVariation) {
        this.damageReceivementRatio *= coefficentofVariation;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        Integer hpInteger = hp.getValue();
        objectOutputStream.writeObject(hpInteger);
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException{
        objectInputStream.defaultReadObject();
        hp = new SimpleIntegerProperty((Integer)objectInputStream.readObject());
    }

    @Override
    public Object clone() throws CloneNotSupportedException{
        return super.clone();
    }

    @Override
    public String toString() {
        return owner.toString();
    }
}
