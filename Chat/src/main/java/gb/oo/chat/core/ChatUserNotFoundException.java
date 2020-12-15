package gb.oo.chat.core;

import lombok.Getter;

public class ChatUserNotFoundException extends Exception {
    @Getter
    private final String userMessage;

    public ChatUserNotFoundException(String userMessage) {
        super();
        this.userMessage    = userMessage;
    }
}
