package Model.Spell;

import Model.Card.MonsterCard;
import Model.Card.Tribe;
import Model.HasHP;
import Model.Player;
import Model.PlayerHero;
import Model.SpellCastable;

import java.util.Set;

public class HPSpell extends Spell implements Cloneable{
    private int changeAmount;

    public HPSpell(Set<SpellArea> effectableArea, Class[] effectableCardType, Set<Tribe> effectableTribe, SpellChoiceType choiceType, int changeAmount) {
        super(effectableArea,effectableCardType,effectableTribe,choiceType);
        this.changeAmount = changeAmount;
    }

    public HPSpell(Set<SpellArea> effectableArea, Class[] effectableCardType, SpellChoiceType choiceType, int changeAmount) {
        super(effectableArea, effectableCardType, choiceType);
        this.changeAmount = changeAmount;
    }

    @Override
    protected void apply(Player owner) {
        for (SpellCastable card: effectableCard) {
                if (changeAmount > 0)
                    ((HasHP)card).heal(changeAmount);
                else
                    ((HasHP)card).takeDamage(-changeAmount);
                ((HasHP) card).checkAlive();
        }
    }

    @Override
    protected void deuse(Player owner) {
        setEffectableCards(owner);
        for (SpellCastable card: effectableCard) {
            if (changeAmount > 0)
                ((HasHP)card).heal(-changeAmount);
            else
                ((HasHP)card).takeDamage(changeAmount);
        }
        //effectedCard.clear();
        effectableCard.clear();
    }

    void checkDead(Player owner){
        setEffectableCards(owner);
        for (SpellCastable card: effectableCard) {
            ((HasHP)card).checkAlive();
        }
        //effectedCard.clear();
        effectableCard.clear();
    }

    @Override
    public Object clone() throws CloneNotSupportedException{
        return super.clone();
    }
}