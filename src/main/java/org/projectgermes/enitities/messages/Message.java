package org.projectgermes.enitities.messages;

import org.projectgermes.server.GameState;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by almu0214 on 12.08.2014.
 */
public class Message implements Serializable {
    protected String message = "";
    protected Date date;
    protected String author;
    protected MessageType type;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public MessageType getType() {
        return type;
    }
    public void setType(MessageType type) {
        this.type = type;
    }

    public Message() {
        new Message("", "SERVER", MessageType.NONE);
    }
    public Message(String message, String author, MessageType type) {
        this.date = new Date();
        this.message = message;
        this.author = author;
        this.type = type;
    }

    public static Message getMessage(MessageType type, GameState state){
        // TODO:
        switch (type){
            case NONE:
                return new Message();
            case INFO:
                return new Message();
            case ERROR:
                return new Message();
            case WARNING:
                return new Message();
            case START_GAME:
                return new Message();
            case CURRENT_STATE:
                return new Message();
            case END_GAME:
                return new Message();
        };
        return null;
    }
}
