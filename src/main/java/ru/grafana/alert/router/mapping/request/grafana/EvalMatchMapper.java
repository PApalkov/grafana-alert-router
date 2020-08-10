package ru.grafana.alert.router.mapping.request.grafana;

import ru.art.entity.Entity;
import ru.art.entity.mapper.ValueFromModelMapper;
import ru.art.entity.mapper.ValueToModelMapper;
import ru.grafana.alert.router.model.request.grafana.EvalMatch;

import static ru.art.core.checker.CheckerForEmptiness.isNotEmpty;

public interface EvalMatchMapper {
    String value = "value";

    String metric = "metric";

    ValueToModelMapper<EvalMatch, Entity> toEvalMatch = entity -> isNotEmpty(entity) ? EvalMatch.builder()
            .value(entity.getString(value))
            .metric(entity.getString(metric))
            .build() : null;

    ValueFromModelMapper<EvalMatch, Entity> fromEvalMatch = model -> isNotEmpty(model) ? Entity.entityBuilder()
            .stringField(value, model.getValue())
            .stringField(metric, model.getMetric())
            .build() : null;
}
