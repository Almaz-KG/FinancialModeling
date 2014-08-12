package org.projectgermes.server;

/**
 * Created by Almaz on 04.08.2014.
 */
public class ServerConfiguration {
    private int port = 3636;
    private int messageListSize = 256;
    private int userListSize = 64;
    private int playerCount = 4;

    public void setPort(int port) {
        this.port = port;
    }
    public void setMessageListSize(int messageListSize) {
        this.messageListSize = messageListSize;
    }
    public void setUserListSize(int userListSize) {
        this.userListSize = userListSize;
    }
    public int getPlayerCount() {
        return playerCount;
    }
    public void setPlayerCount(int playerCount) {
        this.playerCount = playerCount;
    }
    public int getPort() {
        return port;
    }
    public int getMessageListSize() {
        return messageListSize;
    }
    public int getUserListSize() {
        return userListSize;
    }
}
