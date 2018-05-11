package Model.Spell;

import Model.Card.*;
import Model.Player;
import Model.SpellCastable;

import java.util.ArrayList;

public class MoveSpell extends Spell {
    private ArrayList<Card> destination;

    public MoveSpell(ArrayList<ArrayList<SpellCastable>> effectedArea, String[] effectableCardType, Tribe[] effectedTribe, SpellChoiceType choiceType, ArrayList<Card> destination) {
        super(effectedArea, effectableCardType, effectedTribe, choiceType);
        this.destination = destination;
    }

    @Override
    void apply() {
        for (SpellCastable card: effectableCard) {
            ((Card)card).transfer(destination);
        }
    }

    @Override
    void deuse() {
        //deuse nadare (aura nemitoone move kone)
    }
}
