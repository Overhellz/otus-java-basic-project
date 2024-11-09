package com.rodiond26.overhellz.otus.basic.http.processor;

import com.google.gson.Gson;
import com.rodiond26.overhellz.otus.basic.http.HttpRequest;
import com.rodiond26.overhellz.otus.basic.model.Item;
import com.rodiond26.overhellz.otus.basic.service.ItemService;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

public class GetAllItemsProcessor implements RequestProcessor {

    private ItemService itemService;

    public GetAllItemsProcessor(ItemService itemService) {
        this.itemService = itemService;
    }

    @Override
    public void execute(HttpRequest request, OutputStream output) throws IOException {
        Integer pageNum = getPageNum(request.getParameters());
        Integer pageSize = getPageSize(request.getParameters());
        BigDecimal minPrice = getMinPrice(request.getParameters());
        BigDecimal maxPrice = getMaxPrice(request.getParameters());
        String title = getTitle(request.getParameters());
        List<Item> items;

        if (title != null && (minPrice != null || maxPrice != null)) {
            items = itemService.findByTitleAndPrice(title, minPrice, maxPrice, pageNum, pageSize);
        } else if (title == null && (minPrice != null || maxPrice != null)) {
            items = itemService.findByPrice(minPrice, maxPrice, pageNum, pageSize);
        } else if (title != null) {
            items = itemService.findByTitle(title, pageNum, pageSize);
        } else {
            items = itemService.findAll(pageNum, pageSize);
        }

        Gson gson = new Gson();
        String itemsJson = gson.toJson(items);

        String response = "" +
                "HTTP/1.1 200 OK\r\n" +
                "Content-Type: application/json\r\n" +
                "\r\n" +
                itemsJson;
        output.write(response.getBytes(StandardCharsets.UTF_8));
    }

    private Integer getPageNum(Map<String, String> parameters) {
        try {
            return Integer.valueOf(parameters.get("page"));
        } catch (Exception e) {
            // TODO fix
            return null;
        }
    }

    private Integer getPageSize(Map<String, String> parameters) {
        try {
            return Integer.valueOf(parameters.get("size"));
        } catch (Exception e) {
            // TODO fix
            return null;
        }
    }

    private BigDecimal getMinPrice(Map<String, String> parameters) {
        try {
            return BigDecimal.valueOf(Double.parseDouble(parameters.get("minPrice")));
        } catch (Exception e) {
            // TODO fix
            return null;
        }
    }

    private BigDecimal getMaxPrice(Map<String, String> parameters) {
        try {
            return BigDecimal.valueOf(Double.parseDouble(parameters.get("maxPrice")));
        } catch (Exception e) {
            // TODO fix
            return null;
        }
    }

    private String getTitle(Map<String, String> parameters) {
        try {
            return parameters.get("title").trim();
        } catch (Exception e) {
            // TODO fix
            return null;
        }
    }
}
