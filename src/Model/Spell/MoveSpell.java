package Model.Spell;

import Model.Card.Card;
import Model.Card.MonsterCard;
import Model.Card.CardPlace;
import Model.Player;
import Model.Tribe;

import java.util.ArrayList;

public class MoveSpell extends Spell {
    CardPlace destination;
    Player destinationPlayer;

    //TODO initial health needed

    public MoveSpell(ArrayList<ArrayList<Card>> effectedArea, String[] effectableCardType, Tribe[] effectedTribe, SpellChoiceType choiceType, CardPlace destination, Player destinationPlayer) {
        super(effectedArea, effectableCardType, effectedTribe, choiceType);
        this.destination = destination;
        this.destinationPlayer = destinationPlayer;
    }

    @Override
    boolean use() {
        setEffectableCards();
        choose();
        boolean flag = false;
        for (Card card: effectableCard) {
            flag = true;
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
                    destinationPlayer.getDeckCards().add(card);
                    card.setCardPlace(CardPlace.DECK);
                    break;
                case HAND:
                    destinationPlayer.getHandCards().add(card);
                    card.setCardPlace(CardPlace.HAND);
                    break;
                case GRAVEYARD:
                    destinationPlayer.getGraveyardCards().add(card);
                    card.setCardPlace(CardPlace.GRAVEYARD);
                    break;
                case SPELLFIELD:
                    for (int i = 0; i < 5; i++) {
                        if (destinationPlayer.getSpellFieldCards()[i] == null) {
                            destinationPlayer.getSpellFieldCards()[i] = card;
                            card.setCardPlace(CardPlace.SPELLFIELD);
                            break;
                        }
                        else if (i == 4){
                            flag = false;
                            break;
                        }
                    }
                    break;
                case MONSTERFIELD:
                    for (int i = 0; i < 5; i++) {
                        if (destinationPlayer.getMonsterFieldCards()[i] == null) {
                            destinationPlayer.getMonsterFieldCards()[i] = (MonsterCard) card;
                            card.setCardPlace(CardPlace.SPELLFIELD);
                            break;
                        }
                        else if (i == 4){
                            flag = false;
                            break;
                        }
                    }
                    break;
            }
        }
        effectedCard.addAll(effectableCard);
        effectableCard.clear();
        return flag;
    }

    @Override
    boolean use(Card choice) {
        effectableCard = new ArrayList<Card>(1);
        effectableCard.add(choice);
        boolean flag = false;
        for (Card card: effectableCard) {
            flag = true;
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
                    destinationPlayer.getDeckCards().add(card);
                    card.setCardPlace(CardPlace.DECK);
                    break;
                case HAND:
                    destinationPlayer.getHandCards().add(card);
                    card.setCardPlace(CardPlace.HAND);
                    break;
                case GRAVEYARD:
                    destinationPlayer.getGraveyardCards().add(card);
                    card.setCardPlace(CardPlace.GRAVEYARD);
                    break;
                case SPELLFIELD:
                    for (int i = 0; i < 5; i++) {
                        if (destinationPlayer.getSpellFieldCards()[i] == null) {
                            destinationPlayer.getSpellFieldCards()[i] = card;
                            card.setCardPlace(CardPlace.SPELLFIELD);
                            break;
                        }
                        else if (i == 4){
                            flag = false;
                            break;
                        }
                    }
                    break;
                case MONSTERFIELD:
                    for (int i = 0; i < 5; i++) {
                        if (destinationPlayer.getMonsterFieldCards()[i] == null) {
                            destinationPlayer.getMonsterFieldCards()[i] = (MonsterCard) card;
                            card.setCardPlace(CardPlace.SPELLFIELD);
                            break;
                        }
                        else if (i == 4){
                            flag = false;
                            break;
                        }
                    }
                    break;
            }
        }
        effectedCard.addAll(effectableCard);
        effectableCard.clear();
        return flag;
    }

    @Override
    void deuse() {
        //deuse nadare (aura nemitoone move kone)
    }
}
