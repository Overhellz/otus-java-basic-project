package com.rodiond26.overhellz.otus.basic.repository;

import com.rodiond26.overhellz.otus.basic.model.Item;
import com.rodiond26.overhellz.otus.basic.repository.pagination.Page;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ItemRepository {

    List<Item> findAll(Page page);

    Optional<Item> findById(Long id);

    List<Item> findByTitle(String title, Page page);

    List<Item> findByPrice(BigDecimal min, BigDecimal max, Page page);

    List<Item> findByTitleAndPrice(String title, BigDecimal min, BigDecimal max, Page page);

    boolean create(Item item);

    boolean update(Item item);

    boolean deleteById(Long id);
}
