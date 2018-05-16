package Model.Card;

import Model.Spell.GeneralizedSpell;

import java.util.ArrayList;

/**
 * Created by msi-pc on 5/14/2018.
 */
public class GeneralCard extends MonsterCard {

    public GeneralCard(Tribe tribe, String name, int defaultHP, int defaultAP, int defaultManaCost,boolean isDefender, boolean isNimble, GeneralizedSpell battleCry, GeneralizedSpell will){
        super(tribe, name, defaultHP, defaultAP, defaultManaCost, isDefender, isNimble, battleCry, null, will);
        this.price = 700*defaultManaCost;
    }
}
