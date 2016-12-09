package financial.modeling.entities.player;

import java.util.ArrayList;
import java.util.List;

public class PlayerState {
    private List<PlayerOwn> ownsList;

    public PlayerState(List<PlayerOwn> ownsList) {
        this.ownsList = ownsList;
    }


    public static PlayerState getInitialPlayerState() {
        List<PlayerOwn> owns = new ArrayList<>();
        owns.add(new Factory(2));
        owns.add(new Crude(4));
        owns.add(new Product(2));
        owns.add(new Money(10000));

        return new PlayerState(owns);
    }
}
