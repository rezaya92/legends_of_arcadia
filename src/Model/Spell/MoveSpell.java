package Model.Spell;

import Model.Card.*;
import Model.Player;
import Model.SpellCastable;

import java.util.ArrayList;

public class MoveSpell extends Spell {
    private CardPlace destination;
    private Player destinationPlayer;

    //TODO initial health needed

    public MoveSpell(ArrayList<ArrayList<SpellCastable>> effectedArea, String[] effectableCardType, Tribe[] effectedTribe, SpellChoiceType choiceType, CardPlace destination, Player destinationPlayer) {
        super(effectedArea, effectableCardType, effectedTribe, choiceType);
        this.destination = destination;
        this.destinationPlayer = destinationPlayer;
    }

    @Override
    void use() {
        setEffectableCards();
        choose();
        for (SpellCastable card: effectableCard) {
            switch (card.getCardPlace()){
                case DECK:
                    card.getOwner().getDeckCards().remove(card);
                    break;
                case HAND:
                    card.getOwner().getHandCards().remove(card);
                    break;
                case GRAVEYARD:
                    card.getOwner().getGraveyardCards().remove(card);
                    break;
                case SPELLFIELD:
                    for (int i = 0; i < 5; i++) {
                        if (card.getOwner().getSpellFieldCards()[i] == card) {
                            card.getOwner().getSpellFieldCards()[i] = null;
                        }
                    }
                    break;
                case MONSTERFIELD:
                    for (int i = 0; i < 5; i++) {
                        if (card.getOwner().getMonsterFieldCards()[i] == card) {
                            card.getOwner().getMonsterFieldCards()[i] = null;
                        }
                    }
                    break;
            }
            switch (destination){
                case DECK:
                    destinationPlayer.getDeckCards().add((Card) card);
                    card.setCardPlace(CardPlace.DECK);
                    break;
                case HAND:
                    destinationPlayer.getHandCards().add((Card)card);
                    card.setCardPlace(CardPlace.HAND);
                    break;
                case GRAVEYARD:
                    destinationPlayer.getGraveyardCards().add((Card) card);
                    card.setCardPlace(CardPlace.GRAVEYARD);
                    break;
                case SPELLFIELD:
                    for (int i = 0; i < 5; i++) {
                        if (destinationPlayer.getSpellFieldCards()[i] == null) {
                            destinationPlayer.getSpellFieldCards()[i] = (SpellCard) card;
                            card.setCardPlace(CardPlace.SPELLFIELD);
                            break;
                        }
                    }
                    break;
                case MONSTERFIELD:
                    for (int i = 0; i < 5; i++) {
                        if (destinationPlayer.getMonsterFieldCards()[i] == null) {
                            destinationPlayer.getMonsterFieldCards()[i] = (MonsterCard) card;
                            card.setCardPlace(CardPlace.MONSTERFIELD);
                            break;
                        }
                    }
                    break;
            }
            if (card instanceof MonsterCard){
                ((MonsterCard) card).setHp(((MonsterCard) card).getDefualtHP());
                ((MonsterCard) card).setAp(((MonsterCard) card).getDefaultAP());
            }
        }
        effectedCard.addAll(effectableCard);
        effectableCard.clear();
    }

    @Override
    void use(Card choice) {
        effectableCard = new ArrayList<SpellCastable>(1);
        effectableCard.add(choice);
        for (SpellCastable card: effectableCard) {
            switch (card.getCardPlace()){
                case DECK:
                    card.getOwner().getDeckCards().remove(card);
                    break;
                case HAND:
                    card.getOwner().getHandCards().remove(card);
                    break;
                case GRAVEYARD:
                    card.getOwner().getGraveyardCards().remove(card);
                    break;
                case SPELLFIELD:
                    for (int i = 0; i < 5; i++) {
                        if (card.getOwner().getSpellFieldCards()[i] == card) {
                            card.getOwner().getSpellFieldCards()[i] = null;
                        }
                    }
                    break;
                case MONSTERFIELD:
                    for (int i = 0; i < 5; i++) {
                        if (card.getOwner().getMonsterFieldCards()[i] == card) {
                            card.getOwner().getMonsterFieldCards()[i] = null;
                        }
                    }
                    break;
            }
            switch (destination){
                case DECK:
                    destinationPlayer.getDeckCards().add((Card)card);
                    card.setCardPlace(CardPlace.DECK);
                    break;
                case HAND:
                    destinationPlayer.getHandCards().add((Card)card);
                    card.setCardPlace(CardPlace.HAND);
                    break;
                case GRAVEYARD:
                    destinationPlayer.getGraveyardCards().add((Card)card);
                    card.setCardPlace(CardPlace.GRAVEYARD);
                    break;
                case SPELLFIELD:
                    for (int i = 0; i < 5; i++) {
                        if (destinationPlayer.getSpellFieldCards()[i] == null) {
                            destinationPlayer.getSpellFieldCards()[i] =(Card) card;
                            card.setCardPlace(CardPlace.SPELLFIELD);
                            break;
                        }
                        else if (i == 4){
                            break;
                        }
                    }
                    break;
                case MONSTERFIELD:
                    for (int i = 0; i < 5; i++) {
                        if (destinationPlayer.getMonsterFieldCards()[i] == null) {
                            destinationPlayer.getMonsterFieldCards()[i] = (MonsterCard) card;
                            card.setCardPlace(CardPlace.MONSTERFIELD);
                            break;
                        }
                        else if (i == 4){
                            break;
                        }
                    }
                    break;
            }
            if (card instanceof MonsterCard){
                ((MonsterCard) card).setHp(((MonsterCard) card).getDefualtHP());
                ((MonsterCard) card).setAp(((MonsterCard) card).getDefaultAP());
            }
        }
        effectedCard.addAll(effectableCard);
        effectableCard.clear();
    }

    @Override
    void deuse() {
        //deuse nadare (aura nemitoone move kone)
    }
}
