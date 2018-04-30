package Model;

public class MoveSpell extends Spell {
    CardPlace destination;

    public MoveSpell(CardPlace[] effectedArea, CardType[] effectedCard, Tribe[] effectedTribe, SpellChoiceType choiceType, CardPlace destination) {
        super(effectedArea, effectedCard, effectedTribe, choiceType);
        this.destination = destination;
    }
}
