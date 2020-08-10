package ru.grafana.alert.router.mapping.telegram;

import ru.art.entity.Entity;
import ru.art.entity.mapper.ValueFromModelMapper;
import ru.art.entity.mapper.ValueToModelMapper;
import ru.grafana.alert.router.model.telegram.SendMessageTelegramRequest;

import static ru.art.core.checker.CheckerForEmptiness.isNotEmpty;

public interface SendMessageTelegramRequestMapper {
    String chat_id = "chat_id";

    String text = "text";

    ValueToModelMapper<SendMessageTelegramRequest, Entity> toSendMessageTelegramRequest = entity -> isNotEmpty(entity) ? SendMessageTelegramRequest.builder()
            .chat_id(entity.getString(chat_id))
            .text(entity.getString(text))
            .build() : null;

    ValueFromModelMapper<SendMessageTelegramRequest, Entity> fromSendMessageTelegramRequest = model -> isNotEmpty(model) ? Entity.entityBuilder()
            .stringField(chat_id, model.getChat_id())
            .stringField(text, model.getText())
            .build() : null;
}
