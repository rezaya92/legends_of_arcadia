package Model.Card;

import Model.Spell.GeneralizedSpell;

import java.util.ArrayList;

/**
 * Created by msi-pc on 5/14/2018.
 */
public class SpellcasterCard extends MonsterCard {

    public SpellcasterCard(Tribe tribe, String name, int defaultHP, int defaultAP, int defaultManaCost,boolean isDefender, boolean isNimble, GeneralizedSpell spellCasterSpell) {
        super(tribe, name, defaultHP, defaultAP, defaultManaCost, isDefender, isNimble, null, spellCasterSpell, null);
        this.price = 500*defaultManaCost;
    }
}
