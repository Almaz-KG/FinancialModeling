package financial.modeling.entities.player;

public class Money extends PlayerOwn {
    private int value;

    public Money(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
