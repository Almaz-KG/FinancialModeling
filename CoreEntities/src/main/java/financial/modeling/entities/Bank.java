package financial.modeling.entities;

import financial.modeling.entities.player.PlayerState;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

public class Bank {
    private Calendar startModelingDate;
    private Calendar currentModelingCalendar;
    private List<PlayerState> playerStates;
    private int currentPlayerIndex;

    public Bank() throws IOException {
        startModelingDate = Calendar.getInstance();
        currentModelingCalendar = Calendar.getInstance();
        playerStates = new ArrayList<>();
    }

    private void nextMonth(){
        currentModelingCalendar.add(Calendar.MONTH, 1);
    }


}
