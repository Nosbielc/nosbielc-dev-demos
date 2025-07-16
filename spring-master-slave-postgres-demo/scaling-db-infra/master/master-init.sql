-- master-init.sql
-- Criar usuário de replicação
CREATE USER replicator WITH REPLICATION ENCRYPTED PASSWORD 'repl_password123';

CREATE TABLE IF NOT EXISTS users (
     id BIGSERIAL PRIMARY KEY,
     username VARCHAR(100) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

CREATE TABLE IF NOT EXISTS products (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    price DECIMAL(10,2) NOT NULL,
    stock_quantity INTEGER DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

CREATE TABLE IF NOT EXISTS orders (
      id BIGSERIAL PRIMARY KEY,
      user_id BIGINT REFERENCES users(id),
    total_amount DECIMAL(12,2) NOT NULL,
    status VARCHAR(50) DEFAULT 'PENDING',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

-- Inserir dados de exemplo
INSERT INTO users (username, email)
VALUES ('john_doe', 'john@example.com'),
       ('jane_smith', 'jane@example.com'),
       ('bob_wilson', 'bob@example.com');

INSERT INTO products (name, description, price, stock_quantity)
VALUES ('Laptop Dell XPS', 'High-performance laptop', 1299.99, 10),
       ('Mouse Logitech', 'Wireless mouse', 29.99, 50),
       ('Keyboard Mechanical', 'RGB mechanical keyboard', 89.99, 25);

INSERT INTO orders (user_id, total_amount, status)
VALUES (1, 1329.98, 'COMPLETED'),
       (2, 29.99, 'PENDING'),
       (3, 119.98, 'PROCESSING');

-- Criar índices para performance
CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_products_name ON products(name);
CREATE INDEX idx_orders_user_id ON orders(user_id);
CREATE INDEX idx_orders_status ON orders(status);