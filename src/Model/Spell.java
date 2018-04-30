package Model;

abstract class Spell {
    CardPlace[] effectedArea = new CardPlace[6];
    CardType[] effectedCard = new CardType[6];
    Tribe[] effectedTribe = new Tribe[5];
    SpellChoiceType choiceType;

    public Spell(CardPlace[] effectedArea, CardType[] effectedCard, Tribe[] effectedTribe, SpellChoiceType choiceType) {
        this.effectedArea = effectedArea;
        this.effectedCard = effectedCard;
        this.effectedTribe = effectedTribe;
        this.choiceType = choiceType;
    }


}