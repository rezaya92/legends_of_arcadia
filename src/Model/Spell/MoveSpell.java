package Model.Spell;

import Model.Card.*;
import Model.Player;
import Model.SpellCastable;

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
                destinationCardList = new ArrayList<>(owner.getMonsterFieldCards());
                break;
            case FRIENDLY_SPELLFIELD:
                destinationCardList = new ArrayList<>(owner.getSpellFieldCards());
                break;
            case FRIENDLY_HAND:
                destinationCardList = new ArrayList<>(owner.getHandCards());
                break;
            case FRIENDLY_DECK:
                destinationCardList = new ArrayList<>(owner.getDeckCards());
                break;
            case FRIENDLY_GRAVEYARD:
                destinationCardList = new ArrayList<>(owner.getGraveyardCards());
                break;
            case ENEMY_MONSTERFIELD:
                destinationCardList = new ArrayList<>(owner.getOpponent().getMonsterFieldCards());
                break;
            case ENEMY_SPELLFIELD:
                destinationCardList = new ArrayList<>(owner.getOpponent().getSpellFieldCards());
                break;
            case ENEMY_HAND:
                destinationCardList = new ArrayList<>(owner.getOpponent().getHandCards());
                break;
            case ENEMY_DECK:
                destinationCardList = new ArrayList<>(owner.getOpponent().getDeckCards());
                break;
            case ENEMY_GRAVEYARD:
                destinationCardList = new ArrayList<>(owner.getOpponent().getGraveyardCards());
                break;
        }
        for (SpellCastable card: effectableCard) {
            ((Card)card).transfer(destinationCardList);
        }
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
