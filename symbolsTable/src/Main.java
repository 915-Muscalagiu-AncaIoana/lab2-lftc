package src;

public class Main {
    public static void main (String[] args) {
        SymbolsTable<Integer> integerSymbolsTable = new SymbolsTable<>(191);
        SymbolsTable<String> identifiersSymbolsTable = new SymbolsTable<>(191);
        Pair<Integer,Integer> pair = integerSymbolsTable.add(10);
        System.out.println(pair);
        pair = integerSymbolsTable.add(11);
        System.out.println(pair);
        pair = integerSymbolsTable.add(202);
        System.out.println(pair);
        pair = integerSymbolsTable.add(202);
        System.out.println(pair);
        pair = identifiersSymbolsTable.add("aba");
        System.out.println(pair);
        pair = identifiersSymbolsTable.add("anca");
        System.out.println(pair);
        pair = identifiersSymbolsTable.add("ana");
        System.out.println(pair);

    }
}