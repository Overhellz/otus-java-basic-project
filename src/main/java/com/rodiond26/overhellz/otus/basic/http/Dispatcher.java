package com.rodiond26.overhellz.otus.basic.http;

import com.rodiond26.overhellz.otus.basic.http.processor.GetAllItemsProcessor;
import com.rodiond26.overhellz.otus.basic.http.processor.RequestProcessor;
import com.rodiond26.overhellz.otus.basic.repository.ItemRepository;
import com.rodiond26.overhellz.otus.basic.service.ItemService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class Dispatcher {

    private static final Logger LOGGER = LogManager.getLogger(Dispatcher.class.getName());

    private Map<String, RequestProcessor> processors;
    private RequestProcessor defaultNotFoundProcessor;
    private RequestProcessor defaultInternalServerErrorProcessor;
    private RequestProcessor defaultBadRequestProcessor;

    private ItemService itemService;

    public Dispatcher(ItemService itemService) {
        this.itemService = itemService;

        this.processors = new HashMap<>();
        this.processors.put("GET /items", new GetAllItemsProcessor(itemService));
        this.processors.put("POST /items", new CreateNewItemsProcessor(itemRepository));

        this.defaultNotFoundProcessor = new DefaultNotFoundProcessor();
        this.defaultInternalServerErrorProcessor = new DefaultInternalServerErrorProcessor();
        this.defaultBadRequestProcessor = new DefaultBadRequestProcessor();
    }

    public void execute(HttpRequest request, OutputStream out) throws IOException {
        try {
            LOGGER.debug("Пришел запрос: {}", request);
            if (!processors.containsKey(request.getRoutingKey())) {
                defaultNotFoundProcessor.execute(request, out);
                return;
            }
            processors.get(request.getRoutingKey()).execute(request, out);
        } catch (BadRequestException e) {
            LOGGER.error("Ошибка Bad Request при запросе {}: {} = {}", request, e.getCause(), e.getStackTrace());
            request.setException(e);
            defaultBadRequestProcessor.execute(request, out);
        } catch (Exception e) {
            LOGGER.error("Ошибка при запросе {}: {} = {}", request, e.getCause(), e.getStackTrace());
            defaultInternalServerErrorProcessor.execute(request, out);
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
}
