package Model;

import Controller.Main;

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

    public Object clone() throws CloneNotSupportedException;
}
