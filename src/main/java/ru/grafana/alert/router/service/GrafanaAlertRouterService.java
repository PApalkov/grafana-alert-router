package ru.grafana.alert.router.service;

import ru.grafana.alert.router.builder.MessageBuilder;
import ru.grafana.alert.router.model.common.Route;
import ru.grafana.alert.router.model.request.FileMessageRequest;
import ru.grafana.alert.router.model.request.TextMessageRequest;
import ru.grafana.alert.router.model.request.grafana.GrafanaHookRequestMessage;

import java.io.File;

import static java.lang.String.format;
import static java.lang.Thread.currentThread;
import static ru.art.core.checker.CheckerForEmptiness.isEmpty;
import static ru.grafana.alert.router.loader.FileLoader.loadFile;
import static ru.grafana.alert.router.module.GrafanaAlertRouter.alertRouter;
import static ru.grafana.alert.router.sender.MessageSender.sendPhotoMessage;
import static ru.grafana.alert.router.sender.MessageSender.sendTextMessage;

public interface GrafanaAlertRouterService {

    static void resendAlert(GrafanaHookRequestMessage grafanaHookMessage, Route route) {

        String textMessage = MessageBuilder.buildMessage(grafanaHookMessage, route.getAdditionalMessage());

        if (isEmpty(grafanaHookMessage.getImageUrl())) {
            sendTextMessage(new TextMessageRequest(textMessage), route);
            return;
        }

        File photo = loadFile(grafanaHookMessage.getImageUrl(), format(alertRouter().getTmpFilePathTemplate(), currentThread().getId()));
        if (isEmpty(photo)) {
            sendTextMessage(new TextMessageRequest(textMessage), route);
            return;
        }

        FileMessageRequest fileRequest = FileMessageRequest.builder()
                .file(photo)
                .caption(textMessage)
                .build();

        try {
            sendPhotoMessage(fileRequest, route);
        } catch (Throwable e) {
            sendTextMessage(new TextMessageRequest(textMessage), route);
        }
    }
}
