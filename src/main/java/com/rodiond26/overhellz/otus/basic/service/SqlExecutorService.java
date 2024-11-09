package com.rodiond26.overhellz.otus.basic.service;

public interface SqlExecutorService {

    void executeInitSqlScripts();

    void executeSqlScript(String scriptPath);

    void executeSqlScripts(String... scriptPaths);
}
