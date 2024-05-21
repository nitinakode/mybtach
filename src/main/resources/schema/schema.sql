CREATE TABLE IF NOT EXISTS order_transaction_status
  (
     id                     VARCHAR(36) NOT NULL,
     created_timestamp      DATETIME NULL,
     last_updated_timestamp DATETIME NULL,
     created_by             VARCHAR(50) NULL,
     updated_by             VARCHAR(50) NULL,
     message                TEXT NULL,
     reference_id           VARCHAR(1000) NULL,
     event_type             TEXT NULL,
     interface_type         VARCHAR(50) NOT NULL,
     source                 VARCHAR(50) NOT NULL,
     status                 VARCHAR(20) NOT NULL,
     CONSTRAINT pk_order_transaction_status PRIMARY KEY (id)
  )
engine=innodb;

CREATE TABLE IF NOT EXISTS order_transaction_data
  (
     id             BIGINT auto_increment NOT NULL,
     source           LONGTEXT NULL,
     destination          LONGTEXT NULL,
     transaction_id VARCHAR(255) NULL,
     CONSTRAINT pk_order_transaction_data PRIMARY KEY (id),
     CONSTRAINT fk_order_transaction_data_on_transaction FOREIGN KEY (
     transaction_id) REFERENCES order_transaction_status (id)
  )
  engine=innodb;

CREATE TABLE IF NOT EXISTS merge_release_details
(
    release_number         VARCHAR(50) NOT NULL,
    created_timestamp      DATETIME NULL,
    last_updated_timestamp DATETIME NULL,
    created_by             VARCHAR(50) NULL,
    updated_by             VARCHAR(50) NULL,
    order_id               VARCHAR(50) NOT NULL,
    to_po_number           VARCHAR(50) NOT NULL,
    transfer_type          VARCHAR(50) NOT NULL,
    delivery_type          VARCHAR(50) NOT NULL ,
    is_transfer_canceled   BIT(1) NULL,
    is_processed           BIT(1) NULL,
    CONSTRAINT pk_merge_release_details PRIMARY KEY (release_number)
)
    engine=innodb;