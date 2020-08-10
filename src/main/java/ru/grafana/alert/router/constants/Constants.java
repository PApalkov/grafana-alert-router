package ru.grafana.alert.router.constants;

public interface Constants {
    String GRAFANA_ALERT_ROUTER_ID = "grafana-alert-router";
    String OK = "ok";

    String SOS_STICKER = "🆘🆘🆘🆘🆘";
    String OK_STICKER = "✅✅✅✅✅";

    String TMP_FILE_PATH_TEMPLATE = "/Users/pavelapalkov/RTKIT/grafana-alert-router/src/main/resources/images/%s.png";

    interface Config {
        String ALERT_ROUTER = "alertRouter";
        String PROXY = "proxy";
        String ROUTERS = "routers";
        String ADDITIONAL_MESSAGE = "additionalMessage";
        String METRIC = "metric";
        String CURRENT_VALUE = "currentValue";
        String OK_MESSAGE_PREFIX = "okMessagePrefix";
    }
}
