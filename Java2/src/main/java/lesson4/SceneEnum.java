package lesson4;

import lombok.Getter;

public enum SceneEnum {
    SIGN_IN("Sign In", "SignIn"),
    SIGN_UP("Sign Up", "SignUp"),
    CHAT_WINDOW("Chat", "Chat");

    @Getter
    private final String title;
    @Getter
    private final String name;

    SceneEnum(String title, String name) {
        this.title = title;
        this.name = name;
    }
}
