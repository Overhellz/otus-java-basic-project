package com.rodiond26.overhellz.otus.basic.service;

import com.rodiond26.overhellz.otus.basic.model.Item;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ItemService {

    List<Item> findAll(Integer page, Integer size);

    Optional<Item> findById(Long id);

    List<Item> findByTitle(String title, Integer pageNum, Integer pageSize);

    List<Item> findByPrice(BigDecimal min, BigDecimal max, Integer pageNum, Integer pageSize);

    List<Item> findByTitleAndPrice(String title, BigDecimal min, BigDecimal max, Integer pageNum, Integer pageSize);
}
