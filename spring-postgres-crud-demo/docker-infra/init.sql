-- Inicialização do banco de dados
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Criação das tabelas (caso não existam, o Hibernate criará automaticamente)
-- Este arquivo é opcional, pois estamos usando ddl-auto: update

-- Alguns dados de exemplo para teste
-- INSERT INTO users (name, email, phone, active, created_at, updated_at) VALUES 
-- ('João Silva', 'joao@example.com', '11999999999', true, NOW(), NOW()),
-- ('Maria Santos', 'maria@example.com', '11888888888', true, NOW(), NOW());

-- INSERT INTO products (name, description, price, stock_quantity, active, created_at, updated_at) VALUES 
-- ('Notebook Dell', 'Notebook Dell Inspiron 15', 2500.00, 10, true, NOW(), NOW()),
-- ('Mouse Logitech', 'Mouse sem fio Logitech MX Master', 250.00, 50, true, NOW(), NOW());