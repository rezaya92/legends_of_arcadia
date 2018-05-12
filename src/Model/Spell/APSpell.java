package Model.Spell;

import Model.Card.MonsterCard;
import Model.Card.Tribe;
import Model.SpellArea;
import Model.SpellCastable;

import java.util.Set;

public class APSpell extends Spell {
    private int changeAmount;

    public APSpell(Set<SpellArea> effectableArea, Class[] effectableCardType, Set<Tribe> effectableTribe, SpellChoiceType choiceType, int changeAmount) {
        super(effectableArea, effectableCardType, effectableTribe, choiceType);
        this.changeAmount = changeAmount;
    }

    @Override
    void apply() {
        for (SpellCastable card: effectableCard) {
            if (card instanceof MonsterCard) {
                MonsterCard current = (MonsterCard) card;
                current.setAp(current.getAp() + changeAmount);
            } else
                return;
        }
    }

    @Override
    void deuse() {
        for (SpellCastable card: effectableCard) {
            MonsterCard current = (MonsterCard) card;
            current.setAp(current.getAp() - changeAmount);
        }
        effectedCard.clear();
        effectableCard.clear();
    }
}
