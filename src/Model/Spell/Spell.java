package Model.Spell;

import Model.Card.Card;
import Model.Card.Tribe;

import java.util.ArrayList;
import java.util.Random;

public abstract class Spell {
    private ArrayList<ArrayList<Card>> effectableArea;
    private String[] effectableCardType;
    private Tribe[] effectableTribe;
    SpellChoiceType choiceType;
    ArrayList<Card> effectableCard = new ArrayList<>();
    ArrayList<Card> effectedCard = new ArrayList<>();

    public Spell(ArrayList<ArrayList<Card>> effectableArea, String[] effectableCardType, Tribe[] effectableTribe, SpellChoiceType choiceType) {
        this.effectableArea = effectableArea;
        this.effectableCardType = effectableCardType;
        this.effectableTribe = effectableTribe;
        this.choiceType = choiceType;
    }

    void setEffectableCards(){
        effectableCard = new ArrayList<>();
        for (ArrayList<Card> cardArray: effectableArea) {
            for (Card card:cardArray) {
                for (String cardType: effectableCardType){
                    for (Tribe tribe: effectableTribe) {
                        if (card.getClass().getName().equals(cardType) && card.getTribe().equals(tribe)) {
                            effectableCard.add(card);
                            break;
                        }
                    }
                }
            }
        }
        effectableCard.size();
    }

    void choose(){
        switch (choiceType){
            case ALL:
                return;
            case RANDOM:
                Random random = new Random();
                int index = random.nextInt(effectableCard.size());
                Card choice = effectableCard.get(index);
                effectableCard.clear();
                effectableCard.add(choice);
                return;
            case SELECT:
        }
    }

    abstract void use();
    abstract void use(Card choice);

    abstract void deuse();

    public ArrayList<Card> getEffectableCard() {
        return effectableCard;
    }

    public SpellChoiceType getChoiceType() {
        return choiceType;
    }

    public ArrayList<Card> getEffectedCard() {
        return effectedCard;
    }
}