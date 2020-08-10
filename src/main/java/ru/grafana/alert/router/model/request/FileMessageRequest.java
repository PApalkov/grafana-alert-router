package ru.grafana.alert.router.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import ru.art.generator.mapper.annotation.IgnoreGeneration;

import java.io.File;

@Getter
@Builder
@AllArgsConstructor
@IgnoreGeneration
public class FileMessageRequest {
    private final File file;
    private final String caption;
}
