package Model.Card;

public enum Tribe {
    ELVEN, ATLANTIAN, DRAGONBREED, DEMONIC;
    public static Tribe getTribeByName(String name){
        if(name.equalsIgnoreCase("Elven Monster"))
            return ELVEN;
        else if(name.equalsIgnoreCase("Atlantian Monster"))
            return ATLANTIAN;
        else if(name.equalsIgnoreCase("DragonBreed Monster"))
            return DRAGONBREED;
        else
            return DEMONIC;
    }
}
