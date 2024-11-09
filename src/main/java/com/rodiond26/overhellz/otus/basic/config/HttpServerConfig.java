package com.rodiond26.overhellz.otus.basic.config;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class HttpServerConfig {

    private int port;
    private int threadCount;
}
