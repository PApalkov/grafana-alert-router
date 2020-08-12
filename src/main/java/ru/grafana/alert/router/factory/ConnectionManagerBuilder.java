package ru.grafana.alert.router.factory;

import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import ru.grafana.alert.router.model.common.Proxy;

import javax.net.ssl.SSLContext;

import static ru.art.core.checker.CheckerForEmptiness.isNotEmpty;
import static ru.art.http.constants.HttpCommonConstants.HTTPS_SCHEME;
import static ru.art.http.constants.HttpCommonConstants.HTTP_SCHEME;
import static ru.grafana.alert.router.constants.ProxyType.SOCKS;

public class ConnectionManagerBuilder {

    public static HttpClientConnectionManager buildConnectionManager(Proxy proxy, SSLContext sslContext) {
        if (isNotEmpty(proxy) && SOCKS.equals(proxy.getType())) {
            return new PoolingHttpClientConnectionManager(RegistryBuilder.<ConnectionSocketFactory>create()
                    .register(HTTP_SCHEME, new SocksConnectionFactory(proxy.getHost(), proxy.getPort()))
                    .register(HTTPS_SCHEME, new SSLSocksConnectionFactory(proxy.getHost(), proxy.getPort(), sslContext, NoopHostnameVerifier.INSTANCE))
                    .build());
        }

        return new PoolingHttpClientConnectionManager(RegistryBuilder.<ConnectionSocketFactory>create()
                .register(HTTP_SCHEME, PlainConnectionSocketFactory.INSTANCE)
                .register(HTTPS_SCHEME, new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE))
                .build());
    }
}
