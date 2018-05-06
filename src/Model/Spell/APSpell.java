package Model.Spell;

import Model.Card.Card;
import Model.Card.MonsterCard;
import Model.Tribe;

import java.util.ArrayList;

public class APSpell extends Spell {
    int changeAmount;

    public APSpell(ArrayList<ArrayList<Card>> effectedArea, String[] effectableCardType, Tribe[] effectedTribe, SpellChoiceType choiceType, int changeAmount) {
        super(effectedArea, effectableCardType, effectedTribe, choiceType);
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
                current.setAp(current.getAp() + changeAmount);
            } else
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
                current.setAp(current.getAp() + changeAmount);
            } else
                return false;
        }
        effectedCard.addAll(effectableCard);
        effectableCard.clear();
        return flag;
    }

    @Override
    void deuse() {
        for (Card card: effectableCard) {
            MonsterCard current = (MonsterCard) card;
            current.setAp(current.getAp() - changeAmount);
        }
        effectedCard.clear();
        effectableCard.clear();
    }
}
