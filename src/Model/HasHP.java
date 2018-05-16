package Model;

public interface HasHP extends SpellCastable{
    int getHp();
    void heal(int healAmount);
    void takeDamage(int damageAmount);
    void changeDamageReceivementRatio(double coeffitentofVariation);
    boolean checkAlive();
}
