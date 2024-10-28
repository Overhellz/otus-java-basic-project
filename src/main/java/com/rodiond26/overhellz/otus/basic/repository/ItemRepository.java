package com.rodiond26.overhellz.otus.basic.repository;

import com.rodiond26.overhellz.otus.basic.model.Item;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ItemRepository {

    List<Item> findAll(Integer offset, Integer limit);

    Optional<Item> findById(Long id);

    List<Item> findByTitle(String title);

    List<Item> findByPrice(BigDecimal min, BigDecimal max);

    List<Item> findByTitleAndPrice(String title, BigDecimal min, BigDecimal max);

    boolean create(Item item);

    boolean update(Item item);

    boolean deleteById(Long id);
}
