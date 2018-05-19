package Model.Spell;

import Model.Card.MonsterCard;
import Model.Card.Tribe;
import Model.Player;
import Model.SpellCastable;

import java.util.Set;

public class APSpell extends Spell implements Cloneable{
    private int changeAmount;

    public APSpell(Set<SpellArea> effectableArea, Class[] effectableCardType, Set<Tribe> effectableTribe, SpellChoiceType choiceType, int changeAmount) {
        super(effectableArea, effectableCardType, effectableTribe, choiceType);
        this.changeAmount = changeAmount;
    }

    public APSpell(Set<SpellArea> effectableArea, Class[] effectableCardType, SpellChoiceType choiceType, int changeAmount) {
        super(effectableArea, effectableCardType, choiceType);
        this.changeAmount = changeAmount;
    }

    @Override
    protected void apply(Player owner) {
        for (SpellCastable card: effectableCard) {
            if (card instanceof MonsterCard) {
                MonsterCard current = (MonsterCard) card;
                current.setAp(current.getAp() + changeAmount);
            } else
                return;
        }
    }

    @Override
    protected void deuse(Player owner) {
        setEffectableCards(owner);
        for (SpellCastable card: effectableCard) {
            MonsterCard current = (MonsterCard) card;
            current.setAp(current.getAp() - changeAmount);
        }
        //        effectedCard.clear();
    }

    @Override
    public Object clone() throws CloneNotSupportedException{
        return super.clone();
    }
}
