package org.projectgermes.server;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.apache.log4j.Logger;
import org.projectgermes.enitities.messages.Message;
import org.projectgermes.enitities.messages.MessageType;


/*
 * User: Almaz
 * Date: 19.07.14
 * Time: 1:22
 */
public class GermesBank implements Server {
    private static final Logger log = Logger.getLogger(GermesBank.class);
    private ServerSocket serverSocket;

    private ServerConfiguration config;
    private ServerMessageSender msSender;

    private BlockingQueue<PlayerThread> players;
    private BlockingQueue<Message> messages;

    private boolean running;
    private int playerCount;
    private GameState gameState;

    private Thread thServer;
    private Thread thMsgSender;

    public GermesBank() {
        init(new ServerConfiguration());
    }
    public GermesBank(ServerConfiguration config){
        init(config);
    }

    protected void init(ServerConfiguration config){
        this.players = new ArrayBlockingQueue<>(config.getUserListSize());
        this.messages = new ArrayBlockingQueue<>(config.getMessageListSize());
        this.gameState = new GameState();
        this.msSender = new ServerMessageSender(this.players, this.messages);
        this.playerCount = config.getPlayerCount();
        this.config = config;
    }

    @Override
    public void start(int port) throws IOException{
        this.serverSocket = new ServerSocket(port);
        running = true;
        thServer = new Thread(this);
        thServer.start();

        thMsgSender = new Thread(this.msSender);
        thMsgSender.setDaemon(true);
        thMsgSender.start();

        log.info("Server started. Listening " + config.getPort() + " port");
    }
    @Override
    public void stop() {
        this.running = false;
    }
    @Override
    public void send(Message msg) {
        this.messages.add(msg);
    }
    @Override
    public void startModeling(){
        send(Message.getMessage(MessageType.START_GAME, gameState));

    }
    public void run() {
        try{
            while (this.running){
                Socket cs = serverSocket.accept();
                log.info("New player connection");
                PlayerThread player = new PlayerThread(cs, this);
                player.setDaemon(true);
                player.start();
                this.players.add(player);

                if(players.size() >= playerCount)
                    startModeling();
            }
        } catch (IOException e){
            log.error(e);
        }
    }
}
