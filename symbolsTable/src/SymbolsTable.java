package src;

import java.util.ArrayList;
import java.util.List;

public class SymbolsTable<T>{
    private List<List<T>> hashTable;
    private int size;
    private int maximumSize = 191;

    public SymbolsTable (Integer maximumSize) {
        this.maximumSize = maximumSize;
        this.size = 0;
        this.hashTable = new ArrayList<>(maximumSize);
        for (int index = 0;index < this.maximumSize;index ++)
            this.hashTable.add(new ArrayList<>());
    }
    Integer hash(T element){
        return element.hashCode() % maximumSize ;
    }
    Pair<Integer,Integer> add(T element){
        Pair <Integer,Integer> position = lookup(element);
        if (position == null){
            int hashPosition = hash(element);
            hashTable.get(hashPosition).add(element);
            this.size++;
            return new Pair<Integer,Integer>(hashPosition,hashTable.get(hashPosition).size()-1);
        }
        return position;
    }
    Pair<Integer,Integer> lookup(T element){
        int hashPosition = hash(element);
        int index = 0;
        for ( T elem : hashTable.get(hashPosition)){
            if (elem.equals(element)){
                return new Pair<Integer,Integer>(hashPosition,index);
            }
            index++;
        }
        return null;
    }

    Integer getSize(){
        return this.size;
    }

}
