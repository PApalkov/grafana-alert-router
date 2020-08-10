package ru.grafana.alert.router.constants;

import org.apache.http.Consts;
import org.apache.http.entity.ContentType;

public interface TelegramConstants {

    String BOT_SEND_MESSAGE_URL_TEMPLATE = "https://api.telegram.org/bot%s/sendMessage";
    String BOT_SEND_PHOTO_URL_TEMPLATE = "https://api.telegram.org/bot%s/sendPhoto";

    String CHAT_ID = "chat_id";
    String CAPTION = "caption";
    String PHOTO = "photo";
    ContentType TEXT_PLAIN_UTF8 = ContentType.create("text/plain", Consts.UTF_8);

    interface Config {
        String TELEGRAM = "telegram";
        String CHAT_ID_CONFIG = "chatId";
        String BOT_API_KEY = "botApiKey";
    }
}
