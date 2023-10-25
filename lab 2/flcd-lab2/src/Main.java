// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.

public class Main {
    public static void main(String[] args) {
       SymbolTable identifiersTable = new SymbolTable(5);
       SymbolTable constantsTable = new SymbolTable(5);

       identifiersTable.add("var1");
       System.out.println("Identifiers table contains var1? Answer: " + identifiersTable.containsElem("var1"));
       System.out.println("The position of var2 is " + identifiersTable.findElemPosition("var1"));
       identifiersTable.add("var2");
       System.out.println("Identifiers table contains var2? Answer: " + identifiersTable.containsElem("var2"));
       System.out.println("The position of var2 is " + identifiersTable.findElemPosition("var2"));

       System.out.println("The variable from position (3, 0) is " + identifiersTable.findByPosition(new Pair(3, 0)));

       //identifiersTable.findByPosition(new Pair(3, 1)); // throws error because the position is invalid
        System.out.println(identifiersTable.toString());

        constantsTable.add("const1");
        System.out.println("Constants table contains const1? Answer: " + constantsTable.containsElem("const1"));

        System.out.println(constantsTable.toString());
        constantsTable.add("const1");
        System.out.println(constantsTable.toString());

    }
}