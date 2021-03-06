package ru.grafana.alert.router.model.telegram;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SendPhotoTelegramRequest {
    private final String chatId;
    private final String text;
    private final String photo;
}
