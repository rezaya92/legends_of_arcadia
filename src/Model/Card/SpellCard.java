package Model.Card;

import Model.Spell.GeneralizedSpell;

/**
 * Created by msi-pc on 5/5/2018.
 */
public class SpellCard extends Card {
    GeneralizedSpell spell;
    SpellCardType spellCardType;

    @Override
    public void play(int slotNumber) {   // -1 case card is instant spell
        if (manaCost <= owner.getMana()) {
            if (slotNumber == -1) {
                spell.use();   // use can be boolean and this can be in if
                owner.getHandCards().remove(this);   // todo card may get played from deck --> fix this
                owner.setMana(owner.getMana() - manaCost);
                owner.getGraveyardCards().add(this);
            }
            else{
                if (owner.getSpellFieldCards()[slotNumber] == null){

                }
            }
        }
    }

    public void useSpell(){
        spell.use();
    }

    @Override
    public String toString() {
        String output = this.name + "Info:\n";
        output += "Name: " + this.name + "\n";
        output += "MP cost: " + defaultManaCost + "\n";
        output += "Card Type: " + spellCardType.name() + "\n";//TODO check to execute correctly
        output += "Spell Details: " + spell.getDetail() + "\n";
        return output;
    }
}
