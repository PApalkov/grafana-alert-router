package ru.grafana.alert.router.model.common;

import lombok.Builder;
import lombok.Getter;
import ru.grafana.alert.router.model.telegram.TelegramConfig;

@Builder
@Getter
public class Route {
    private final String path;
    private final String additionalMessage;
    private final TelegramConfig telegramConfig;
}
