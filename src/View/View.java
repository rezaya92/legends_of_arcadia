package View;

import Controller.*;

abstract public class View {
    public static void afterMatch(){
        System.out.println("1. Enter Shop: To enter shop and buy or sell cards and items");
        System.out.println("2. Edit Inventory: To edit your amulet or deck");
        System.out.println("3. Next: To go to deck and amulet customization");
    }

    public static void afterMatchHelp(){
        afterMatch();
    }

    public static void enterShop(){
        System.out.println("Remaining Gil: " + Main.human.getGil() + " Gil");
        System.out.println("1. Card Shop");
        System.out.println("2. Item Shop");
        System.out.println("3. Amulet Shop");
        System.out.println("4. Exit\n");
    }

    public static void enterShopHelp(){
        enterShop();
    }

    public static void cardShop(){
        System.out.println("Remaining Gil: " + Main.human.getGil() + " Gil");

    }

    public static void cardShopHelp(){

    }

    public static void invalidCommand(){
        System.out.println("Invalid command. Type Help for more information.");
    }
}
