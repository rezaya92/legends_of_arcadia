package Model.Spell;

public class GeneralizedSpell {
    private Spell[] spells;
    private String detail;

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
}
