package financial.modeling.server;

import financial.modeling.entities.Bank;
import financial.modeling.server.utils.LanguageResourceProvider;
import financial.modeling.server.utils.ResourceProvider;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

public class Server {
    public static final int PORT = 10001;
    private String messagesFileName = "localization/ru-messages.txt";

    private int MINIMUM_PLAYERS_COUNT = 2;
    private IoAcceptor acceptor;
    private ResourceProvider resourceProvider;
    private Bank bank;

    public Server() throws IOException{
        bank = new Bank();
        resourceProvider = new LanguageResourceProvider(messagesFileName);

        acceptor = new NioSocketAcceptor();
        acceptor.getFilterChain().addLast( "codec",
                new ProtocolCodecFilter( new TextLineCodecFactory( Charset.forName( "UTF-8" ))));

        acceptor.setHandler(new ConnectionHandler(bank, resourceProvider));
    }

    private void start() throws IOException {
        System.out.println("Bind to port: " + PORT);
        acceptor.bind(new InetSocketAddress(PORT) );

        while(acceptor.getManagedSessions().size() < MINIMUM_PLAYERS_COUNT){
            continue;
        }

        acceptor.getManagedSessions().forEach((l, e) -> e.write("Start battle!"));

        startBattle();
    }

    private void startBattle() {
        System.out.println("Please, implement battle business logic");
    }


    public static void main(String[] args) throws IOException {
        System.out.println("Simple server for financial modeling project");
        System.out.println("Starting server on port: " + PORT);
        Server server = new Server();
        server.start();

        System.out.println("Server running");
    }
}
