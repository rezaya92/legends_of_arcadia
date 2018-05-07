package Model.Card;

import Model.Spell.GeneralizedSpell;

/**
 * Created by msi-pc on 5/5/2018.
 */
public abstract class SpellCard extends Card{
    GeneralizedSpell spell;
    SpellCardType spellCardType;

    @Override
    public String toString(){
        String output = this.name + "Info:\n";
        output += "Name: " + this.name + "\n";
        output += "MP cost: " + defaultManaCost + "\n";
        output += "Card Type: " + spellCardType.name() + "\n";//TODO check to execute correctly
        output += "Spell Details: " + spell.getDetail() + "\n";
        return output;
    }
}
