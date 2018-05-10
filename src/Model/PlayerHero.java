package Model;

import Model.Card.CardPlace;
import Model.Card.Tribe;
import Model.Spell.GeneralizedSpell;

import java.util.ArrayList;

// description: this could be in Player class, brought here for code to be more clear.
/**
 * Created by msi-pc on 5/7/2018.
 */
public class PlayerHero implements SpellCastable { // todo amulet
    private int defaultHP;
    private CardPlace cardPlace = CardPlace.PLAYERAREA;
    private int hp;
    private String name = "Enemy Player";
    private ArrayList<GeneralizedSpell> items;
    private Player owner;
    private Tribe tribe;

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

    public CardPlace getCardPlace() {
        return cardPlace;
    }

    public void setCardPlace(CardPlace cardPlace) {
        this.cardPlace = cardPlace;
    }

    public Tribe getTribe() {
        return tribe;
    }

    @Override
    public String getName() {
        return name;
    }
}
