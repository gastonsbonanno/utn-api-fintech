CREATE DATABASE IF NOT EXISTS `utn_fintech` DEFAULT CHARACTER SET = 'utf8mb4' COLLATE = 'utf8mb4_unicode_ci';
USE `utn_fintech`;

CREATE TABLE IF NOT EXISTS `client`
(
    `id`                 BIGINT   NOT NULL AUTO_INCREMENT,
    `nombre`             VARCHAR(255)      DEFAULT NULL,
    `apellido`           VARCHAR(255)      DEFAULT NULL,
    `razon_social`       VARCHAR(255)      DEFAULT NULL,
    `documento`          VARCHAR(100)      DEFAULT NULL,
    `cuit`               VARCHAR(50)       DEFAULT NULL,
    `direccion`          VARCHAR(255)      DEFAULT NULL,
    `telefono`           VARCHAR(50)       DEFAULT NULL,
    `email`              VARCHAR(255)      DEFAULT NULL,
    `tipo_cliente`       VARCHAR(50)       DEFAULT NULL,
    `activo`             BOOLEAN  NOT NULL DEFAULT TRUE,
    `fecha_creacion`     DATETIME NULL,
    `fecha_modificacion` DATETIME NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `account`
(
    `account_id`         BIGINT   NOT NULL AUTO_INCREMENT,
    `client_id`          BIGINT   NOT NULL,
    `numero_cuenta`      VARCHAR(100)      DEFAULT NULL,
    `moneda`             VARCHAR(10)       DEFAULT NULL,
    `saldo`              DOUBLE            DEFAULT 0,
    `activo`             BOOLEAN  NOT NULL DEFAULT TRUE,
    `fecha_creacion`     DATETIME NULL,
    `fecha_modificacion` DATETIME NULL,
    PRIMARY KEY (`account_id`),
    INDEX `idx_account_client` (`client_id`),
    CONSTRAINT `fk_account_client` FOREIGN KEY (`client_id`) REFERENCES `client` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;


INSERT INTO `client` (`nombre`, `apellido`, `razon_social`, `documento`, `cuit`,
                      `direccion`, `telefono`, `email`, `tipo_cliente`, `activo`,
                      `fecha_creacion`)
VALUES ('María', 'González', '',
        '20-12345678-9', '',
        'Av. Siempre Viva 742', '1166660000', 'maria.gonzalez@example.com',
        'PERSONA_FISICA',
        TRUE,
        '2026-06-07 11:00:00');

INSERT INTO `client` (
    `nombre`, `apellido`, `razon_social`, `documento`, `cuit`,
    `direccion`, `telefono`, `email`, `tipo_cliente`, `activo`,
    `fecha_creacion`
) VALUES ('Alejando', 'Peréz', 'PerezConPan',
          '20-12345678-9', '123123123',
          'Av. Hola 123', '1123563425234', 'PerezConPan@example.com',
          'EMPRESA',
          TRUE,
          '2026-06-07 11:00:00');
