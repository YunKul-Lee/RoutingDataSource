package com.jake.routingdatasource.dao;

import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

public class ClientDao {
    private static final String SQL_GET_CLIENT_NAME = "select name from client";

    private final JdbcTemplate jdbcTemplate;

    public ClientDao(DataSource datasource) {
        this.jdbcTemplate = new JdbcTemplate(datasource);
    }

    public String getClientName() {
        return this.jdbcTemplate.query(SQL_GET_CLIENT_NAME, (rs, rowNum) -> rs.getString("name")).get(0);
    }
}