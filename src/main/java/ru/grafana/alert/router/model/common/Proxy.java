package ru.grafana.alert.router.model.common;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Proxy {
    private final String host;
    private final int port;
    private final String username;
    private final String password;
}
