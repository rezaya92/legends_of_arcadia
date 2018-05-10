package Model.Spell;

import Model.Card.Card;
import Model.Card.MonsterCard;
import Model.Card.PlayerHero;
import Model.Card.Tribe;
import Model.SpellCastable;

import java.util.ArrayList;

public class HPSpell extends Spell {
    private int changeAmount;

    public HPSpell(ArrayList<ArrayList<SpellCastable>> effectableArea, String[] effectableCardType, Tribe[] effectableTribe, SpellChoiceType choiceType, int changeAmount) {
        super(effectableArea,effectableCardType,effectableTribe,choiceType);
        this.changeAmount = changeAmount;
    }

    @Override
    void use() {
        setEffectableCards();
        choose();
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
        effectedCard.addAll(effectableCard);
        effectableCard.clear();
    }

    @Override
    void use(Card choice) {
        effectableCard = new ArrayList<SpellCastable>(1);
        effectableCard.add(choice);
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
        effectedCard.addAll(effectableCard);
        effectableCard.clear();
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
        effectedCard.clear();
        effectableCard.clear();
    }
}
