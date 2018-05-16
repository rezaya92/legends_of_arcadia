package Model.Card;

import Model.Spell.GeneralizedSpell;

import java.util.ArrayList;

/**
 * Created by msi-pc on 5/14/2018.
 */
public class HeroCard extends MonsterCard {

    public HeroCard(Tribe tribe, String name, int defaultHP, int defaultAP, int defaultManaCost, boolean isDefender, boolean isNimble, GeneralizedSpell battleCry, GeneralizedSpell spellCasterSpell, GeneralizedSpell will) {
        super(tribe, name, defaultHP, defaultAP, defaultManaCost, isDefender, isNimble, battleCry, spellCasterSpell, will);
        this.price = 1000*defaultManaCost;
    }
}
