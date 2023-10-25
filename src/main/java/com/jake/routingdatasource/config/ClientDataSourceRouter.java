package com.jake.routingdatasource.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

public class ClientDataSourceRouter extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        return ClientDatabaseContextHolder.getClientDatabase();
    }

    public void initDatasource(DataSource clientDataSourceA, DataSource clientDataSourceB) {
        Map<Object, Object> dataSourceMap = new HashMap<>();
        dataSourceMap.put(ClientDatabase.CLIENT_A, clientDataSourceA);
        dataSourceMap.put(ClientDatabase.CLIENT_B, clientDataSourceB);
        this.setTargetDataSources(dataSourceMap);
        this.setDefaultTargetDataSource(clientDataSourceA);
    }
}
