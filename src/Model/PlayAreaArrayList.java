package Model;

import java.util.ArrayList;

/**
 * Created by msi-pc on 5/10/2018.
 */
public class PlayAreaArrayList<E> extends ArrayList<E> {

    public PlayAreaArrayList(){
        super();
    }

    public PlayAreaArrayList(int initialCapacity){
        super(initialCapacity);
    }

    @Override
    public boolean remove(Object o){
        int indexOfObject = this.indexOf(o);
        if (indexOfObject == -1)
            return false;
        this.set(indexOfObject, (E)o);
        return true;
    }

    @Override
    public boolean add(E e){
        int indexOfNull = this.indexOf(null);
        if (indexOfNull == -1)
            return false;
        this.set(indexOfNull, e);
        return true;
    }
}