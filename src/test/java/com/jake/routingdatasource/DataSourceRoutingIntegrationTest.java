package com.jake.routingdatasource;

import com.jake.routingdatasource.config.ClientDatabase;
import com.jake.routingdatasource.config.ClientDatabaseContextHolder;
import com.jake.routingdatasource.model.ClientADetails;
import com.jake.routingdatasource.model.ClientBDetails;
import com.jake.routingdatasource.service.ClientService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {ClientADetails.class, ClientBDetails.class})
@ContextConfiguration(classes = DataSourceRoutingTestConfiguration.class)
@DirtiesContext
@EnableConfigurationProperties(ClientBDetails.class)
public class DataSourceRoutingIntegrationTest {

    @Autowired
    DataSource routingDataSource;

    @Autowired
    ClientService clientService;

    @BeforeEach
    public void setup() {
        final String SQL_CLIENT_A = "insert into client (id, name) values (1, 'CLIENT A')";
        final String SQL_CLIENT_B = "insert into client (id, name) values (2, 'CLIENT B')";

        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(routingDataSource);

        ClientDatabaseContextHolder.set(ClientDatabase.CLIENT_A);
        jdbcTemplate.execute(SQL_CLIENT_A);
        ClientDatabaseContextHolder.clear();

        ClientDatabaseContextHolder.set(ClientDatabase.CLIENT_B);
        jdbcTemplate.execute(SQL_CLIENT_B);
        ClientDatabaseContextHolder.clear();
    }

    @Test
    public void testRouteToCorrectDatabase() throws Exception {
        String clientName = clientService.getClientName(ClientDatabase.CLIENT_A);
        assertEquals(clientName, "CLIENT A");

        clientName = clientService.getClientName(ClientDatabase.CLIENT_B);
        assertEquals(clientName, "CLIENT B");
    }
}
