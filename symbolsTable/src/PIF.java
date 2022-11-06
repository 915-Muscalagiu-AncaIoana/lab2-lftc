package src;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

public class PIF {
    List<Pair<String,Object>> table = new ArrayList<>();
    void add(Pair<String,Object> pair){
        table.add(pair);
    }

    public Formatter getTable() {
        Formatter fmt = new Formatter();
        fmt.format("%15s %15s\n", "Token", "ST position");
        for (Pair<String,Object> pair : table){
            fmt.format("%14s %14s\n", pair.getKey(),pair.getValue());
        }
        System.out.println(fmt);
        return fmt ;
    }
}
