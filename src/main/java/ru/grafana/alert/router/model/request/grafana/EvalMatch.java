package ru.grafana.alert.router.model.request.grafana;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EvalMatch {
    private String value;
    private String metric;
}
