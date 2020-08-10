package ru.grafana.alert.router.module;

import lombok.Getter;
import ru.art.core.module.Module;
import ru.art.core.module.ModuleState;
import ru.grafana.alert.router.configuration.GrafanaAlertRouterConfiguration;
import ru.grafana.alert.router.constants.Constants;
import ru.grafana.alert.router.specification.GrafanaServiceSpecification;

import static lombok.AccessLevel.PRIVATE;
import static ru.art.config.extensions.activator.AgileConfigurationsActivator.useAgileConfigurations;
import static ru.art.core.context.Context.context;
import static ru.art.http.server.HttpServer.startHttpServer;


@Getter
public class GrafanaAlertRouter implements Module<GrafanaAlertRouterConfiguration, ModuleState> {
    @Getter(lazy = true, value = PRIVATE)
    private static final GrafanaAlertRouterConfiguration grafanaAlertRouterConfiguration = context().getModule(Constants.GRAFANA_ALERT_ROUTER_ID, GrafanaAlertRouter::new);
    private final GrafanaAlertRouterConfiguration defaultConfiguration = new GrafanaAlertRouterConfiguration();
    private final String id = Constants.GRAFANA_ALERT_ROUTER_ID;

    public static GrafanaAlertRouterConfiguration alertRouter() {
        return getGrafanaAlertRouterConfiguration();
    }

    public static void main(String[] args) {
        startGrafanaAlertRouter();
    }

    /**
     * Starting module, loading all linked modules
     * and registering services by theirs specifications.
     */
    public static void startGrafanaAlertRouter() {
        useAgileConfigurations(Constants.GRAFANA_ALERT_ROUTER_ID)
                .loadModule(new GrafanaAlertRouter());

        GrafanaServiceSpecification.registerRoutes();
        startHttpServer();
    }
}
