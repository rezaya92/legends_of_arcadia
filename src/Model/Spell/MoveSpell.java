package Model.Spell;

import Model.Card.Card;
import Model.CardPlace;
import Model.Spell.Spell;
import Model.SpellChoiceType;
import Model.Tribe;

import java.util.ArrayList;

public class MoveSpell extends Spell {
    CardPlace destination;

    public MoveSpell(ArrayList<ArrayList<Card>> effectedArea, String[] effectableCardType, Tribe[] effectedTribe, SpellChoiceType choiceType, CardPlace destination) {
        super(effectedArea, effectableCardType, effectedTribe, choiceType);
        this.destination = destination;
    }

    @Override
    boolean use() {
        return false;
    }

    @Override
    void deuse() {

    }
}
