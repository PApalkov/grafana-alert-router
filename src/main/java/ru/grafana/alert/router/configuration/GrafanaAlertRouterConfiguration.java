package ru.grafana.alert.router.configuration;

import lombok.Getter;
import ru.art.config.Config;
import ru.art.core.module.ModuleConfiguration;
import ru.grafana.alert.router.model.common.Proxy;
import ru.grafana.alert.router.model.common.Route;
import ru.grafana.alert.router.model.telegram.TelegramConfig;

import java.util.Map;

import static com.predic8.membrane.core.interceptor.oauth2.ParamNames.PASSWORD;
import static com.predic8.membrane.core.interceptor.oauth2.ParamNames.USERNAME;
import static ru.art.config.extensions.ConfigExtensions.*;
import static ru.art.config.extensions.common.CommonConfigKeys.*;
import static ru.art.core.checker.CheckerForEmptiness.isEmpty;
import static ru.art.core.constants.StringConstants.DOT;
import static ru.art.core.factory.CollectionsFactory.mapOf;
import static ru.grafana.alert.router.constants.Constants.Config.*;
import static ru.grafana.alert.router.constants.TelegramConstants.Config.*;

@Getter
public class GrafanaAlertRouterConfiguration implements ModuleConfiguration {

    private Proxy proxy;
    private Map<String, Route> routeMap;
    private String metric;
    private String currentValue;
    private String okMessagePrefix;

    public GrafanaAlertRouterConfiguration() {
        refresh();
    }

    @Override
    public void refresh() {
        routeMap = configInnerMap(ALERT_ROUTER, ROUTERS, config ->
                Route.builder()
                        .path(config.getString(PATH))
                        .telegramConfig(TelegramConfig.builder()
                                .botApiKey(config.getString(TELEGRAM + DOT + BOT_API_KEY))
                                .chatId(config.getString(TELEGRAM + DOT + CHAT_ID_CONFIG))
                                .build())
                        .additionalMessage(config.getString(ADDITIONAL_MESSAGE))
                        .build(), mapOf());

        metric = configString(ALERT_ROUTER, METRIC);
        currentValue = configString(ALERT_ROUTER, CURRENT_VALUE);
        okMessagePrefix = configString(ALERT_ROUTER, OK_MESSAGE_PREFIX);

        initProxy();
    }

    private void initProxy() {
        Config proxyConfig = configInner(ALERT_ROUTER, PROXY);

        if (isEmpty(proxyConfig)) return;

        proxy = Proxy.builder()
                .host(proxyConfig.getString(HOST))
                .port(proxyConfig.getInt(PORT))
                .username(proxyConfig.getString(USERNAME))
                .password(proxyConfig.getString(PASSWORD))
                .build();
    }
}
