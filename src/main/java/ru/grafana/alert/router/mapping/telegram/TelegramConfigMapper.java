package ru.grafana.alert.router.mapping.telegram;

import ru.art.entity.Entity;
import ru.art.entity.mapper.ValueFromModelMapper;
import ru.art.entity.mapper.ValueToModelMapper;
import ru.grafana.alert.router.model.telegram.TelegramConfig;

import static ru.art.core.checker.CheckerForEmptiness.isNotEmpty;

public interface TelegramConfigMapper {
    String chatId = "chatId";

    String botApiKey = "botApiKey";

    ValueToModelMapper<TelegramConfig, Entity> toTelegramConfig = entity -> isNotEmpty(entity) ? TelegramConfig.builder()
            .chatId(entity.getString(chatId))
            .botApiKey(entity.getString(botApiKey))
            .build() : null;

    ValueFromModelMapper<TelegramConfig, Entity> fromTelegramConfig = model -> isNotEmpty(model) ? Entity.entityBuilder()
            .stringField(chatId, model.getChatId())
            .stringField(botApiKey, model.getBotApiKey())
            .build() : null;
}
