package Model.Spell;

import Model.Card.*;
import Model.Player;

import java.util.ArrayList;
import java.util.Set;

public class MoveSpell extends Spell implements Cloneable{
    private SpellArea destination;
    private ArrayList<Card> destinationCardList;

    public MoveSpell(Set<SpellArea> effectedArea, Class[] effectableCardType, Set<Tribe> effectedTribe, SpellChoiceType choiceType, SpellArea destination) {
        super(effectedArea, effectableCardType, effectedTribe, choiceType);
        this.destination = destination;
    }

    public MoveSpell(Set<SpellArea> effectableArea, Class[] effectableCardType, SpellChoiceType choiceType, SpellArea destination) {
        super(effectableArea, effectableCardType, choiceType);
        this.destination = destination;
    }

    @Override
    protected void apply(Player owner) {
        switch (destination){
            case FRIENDLY_MONSTERFIELD:
                destinationCardList = owner.getMonsterFieldCards();
                break;
            case FRIENDLY_SPELLFIELD:
                destinationCardList = owner.getSpellFieldCards();
                break;
            case FRIENDLY_HAND:
                destinationCardList = owner.getHandCards();
                break;
            case FRIENDLY_DECK:
                destinationCardList = owner.getDeckCards();
                break;
            case FRIENDLY_GRAVEYARD:
                destinationCardList = owner.getGraveyardCards();
                break;
            case ENEMY_MONSTERFIELD:
                destinationCardList = owner.getOpponent().getMonsterFieldCards();
                break;
            case ENEMY_SPELLFIELD:
                destinationCardList = owner.getOpponent().getSpellFieldCards();
                break;
            case ENEMY_HAND:
                destinationCardList = owner.getOpponent().getHandCards();
                break;
            case ENEMY_DECK:
                destinationCardList = owner.getOpponent().getDeckCards();
                break;
            case ENEMY_GRAVEYARD:
                destinationCardList = owner.getOpponent().getGraveyardCards();
                break;
        }
        for (SpellCastable card: effectableCard) {
            ((Card)card).transfer(destinationCardList);
        }
        effectableCard.clear();
        effectableAreaCards.clear();
    }

    @Override
    protected void deuse(Player owner) {
        //deuse nadare (aura nemitoone move kone)
    }

    @Override
    public Object clone() throws CloneNotSupportedException{
        return super.clone();
    }
}
