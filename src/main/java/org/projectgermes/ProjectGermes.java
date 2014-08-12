package org.projectgermes;

import org.projectgermes.client.Player;
import org.projectgermes.client.SimpleBot;
import org.projectgermes.client.User;
import org.projectgermes.server.GermesBank;

import java.io.IOException;

/**
 * Created by Almaz on 31.07.2014.
 */
public class ProjectGermes {
    public static void main(String[] args) throws IOException{
        GermesBank server = new GermesBank();
        server.start(3636);

        Player player1 = new SimpleBot();
        player1.connect(3636);

        Player player2 = new SimpleBot();
        player2.connect(3636);

        Player player3 = new SimpleBot();
        player3.connect(3636);

        Player player4 = new User();
        player4.connect(3636);
    }
}
