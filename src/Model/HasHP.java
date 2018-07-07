package Model;

import javafx.beans.property.SimpleIntegerProperty;

public interface HasHP extends SpellCastable, Cloneable{
    int getHp();
    void heal(int healAmount);
    void takeDamage(int damageAmount);
    void changeDamageReceivementRatio(double coeffitentofVariation);
    boolean checkAlive();

    public Object clone() throws CloneNotSupportedException;
}
