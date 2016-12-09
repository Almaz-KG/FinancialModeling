package financial.modeling.entities.player;

public class Factory extends PlayerOwn {
    private int count;

    public Factory(int count) {
        this.count = count;
    }
    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }
}
