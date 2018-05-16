package Model.Card;

public enum SpellCardType {
    AURA, CONTINUOUS, INSTANT;
    public static SpellCardType nameToSpellCardType(String name){
        switch (name){
            case "aura":
            case "AURA":
                return AURA;
            case "continuous":
            case "CONTINUOUS":
                return CONTINUOUS;
                default:
                    return INSTANT;
        }
    }
}
