package Model.Spell;

import Model.Card.*;
import Model.Player;
import Model.SpellCastable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Set;

public class MoveSpell extends Spell implements Cloneable{
    private SpellArea destination;
    private ObservableList<Card> destinationCardList;

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
                destinationCardList = FXCollections.observableArrayList(owner.getMonsterFieldCards());
                break;
            case FRIENDLY_SPELLFIELD:
                destinationCardList = FXCollections.observableArrayList(owner.getSpellFieldCards());
                break;
            case FRIENDLY_HAND:
                destinationCardList = FXCollections.observableArrayList(owner.getHandCards());
                break;
            case FRIENDLY_DECK:
                destinationCardList = FXCollections.observableArrayList(owner.getDeckCards());
                break;
            case FRIENDLY_GRAVEYARD:
                destinationCardList = FXCollections.observableArrayList(owner.getGraveyardCards());
                break;
            case ENEMY_MONSTERFIELD:
                destinationCardList = FXCollections.observableArrayList(owner.getOpponent().getMonsterFieldCards());
                break;
            case ENEMY_SPELLFIELD:
                destinationCardList = FXCollections.observableArrayList(owner.getOpponent().getSpellFieldCards());
                break;
            case ENEMY_HAND:
                destinationCardList = FXCollections.observableArrayList(owner.getOpponent().getHandCards());
                break;
            case ENEMY_DECK:
                destinationCardList = FXCollections.observableArrayList(owner.getOpponent().getDeckCards());
                break;
            case ENEMY_GRAVEYARD:
                destinationCardList = FXCollections.observableArrayList(owner.getOpponent().getGraveyardCards());
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
