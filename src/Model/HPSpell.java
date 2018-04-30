package Model;

public class HPSpell extends Spell {
    int changeAmount;

    public HPSpell(CardPlace[] effectedArea, CardType[] effectedCard, Tribe[] effectedTribe, SpellChoiceType choiceType, int changeAmount) {
        super(effectedArea, effectedCard, effectedTribe, choiceType);
        this.changeAmount = changeAmount;
    }
}
