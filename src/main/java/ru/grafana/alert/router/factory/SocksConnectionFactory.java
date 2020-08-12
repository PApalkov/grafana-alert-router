package ru.grafana.alert.router.factory;


import lombok.AllArgsConstructor;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.protocol.HttpContext;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Socket;

import static java.net.Proxy.Type.SOCKS;

@AllArgsConstructor
public class SocksConnectionFactory extends PlainConnectionSocketFactory {
    private final String socksHost;
    private final int socksPort;

    @Override
    public Socket createSocket(final HttpContext params) {
        InetSocketAddress socksAddr = new InetSocketAddress(socksHost, socksPort);
        Proxy proxy = new Proxy(SOCKS, socksAddr);
        return new Socket(proxy);
    }
}

