package financial.modeling.client;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

public class SimpleClient {
    private static int CONNECT_TIMEOUT = 1000;
    private static int PORT = 10001;
    private static String HOSTNAME = "localhost";

    public static void main(String[] args) {
        System.out.println("Simple client for financial modeling server");
        System.out.println("Trying to connect");
        System.out.println(HOSTNAME + ": " + PORT);

        System.out.println("Run client with params: <HOST> <PORT> <TIME_OUT> for changing default settings");
        try {
            if (args.length == 3) {
                HOSTNAME = args[0];
                PORT = Integer.parseInt(args[1]);
                CONNECT_TIMEOUT = Integer.parseInt(args[2]);
            }

            NioSocketConnector connector = new NioSocketConnector();
            connector.setConnectTimeoutMillis(CONNECT_TIMEOUT);

            connector.getFilterChain().addLast("codec",
                    new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));

            connector.setHandler(new ClientSessionHandler());

            ConnectFuture future = connector.connect(new InetSocketAddress(HOSTNAME, PORT));
            future.awaitUninterruptibly();
            IoSession session = future.getSession();

            session.getCloseFuture().awaitUninterruptibly();
            connector.dispose();
        } catch (Exception ex) {
            System.out.println("Oooops!\nSome errors occurred");
            System.out.println(ex.fillInStackTrace());
            System.exit(-1);
        }
    }
}
