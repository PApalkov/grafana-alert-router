package ru.grafana.alert.router.model.request.grafana;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GrafanaHookRequestMessage {
    private Integer dashboardId;
    private List<EvalMatch> evalMatches;
    private String imageUrl;
    private String message;
    private Integer orgId;
    private Integer panelId;
    private Integer ruleId;
    private String ruleName;
    private String ruleUrl;
    private String state;
    //    private Los tags;
    private String title;
}
