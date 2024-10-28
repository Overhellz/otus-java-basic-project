package com.rodiond26.overhellz.otus.basic.config;

public interface DbConfig {

    String getDatabaseUrl();

    String getUserName();

    String getUserPassword();

    boolean isNeedToInitialize();
}
