package Model.Card;

import Model.Spell.GeneralizedSpell;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.ArrayList;

/**
 * Created by msi-pc on 5/14/2018.
 */
public class NormalCard extends MonsterCard implements Cloneable{

    public NormalCard(Tribe tribe, String name, int defaultHP, int defaultAP, int defaultManaCost, boolean isDefender, boolean isNimble) {
        super(tribe, name, defaultHP, defaultAP, defaultManaCost, isDefender, isNimble, null, null, null);
        this.price = 300*defaultManaCost;
    }

    @Override
    public Object clone() throws CloneNotSupportedException{
        return super.clone();
    }
}
