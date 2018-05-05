package Model.Spell;

import Model.Card.Card;
import Model.Card.MonsterCard;
import Model.CardPlace;
import Model.Spell.Spell;
import Model.SpellChoiceType;
import Model.Tribe;

import java.util.ArrayList;
import java.util.Iterator;

public class HPSpell extends Spell {
    int changeAmount;

    public HPSpell(ArrayList<ArrayList<Card>> effectedArea, String[] effectedCard, Tribe[] effectedTribe, SpellChoiceType choiceType, int changeAmount) {
        super(effectedArea, effectedCard, effectedTribe, choiceType);
        this.changeAmount = changeAmount;
    }

    @Override
    boolean use() {
        setEffectableCards();
        choose();
        boolean flag = false;
        for (Card card: effectableCard) {
            flag = true;
            if (card instanceof MonsterCard) {
                MonsterCard current = (MonsterCard) card;
                current.setHp(current.getHp() + changeAmount);
                current.checkAlive();
            }
            /*else if (card instanceof  PlayerCard){
                PlayerCard current = (PlayerCard) card;
                current.setHp(current.getHp() + changeAmount);
            }*/
            //TODO PlayerCard needed
            else
                return false;
        }
        effectedCard.addAll(effectableCard);
        effectableCard.clear();
        return flag;
    }

    @Override
    boolean use(Card choice) {
        effectableCard = new ArrayList<Card>(1);
        effectableCard.add(choice);
        boolean flag = false;
        for (Card card: effectableCard) {
            flag = true;
            if (card instanceof MonsterCard) {
                MonsterCard current = (MonsterCard) card;
                current.setHp(current.getHp() + changeAmount);
                current.checkAlive();
            }
            /*else if (card instanceof  PlayerCard){
                PlayerCard current = (PlayerCard) card;
                current.setHp(current.getHp() + changeAmount);
            }*/
            //TODO PlayerCard needed
            else
                return false;
        }
        effectedCard.addAll(effectableCard);
        effectableCard.clear();
        return flag;
    }

    @Override
    void deuse() {
        for (Card card: effectableCard) {
            if (card instanceof MonsterCard) {
                MonsterCard current = (MonsterCard) card;
                current.setHp(current.getHp() - changeAmount);
                current.checkAlive();
            }
            /*else if (card instanceof  PlayerCard){
                PlayerCard current = (PlayerCard) card;
                current.setHp(current.getHp() - changeAmount);
            }*/
            //TODO PlayerCard needed
        }
        effectedCard.clear();
        effectableCard.clear();
    }
}
