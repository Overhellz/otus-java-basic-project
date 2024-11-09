package com.rodiond26.overhellz.otus.basic.service.impl;

import com.rodiond26.overhellz.otus.basic.config.DbConfig;
import com.rodiond26.overhellz.otus.basic.repository.SqlExecutorRepository;
import com.rodiond26.overhellz.otus.basic.service.SqlExecutorService;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class SqlExecutorServiceImpl implements SqlExecutorService {

    private final SqlExecutorRepository repository;
    private final DbConfig config;
    private final String DEFAULT_INIT_SCRIPT = "./src/main/resources/sql-scripts/001-init.sql";

    public SqlExecutorServiceImpl(SqlExecutorRepository repository, DbConfig config) {
        this.repository = repository;
        this.config = config;
    }

    @Override
    public void executeInitSqlScripts() {
        if (config.getIsNeedToInitialize()) {
            executeSqlScript(DEFAULT_INIT_SCRIPT); // TODO fix
        }
    }

    public void executeSqlScript(String scriptPath) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(scriptPath))) {
            StringBuilder builder = new StringBuilder();
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                if (line.isBlank() || line.startsWith("--")) {
                    continue;
                }
                builder.append(line);

                if (line.contains(";")) {
                    System.out.println(builder); // TODO delete
                    repository.execute(builder.toString());
                    builder.setLength(0);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void executeSqlScripts(String... scriptFilePaths) {
        for (String script : scriptFilePaths) {
            executeSqlScript(script);
        }
    }
}
