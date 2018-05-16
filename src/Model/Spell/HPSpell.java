package Model.Spell;

import Model.Card.MonsterCard;
import Model.Card.Tribe;
import Model.PlayerHero;
import Model.SpellCastable;

import java.util.Set;

public class HPSpell extends Spell {
    private int changeAmount;

    public HPSpell(Set<SpellArea> effectableArea, Class[] effectableCardType, Set<Tribe> effectableTribe, SpellChoiceType choiceType, int changeAmount) {
        super(effectableArea,effectableCardType,effectableTribe,choiceType);
        this.changeAmount = changeAmount;
    }

    @Override
    void apply() {
        for (SpellCastable card: effectableCard) {
            if (card instanceof MonsterCard) {
                MonsterCard current = (MonsterCard) card;
                current.setHp(current.getHp() + changeAmount);
                current.checkAlive();
            }
            else if (card instanceof PlayerHero){
                PlayerHero current = (PlayerHero) card;
                current.setHp(current.getHp() + changeAmount);
                current.checkAlive();
            }
            else
                return;
        }

    }

    @Override
    void deuse() {
        for (SpellCastable card: effectableCard) {
            if (card instanceof MonsterCard) {
                MonsterCard current = (MonsterCard) card;
                current.setHp(current.getHp() - changeAmount);
                current.checkAlive();
            }
            else if (card instanceof PlayerHero){
                PlayerHero current = (PlayerHero) card;
                current.setHp(current.getHp() + changeAmount);
            }
        }
        //effectedCard.clear();
        effectableCard.clear();
    }
}