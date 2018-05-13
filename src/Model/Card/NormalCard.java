package Model.Card;

import java.util.ArrayList;

/**
 * Created by msi-pc on 4/27/2018.
 */
public class NormalCard extends MonsterCard {

    public NormalCard(int manaCost, int hp, int ap, ArrayList<Card> cardPlace, boolean isNimble, boolean isDefender) {
        super(manaCost, hp, ap, cardPlace, isNimble, isDefender);
    }
}
