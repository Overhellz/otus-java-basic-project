package com.rodiond26.overhellz.otus.basic;

import java.math.BigDecimal;

public final class AppConstants {
    private AppConstants() {
    }

    public static final int MAX_PAGE_SIZE = 10;
    public static final int DEFAULT_PAGE = 1;

    public static final BigDecimal MIN_PRICE = BigDecimal.valueOf(0L);
    public static final BigDecimal MAX_PRICE = BigDecimal.valueOf(1_000_000_000L);
}
