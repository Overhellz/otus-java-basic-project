package com.rodiond26.overhellz.otus.basic.config;

import lombok.Data;

@Data
public class AppConfig {

    private HttpServerConfig server;
    private DbConfig dbConfig;
}
