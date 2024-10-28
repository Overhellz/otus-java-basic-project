package com.rodiond26.overhellz.otus.basic.repository.impl;

import com.rodiond26.overhellz.otus.basic.config.DbConfig;
import com.rodiond26.overhellz.otus.basic.repository.SqlExecutorRepository;

import java.sql.*;

public class SqlExecutorRepositoryImpl implements SqlExecutorRepository {

    private final DbConfig config;
    private Connection connection;

    public SqlExecutorRepositoryImpl(DbConfig config) throws SQLException {
        this.config = config;
        connection = getConnection();
        if (connection != null) {
            printMetaData(connection);
            System.out.println("Успешное подключение к базе данных: " + config.getDatabaseUrl()); // TODO logger
        }
    }

    @Override
    public void execute(String sqlScript) {
        try (Statement statement = connection.createStatement()) {
            statement.execute(sqlScript);
        } catch (SQLException e) {
            e.printStackTrace(); // TODO fix
        }
    }

    private void printMetaData(Connection connection) throws SQLException {
        DatabaseMetaData metaData = connection.getMetaData();
        String format = "\nDatabase metadata\n"
                + "Database name : %s\n"
                + "Database version : %s\n"
                + "Database driver name : %s\n"
                + "Database driver version : %s\n\n";
        System.out.printf(format,
                metaData.getDatabaseProductName(),
                metaData.getDatabaseProductVersion(),
                metaData.getDriverName(),
                metaData.getDriverVersion());
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                config.getDatabaseUrl(),
                config.getUserName(),
                config.getUserPassword()
        );
    }
}
