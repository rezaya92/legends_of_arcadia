package Model.Spell;

import Model.Card.Card;
import Model.SpellChoiceType;

import java.util.ArrayList;

public class GeneralizedSpell {
    private Spell[] spells;
    private String detail;

    public GeneralizedSpell(Spell[] spells, String detail) {
        this.spells = spells;
        this.detail = detail;
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

    public ArrayList<Card> inputNeeded(){
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

    public void use(Card choice){
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
}
