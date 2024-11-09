package com.rodiond26.overhellz.otus.basic.repository.script;

public final class ItemCrudSqlScript {

    private ItemCrudSqlScript() {
    }

    public static final String SELECT_ITEMS_WITH_LIMIT_AND_OFFSET = """
            SELECT item_id, title, price
              FROM otus_basic.items
             ORDER BY title, price
             LIMIT ?
            OFFSET ?
            """;

    public static final String SELECT_ITEM_BY_ID = """
            SELECT item_id, title, price
              FROM otus_basic.items
             WHERE item_id = ?
            """;

    public static final String SELECT_ITEM_BY_TITLE = """
            SELECT item_id, title, price
              FROM otus_basic.items
             WHERE title LIKE '%' + ? + '%'
            """;

    public static final String SELECT_ITEM_BY_PRICE = """
            SELECT item_id, title, price
              FROM otus_basic.items
             WHERE price BETWEEN ? AND ?
            """;

    public static final String SELECT_ITEM_BY_TITLE_AND_PRICE = """
            SELECT item_id, title, price
              FROM otus_basic.items
             WHERE title LIKE '%' + ? + '%'
               AND price BETWEEN ? AND ?
            """;

    public static final String INSERT_ITEM = """
            INSERT INTO otus_basic.items(title, price)
            VALUES (?, ?)
            """;

    public static final String UPDATE_ITEM_BY_ID = """
            UPDATE otus_basic.items
               SET title = ?, price = ?
             WHERE item_id = ?
            """;

    public static final String DELETE_ITEM_BY_ID = """
            DELETE FROM otus_basic.items
             WHERE item_id = ?
            """;
}
