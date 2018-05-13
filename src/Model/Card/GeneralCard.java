package Model.Card;

import Model.Spell.GeneralizedSpell;

import java.util.ArrayList;

/**
 * Created by msi-pc on 4/27/2018.
 */
public class GeneralCard extends MonsterCard {
    private GeneralizedSpell battleCry;
    private GeneralizedSpell will;

    public GeneralCard(int manaCost, int hp, int ap, ArrayList<Card> cardPlace, boolean isNimble, boolean isDefender) {
        super(manaCost, hp, ap, cardPlace, isNimble, isDefender);
    }

    @Override
    public String toString(){
        String output = super.toString();
        output += "BattleCry Details: " + battleCry.getDetail() + "\n";
        output += "Will Details: " + will.getDetail() + "\n";
        return output;
    }
}
