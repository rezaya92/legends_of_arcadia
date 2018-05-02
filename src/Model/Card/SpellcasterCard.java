package Model.Card;

import Model.CardPlace;
import Model.Spell.Spell;

/**
 * Created by msi-pc on 4/27/2018.
 */
public class SpellcasterCard extends MonsterCard {
    Spell spellCast;

    public SpellcasterCard(int manaCost, int hp, int ap, CardPlace cardPlace, boolean isNimble, boolean isDefender) {
        super(manaCost, hp, ap, cardPlace, isNimble, isDefender);
    }
}
