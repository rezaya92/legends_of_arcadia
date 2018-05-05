package Model.Spell;

import Model.Card.Card;
import Model.SpellChoiceType;
import Model.Tribe;

import java.util.ArrayList;
import java.util.Random;

public abstract class Spell {
    ArrayList<ArrayList<Card>> effectableArea;
    String[] effectableCardType;
    Tribe[] effectableTribe;
    SpellChoiceType choiceType;
    ArrayList<Card> effectableCard = new ArrayList<>();

    public Spell(ArrayList<ArrayList<Card>> effectedArea, String[] effectableCardType, Tribe[] effectedTribe, SpellChoiceType choiceType) {
        this.effectableArea = effectedArea;
        this.effectableCardType = effectableCardType;
        this.effectableTribe = effectedTribe;
        this.choiceType = choiceType;
    }

    public boolean setEffectableCards(){
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
        return choose();
    }

    private boolean choose(){
        switch (choiceType){
            case ALL:
                return true;
            case RANDOM:
                Random random = new Random();
                int index = random.nextInt(effectableCard.size());
                Card choice = effectableCard.get(index);
                effectableCard.clear();
                effectableCard.add(choice);
                return true;
            case SELECT:
                return false;
        }
        return false;
    }

    public boolean use(){ return false; }

    void deuse(){}

    public ArrayList<Card> getEffectableCard() {
        return effectableCard;
    }
}