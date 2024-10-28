package com.rodiond26.overhellz.otus.basic.config.impl;

import com.rodiond26.overhellz.otus.basic.config.DbConfig;

// TODO fix using general config
public final class PostgresDbConfigImpl implements DbConfig {

    private String url = "jdbc:postgresql://localhost:5432/postgres";
    private String userName = "postgres";
    private String userPassword = "postgres";
    private boolean isNeedToInitialize = true;

    public String getDatabaseUrl() {
        return url;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public boolean isNeedToInitialize() {
        return isNeedToInitialize;
    }
}
