import java.util.ArrayList;

public class HashTable {
    private final ArrayList<ArrayList<String>> hashTable;
    private final Integer size;

    public HashTable(Integer size) {
        this.hashTable = new ArrayList<>();
        this.size = size;

        // initialize each element of the hashTable with a new ArrayList
        for (int i = 0; i < this.size; i++) {
            this.hashTable.add(new ArrayList<>());
        }
    }

    public Integer getSize() {
        return this.size;
    }

    private Integer hash(String key) {
        int charsSum = 0;
        for (int i = 0; i < key.length(); i++) {
            charsSum += key.charAt(i);
        }
        return charsSum % this.size;
    }

    public Pair findElemPosition(String elem) {
        int position = hash(elem);

        if (!hashTable.get(position).isEmpty()) {
            ArrayList<String> elems = this.hashTable.get(position);
            for (int i = 0; i < elems.size(); i++) {
                if (elems.get(i).equals(elem)) {
                    return new Pair(position, i);
                }
            }
        }
        return null;
    }

    public boolean containsElem(String elem) {
        return this.findElemPosition(elem) != null;
    }

    public String findByPosition(Pair position) {
        int listPosition = position.getFirstItem();
        int elemPosition = position.getSecondItem();

        if (this.hashTable.size() <= listPosition || this.hashTable.get(listPosition).size() <= elemPosition) {
            throw new IndexOutOfBoundsException("Invalid position!");
        }
        return this.hashTable.get(listPosition).get(elemPosition);
    }

    public boolean addElem(String elem) {
        if (containsElem(elem)) {
            return false;
        }

        Integer position = hash(elem);
        ArrayList<String> elems = this.hashTable.get(position);
        elems.add(elem);
        return true;
    }

    @Override
    public String toString() {
        return "SymbolTable {size: " + this.size + ", elements: " + this.hashTable + "}";
    }
}
