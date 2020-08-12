package ru.grafana.alert.router.model.common;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import ru.grafana.alert.router.constants.ProxyType;

@Builder
@Getter
@ToString
public class Proxy {
    private final ProxyType type;
    private final String host;
    private final int port;
    private final String username;
    private final String password;
}
