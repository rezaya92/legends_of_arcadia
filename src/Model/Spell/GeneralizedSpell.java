package Model.Spell;

import Model.Player;
import Model.SpellCastable;

import java.io.IOException;
import java.util.ArrayList;

public class GeneralizedSpell implements Cloneable{
    final private Spell[] spells;
    final private String detail;
    final private String name;
    public static ArrayList<GeneralizedSpell> allSpells = new ArrayList<>();
    final private boolean merge;

    public GeneralizedSpell(Spell[] spells, String detail, String name,boolean merge) {
        this.spells = spells;
        this.detail = detail;
        this.name = name;
        this.merge = merge;
        allSpells.add(this);
    }

    public GeneralizedSpell(Spell[] spells, String detail, String name) {
        this(spells,detail,name,false);
    }

    public Spell[] getSpells() {
        return spells;
    }

    public String getDetail() {
        return detail;
    }

    /*public ArrayList<SpellCastable> inputNeeded(){
        for (Spell spell: spells) {
            if (spell.choiceType == SpellChoiceType.SELECT){
                spell.setEffectableCards();
                return spell.effectableCard;
            }
        }
        return null;
    }*/

    public static GeneralizedSpell getSpellByName(String name){
        for(GeneralizedSpell spell : allSpells){
            if(spell.getName().equals(name))
                return spell;
        }
        return null;
    }

    public void use(Player owner) throws NoEffectableCardException {
        if (merge){
            spells[0].use(owner);
            for (int i = 1; i < spells.length; i++) {
                spells[i].setEffectableCard(spells[0].getEffectableCard());
                spells[i].apply(owner);
            }
        }
        else {
            for (Spell spell: spells)
                spell.use(owner);
        }
    }

    public void checkDead(Player owner){
        for (Spell spell:spells) {
            if (spell instanceof HPSpell)
                ((HPSpell)spell).checkDead(owner);
            spell.effectableAreaCards.clear();
            spell.effectableCard.clear();
        }
    }

    /*public void use(SpellCastable choice){
        for (Spell spell: spells) {
            if (spell.choiceType == SpellChoiceType.SELECT)
                spell.use(choice);
            else
                spell.use();
        }
    }*/

    public void deuse(Player owner){
        for (Spell spell : spells) {
            spell.deuse(owner);
            spell.effectableCard.clear();
            spell.effectableAreaCards.clear();
        }
    }

    public String getName() {
        return name;
    }

    @Override
    public Object clone() throws CloneNotSupportedException{
        return super.clone();
    }
}
