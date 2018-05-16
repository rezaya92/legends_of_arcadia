package Model.Spell;

import Model.Card.*;
import Model.SpellCastable;

import java.util.ArrayList;
import java.util.Set;

public class MoveSpell extends Spell {
    private SpellArea destination;
    private ArrayList<Card> destinationCardList;

    public MoveSpell(Set<SpellArea> effectedArea, Class[] effectableCardType, Set<Tribe> effectedTribe, SpellChoiceType choiceType, SpellArea destination) {
        super(effectedArea, effectableCardType, effectedTribe, choiceType);
        this.destination = destination;
    }

    @Override
    void apply() {
        switch (destination){
            case FRIENDLY_MONSTERFIELD:
                destinationCardList = new ArrayList<>(owner.getOwner().getMonsterFieldCards());
                break;
            case FRIENDLY_SPELLFIELD:
                destinationCardList = new ArrayList<>(owner.getOwner().getSpellFieldCards());
                break;
            case FRIENDLY_HAND:
                destinationCardList = new ArrayList<>(owner.getOwner().getHandCards());
                break;
            case FRIENDLY_DECK:
                destinationCardList = new ArrayList<>(owner.getOwner().getDeckCards());
                break;
            case FRIENDLY_GRAVEYARD:
                destinationCardList = new ArrayList<>(owner.getOwner().getGraveyardCards());
                break;
            case ENEMY_MONSTERFIELD:
                destinationCardList = new ArrayList<>(owner.getOwner().getOpponent().getMonsterFieldCards());
                break;
            case ENEMY_SPELLFIELD:
                destinationCardList = new ArrayList<>(owner.getOwner().getOpponent().getSpellFieldCards());
                break;
            case ENEMY_HAND:
                destinationCardList = new ArrayList<>(owner.getOwner().getOpponent().getHandCards());
                break;
            case ENEMY_DECK:
                destinationCardList = new ArrayList<>(owner.getOwner().getOpponent().getDeckCards());
                break;
            case ENEMY_GRAVEYARD:
                destinationCardList = new ArrayList<>(owner.getOwner().getOpponent().getGraveyardCards());
                break;
        }
        for (SpellCastable card: effectableCard) {
            ((Card)card).transfer(destinationCardList);
        }
    }

    @Override
    void deuse() {
        //deuse nadare (aura nemitoone move kone)
    }
}
