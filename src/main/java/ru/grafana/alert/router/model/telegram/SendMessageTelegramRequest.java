package ru.grafana.alert.router.model.telegram;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SendMessageTelegramRequest {
    private final String chat_id;
    private final String text;
}
