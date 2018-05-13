package Model.Card;

import Model.Spell.GeneralizedSpell;

import java.util.ArrayList;

/**
 * Created by msi-pc on 4/27/2018.
 */
public class SpellcasterCard extends MonsterCard {
    private GeneralizedSpell spell;

    public SpellcasterCard(int manaCost, int hp, int ap, ArrayList<Card> cardPlace, boolean isNimble, boolean isDefender) {
        super(manaCost, hp, ap, cardPlace, isNimble, isDefender);
    }

    @Override
    public String toString(){
        String output = super.toString();
        output += "Spell Details: " + spell.getDetail() + "\n";
        return output;
    }
}
