package financial.modeling.server.utils;

import financial.modeling.entities.Bank;

import java.io.*;
import java.util.Properties;

public class LanguageResourceProvider implements ResourceProvider{
    public static final String WELCOME_MESSAGE = "welcome-message";



    private Properties messages;

    public LanguageResourceProvider(String messagesFileName) throws IOException {
        messages = new Properties();
        load(messagesFileName);
    }

    private void load(String fileName) throws IOException {
        messages.load(new InputStreamReader(new FileInputStream(
                Bank.class.getClassLoader().getResource(fileName).getFile()),
                "UTF-8"));
    }

    public void reload(String fileName) throws IOException {
        messages = new Properties();
        load(fileName);
    }

    @Override
    public String getValue(String paramName) {
        Object o = messages.get(paramName);

        return o == null ? "" : o.toString();
    }
}
