package com.rodiond26.overhellz.otus.basic.service.impl;

import com.rodiond26.overhellz.otus.basic.model.Item;
import com.rodiond26.overhellz.otus.basic.repository.ItemRepository;
import com.rodiond26.overhellz.otus.basic.repository.pagination.Page;
import com.rodiond26.overhellz.otus.basic.service.ItemService;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.rodiond26.overhellz.otus.basic.AppConstants.*;

@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    @Override
    public List<Item> findAll(Integer pageNum, Integer pageSize) {
        return itemRepository.findAll(createPage(pageNum, pageSize));
    }

    @Override
    public Optional<Item> findById(Long id) {
        if (id == null || id < 0) {
            return Optional.empty();
        }
        return itemRepository.findById(id);
    }

    @Override
    public List<Item> findByTitle(String title, Integer pageNum, Integer pageSize) {
        if (title == null || title.isBlank()) {
            return Collections.emptyList();
        }
        return itemRepository.findByTitle(title.trim(), createPage(pageNum, pageSize));
    }

    @Override
    public List<Item> findByPrice(BigDecimal min, BigDecimal max, Integer pageNum, Integer pageSize) {
        if (min == null || min.compareTo(MIN_PRICE) < 0) {
            min = MIN_PRICE;
        }
        if (max == null || max.compareTo(MAX_PRICE) > 0) {
            max = MAX_PRICE;
        }
        if (min.compareTo(max) > 0) {
            return Collections.emptyList();
        }
        return itemRepository.findByPrice(min, max, createPage(pageNum, pageSize));
    }

    @Override
    public List<Item> findByTitleAndPrice(String title, BigDecimal min, BigDecimal max, Integer pageNum, Integer pageSize) {
        if (title == null || title.isBlank()) {
            return Collections.emptyList();
        }
        if (min == null || min.compareTo(MIN_PRICE) < 0) {
            min = MIN_PRICE;
        }
        if (max == null || max.compareTo(MAX_PRICE) > 0) {
            max = MAX_PRICE;
        }
        if (min.compareTo(max) > 0) {
            return Collections.emptyList();
        }
        return itemRepository.findByTitleAndPrice(title, min, max, createPage(pageNum, pageSize));
    }

    private Page createPage(Integer pageNum, Integer pageSize) {
        if (pageNum == null || pageNum < DEFAULT_PAGE) {
            pageNum = DEFAULT_PAGE;
        }
        if (pageSize == null || pageSize < 0 || pageSize > MAX_PAGE_SIZE) {
            pageSize = MAX_PAGE_SIZE;
        }
        return new Page(pageNum, pageSize);
    }
}
