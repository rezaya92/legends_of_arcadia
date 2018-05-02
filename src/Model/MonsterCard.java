package Model;

/**
 * Created by msi-pc on 4/27/2018.
 */
public abstract class MonsterCard extends Card {
    
    boolean isNimble;
    boolean isDefender;
    int ap;
    int hp;
    Spell battleCry;
    Spell spellCast;
    Spell will;
    //todo set in constructor

    public MonsterCard(int manaCost, int hp, int ap, CardPlace cardPlace, boolean isNimble, boolean isDefender) {
        this.manaCost = manaCost;
        this.hp = hp;
        this.ap = ap;
        this.cardPlace = cardPlace;
        this.isNimble = isNimble;
        this.isDefender = isDefender;
    }

    public void Attack(MonsterCard monsterCard){
        // defender magic case || secrets
        monsterCard.hp -= ap;
        hp -= monsterCard.ap;
        this.checkAlive();
        monsterCard.checkAlive();
    }

    public void checkAlive(){
        // how about the player
        if (hp <= 0){
            if (will != null){
                //todo will.use();
            }
            for (int i = 0; i < 5; i++){
                if (owner.getMonsterFieldCards()[i].equals(this)){
                    owner.getMonsterFieldCards()[i] = null;
                    owner.getGraveyardCards().add(this); // cut to "die" in cards?
                }
            }
        }
    }

    @Override
    public void play(int slotNumber){
        if (manaCost <= owner.getMana()){ //todo add mana to palyer

            //owner.setMana();
        }
    }

    // to consider sleeping
}
