package Model.Card;

import Model.Card.MonsterCard;
import Model.CardPlace;
import Model.Spell.GeneralizedSpell;

/**
 * Created by msi-pc on 4/27/2018.
 */
public class HeroCard extends MonsterCard {
    private GeneralizedSpell battleCry;
    private GeneralizedSpell spell;
    private GeneralizedSpell will;

    public HeroCard(int manaCost, int hp, int ap, CardPlace cardPlace, boolean isNimble, boolean isDefender) {
        super(manaCost, hp, ap, cardPlace, isNimble, isDefender);
    }

    @Override
    public String toString(){
        String output = super.toString();
        output += "Spell Details: " + spell.getDetail() + "\n";
        output += "BattleCry Details: " + battleCry.getDetail() + "\n";
        output += "Will Details: " + will.getDetail() + "\n";
        return output;
    }
}
