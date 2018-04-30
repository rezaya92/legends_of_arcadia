package Model;

abstract class Spell {
    CardPlace[] effectedArea = new CardPlace[6];
    CardType[] effectedCard = new CardType[6];
    Tribe[] effectedTribe = new Tribe[5];
    SpellChoiceType choiceType;

}