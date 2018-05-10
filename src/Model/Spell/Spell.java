package Model.Spell;

import Model.Card.Tribe;
import Model.SpellCastable;

import java.util.ArrayList;
import java.util.Random;

public abstract class Spell {
    private ArrayList<ArrayList<SpellCastable>> effectableArea;
    private String[] effectableCardType;
    private Tribe[] effectableTribe;
    SpellChoiceType choiceType;
    ArrayList<SpellCastable> effectableCard = new ArrayList<SpellCastable>();
    ArrayList<SpellCastable> effectedCard = new ArrayList<SpellCastable>();

    public Spell(ArrayList<ArrayList<SpellCastable>> effectableArea, String[] effectableCardType, Tribe[] effectableTribe, SpellChoiceType choiceType) {
        this.effectableArea = effectableArea;
        this.effectableCardType = effectableCardType;
        this.effectableTribe = effectableTribe;
        this.choiceType = choiceType;
    }

    void setEffectableCards(){
        effectableCard = new ArrayList<SpellCastable>();
        for (ArrayList<SpellCastable> cardArray: effectableArea) {
            for (SpellCastable card:cardArray) {
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
                SpellCastable choice = effectableCard.get(index);
                effectableCard.clear();
                effectableCard.add(choice);
                return;
            case SELECT:
        }
    }

    void use(){
        setEffectableCards();
        choose();
        apply();
        effectedCard.addAll(effectableCard);
        effectableCard.clear();
    }
    void use(SpellCastable choice){
        effectableCard = new ArrayList<SpellCastable>(1);
        effectableCard.add(choice);
        apply();
        effectedCard.addAll(effectableCard);
        effectableCard.clear();
    }
    abstract void apply();

    abstract void deuse();

    public ArrayList<SpellCastable> getEffectableCard() {
        return effectableCard;
    }

    public SpellChoiceType getChoiceType() {
        return choiceType;
    }

    public ArrayList<SpellCastable> getEffectedCard() {
        return effectedCard;
    }
}