package financial.modeling.entities.player;

public class Crude extends PlayerOwn {
    private int value;

    public Crude(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
