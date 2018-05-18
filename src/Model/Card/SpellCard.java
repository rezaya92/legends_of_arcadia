package Model.Card;

import Model.Spell.GeneralizedSpell;
import Model.Spell.Spell;
import View.View;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by msi-pc on 5/5/2018.
 */
public class SpellCard extends Card implements Cloneable{
    GeneralizedSpell spell;
    SpellCardType spellCardType;

    public GeneralizedSpell getSpell(){
        return spell;
    }

    public SpellCardType getSpellCardType() {
        return spellCardType;
    }

    public SpellCard(GeneralizedSpell spell, int defaultManaCost, SpellCardType spellCardType){
        this.defaultManaCost = defaultManaCost;
        this.spell = spell;
        this.spellCardType = spellCardType;
        this.name = spell.getName();
        this.price = 700*defaultManaCost;
        allStuff.add(this);
    }

    @Override
    public boolean play(int slotNumber) {   // -1 case card is instant spell  // only plays from hand
        if (manaCost <= owner.getMana()) {
            if (slotNumber == -1) {
                if (spellCardType == SpellCardType.INSTANT) {
                    try {
                        spell.use(owner);   // use can be boolean and this can be in if
                    } catch (Exception ignored){

                    }
                    cardPlace.remove(this);
                    owner.setMana(owner.getMana() - manaCost);
                    owner.getGraveyardCards().add(this);    // can use transfer instead
                    View.spellCardCasted(name);
                    return true;
                }
                else{
                    View.indexOutOfBound();
                }
            }
            else{
                if (spellCardType != SpellCardType.INSTANT) {
                    if (owner.getSpellFieldCards().get(slotNumber) == null) {
                        deuseAuraCards();
                        cardPlace.remove(this);    // cardPlace = hand here
                        owner.getSpellFieldCards().set(slotNumber, this);
                        cardPlace = owner.getSpellFieldCards();
                        useAuraCards();
                        View.playedInSpellField(name);
                        return true;
                    } else {
                        View.slotIsFull(owner);
                    }
                }
                else{
                    View.indexOutOfBound();
                }
            }
        }
        else {
            View.insufficientMana(owner);
        }
        return false;
    }

    @Override
    public boolean play(){
        if (spellCardType == SpellCardType.INSTANT)
            return play(-1);
        else{
            int slot = owner.getSpellFieldCards().indexOf(null);
            if (slot != -1)
                return play(slot);
        }
        return false;
    }

/*    @Override     // this is not needed since transfer does the deuse and use of AuraCards.
    public void transfer(ArrayList<Card> destination){   // bug: nabaiad ye spell bashe ke instantSpellCard majani bazi kone!
        if (spellCardType == SpellCardType.AURA  && owner != null) {
            if (destination == owner.getSpellFieldCards()) {
                spell.use();
            }
            if (cardPlace == owner.getSpellFieldCards()) {
                spell.deuse();
            }
        }
        super.transfer(destination);
    }*/

    @Override
    public void restoreValues(){
        spell.checkDead(owner);
        manaCost = defaultManaCost;
    }

    @Override
    public String toString() {
        String output = this.name + " Info:\n";
        output += "Name: " + this.name + "\n";
        output += "MP cost: " + defaultManaCost + "\n";
        output += "Card Type: " + spellCardType.name() + "\n";//TODO check to execute correctly
        output += "Spell Details: " + spell.getDetail() + "\n";
        return output;
    }

    @Override
    public Object clone() throws CloneNotSupportedException{
        return super.clone();
    }
}
