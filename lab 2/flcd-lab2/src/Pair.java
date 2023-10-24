public class Pair {
    private final Integer firstItem;
    private final Integer secondItem;

    public Pair(Integer firstItem, Integer secondItem) {
        this.firstItem = firstItem;
        this.secondItem = secondItem;
    }
    public Integer getFirstItem() {
        return this.firstItem;
    }

    public Integer getSecondItem() {
        return this.secondItem;
    }

    @Override
    public String toString() {
        return "{key: " + this.firstItem + ", value: " + this.secondItem + "}";
    }
}
