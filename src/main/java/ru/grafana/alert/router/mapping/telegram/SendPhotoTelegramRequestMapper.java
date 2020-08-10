package ru.grafana.alert.router.mapping.telegram;

import ru.art.entity.Entity;
import ru.art.entity.mapper.ValueFromModelMapper;
import ru.art.entity.mapper.ValueToModelMapper;
import ru.grafana.alert.router.model.telegram.SendPhotoTelegramRequest;

import static ru.art.core.checker.CheckerForEmptiness.isNotEmpty;

public interface SendPhotoTelegramRequestMapper {
    String chatId = "chatId";

    String text = "text";

    String photo = "photo";

    ValueToModelMapper<SendPhotoTelegramRequest, Entity> toSendPhotoTelegramRequest = entity -> isNotEmpty(entity) ? SendPhotoTelegramRequest.builder()
            .chatId(entity.getString(chatId))
            .text(entity.getString(text))
            .photo(entity.getString(photo))
            .build() : null;

    ValueFromModelMapper<SendPhotoTelegramRequest, Entity> fromSendPhotoTelegramRequest = model -> isNotEmpty(model) ? Entity.entityBuilder()
            .stringField(chatId, model.getChatId())
            .stringField(text, model.getText())
            .stringField(photo, model.getPhoto())
            .build() : null;
}
