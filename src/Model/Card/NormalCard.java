package Model.Card;

import Model.Card.MonsterCard;
import Model.CardPlace;

/**
 * Created by msi-pc on 4/27/2018.
 */
public class NormalCard extends MonsterCard {

    public NormalCard(int manaCost, int hp, int ap, CardPlace cardPlace, boolean isNimble, boolean isDefender) {
        super(manaCost, hp, ap, cardPlace, isNimble, isDefender);
    }
}