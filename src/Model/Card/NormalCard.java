package Model.Card;

import java.util.ArrayList;

/**
 * Created by msi-pc on 5/14/2018.
 */
public class NormalCard extends MonsterCard {
    public NormalCard(int defaultManaCost, int defaultHP, int defaultAP, ArrayList<Card> cardPlace, boolean isNimble, boolean isDefender) {
        super(defaultManaCost, defaultHP, defaultAP, cardPlace, isNimble, isDefender);
    }
}
