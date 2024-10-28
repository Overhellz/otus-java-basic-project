package com.rodiond26.overhellz.otus.basic;

import com.rodiond26.overhellz.otus.basic.config.DbConfig;
import com.rodiond26.overhellz.otus.basic.config.impl.PostgresDbConfigImpl;
import com.rodiond26.overhellz.otus.basic.repository.SqlExecutorRepository;
import com.rodiond26.overhellz.otus.basic.repository.impl.SqlExecutorRepositoryImpl;
import com.rodiond26.overhellz.otus.basic.service.SqlExecutorService;
import com.rodiond26.overhellz.otus.basic.service.impl.SqlExecutorServiceImpl;

import java.io.File;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class App {
    public static void main(String[] args) throws SQLException {
        App app = new App();
        System.out.println(app.getFiles("."));
        System.out.println(app.getFiles("./src"));
        System.out.println(app.getFiles("./src"));

        DbConfig config = new PostgresDbConfigImpl();
        SqlExecutorRepository repository = new SqlExecutorRepositoryImpl(config);
        SqlExecutorService service = new SqlExecutorServiceImpl(repository);
        service.executeSqlScript("./src/main/resources/sql-scripts/001-init.sql");
    }

    /**
     * Возвращает список файлов из каталога directoryPath
     */
    public Set<String> getFiles(String directoryPath) {
        File directory = new File(directoryPath);
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException("Указанный путь не является директорией");
        }

        File[] files = directory.listFiles();
        if (files == null) {
            return Collections.emptySet();
        }

        return Arrays.stream(files)
                .filter(File::isFile)
                .map(File::getName)
                .collect(Collectors.toCollection(TreeSet::new));
    }
}
