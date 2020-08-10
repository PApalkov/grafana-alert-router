package ru.grafana.alert.router.specification;

import ru.grafana.alert.router.service.GrafanaAlertRouterService;

import static ru.art.core.caster.Caster.cast;
import static ru.art.http.constants.MimeToContentTypeMapper.applicationJsonUtf8;
import static ru.art.http.server.function.HttpServiceFunction.httpPost;
import static ru.art.service.constants.RequestValidationPolicy.NOT_NULL;
import static ru.grafana.alert.router.mapping.request.grafana.GrafanaHookRequestMessageMapper.toGrafanaHookRequestMessage;
import static ru.grafana.alert.router.module.GrafanaAlertRouter.alertRouter;

public class GrafanaServiceSpecification {

    public static void registerRoutes() {
        alertRouter().getRouteMap().forEach((routeName, routeSettings) ->
                httpPost(routeSettings.getPath())
                        .fromBody()
                        .producesMimeType(applicationJsonUtf8())
                        .consumesMimeType(applicationJsonUtf8())
                        .requestMapper(toGrafanaHookRequestMessage)
                        .validationPolicy(NOT_NULL)
                        .consume((request) -> GrafanaAlertRouterService.resendAlert(cast(request), routeSettings))
        );
    }
}
