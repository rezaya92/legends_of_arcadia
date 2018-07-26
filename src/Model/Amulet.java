package Model;

import Controller.Main;
import Model.Spell.GeneralizedSpell;

import java.io.Serializable;

public class Amulet implements Stuff, Cloneable, Serializable {
    private GeneralizedSpell effect;
    private String name;
    private int price;

    //TODO set owner for every spell in constructor
    public Amulet(GeneralizedSpell effect, int price){
        this(effect, price, effect.getName());
    }

    public Amulet(GeneralizedSpell effect, int price, String name){
        this.effect = effect;
        this.price = price;
        this.name = name;
        Main.allStuff.add(this);
    }

    public GeneralizedSpell getEffect() {
        return effect;
    }
    public void setEffect(GeneralizedSpell effect) {
        this.effect = effect;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }

    public boolean equalsInName(Object arg0){
        if(!(arg0 instanceof Amulet))
            return false;
        return (this.name.equals(((Amulet) arg0).name));
    }

    public String info(){
        return effect.getDetail();//TODO amulet has it's own description or is equal to it's "effect" description?
    }

    @Override
    public String toString(){
        return this.name + ":\n" + this.info() + "\n";
    }

    @Override
    public Object clone() throws CloneNotSupportedException{
        return super.clone();
    }
}
