package ru.grafana.alert.router.mapping.common;

import ru.art.entity.Entity;
import ru.art.entity.mapper.ValueFromModelMapper;
import ru.art.entity.mapper.ValueToModelMapper;
import ru.grafana.alert.router.model.common.Proxy;

import static ru.art.core.checker.CheckerForEmptiness.isNotEmpty;

public interface ProxyMapper {
    String host = "host";

    String port = "port";

    String username = "username";

    String password = "password";

    ValueToModelMapper<Proxy, Entity> toProxy = entity -> isNotEmpty(entity) ? Proxy.builder()
            .host(entity.getString(host))
            .port(entity.getInt(port))
            .username(entity.getString(username))
            .password(entity.getString(password))
            .build() : null;

    ValueFromModelMapper<Proxy, Entity> fromProxy = model -> isNotEmpty(model) ? Entity.entityBuilder()
            .stringField(host, model.getHost())
            .intField(port, model.getPort())
            .stringField(username, model.getUsername())
            .stringField(password, model.getPassword())
            .build() : null;
}
