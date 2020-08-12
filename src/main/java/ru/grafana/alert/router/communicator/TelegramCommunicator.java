package ru.grafana.alert.router.communicator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import ru.grafana.alert.router.entity.MultipartCustomEntity;
import ru.grafana.alert.router.exception.SendRequestException;
import ru.grafana.alert.router.model.request.FileMessageRequest;
import ru.grafana.alert.router.model.request.TextMessageRequest;
import ru.grafana.alert.router.model.telegram.SendMessageTelegramRequest;
import ru.grafana.alert.router.model.telegram.TelegramConfig;

import static java.lang.String.format;
import static org.apache.http.HttpHeaders.CONTENT_TYPE;
import static org.apache.http.entity.ContentType.IMAGE_PNG;
import static ru.art.core.checker.CheckerForEmptiness.isEmpty;
import static ru.art.core.checker.CheckerForEmptiness.isNotEmpty;
import static ru.art.core.constants.StringConstants.EMPTY_STRING;
import static ru.art.http.client.communicator.HttpCommunicator.httpCommunicator;
import static ru.art.http.constants.MimeToContentTypeMapper.applicationJsonUtf8;
import static ru.grafana.alert.router.constants.Constants.Error.EMPTY_RESPONSE_ERROR;
import static ru.grafana.alert.router.constants.Constants.Error.STATUS_ERROR;
import static ru.grafana.alert.router.constants.TelegramConstants.*;
import static ru.grafana.alert.router.factory.HttpClientFactory.createProxyHttpClient;
import static ru.grafana.alert.router.factory.HttpClientFactory.createProxyHttpFileClient;
import static ru.grafana.alert.router.mapping.telegram.SendMessageTelegramRequestMapper.fromSendMessageTelegramRequest;

//todo refactor

@Getter
@AllArgsConstructor
public class TelegramCommunicator {

    public static void sendTelegramMessage(TextMessageRequest request, TelegramConfig telegramConfig) {

        httpCommunicator(format(BOT_SEND_MESSAGE_URL_TEMPLATE, telegramConfig.getBotApiKey()))
                .post()
                .client(createProxyHttpClient())
                .consumes(applicationJsonUtf8())
                .produces(applicationJsonUtf8())
                .requestMapper(fromSendMessageTelegramRequest)
                .addHeader(CONTENT_TYPE, "application/json")
                .execute(SendMessageTelegramRequest.builder()
                        .chat_id(telegramConfig.getChatId())
                        .text(isNotEmpty(request) ? request.getTextMessage() : EMPTY_STRING)
                        .build());
    }

    public static void sendTelegramPhoto(FileMessageRequest request, TelegramConfig telegramConfig) throws Exception {

        CloseableHttpClient httpClient = createProxyHttpFileClient();
        HttpPost uploadFile = new HttpPost(format(BOT_SEND_PHOTO_URL_TEMPLATE, telegramConfig.getBotApiKey()));
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();

        builder.addTextBody(CHAT_ID, telegramConfig.getChatId(), TEXT_PLAIN_UTF8);
        builder.addBinaryBody(PHOTO, request.getFile(), IMAGE_PNG, request.getFile().getName());
        builder.addTextBody(CAPTION, request.getCaption(), TEXT_PLAIN_UTF8);

        HttpEntity multipart = new MultipartCustomEntity(builder.build());
        uploadFile.setEntity(multipart);
        CloseableHttpResponse execute = httpClient.execute(uploadFile);

        if (isEmpty(execute) || isEmpty(execute.getStatusLine()))
            throw new SendRequestException(EMPTY_RESPONSE_ERROR);

        if (200 != execute.getStatusLine().getStatusCode())
            throw new SendRequestException(STATUS_ERROR);
    }
}
