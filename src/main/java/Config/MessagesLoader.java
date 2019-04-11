package Config;

import Model.Message;
import com.google.gson.Gson;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MessagesLoader {

    List<Message> messages;

    public MessagesLoader(InputStream configStream) throws UnsupportedEncodingException {
        Gson gson = new Gson();
        Reader reader = new InputStreamReader(configStream, "UTF-8");
        Message[] rawMessages = gson.fromJson(reader, Message[].class);
        messages = Arrays.asList(rawMessages);
    }

    public List<Message> getMessages() {
        return messages;
    }
}
