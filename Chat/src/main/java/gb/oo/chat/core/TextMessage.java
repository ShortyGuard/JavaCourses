package gb.oo.chat.core;

import com.sun.javafx.binding.StringFormatter;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class TextMessage implements ChatMessage {
    @Builder.Default
    private Instant sendDate = Instant.now();
    private String author;
    private String text;
    private boolean isPrivateMessage = false;
    private String recipientNickName;

    @Override
    public ChatMessageType getMessageType() {
        return ChatMessageType.TEXT_MESSAGE;
    }

    public String getFormattedInstant() {
        return DateTimeFormatter.ISO_INSTANT.format(sendDate.truncatedTo(ChronoUnit.SECONDS));
    }

    @Override
    public String toString() {
        return StringFormatter.format("[%s] %s: %s",
            DateTimeFormatter.ISO_INSTANT.format(sendDate.truncatedTo(ChronoUnit.SECONDS)),
            author,
            text
        ).getValue();
    }
}
