package Model.Card;

import Model.Spell.GeneralizedSpell;

import java.util.ArrayList;

/**
 * Created by msi-pc on 5/14/2018.
 */
public class NormalCard extends MonsterCard {

    public NormalCard(int defaultManaCost, int defaultHP, int defaultAP, GeneralizedSpell battleCry, GeneralizedSpell spellCasterSpell, GeneralizedSpell will, boolean isNimble, boolean isDefender) {
        super(defaultManaCost, defaultHP, defaultAP, battleCry, spellCasterSpell, will, isNimble, isDefender);
    }
}
