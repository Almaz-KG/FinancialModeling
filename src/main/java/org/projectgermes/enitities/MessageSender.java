package org.projectgermes.enitities;

import org.apache.log4j.Logger;
import org.projectgermes.enitities.messages.Message;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.concurrent.BlockingQueue;

/**
 * Created by Almaz on 24.07.2014.
 */
public class MessageSender implements Runnable {
    private static final Logger log = Logger.getLogger(MessageSender.class);
    private ObjectOutputStream outputStream;
    private BlockingQueue<Message> messages;
    private boolean isAlive;

    public MessageSender(ObjectOutputStream outputStream, BlockingQueue<Message> messages) {
        this.outputStream = outputStream;
        this.messages = messages;
    }
    @Override
    public void run() {
        this.setAlive(true);

        while(isAlive()){
            try {
                Message msg = messages.take();

                this.outputStream.writeObject(msg);
                this.outputStream.flush();
            } catch (IOException  | InterruptedException e) {
                log.error(e.getMessage());
                e.printStackTrace();
                this.setAlive(false);
            }
        }
    }
    public void send(Message msg){
        if(msg != null)
            this.messages.add(msg);
    }
    public boolean isAlive() {
        return isAlive;
    }
    public void setAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }
}
