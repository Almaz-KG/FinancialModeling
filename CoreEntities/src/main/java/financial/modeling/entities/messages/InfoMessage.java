package financial.modeling.entities.messages;

public class InfoMessage implements Message {
    private final String body;

    public InfoMessage(String body) {
        this.body = body;
    }

    @Override
    public String getBody() {
        return body;
    }
}
