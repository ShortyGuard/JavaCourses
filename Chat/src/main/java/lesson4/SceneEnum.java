package lesson4;

public enum SceneEnum {
    SIGN_IN("Sign In", "SignIn"),
    SIGN_UP("Sign Up", "SignUp"),
    CHAT_WINDOW("Chat", "Chat");

    private final String title;
    private final String name;

    SceneEnum(String title, String name) {
        this.title = title;
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public String getName() {
        return name;
    }
}
