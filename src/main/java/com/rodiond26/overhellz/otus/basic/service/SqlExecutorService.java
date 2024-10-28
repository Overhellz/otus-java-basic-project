package com.rodiond26.overhellz.otus.basic.service;

public interface SqlExecutorService {

    void executeSqlScript(String scriptPath);

    void executeSqlScripts(String... scriptPaths);
}
