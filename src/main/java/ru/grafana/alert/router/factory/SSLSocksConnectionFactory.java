package ru.grafana.alert.router.factory;


import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.protocol.HttpContext;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Socket;

import static java.net.Proxy.Type.SOCKS;

public class SSLSocksConnectionFactory extends SSLConnectionSocketFactory {
    private final String socksHost;
    private final int socksPort;

    @Override
    public Socket createSocket(final HttpContext params) {
        InetSocketAddress socksAddr = new InetSocketAddress(socksHost, socksPort);
        Proxy proxy = new Proxy(SOCKS, socksAddr);
        return new Socket(proxy);
    }

    public SSLSocksConnectionFactory(String socksHost, int socksPort, SSLContext sslContext, HostnameVerifier hostnameVerifier) {
        super(sslContext, hostnameVerifier);
        this.socksHost = socksHost;
        this.socksPort = socksPort;
    }
}

