package Model.Spell;

import Model.Player;
import Model.SpellCastable;

import java.util.ArrayList;

public class GeneralizedSpell {
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

    public void use(){
        if (merge){
            spells[0].use();
            for (int i = 1; i < spells.length; i++) {
                spells[i].setEffectableCard(spells[0].getEffectableCard());
                spells[i].apply();
            }
        }
        else {
            for (Spell spell: spells)
                spell.use();
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

    public void deuse(){
        for (Spell spell : spells)
            spell.deuse();
    }

    public String getName() {
        return name;
    }

    public void setOwner(SpellCastable owner) {
        for (Spell spell: spells) {
            spell.setOwner(owner);
        }
    }
}
