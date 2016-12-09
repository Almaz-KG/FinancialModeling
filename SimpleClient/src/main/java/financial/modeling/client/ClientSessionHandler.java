package financial.modeling.client;

import financial.modeling.entities.messages.Message;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

import java.util.Scanner;

public class ClientSessionHandler extends IoHandlerAdapter {
    private Scanner scanner;

    public ClientSessionHandler() {
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void sessionOpened(IoSession session) throws Exception {
        System.out.println("Session started");
    }

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        if(message instanceof Message){
            System.out.println(((Message) message).getBody());
        } else{
            System.out.println("Unknown message type received: " + message.getClass().toString());
        }
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        System.out.println("Ooooops! Some error occurred");
        System.out.println(cause.fillInStackTrace());
    }
}
