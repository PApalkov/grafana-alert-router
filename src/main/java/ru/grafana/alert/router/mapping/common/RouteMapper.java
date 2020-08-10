package ru.grafana.alert.router.mapping.common;

import ru.art.entity.Entity;
import ru.art.entity.mapper.ValueFromModelMapper;
import ru.art.entity.mapper.ValueToModelMapper;
import ru.grafana.alert.router.mapping.telegram.TelegramConfigMapper;
import ru.grafana.alert.router.model.common.Route;

import static ru.art.core.checker.CheckerForEmptiness.isNotEmpty;

public interface RouteMapper {
    String path = "path";

    String additionalMessage = "additionalMessage";

    String telegramConfig = "telegramConfig";

    ValueToModelMapper<Route, Entity> toRoute = entity -> isNotEmpty(entity) ? Route.builder()
            .path(entity.getString(path))
            .additionalMessage(entity.getString(additionalMessage))
            .telegramConfig(entity.getValue(telegramConfig, TelegramConfigMapper.toTelegramConfig))
            .build() : null;

    ValueFromModelMapper<Route, Entity> fromRoute = model -> isNotEmpty(model) ? Entity.entityBuilder()
            .stringField(path, model.getPath())
            .stringField(additionalMessage, model.getAdditionalMessage())
            .entityField(telegramConfig, model.getTelegramConfig(), TelegramConfigMapper.fromTelegramConfig)
            .build() : null;
}
