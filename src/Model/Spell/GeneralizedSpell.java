package Model.Spell;

import Model.Player;
import Model.SpellCastable;

import java.util.ArrayList;

public class GeneralizedSpell {
    private Spell[] spells;
    private String detail;
    private String name;

    public GeneralizedSpell(Spell[] spells, String detail, String name) {
        this.spells = spells;
        this.detail = detail;
        this.name = name;
    }

    public Spell[] getSpells() {
        return spells;
    }
    public void setSpells(Spell[] spells) {
        this.spells = spells;
    }

    public String getDetail() {
        return detail;
    }
    public void setDetail(String detail) {
        this.detail = detail;
    }

    public ArrayList<SpellCastable> inputNeeded(){
        for (Spell spell: spells) {
            if (spell.choiceType == SpellChoiceType.SELECT){
                spell.setEffectableCards();
                return spell.effectableCard;
            }
        }
        return null;
    }

    public void use(){
        for (Spell spell: spells)
            spell.use();
    }

    public void use(SpellCastable choice){
        for (Spell spell: spells) {
            if (spell.choiceType == SpellChoiceType.SELECT)
                spell.use(choice);
            else
                spell.use();
        }
    }

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
