-- Использовать комментарии только с начала строки

CREATE SCHEMA IF NOT EXISTS otus_basic;

DROP TABLE IF EXISTS otus_basic.items CASCADE;

CREATE TABLE otus_basic.items (
    item_id  SERIAL          NOT NULL,
    title    TEXT            NOT NULL,
    price    DECIMAL(20, 6)  NOT NULL
);

ALTER TABLE ONLY otus_basic.items
    ADD CONSTRAINT pk_items PRIMARY KEY (item_id);

INSERT INTO otus_basic.items(title, price)
VALUES
('title1', 1),
('title2', 2),
('title3', 3),
('title4', 4),
('title5', 5),
('title6', 6),
('title7', 7),
('title8', 8),
('title9', 9),
('title10', 10),
('title11', 11),
('title12', 12),
('title13', 13),
('title14', 14),
('title15', 15);
