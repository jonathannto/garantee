-- -----------------------------------------------------
-- Table `Categoria`
-- -----------------------------------------------------
CREATE TABLE categoria (
  codigo_categoria INT NOT NULL AUTO_INCREMENT,
  nome VARCHAR(45) NOT NULL,
  PRIMARY KEY (codigo_categoria))
ENGINE = InnoDB 
DEFAULT CHARSET=utf8;

INSERT INTO categoria (nome) values ('smartfone');
INSERT INTO categoria (nome) values ('tablet'); 
INSERT INTO categoria (nome) values ('smartwatch');
INSERT INTO categoria (nome) values ('fone de ouvido');
INSERT INTO categoria (nome) values ('carregador');