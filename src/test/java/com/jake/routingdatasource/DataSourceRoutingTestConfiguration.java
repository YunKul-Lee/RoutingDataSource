package com.jake.routingdatasource;

import com.jake.routingdatasource.config.ClientDataSourceRouter;
import com.jake.routingdatasource.config.ClientDatabase;
import com.jake.routingdatasource.dao.ClientDao;
import com.jake.routingdatasource.model.ClientADetails;
import com.jake.routingdatasource.model.ClientBDetails;
import com.jake.routingdatasource.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DataSourceRoutingTestConfiguration {

    @Autowired
    private ClientADetails clientADetails;

    @Autowired
    private ClientBDetails clientBDetails;

    @Bean
    public ClientService clientService() {
        return new ClientService(new ClientDao(clientDataSource()));
    }

    @Bean
    public DataSource clientDataSource() {
        Map<Object, Object> targetDataSources = new HashMap<>();
        DataSource clientADataSource = clientADataSource();
        DataSource clientBDataSource = clientBDataSource();
        targetDataSources.put(ClientDatabase.CLIENT_A, clientADataSource);
        targetDataSources.put(ClientDatabase.CLIENT_B, clientBDataSource);

        ClientDataSourceRouter clientRoutingDataSource = new ClientDataSourceRouter();
        clientRoutingDataSource.setTargetDataSources(targetDataSources);
        clientRoutingDataSource.setDefaultTargetDataSource(clientADataSource);
        return clientRoutingDataSource;
    }

    private DataSource clientADataSource() {
        EmbeddedDatabaseBuilder dbBuilder = new EmbeddedDatabaseBuilder();
        return dbBuilder.setType(EmbeddedDatabaseType.H2)
                .setName(clientADetails.getName())
                .addScript("dsrouting-db.sql")
                .build();
    }

    private DataSource clientBDataSource() {
        EmbeddedDatabaseBuilder dbBuilder = new EmbeddedDatabaseBuilder();
        return dbBuilder.setType(EmbeddedDatabaseType.H2)
                .setName(clientBDetails.getName())
                .addScript("dsrouting-db.sql")
                .build();
    }
}
