package Model.Spell;

import Model.Card.Card;
import Model.Card.MonsterCard;
import Model.Card.Tribe;

import java.util.ArrayList;

public class APSpell extends Spell {
    private int changeAmount;

    public APSpell(ArrayList<ArrayList<Card>> effectedArea, String[] effectableCardType, Tribe[] effectedTribe, SpellChoiceType choiceType, int changeAmount) {
        super(effectedArea, effectableCardType, effectedTribe, choiceType);
        this.changeAmount = changeAmount;
    }

    @Override
    void use() {
        setEffectableCards();
        choose();
        for (Card card: effectableCard) {
            if (card instanceof MonsterCard) {
                MonsterCard current = (MonsterCard) card;
                current.setAp(current.getAp() + changeAmount);
            } else
                return;
        }
        effectedCard.addAll(effectableCard);
        effectableCard.clear();
    }

    @Override
    void use(Card choice) {
        effectableCard = new ArrayList<>(1);
        effectableCard.add(choice);
        for (Card card: effectableCard) {
            if (card instanceof MonsterCard) {
                MonsterCard current = (MonsterCard) card;
                current.setAp(current.getAp() + changeAmount);
            } else
                return;
        }
        effectedCard.addAll(effectableCard);
        effectableCard.clear();
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
