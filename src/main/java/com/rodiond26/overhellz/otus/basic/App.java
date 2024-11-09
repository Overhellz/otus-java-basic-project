package com.rodiond26.overhellz.otus.basic;

import com.rodiond26.overhellz.otus.basic.config.AppConfig;
import com.rodiond26.overhellz.otus.basic.config.DbConfig;
import com.rodiond26.overhellz.otus.basic.config.GeneralConfig;
import com.rodiond26.overhellz.otus.basic.config.HttpServerConfig;
import com.rodiond26.overhellz.otus.basic.http.Dispatcher;
import com.rodiond26.overhellz.otus.basic.http.HttpServer;
import com.rodiond26.overhellz.otus.basic.repository.ItemRepository;
import com.rodiond26.overhellz.otus.basic.repository.SqlExecutorRepository;
import com.rodiond26.overhellz.otus.basic.repository.impl.ItemJdbcRepositoryImpl;
import com.rodiond26.overhellz.otus.basic.repository.impl.SqlExecutorRepositoryImpl;
import com.rodiond26.overhellz.otus.basic.service.ItemService;
import com.rodiond26.overhellz.otus.basic.service.SqlExecutorService;
import com.rodiond26.overhellz.otus.basic.service.impl.ItemServiceImpl;
import com.rodiond26.overhellz.otus.basic.service.impl.SqlExecutorServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class App {
    private static final Logger LOGGER = LogManager.getLogger(App.class.getName());

    public static void main(String[] args) {
        LOGGER.info("Старт приложения");

        GeneralConfig generalConfig = new GeneralConfig();
        AppConfig appConfig = generalConfig.getAppConfig(GeneralConfig.DEFAULT_CONFIG_PATH);
        DbConfig dbConfig = appConfig.getDbConfig();
        HttpServerConfig httpServerConfig = appConfig.getServer();

        ItemRepository itemRepository = new ItemJdbcRepositoryImpl(dbConfig);
        SqlExecutorRepository sqlExecutorRepository = new SqlExecutorRepositoryImpl(dbConfig);

        SqlExecutorService sqlExecutorService = new SqlExecutorServiceImpl(sqlExecutorRepository, dbConfig);
        sqlExecutorService.executeInitSqlScripts();
        ItemService itemService = new ItemServiceImpl(itemRepository);

        Dispatcher dispatcher = new Dispatcher(itemService);
        HttpServer httpServer = new HttpServer(httpServerConfig, dispatcher);

        httpServer.start();
    }
}
