package Model;

import Model.Spell.GeneralizedSpell;

public class Item implements Stuff, Cloneable{
    private GeneralizedSpell effect;
    private String name;
    private int price;

    //TODO set owner for every spell in constructor
    public Item(GeneralizedSpell effect, int price){
        this.effect = effect;
        this.price = price;
        this.name = effect.getName();
        Stuff.allStuff.add(this);
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

    public void use(Player player){  // much better to be boolean and if spell casted return true ( actually spell should be boolean)
        effect.use(player);
    }

    public boolean equalsInName(Object arg0){
        if(!(arg0 instanceof Item))
            return false;
        return (this.name.equals(((Item) arg0).name));
    }

    public String info(){
        return effect.getDetail();//TODO item has it's own description or is equal to it's "effect" description?
    }

    @Override
    public String toString(){
        return this.name + " Info:\n" + this.info() + "\n";
    }

    @Override
    public Object clone() throws CloneNotSupportedException{
        return super.clone();
    }
}
