package Model;

public enum TypeOfStuffToBuyAndSell {
    CARD, ITEM, AMULET;
    public static TypeOfStuffToBuyAndSell getTypeOfStuffByName(String name){
        if(name.equalsIgnoreCase("Card") || name.equalsIgnoreCase("Cards"))
            return CARD;
        if(name.equalsIgnoreCase("Item") || name.equalsIgnoreCase("Items"))
            return ITEM;
        return AMULET;
    }
}
