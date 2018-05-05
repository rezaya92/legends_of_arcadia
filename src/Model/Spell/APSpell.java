package Model.Spell;

import Model.Card.Card;
import Model.Card.MonsterCard;
import Model.Spell.Spell;
import Model.SpellChoiceType;
import Model.Tribe;

import java.util.ArrayList;
import java.util.Iterator;

public class APSpell extends Spell {
    int changeAmount;

    public APSpell(ArrayList<ArrayList<Card>> effectedArea, String[] effectableCardType, Tribe[] effectedTribe, SpellChoiceType choiceType, int changeAmount) {
        super(effectedArea, effectableCardType, effectedTribe, choiceType);
        this.changeAmount = changeAmount;
    }

    @Override
    boolean use() {
        boolean flag = false;
        for (Card card: effectableCard) {
            flag = true;
            if (card instanceof MonsterCard) {
                MonsterCard current = (MonsterCard) card;
                current.setAp(current.getAp() + changeAmount);
            } else
                return false;
        }
        return flag;
    }

    @Override
    void deuse() {

    }
}
