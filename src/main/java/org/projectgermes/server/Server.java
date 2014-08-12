package org.projectgermes.server;

import org.projectgermes.enitities.messages.Message;

import java.io.IOException;

/**
 * Created by almu0214 on 12.08.2014.
 */
public interface Server extends Runnable{
    void start(int port) throws IOException;
    void stop() throws  IOException;
    void run();
    void send(Message msg);
    void startModeling();
}
