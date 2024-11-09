package com.rodiond26.overhellz.otus.basic.http;

import com.rodiond26.overhellz.otus.basic.config.HttpServerConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServer {

    private static final Logger LOGGER = LogManager.getLogger(HttpServer.class.getName());
    private final HttpServerConfig serverConfig;
    private final Dispatcher dispatcher;
    private ExecutorService executorService;

    public HttpServer(HttpServerConfig serverConfig, Dispatcher dispatcher) {
        this.serverConfig = serverConfig;
        this.dispatcher = dispatcher;
        this.executorService = Executors.newFixedThreadPool(serverConfig.getThreadCount());
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(serverConfig.getPort())) {
            LOGGER.info("Сервер запущен на порту: {}", serverConfig.getPort());
            while (serverSocket.isBound() && !serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                LOGGER.info("Клиент подключен на порту: {}", socket.getPort());
                executorService.execute(() -> new HttpConnectionWorker(socket, dispatcher).run());
            }
        } catch (IOException e) {
            LOGGER.error("Ошибка ввода-вывода {} = {}", e.getMessage(), e.getStackTrace());
        } finally {
            executorService.shutdown();
        }
    }
}
