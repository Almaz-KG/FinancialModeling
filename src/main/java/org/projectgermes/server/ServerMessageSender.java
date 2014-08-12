package org.projectgermes.server;


import org.projectgermes.enitities.messages.Message;

import java.util.concurrent.BlockingQueue;

/**
 * Created by Almaz on 24.07.2014.
 */
public class ServerMessageSender implements Runnable {
    private BlockingQueue<PlayerThread> clients;
    private BlockingQueue<Message> messages;
    private boolean running;



    public ServerMessageSender(BlockingQueue<PlayerThread> clients, BlockingQueue<Message> messages) {
        this.clients = clients;
        this.messages = messages;
    }

    @Override
    public void run() {
        this.running = true;
        while (running){
            try {
                Message msg = this.messages.take();
                this.clients.forEach((client) -> client.send(msg));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
}
