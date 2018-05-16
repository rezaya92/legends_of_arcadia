package Model.Card;

import Model.Spell.GeneralizedSpell;

import java.util.ArrayList;

/**
 * Created by msi-pc on 5/14/2018.
 */
public class NormalCard extends MonsterCard {

    public NormalCard(Tribe tribe, String name, int defaultManaCost, int defaultHP, int defaultAP, boolean isDefender, boolean isNimble) {
        super(tribe, name, defaultHP, defaultAP, defaultManaCost, isDefender, isNimble, null, null, null);
        this.price = 300*defaultManaCost;
    }
}
