package Model;

import Model.Card.Tribe;

public interface HasTribe extends SpellCastable {
    public Tribe getTribe();
}
