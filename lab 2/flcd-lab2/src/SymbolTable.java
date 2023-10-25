public class SymbolTable {
    private final Integer size;
    private final HashTable hashTable;

    public SymbolTable(Integer size) {
        this.hashTable = new HashTable(size);
        this.size = this.hashTable.getSize();
    }

    public HashTable getHashTable() {
        return this.hashTable;
    }

    public Integer getSize() {
        return this.hashTable.getSize();
    }

    public String findByPosition(Pair position) {
        return hashTable.findByPosition(position);
    }

    public Pair findElemPosition(String elem) {
        return this.hashTable.findElemPosition(elem);
    }

    public boolean containsElem(String elem) {
        return this.hashTable.containsElem(elem);
    }

    public boolean add(String elem) {
        return this.hashTable.addElem(elem);
    }

    public String toString() {
        return this.hashTable.toString();
    }
}
