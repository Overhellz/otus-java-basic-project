package com.rodiond26.overhellz.otus.basic.repository.pagination;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Page {

    private Integer pageNum;
    private Integer pageSize;
}
