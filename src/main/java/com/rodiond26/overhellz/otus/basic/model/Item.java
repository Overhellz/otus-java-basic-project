package com.rodiond26.overhellz.otus.basic.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Item {

    private Long id;
    private String title;
    private BigDecimal price;
}
