package Model.Spell;

import Model.Card.Card;
import Model.CardPlace;
import Model.Spell.Spell;
import Model.SpellChoiceType;
import Model.Tribe;

import java.util.ArrayList;

public class HPSpell extends Spell {
    int changeAmount;

    public HPSpell(ArrayList<ArrayList<Card>> effectedArea, String[] effectedCard, Tribe[] effectedTribe, SpellChoiceType choiceType, int changeAmount) {
        super(effectedArea, effectedCard, effectedTribe, choiceType);
        this.changeAmount = changeAmount;
    }

    @Override
    boolean use() {
        return false;
    }

    @Override
    void deuse() {

    }
}
