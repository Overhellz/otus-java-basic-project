package com.rodiond26.overhellz.otus.basic.http;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServer {

    private static final Logger LOGGER = LogManager.getLogger(HttpServer.class.getName());

    private int port;
    private Dispatcher dispatcher;
    private ExecutorService executorService;

    public HttpServer(int port, int nThreads) {
        this.port = port;
        this.dispatcher = new Dispatcher();
        this.executorService = Executors.newFixedThreadPool(nThreads);
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            LOGGER.info("Сервер запущен на порту: {}", port);
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
