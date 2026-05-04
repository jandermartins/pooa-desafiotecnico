CREATE TABLE IF NOT EXISTS contato (
                                       id SERIAL PRIMARY KEY,
                                       nome VARCHAR(255) NOT NULL,
                                       email VARCHAR(255) NOT NULL UNIQUE,
                                       telefone VARCHAR(50) NOT NULL,
                                       categoria VARCHAR(50) NOT NULL,
                                       dataCadastro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);