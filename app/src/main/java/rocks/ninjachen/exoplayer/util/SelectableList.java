package rocks.ninjachen.exoplayer.util;

import java.util.ArrayList;
import java.util.Collection;


/**
 * Created by ninja on 8/11/16.
 */
public class SelectableList<E> extends ArrayList<E>{
    // for further
    protected static final int EMPTY_POSITION = -1;
    protected int selectPosition = 0;
    public SelectableList() {}
    public SelectableList(Collection<? extends E> collection) {
        super(collection);
    }

    public SelectableList(int size) {
        super(size);
    }

    public int getSelectPosition() {
        return selectPosition;
    }

    public void setSelectPosition(int selectPosition) {
        this.selectPosition = selectPosition;
    }

    /**
     *
     * @return null if not selected
     */
    public E getSelectedItem(){
        int position = getSelectPosition();
        if(position == EMPTY_POSITION)
            return null;
        else
            return get(position);
    }

    @Override
    public void clear() {
        super.clear();
        this.selectPosition = 0;
    }

    /**
     * clear this ,and copy from source
     * @param source
     */
    public void from(Collection<E> source){
        clear();
        addAll(source);
//        setSelectPosition(source.getSelectPosition());
    }
}
