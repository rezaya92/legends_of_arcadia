package Model.Spell;

import Model.Card.Card;
import Model.Spell.Spell;
import Model.SpellChoiceType;
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
        setEffectableCard();
        return true;

    }

    @Override
    void deuse() {

    }
}
