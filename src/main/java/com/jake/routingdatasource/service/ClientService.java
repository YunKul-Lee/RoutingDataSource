package com.jake.routingdatasource.service;

import com.jake.routingdatasource.config.ClientDatabase;
import com.jake.routingdatasource.config.ClientDatabaseContextHolder;
import com.jake.routingdatasource.dao.ClientDao;

public class ClientService {

    private final ClientDao clientDao;

    public ClientService(ClientDao clientDao) {
        this.clientDao = clientDao;
    }

    public String getClientName(ClientDatabase clientDb) {
        ClientDatabaseContextHolder.set(clientDb);
        String clientName = this.clientDao.getClientName();
        ClientDatabaseContextHolder.clear();
        return clientName;
    }

}
