package com.rodiond26.overhellz.otus.basic.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.rodiond26.overhellz.otus.basic.exception.ConfigException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public final class GeneralConfig {

    private final ObjectMapper objectMapper;

    public static final String DEFAULT_CONFIG_PATH = "src/main/resources/application.yml";
    private static final Logger LOGGER = LogManager.getLogger(GeneralConfig.class.getName());

    public GeneralConfig() {
        objectMapper = new ObjectMapper(new YAMLFactory());
        objectMapper.findAndRegisterModules();
    }

    public AppConfig getAppConfig(String configPath) {
        try {
            return objectMapper.readValue(new File(configPath), AppConfig.class);
        } catch (FileNotFoundException e) {
            LOGGER.error("Файл {} не найден", configPath);
            throw new ConfigException("Ошибка при открытии файла конфигурации");
        } catch (IOException e) {
            LOGGER.error("Ошибка при чтении файла {} = {}", configPath, e.getMessage());
            throw new ConfigException("Ошибка при чтении файла конфигурации");
        }
    }
}
