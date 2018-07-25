package Model.Spell;

import Model.Card.MonsterCard;
import Model.Card.Tribe;
import Model.Player;
import View.GameView.ConsoleView;
import View.GameView.GameView;

import java.io.Serializable;
import java.util.*;

import static Controller.Main.human;

public abstract class Spell implements Cloneable, Serializable{
    ArrayList<ArrayList<SpellCastable>> effectableAreaCards = new ArrayList<>();
    private Set<SpellArea> effectableArea;
    private Class[] effectableCardType;
    private Set<Tribe> effectableTribe;
    SpellChoiceType choiceType;
    ArrayList<SpellCastable> effectableCard = new ArrayList<>();
    //ArrayList<SpellCastable> effectedCard = new ArrayList<>();
    private String name;


    public Spell(Set<SpellArea> effectableArea, Class[] effectableCardType, Set<Tribe> effectableTribe, SpellChoiceType choiceType) {
        this.effectableArea = effectableArea;
        this.effectableCardType = effectableCardType;
        this.effectableTribe = effectableTribe;
        this.choiceType = choiceType;
    }

    public Spell(Set<SpellArea> effectableArea, Class[] effectableCardType, SpellChoiceType choiceType) {
        this(effectableArea, effectableCardType,EnumSet.of(Tribe.DEMONIC, Tribe.ELVEN, Tribe.DRAGONBREED, Tribe.ATLANTIAN), choiceType);
    }

    private void setEffectableAreaCards(Player owner){
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
                    if (owner.getOpponent()!= null) {
                        toAdd = new ArrayList<>(owner.getOpponent().getMonsterFieldCards());
                    }
                    break;
                case ENEMY_SPELLFIELD:
                    if (owner.getOpponent()!= null) {
                        toAdd = new ArrayList<>(owner.getOpponent().getSpellFieldCards());
                    }
                    break;
                case ENEMY_HAND:
                    if (owner.getOpponent()!= null) {
                        toAdd = new ArrayList<>(owner.getOpponent().getHandCards());
                    }
                    break;
                case ENEMY_DECK:
                    if (owner.getOpponent()!= null) {
                        toAdd = new ArrayList<>(owner.getOpponent().getDeckCards());
                    }
                    break;
                case ENEMY_GRAVEYARD:
                    if (owner.getOpponent()!= null){
                        toAdd = new ArrayList<>(owner.getOpponent().getGraveyardCards());
                    }
                    break;
                case ENEMY_PLAYER:
                    if (owner.getOpponent()!= null) {
                        toAdd = new ArrayList<>();
                        toAdd.add(owner.getOpponent().getPlayerHero());
                    }
                    break;
            }
            effectableAreaCards.add(toAdd);
        }
    }

    protected void setEffectableCards(Player owner){
        setEffectableAreaCards(owner);
        effectableCard = new ArrayList<>();
        for (ArrayList<SpellCastable> cardArray: effectableAreaCards) {
            for (SpellCastable card:cardArray) {
                differentSituations:
                for (Class cardType: effectableCardType){
                    for (Tribe tribe: effectableTribe) {
                        if (cardType.isInstance(card) && ( !(card instanceof MonsterCard) || ((MonsterCard)card).getTribe().equals(tribe) )) {
                            effectableCard.add(card);
                            break differentSituations;
                        }
                    }
                }
            }
        }
        effectableCard = new ArrayList<>(new LinkedHashSet<>(effectableCard));
    }


    private void choose(Player owner) throws NoEffectableCardException {
        if (effectableCard.size() == 0 && choiceType == SpellChoiceType.SELECT)
            throw new NoEffectableCardException();
        switch (choiceType){
            case ALL:
                return;
            case SELECT:
                if (owner == human) {
                    ConsoleView.viewSpellEffectableCards(effectableCard);
                    GameView.showSpellCastScene(this);
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

    void use(Player owner) throws NoEffectableCardException{
        setEffectableCards(owner);
        choose(owner);
        if (choiceType != SpellChoiceType.SELECT || owner != human) {
            apply(owner);
            GameView.updateFields();
        }
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

    protected abstract void apply(Player owner);

    public void apply(Player owner, SpellCastable effectableCard){
        this.effectableCard.clear();
        this.effectableCard.add(effectableCard);
        apply(owner);
        GameView.updateFields();
    }

    protected abstract void deuse(Player owner);

    public ArrayList<SpellCastable> getEffectableCard() {
        return effectableCard;
    }

    public SpellChoiceType getChoiceType() {
        return choiceType;
    }

    //public ArrayList<SpellCastable> getEffectedCard() {
    //    return effectedCard;
    //}

    void setEffectableCard(ArrayList<SpellCastable> effectableCard) {
        this.effectableCard = effectableCard;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString(){
        //TODO
        return "TODO";
    }

    @Override
    public Object clone() throws CloneNotSupportedException{
        return super.clone();
    }
}