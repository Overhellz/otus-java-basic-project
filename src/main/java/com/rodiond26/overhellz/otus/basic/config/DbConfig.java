package com.rodiond26.overhellz.otus.basic.config;

import lombok.Data;

@Data
public final class DbConfig {

    private String databaseUrl;
    private String userName;
    private String userPassword;
    private Boolean isNeedToInitialize;
}
