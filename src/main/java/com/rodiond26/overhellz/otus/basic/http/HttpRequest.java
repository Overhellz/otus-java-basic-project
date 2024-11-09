package com.rodiond26.overhellz.otus.basic.http;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
public class HttpRequest {

    private static final Logger LOGGER = LogManager.getLogger(HttpRequest.class.getName());

    private String rawRequest;
    private HttpMethod method;
    private String uri;
    private Map<String, String> parameters;
    private Map<String, String> headers;
    private String body;
    private Exception exception;

    public String getRoutingKey() {
        return method + " " + uri;
    }

    public HttpRequest(String rawRequest) {
        this.rawRequest = rawRequest;
        this.parse();
    }

    public String getParameter(String key) {
        return parameters.get(key);
    }

    public boolean containsParameter(String key) {
        return parameters.containsKey(key);
    }

    private void parse() {
        int startIndex = rawRequest.indexOf(' ');
        int endIndex = rawRequest.indexOf(' ', startIndex + 1);
        uri = rawRequest.substring(startIndex + 1, endIndex);
        method = HttpMethod.valueOf(rawRequest.substring(0, startIndex));
        parameters = new HashMap<>();
        if (uri.contains("?")) {
            String[] elements = uri.split("[?]");
            uri = elements[0];
            String[] keysValues = elements[1].split("[&]");
            for (String o : keysValues) {
                String[] keyValue = o.split("=");
                parameters.put(keyValue[0], keyValue[1]);
            }
        }
        headers = Arrays.stream(rawRequest.substring(rawRequest.indexOf("\r\n") + 2, rawRequest.indexOf("\r\n\r\n")).split("\r\n"))
                .collect(Collectors.toMap(
                        str -> str.substring(0, str.indexOf(':')),
                        str -> str.substring(str.indexOf(':') + 1).trim())
                );
        if (method == HttpMethod.POST) {
            this.body = rawRequest.substring(rawRequest.indexOf("\r\n\r\n") + 4);
        }
    }

    public void info() {
        LOGGER.info("rawRequest: {}", rawRequest);
        LOGGER.info("Method: {}", method);
        LOGGER.info("URI: {}", uri);
        LOGGER.info("Parameters: {}", parameters);
        LOGGER.info("Headers: {}", headers);
        LOGGER.info("Body: {}", body);
    }
}
