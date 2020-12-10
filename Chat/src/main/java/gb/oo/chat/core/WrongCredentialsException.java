package gb.oo.chat.core;

import lombok.Getter;

public class WrongCredentialsException extends Exception {
    @Getter
    private final String userMessage;

    public WrongCredentialsException(String userMessage) {
        super();
        this.userMessage    = userMessage;
    }
}
