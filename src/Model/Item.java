package Model;

import Model.Spell.GeneralizedSpell;

public class Item implements Stuff{
    private GeneralizedSpell spell;
    private String name;
    private int price;
    private int manaCost;

    //TODO set owner for every spell in constructor
    public Item(String name, int manaCost, int price, GeneralizedSpell spell){
        this.name = name;
        this.manaCost = manaCost;
        this.price = price;
        this.spell = spell;
        Stuff.allStuff.add(this);
    }

    public GeneralizedSpell getSpell() {
        return spell;
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

    public int getManaCost(){return manaCost;}

    public void use(){  // much better to be boolean and if spell casted return true ( actually spell should be boolean)
        spell.use();
    }

    public boolean equalsInName(Object arg0){
        if(!(arg0 instanceof Item))
            return false;
        return (this.name.equals(((Item) arg0).name));
    }

    public String info(){
        return spell.getDetail();//TODO item has it's own description or is equal to it's "effect" description?
    }

    @Override
    public String toString(){
        return this.name + "Info:\n" + this.info();
    }
}
