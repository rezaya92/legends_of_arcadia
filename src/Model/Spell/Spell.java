package Model.Spell;

import Model.Card.Card;
import Model.Card.SpellCard;
import Model.Card.Tribe;
import Model.HasTribe;
import Model.SpellCastable;
import View.View;

import java.util.*;

import static Controller.Main.human;

public abstract class Spell {
    private ArrayList<ArrayList<SpellCastable>> effectableAreaCards = new ArrayList<>();
    private Set<SpellArea> effectableArea;
    private Class[] effectableCardType;
    private Set<Tribe> effectableTribe;
    SpellCastable owner;
    SpellChoiceType choiceType;
    ArrayList<SpellCastable> effectableCard = new ArrayList<>();
    //ArrayList<SpellCastable> effectedCard = new ArrayList<>();


    public Spell(Set<SpellArea> effectableArea, Class[] effectableCardType, Set<Tribe> effectableTribe, SpellChoiceType choiceType) {
        this.effectableArea = effectableArea;
        this.effectableCardType = effectableCardType;
        this.effectableTribe = effectableTribe;
        this.choiceType = choiceType;
    }

    public Spell(Set<SpellArea> effectableArea, Class[] effectableCardType, SpellChoiceType choiceType) {
        this(effectableArea, effectableCardType,EnumSet.of(Tribe.DEMONIC, Tribe.ELVEN, Tribe.DRAGONBREED, Tribe.ATLANTIAN), choiceType);
    }

    private void setEffectableAreaCards(){
        for (SpellArea spellArea: effectableArea) {
            ArrayList<SpellCastable> toAdd = new ArrayList<>();
            switch (spellArea){
                case FRIENDLY_MONSTERFIELD:
                    toAdd = new ArrayList<>(owner.getOwner().getMonsterFieldCards());
                    break;
                case FRIENDLY_SPELLFIELD:
                    toAdd = new ArrayList<>(owner.getOwner().getSpellFieldCards());
                    break;
                case FRIENDLY_HAND:
                    toAdd = new ArrayList<>(owner.getOwner().getHandCards());
                    break;
                case FRIENDLY_DECK:
                    toAdd = new ArrayList<>(owner.getOwner().getDeckCards());
                    break;
                case FRIENDLY_GRAVEYARD:
                    toAdd = new ArrayList<>(owner.getOwner().getGraveyardCards());
                    break;
                case FRIENDLY_PLAYER:
                    toAdd = new ArrayList<>();
                    toAdd.add(owner.getOwner().getPlayerHero());
                    break;
                case ENEMY_MONSTERFIELD:
                    toAdd = new ArrayList<>(owner.getOwner().getOpponent().getMonsterFieldCards());
                    break;
                case ENEMY_SPELLFIELD:
                    toAdd = new ArrayList<>(owner.getOwner().getOpponent().getSpellFieldCards());
                    break;
                case ENEMY_HAND:
                    toAdd = new ArrayList<>(owner.getOwner().getOpponent().getHandCards());
                    break;
                case ENEMY_DECK:
                    toAdd = new ArrayList<>(owner.getOwner().getOpponent().getDeckCards());
                    break;
                case ENEMY_GRAVEYARD:
                    toAdd = new ArrayList<>(owner.getOwner().getOpponent().getGraveyardCards());
                    break;
                case ENEMY_PLAYER:
                    toAdd = new ArrayList<>();
                    toAdd.add(owner.getOwner().getOpponent().getPlayerHero());
                    break;
            }
            effectableAreaCards.add(toAdd);
        }
    }

    private void setEffectableCards(){
        setEffectableAreaCards();
        effectableCard = new ArrayList<>();
        for (ArrayList<SpellCastable> cardArray: effectableAreaCards) {
            for (SpellCastable card:cardArray) {
                differentSituations:
                for (Class cardType: effectableCardType){
                    for (Tribe tribe: effectableTribe) {
                        if (cardType.isInstance(card) && (card instanceof SpellCard || ((HasTribe)card).getTribe().equals(tribe) || ((HasTribe)card).getTribe().equals(Tribe.HUMAN))) {
                            effectableCard.add(card);
                            break differentSituations;
                        }
                    }
                }
            }
        }
        effectableCard = new ArrayList<>(new LinkedHashSet<>(effectableCard));
    }


    private void choose(){
        switch (choiceType){
            case ALL:
                return;
            case SELECT:
                if (owner.getOwner() == human) {
                    View.viewSpellEffectableCards(effectableCard);
                    Scanner scanner = new Scanner(System.in);
                    int index = scanner.nextInt() - 1;
                    SpellCastable choice = effectableCard.get(index);
                    effectableCard.clear();
                    effectableCard.add(choice);
                    return;
                }
            case RANDOM:
                Random random = new Random();
                int index = random.nextInt(effectableCard.size());
                SpellCastable choice = effectableCard.get(index);
                effectableCard.clear();
                effectableCard.add(choice);
        }
    }

    void use(){
        setEffectableCards();
        choose();
        apply();
    //    effectedCard.addAll(effectableCard);
    //    effectableCard.clear();
    }

    /*void use(SpellCastable choice){
        effectableCard = new ArrayList<>(1);
        effectableCard.add(choice);
        apply();
    //    effectedCard.addAll(effectableCard);
    //    effectableCard.clear();
    }*/

    abstract void apply();

    abstract void deuse();

    public ArrayList<SpellCastable> getEffectableCard() {
        return effectableCard;
    }

    public SpellChoiceType getChoiceType() {
        return choiceType;
    }

    //public ArrayList<SpellCastable> getEffectedCard() {
    //    return effectedCard;
    //}

    void setOwner(SpellCastable owner) {
        this.owner = owner;
    }

    public void setEffectableCard(ArrayList<SpellCastable> effectableCard) {
        this.effectableCard = effectableCard;
    }
}