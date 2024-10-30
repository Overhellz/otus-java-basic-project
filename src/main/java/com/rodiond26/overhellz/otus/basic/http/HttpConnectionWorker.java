package com.rodiond26.overhellz.otus.basic.http;

import java.io.IOException;
import java.net.Socket;

public class HttpConnectionWorker {

    private Socket socket;
    private Dispatcher dispatcher;

    public HttpConnectionWorker(Socket socket, Dispatcher dispatcher) {
        this.socket = socket;
        this.dispatcher = dispatcher;
    }

    public void run() {
        try {
            byte[] buffer = new byte[8192];
            int n = socket.getInputStream().read(buffer);

            String rawRequest = new String(buffer, 0, n);
            HttpRequest request = new HttpRequest(rawRequest);
            request.info();
            dispatcher.execute(request, socket.getOutputStream());
        } catch (IOException e) {
            Thread.currentThread().interrupt();
        }
    }
}
