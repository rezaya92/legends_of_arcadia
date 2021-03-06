package Model.Spell;

import Model.Card.Tribe;
import Model.Player;
import Model.PlayerHero;

import java.io.Serializable;
import java.util.Set;

public class MPSpell extends Spell implements Cloneable, Serializable{
    private int changeAmount;

    public MPSpell(Set<SpellArea> effectableArea, Class[] effectableCardType, Set<Tribe> effectableTribe, SpellChoiceType choiceType, int changeAmount) {
        super(effectableArea, effectableCardType, effectableTribe, choiceType);
        this.changeAmount = changeAmount;
    }

    public MPSpell(Set<SpellArea> effectableArea, Class[] effectableCardType, SpellChoiceType choiceType, int changeAmount) {
        super(effectableArea, effectableCardType, choiceType);
        this.changeAmount = changeAmount;
    }

    @Override
    protected void apply(Player owner) {
        for (SpellCastable card: effectableCard) {
            if (card instanceof PlayerHero) {
                PlayerHero current = (PlayerHero) card;
                current.getOwner().setMana(current.getOwner().getMana() + changeAmount);
            } else {
                effectableCard.clear();
                effectableAreaCards.clear();
                return;
            }
        }
        effectableCard.clear();
        effectableAreaCards.clear();
    }

    @Override
    protected void deuse(Player owner) {
        setEffectableCards(owner);
        for (SpellCastable card: effectableCard) {
            PlayerHero current = (PlayerHero) card;
            current.getOwner().setMana(current.getOwner().getMana() - changeAmount);
        }
        //        effectedCard.clear();
        effectableCard.clear();
    }

    @Override
    public Object clone() throws CloneNotSupportedException{
        return super.clone();
    }
}
