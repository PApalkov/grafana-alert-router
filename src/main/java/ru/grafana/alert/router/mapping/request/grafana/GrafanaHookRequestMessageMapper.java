package ru.grafana.alert.router.mapping.request.grafana;

import ru.art.entity.Entity;
import ru.art.entity.mapper.ValueFromModelMapper;
import ru.art.entity.mapper.ValueToModelMapper;
import ru.grafana.alert.router.model.request.grafana.GrafanaHookRequestMessage;

import static ru.art.core.checker.CheckerForEmptiness.isNotEmpty;

public interface GrafanaHookRequestMessageMapper {
    String dashboardId = "dashboardId";

    String evalMatches = "evalMatches";

    String imageUrl = "imageUrl";

    String message = "message";

    String orgId = "orgId";

    String panelId = "panelId";

    String ruleId = "ruleId";

    String ruleName = "ruleName";

    String ruleUrl = "ruleUrl";

    String state = "state";

    String title = "title";

    ValueToModelMapper<GrafanaHookRequestMessage, Entity> toGrafanaHookRequestMessage = entity -> isNotEmpty(entity) ? GrafanaHookRequestMessage.builder()
            .dashboardId(entity.getInt(dashboardId))
            .evalMatches(entity.getEntityList(evalMatches, EvalMatchMapper.toEvalMatch))
            .imageUrl(entity.getString(imageUrl))
            .message(entity.getString(message))
            .orgId(entity.getInt(orgId))
            .panelId(entity.getInt(panelId))
            .ruleId(entity.getInt(ruleId))
            .ruleName(entity.getString(ruleName))
            .ruleUrl(entity.getString(ruleUrl))
            .state(entity.getString(state))
            .title(entity.getString(title))
            .build() : null;

    ValueFromModelMapper<GrafanaHookRequestMessage, Entity> fromGrafanaHookRequestMessage = model -> isNotEmpty(model) ? Entity.entityBuilder()
            .intField(dashboardId, model.getDashboardId())
            .entityCollectionField(evalMatches, model.getEvalMatches(), EvalMatchMapper.fromEvalMatch)
            .stringField(imageUrl, model.getImageUrl())
            .stringField(message, model.getMessage())
            .intField(orgId, model.getOrgId())
            .intField(panelId, model.getPanelId())
            .intField(ruleId, model.getRuleId())
            .stringField(ruleName, model.getRuleName())
            .stringField(ruleUrl, model.getRuleUrl())
            .stringField(state, model.getState())
            .stringField(title, model.getTitle())
            .build() : null;
}
