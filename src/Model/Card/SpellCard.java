package Model.Card;

import Model.Spell.GeneralizedSpell;
import Model.Spell.Spell;

import java.util.ArrayList;

/**
 * Created by msi-pc on 5/5/2018.
 */
public class SpellCard extends Card {
    GeneralizedSpell spell;
    SpellCardType spellCardType;

    public GeneralizedSpell getSpell(){
        return spell;
    }

    public SpellCardType getSpellCardType() {
        return spellCardType;
    }

    @Override
    public void play(int slotNumber) {   // -1 case card is instant spell  // only plays from hand
        if (manaCost <= owner.getMana()) {
            if (slotNumber == -1) {
                spell.use();   // use can be boolean and this can be in if
                cardPlace.remove(this);
                owner.setMana(owner.getMana() - manaCost);
                owner.getGraveyardCards().add(this);    // can use transfer instead
            }
            else{
                if (owner.getSpellFieldCards().get(slotNumber) == null){
                    cardPlace.remove(this);    // cardPlace = hand here
                    owner.getSpellFieldCards().set(slotNumber, this);
                    cardPlace = owner.getSpellFieldCards();
                }
            }
        }
    }

    @Override
    public void transfer(ArrayList<Card> destination){   // bug: nabaiad ye spell bashe ke instantSpellCard majani bazi kone!
        if (spellCardType == SpellCardType.AURA) {
            if (destination == owner.getSpellFieldCards()) {
                spell.use();
            }
            if (cardPlace == owner.getSpellFieldCards()) {
                spell.deuse();
            }
        }
        super.transfer(destination);
    }

    @Override
    public void restoreValues(){
        manaCost = defaultManaCost;
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
