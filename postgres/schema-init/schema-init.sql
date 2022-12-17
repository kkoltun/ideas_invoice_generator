CREATE TABLE invoice
(
    id             SERIAL PRIMARY KEY,
    invoice_date   DATE           NOT NULL,
    due_date       DATE           NOT NULL,
    invoice_number VARCHAR(255)   NOT NULL UNIQUE,
    vendor_id      INTEGER        NOT NULL,
    debtor_id      INTEGER        NOT NULL,
    description    VARCHAR(255)   NOT NULL,
    unit_amount    NUMERIC(19, 6) NOT NULL,
    unit_name      VARCHAR(55)    NOT NULL,
    unit_net_price NUMERIC(19, 2) NOT NULL,
    invoice_amount NUMERIC(19, 2) NOT NULL,
    usd_pln_rate   NUMERIC(19, 4),
    vat_rate       NUMERIC(19, 2),
    created        TIMESTAMP      NOT NULL,
    lastupdated    TIMESTAMP      NOT NULL
);

CREATE TABLE international_invoice
(
    id               SERIAL PRIMARY KEY,
    invoice_date     DATE           NOT NULL,
    due_date         DATE           NOT NULL,
    invoice_number   VARCHAR(255)   NOT NULL UNIQUE,
    vendor_id        INTEGER        NOT NULL,
    debtor_id        INTEGER        NOT NULL,
    description      VARCHAR(255)   NOT NULL,
    description_pl   VARCHAR(255)   NOT NULL,
    unit_amount      NUMERIC(19, 6) NOT NULL,
    unit_name        VARCHAR(55)    NOT NULL,
    unit_net_price   NUMERIC(19, 2) NOT NULL,
    invoice_amount   NUMERIC(19, 2) NOT NULL,
    usd_pln_rate     NUMERIC(19, 4),
    vat_rate         NUMERIC(19, 2),
    nbp_table_number VARCHAR(255),
    nbp_table_date   DATE           NOT NULL,
    created          TIMESTAMP      NOT NULL,
    lastupdated      TIMESTAMP      NOT NULL
);

CREATE TABLE company
(
    id                  SERIAL PRIMARY KEY,
    name                VARCHAR(255) NOT NULL,
    address_line_1      VARCHAR(255),
    address_line_2      VARCHAR(255),
    address_line_3      VARCHAR(255),
    registration_number VARCHAR(255) NOT NULL,
    created             TIMESTAMP    NOT NULL,
    lastupdated         TIMESTAMP    NOT NULL
);