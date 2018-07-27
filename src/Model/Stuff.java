package Model;

import Controller.Main;
import Model.Card.Card;

import java.util.ArrayList;
import java.util.List;

public interface Stuff extends ListShowable {
    String getName();
    int getPrice();
    String toString();

    static Stuff getStuffByName(String name){
        for (Stuff stuff: Main.allStuff){
            if (stuff.getName().equals(name)){
                return stuff;
            }
        }
        return null;
    }

    static int numberOfStuffInList(String name, List<? extends Stuff> list){
        int result = 0;
        for(Stuff stuff : list){
            if(stuff.getName().equalsIgnoreCase(name)){
                result++;
            }
        }
        return result;
    }

    static int numberOfStuffInList(Stuff stuff, List<? extends Stuff> list){
        return numberOfStuffInList(stuff.getName(), list);
    }

    public static ArrayList<Stuff> getSpecificStuffInAllStuff(TypeOfStuffToBuyAndSell typeOfStuffToBuyAndSell){
        ArrayList<Stuff> result = new ArrayList<>();
        switch (typeOfStuffToBuyAndSell){
            case CARD:
                for(Stuff stuff : Main.allStuff){
                    if(stuff instanceof Card){
                        result.add(stuff);
                    }
                }
                break;
            case ITEM:
                for(Stuff stuff : Main.allStuff){
                    if(stuff instanceof Item){
                        result.add(stuff);
                    }
                }
                break;
            case AMULET:
                for(Stuff stuff : Main.allStuff){
                    if(stuff instanceof Amulet){
                        result.add(stuff);
                    }
                }
        }
        return result;
    }

    public Object clone() throws CloneNotSupportedException;
}
