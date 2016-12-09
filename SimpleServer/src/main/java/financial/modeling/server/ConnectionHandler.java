package financial.modeling.server;

import financial.modeling.entities.Bank;
import financial.modeling.entities.messages.InfoMessage;
import financial.modeling.entities.player.PlayerState;
import financial.modeling.server.utils.LanguageResourceProvider;
import financial.modeling.server.utils.ResourceProvider;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

public class ConnectionHandler extends IoHandlerAdapter {
    private Bank bank;
    private ResourceProvider resourceProvider;

    public ConnectionHandler(Bank bank, ResourceProvider resourceProvider) {
        this.bank = bank;
        this.resourceProvider = resourceProvider;
    }

    public void sessionOpened(IoSession session) {
        session.setAttribute("time", System.currentTimeMillis());
        session.setAttribute("player", PlayerState.getInitialPlayerState());
        session.write(new InfoMessage(resourceProvider.getValue(LanguageResourceProvider.WELCOME_MESSAGE)));
    }

    public void messageReceived(IoSession session, Object message) {
        session.write(message);
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        session.close(true);
    }
}
