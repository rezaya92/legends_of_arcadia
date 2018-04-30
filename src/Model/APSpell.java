package Model;

public class APSpell extends Spell {
    int changeAmount;

    public APSpell(CardPlace[] effectedArea, CardType[] effectedCard, Tribe[] effectedTribe, SpellChoiceType choiceType, int changeAmount) {
        super(effectedArea, effectedCard, effectedTribe, choiceType);
        this.changeAmount = changeAmount;
    }
}
