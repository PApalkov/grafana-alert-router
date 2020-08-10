package ru.grafana.alert.router.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import ru.art.generator.mapper.annotation.IgnoreGeneration;

@Getter
@Builder
@AllArgsConstructor
@IgnoreGeneration
public class TextMessageRequest {
    private final String textMessage;
}
