package Model;

import java.util.ArrayList;

public interface Stuff {
    String getName();
    int getPrice();
    String toString();
    public static ArrayList<Stuff> allStuff = new ArrayList<>();
    static Stuff getStuffByName(String name){
        for (Stuff stuff: allStuff){
            if (stuff.getName().equals(name)){
                return stuff;
            }
        }
        return null;
    }
}
