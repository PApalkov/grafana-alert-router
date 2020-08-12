package ru.grafana.alert.router.factory;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.zalando.logbook.httpclient.LogbookHttpRequestInterceptor;
import org.zalando.logbook.httpclient.LogbookHttpResponseInterceptor;
import ru.grafana.alert.router.model.common.Proxy;

import javax.net.ssl.SSLContext;

import static ru.art.core.checker.CheckerForEmptiness.isNotEmpty;
import static ru.art.core.extension.ExceptionExtensions.nullIfException;
import static ru.art.http.client.module.HttpClientModule.httpClientModule;
import static ru.grafana.alert.router.factory.ConnectionManagerBuilder.buildConnectionManager;
import static ru.grafana.alert.router.module.GrafanaAlertRouter.alertRouter;

public class HttpClientFactory {

    public static CloseableHttpClient createProxyHttpClient() {
        SSLContext sslContext = nullIfException(() -> SSLContextBuilder.create()
                .loadTrustMaterial((a, b) -> true)
                .build());

        BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        RequestConfig.Builder requestConfig = RequestConfig.custom();

        Proxy proxy = alertRouter().getProxy();

        if (isNotEmpty(proxy)) {
            Credentials credentials = new UsernamePasswordCredentials(proxy.getUsername(), proxy.getPassword());
            credentialsProvider.setCredentials(new AuthScope(proxy.getHost(), proxy.getPort()), credentials);
            requestConfig.setProxy(new HttpHost(proxy.getHost(), proxy.getPort()));
        }

        return HttpClients.custom()
                .setMaxConnPerRoute(httpClientModule().getMaxConnectionsPerRoute())
                .setMaxConnTotal(httpClientModule().getMaxConnectionsTotal())
                .setDefaultRequestConfig(httpClientModule().getRequestConfig())
                .setDefaultConnectionConfig(httpClientModule().getConnectionConfig())
                .setDefaultSocketConfig(httpClientModule().getSocketConfig())
                .addInterceptorFirst(new LogbookHttpRequestInterceptor(httpClientModule().getLogbook()))
                .addInterceptorLast(new LogbookHttpResponseInterceptor())
                .setConnectionManager(buildConnectionManager(proxy, sslContext))
                .setDefaultCredentialsProvider(credentialsProvider)
                .setSSLContext(sslContext)
                .build();
    }

    public static CloseableHttpClient createProxyHttpFileClient() {
        SSLContext sslContext = nullIfException(() -> SSLContextBuilder.create()
                .loadTrustMaterial((a, b) -> true)
                .build());

        BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        RequestConfig.Builder requestConfig = RequestConfig.custom();

        Proxy proxy = alertRouter().getProxy();

        if (isNotEmpty(proxy)) {
            Credentials credentials = new UsernamePasswordCredentials(proxy.getUsername(), proxy.getPassword());
            credentialsProvider.setCredentials(new AuthScope(proxy.getHost(), proxy.getPort()), credentials);
            requestConfig.setProxy(new HttpHost(proxy.getHost(), proxy.getPort()));
        }

        return HttpClients.custom()
                .setMaxConnPerRoute(httpClientModule().getMaxConnectionsPerRoute())
                .setMaxConnTotal(httpClientModule().getMaxConnectionsTotal())
                .setDefaultRequestConfig(httpClientModule().getRequestConfig())
                .setDefaultConnectionConfig(httpClientModule().getConnectionConfig())
                .setDefaultSocketConfig(httpClientModule().getSocketConfig())
                .addInterceptorLast(new LogbookHttpResponseInterceptor())
                .setConnectionManager(buildConnectionManager(proxy, sslContext))
                .setDefaultCredentialsProvider(credentialsProvider)
                .setSSLContext(sslContext)
                .build();
    }
}
