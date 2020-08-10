package ru.grafana.alert.router.sender;

import ru.grafana.alert.router.communicator.TelegramCommunicator;
import ru.grafana.alert.router.model.common.Route;
import ru.grafana.alert.router.model.request.FileMessageRequest;
import ru.grafana.alert.router.model.request.TextMessageRequest;

import static ru.art.core.checker.CheckerForEmptiness.isNotEmpty;

public class MessageSender {

    // can route in different channels
    public static void sendTextMessage(TextMessageRequest request, Route route) {
        if (isNotEmpty(route.getTelegramConfig())) {
            TelegramCommunicator.sendTelegramMessage(request, route.getTelegramConfig());
        }
    }

    public static void sendPhotoMessage(FileMessageRequest request, Route route) throws Exception {
        if (isNotEmpty(route.getTelegramConfig())) {
            TelegramCommunicator.sendTelegramPhoto(request, route.getTelegramConfig());
        }
    }
}
