package Model.Spell;

import Model.Card.Tribe;
import Model.Player;
import Model.SpellArea;
import Model.SpellCastable;

import java.util.*;

public abstract class Spell {
    private ArrayList<ArrayList<SpellCastable>> effectableAreaCards = new ArrayList<>();
    private Set<SpellArea> effectableArea;
    private Class[] effectableCardType;
    private Set<Tribe> effectableTribe;
    private Player owner;
    SpellChoiceType choiceType;
    ArrayList<SpellCastable> effectableCard = new ArrayList<>();
    ArrayList<SpellCastable> effectedCard = new ArrayList<>();


    public Spell(Set<SpellArea> effectableArea, Class[] effectableCardType, Set<Tribe> effectableTribe, SpellChoiceType choiceType) {
        this.effectableArea = effectableArea;
        this.effectableCardType = effectableCardType;
        this.effectableTribe = effectableTribe;
        this.choiceType = choiceType;
    }

    private void setEffectableAreaCards(){
        for (SpellArea spellArea: effectableArea) {
            ArrayList<SpellCastable> toAdd = new ArrayList<>();
            switch (spellArea){
                case FRIENDLY_MONSTERFIELD:
                    toAdd = new ArrayList<>(owner.getMonsterFieldCards());
                    break;
                case FRIENDLY_SPELLFIELD:
                    toAdd = new ArrayList<>(owner.getSpellFieldCards());
                    break;
                case FRIENDLY_HAND:
                    toAdd = new ArrayList<>(owner.getHandCards());
                    break;
                case FRIENDLY_DECK:
                    toAdd = new ArrayList<>(owner.getDeckCards());
                    break;
                case FRIENDLY_GRAVEYARD:
                    toAdd = new ArrayList<>(owner.getGraveyardCards());
                    break;
                case FRIENDLY_PLAYER:
                    toAdd = new ArrayList<>();
                    toAdd.add(owner.getPlayerHero());
                    break;
                case ENEMY_MONSTERFIELD:
                    toAdd = new ArrayList<>(owner.getOpponent().getMonsterFieldCards());
                    break;
                case ENEMY_SPELLFIELD:
                    toAdd = new ArrayList<>(owner.getOpponent().getSpellFieldCards());
                    break;
                case ENEMY_HAND:
                    toAdd = new ArrayList<>(owner.getOpponent().getHandCards());
                    break;
                case ENEMY_DECK:
                    toAdd = new ArrayList<>(owner.getOpponent().getDeckCards());
                    break;
                case ENEMY_GRAVEYARD:
                    toAdd = new ArrayList<>(owner.getOpponent().getGraveyardCards());
                    break;
                case ENEMY_PLAYER:
                    toAdd = new ArrayList<>();
                    toAdd.add(owner.getOpponent().getPlayerHero());
                    break;
            }
            effectableAreaCards.add(toAdd);
        }
    }

    void setEffectableCards(){
        setEffectableAreaCards();
        effectableCard = new ArrayList<>();
        for (ArrayList<SpellCastable> cardArray: effectableAreaCards) {
            for (SpellCastable card:cardArray) {
                for (Class cardType: effectableCardType){
                    for (Tribe tribe: effectableTribe) {
                        if (cardType.isInstance(card) && (card.getTribe().equals(tribe) || card.getTribe().equals(Tribe.HUMAN))) {
                            effectableCard.add(card);
                            break;
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
        effectableCard = new ArrayList<>(1);
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

    void setOwner(Player owner) {
        this.owner = owner;
    }
}