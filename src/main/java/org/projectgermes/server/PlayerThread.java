package org.projectgermes.server;

import org.apache.log4j.Logger;
import org.projectgermes.enitities.messages.Message;
import org.projectgermes.enitities.MessageReader;
import org.projectgermes.enitities.MessageSender;
import org.projectgermes.enitities.messages.MessageType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * User: Almaz
 * Date: 20.07.14
 * Time: 18:19
 */
public class PlayerThread extends Thread{
    private Logger Log = Logger.getLogger(PlayerThread.class);
    private Socket socket;
    private Server server;
    private MessageReader msReader;
    private MessageSender msSender;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private BlockingQueue<Message> responseMessages;
    private BlockingQueue<Message> requestMessages;

    private String user;
    private boolean isAlive;

    public PlayerThread(Socket socket, Server server) throws IOException{
        initialize(socket, server);
    }

    public void initialize(Socket socket, Server server) throws IOException {
        this.server = server;
        this.socket = socket;

        this.inputStream = new ObjectInputStream(socket.getInputStream());
        this.outputStream = new ObjectOutputStream(socket.getOutputStream());
        this.responseMessages = new ArrayBlockingQueue<>(64);
        this.requestMessages = new ArrayBlockingQueue<>(64);
        this.msSender = new MessageSender(this.outputStream, this.responseMessages);
        this.msReader = new MessageReader(this.inputStream, this.requestMessages);

        this.isAlive = true;
        this.msSender.send(Message.getMessage(MessageType.NONE, null));
    }

    public void send(Message message){
        this.msSender.send(message);
    }

    public void run() {
        Log.info("Player thread started");
        while (isAlive) {
            try {
                Message msg = this.requestMessages.take();
                this.server.send(msg);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public void start(){
        Thread thread = new Thread(this);
        Thread reader = new Thread(this.msSender);
        Thread writer = new Thread(this.msReader);
        //maybe some logic there in the future

        thread.start();
        reader.start();
        writer.start();
        this.setAlive(true);
    }

    public void setAlive(boolean alive){
        this.isAlive = alive;
    }
}
