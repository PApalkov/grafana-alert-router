package ru.grafana.alert.router.model.telegram;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TelegramConfig {
    private final String chatId;
    private final String botApiKey;
}
