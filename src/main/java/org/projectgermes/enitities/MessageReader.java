package org.projectgermes.enitities;

import org.apache.log4j.Logger;
import org.projectgermes.enitities.messages.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.concurrent.BlockingQueue;

/**
 * Created by Almaz on 24.07.2014.
 */
public class MessageReader implements Runnable {
    private static final Logger log = Logger.getLogger(MessageReader.class);
    private BlockingQueue<Message> messages;
    private ObjectInputStream inputStream;
    private boolean isAlive;

    public MessageReader(ObjectInputStream inputStream, BlockingQueue<Message> messages){
        this.messages = messages;
        this.inputStream = inputStream;
    }

    @Override
    public void run() {
        this.setAlive(true);
        while (this.isAlive()){
            try {
                Object obj = inputStream.readObject();

                Message msg = (Message) obj;
                log.info(msg.getMessage());

                messages.add(msg);
            } catch (IOException | ClassNotFoundException e) {
                this.setAlive(false);
                log.error(e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public boolean isAlive() {
        return isAlive;
    }
    public void setAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }
}
