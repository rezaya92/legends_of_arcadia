package Model;

import Model.Spell.GeneralizedSpell;

public class Amulet implements Stuff {
    private GeneralizedSpell effect;
    private String name;
    private int price;

    //TODO set owner for every spell in constructor
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
        return effect.getDetail();//TODO item has it's own description or is equal to it's "effect" description?
    }

//    @Override
//    public String toString(){
//        return this.name + "\n" + this.info();
//    }
}
