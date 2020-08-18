package ru.grafana.alert.router.builder;

import ru.grafana.alert.router.model.request.grafana.EvalMatch;
import ru.grafana.alert.router.model.request.grafana.GrafanaHookRequestMessage;

import java.util.List;

import static ru.art.core.checker.CheckerForEmptiness.isEmpty;
import static ru.art.core.constants.NetworkConstants.LOCALHOST;
import static ru.art.core.constants.StringConstants.EMPTY_STRING;
import static ru.art.core.constants.StringConstants.NEW_LINE;
import static ru.grafana.alert.router.constants.Constants.*;
import static ru.grafana.alert.router.module.GrafanaAlertRouter.alertRouter;

public class MessageBuilder {

    //todo add pending etc.
    public static String buildMessage(GrafanaHookRequestMessage grafanaHookRequestMessage, String additionalMessage) {
        switch (grafanaHookRequestMessage.getState()) {
            case OK:
                return buildOkMessage(grafanaHookRequestMessage, additionalMessage);
            default:
                return buildAlertingMessage(grafanaHookRequestMessage, additionalMessage);
        }
    }

    public static String buildOkMessage(GrafanaHookRequestMessage grafanaHookRequestMessage, String additionalMessage) {
        return OK_STICKER + NEW_LINE + NEW_LINE +
                alertRouter().getOkMessagePrefix() + NEW_LINE +
                grafanaHookRequestMessage.getRuleName() + NEW_LINE +
                grafanaHookRequestMessage.getMessage() + NEW_LINE + NEW_LINE +
                buildGrafanaUrl(grafanaHookRequestMessage.getRuleUrl()) + NEW_LINE +
                addEvalMatches(grafanaHookRequestMessage.getEvalMatches()) + NEW_LINE +
                additionalMessage;
    }

    public static String buildAlertingMessage(GrafanaHookRequestMessage grafanaHookRequestMessage, String additionalMessage) {
        return SOS_STICKER + NEW_LINE + NEW_LINE +
                grafanaHookRequestMessage.getRuleName() + NEW_LINE +
                grafanaHookRequestMessage.getMessage() + NEW_LINE + NEW_LINE +
                buildGrafanaUrl(grafanaHookRequestMessage.getRuleUrl()) + NEW_LINE +
                addEvalMatches(grafanaHookRequestMessage.getEvalMatches()) + NEW_LINE +
                additionalMessage;
    }

    private static String addEvalMatches(List<EvalMatch> evalMatches) {
        if (isEmpty(evalMatches))
            return EMPTY_STRING;

        StringBuilder evalMatchMessageBuilder = new StringBuilder();
        for (EvalMatch evalMatch : evalMatches) {
            evalMatchMessageBuilder.append(NEW_LINE)
                    .append(alertRouter().getMetric()).append(evalMatch.getMetric()).append(NEW_LINE)
                    .append(alertRouter().getCurrentValue()).append(evalMatch.getValue()).append(NEW_LINE);
        }

        return evalMatchMessageBuilder.toString();
    }

    private static String buildGrafanaUrl(String url) {
        return isEmpty(url) ? EMPTY_STRING : URL_PREFIX + url.replace(LOCALHOST, alertRouter().getGrafanaRealHost());
    }
}
