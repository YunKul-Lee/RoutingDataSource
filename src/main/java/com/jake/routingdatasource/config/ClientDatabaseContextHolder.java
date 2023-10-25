package com.jake.routingdatasource.config;

import org.springframework.util.Assert;

public class ClientDatabaseContextHolder {

    private static ThreadLocal<ClientDatabase> _CONTEXT = new ThreadLocal<>();

    public static void set(ClientDatabase clientDatabase) {
        Assert.notNull(clientDatabase, "clientDatabase cannot be null");
        _CONTEXT.set(clientDatabase);
    }

    public static ClientDatabase getClientDatabase() {
        return _CONTEXT.get();
    }

    public static void clear() {
        _CONTEXT.remove();
    }
}
