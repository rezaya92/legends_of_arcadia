package Model.Spell;

import Model.Card.Tribe;
import Model.HasHP;
import Model.Player;
import Model.SpellCastable;

import java.util.ArrayList;
import java.util.Set;

public class RatioSpell extends Spell implements Cloneable{
    private double coefficentofVariation;

    public RatioSpell(Set<SpellArea> effectableArea, Class[] effectableCardType, Set<Tribe> effectableTribe, SpellChoiceType choiceType,double coefficentofVariation) {
        super(effectableArea, effectableCardType, effectableTribe, choiceType);
        this.coefficentofVariation = coefficentofVariation;
    }

    public RatioSpell(Set<SpellArea> effectableArea, Class[] effectableCardType, SpellChoiceType choiceType,double coefficentofVariation) {
        super(effectableArea, effectableCardType, choiceType);
        this.coefficentofVariation = coefficentofVariation;
    }

    @Override
    void apply(Player owner) {
        for (SpellCastable card: effectableCard) {
            ((HasHP)card).changeDamageReceivementRatio(coefficentofVariation);
        }
    }

    @Override
    void deuse(Player owner) {
        setEffectableCards(owner);
        for (SpellCastable card: effectableCard) {
            ((HasHP)card).changeDamageReceivementRatio(1 / coefficentofVariation);
        }
    }

    @Override
    public Object clone() throws CloneNotSupportedException{
        return super.clone();
    }
}
