package ru.grafana.alert.router.constants;

public interface Constants {
    String GRAFANA_ALERT_ROUTER_ID = "grafana-alert-router";
    String OK = "ok";

    String SOS_STICKER = "🆘🆘🆘🆘🆘";
    String OK_STICKER = "✅✅✅✅✅";
    String URL_PREFIX = "URl: ";

    interface Config {
        String ALERT_ROUTER = "alertRouter";
        String PROXY = "proxy";
        String TYPE = "type";
        String TMP_FILE_PATH = "tmpFilePath";
        String GRAFANA_REAL_HOST = "grafanaHost";
        String ROUTERS = "routers";
        String ADDITIONAL_MESSAGE = "additionalMessage";
        String METRIC = "metric";
        String CURRENT_VALUE = "currentValue";
        String OK_MESSAGE_PREFIX = "okMessagePrefix";
    }

    interface Error {
        String EMPTY_RESPONSE_ERROR = "Empty response!";
        String STATUS_ERROR = "Status is not 200!";
    }
}
