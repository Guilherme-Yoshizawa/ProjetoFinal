CREATE TABLE cliente (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  nome VARCHAR(120) NOT NULL,
  telefone VARCHAR(30),
  email VARCHAR(120),
  UNIQUE KEY uk_cliente_email (email)
);

CREATE TABLE pedido (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  cliente_id BIGINT NOT NULL,
  data_entrega DATETIME NOT NULL,
  criado_em DATETIME NOT NULL,
  status VARCHAR(20) NOT NULL,
  valor_total DECIMAL(10,2) NOT NULL DEFAULT 0.00,
  CONSTRAINT fk_pedido_cliente FOREIGN KEY (cliente_id) REFERENCES cliente(id)
);

CREATE TABLE item_pedido (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  pedido_id BIGINT NOT NULL,
  tipo VARCHAR(20) NOT NULL, -- BOLO ou DOCINHO
  sabor VARCHAR(80) NOT NULL,
  peso_kg INT NULL,          -- para bolo (1,2,3...)
  quantidade INT NULL,       -- para docinho (em unidades)
  preco_unitario DECIMAL(10,2) NOT NULL,
  subtotal DECIMAL(10,2) NOT NULL,
  CONSTRAINT fk_item_pedido_pedido FOREIGN KEY (pedido_id) REFERENCES pedido(id)
);