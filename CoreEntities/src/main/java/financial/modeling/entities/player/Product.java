package financial.modeling.entities.player;

public class Product extends PlayerOwn {
    private int value;

    public Product(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
