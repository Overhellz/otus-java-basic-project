package com.rodiond26.overhellz.otus.basic.http.processor;

import com.rodiond26.overhellz.otus.basic.http.HttpRequest;

import java.io.IOException;
import java.io.OutputStream;

public interface RequestProcessor {
    void execute(HttpRequest request, OutputStream output) throws IOException;
}
