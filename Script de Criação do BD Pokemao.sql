-- criando o banco de dados
CREATE DATABASE bd_pokemao;

-- selecionando o banco de dados que será utilizado
USE bd_pokemao;

-- criando a tabela pokemao_catalogo
CREATE TABLE pokemao_catalogo(
id_pokemao_catalogo BIGINT UNIQUE NOT NULL,
emoji VARCHAR(15) UNIQUE NOT NULL,
nome VARCHAR(50) UNIQUE NOT NULL,
ataque INT NOT NULL,
defesa INT NOT NULL,
hp INT NOT NULL,
raridade INT NOT NULL,
descricao VARCHAR(100) NOT NULL,
PRIMARY KEY(id_pokemao_catalogo)
);

-- criando a tabela de usuarios (treinadores)
CREATE TABLE treinador(
id_treinador BIGINT UNIQUE NOT NULL AUTO_INCREMENT,
usuario VARCHAR(20) UNIQUE NOT NULL,
senha VARCHAR(20) NOT NULL,
nome VARCHAR(50) NOT NULL,
nascimento TIMESTAMP NOT NULL,
PRIMARY KEY(id_treinador)
);

-- criando a tabela de pokemaos do mundo
CREATE TABLE pokemao_treinador(
id_pokemao BIGINT UNIQUE NOT NULL AUTO_INCREMENT,
velocidade_ataque INT NOT NULL,
ataque INT NOT NULL,
defesa INT NOT NULL,
disponivel_para_troca BOOL NOT NULL,
xp DOUBLE NOT NULL,
data_captura TIMESTAMP NOT NULL,
id_pokemao_catalogo BIGINT NOT NULL,
id_treinador BIGINT NOT NULL,
nome_custom VARCHAR(20),
PRIMARY KEY(id_pokemao),
FOREIGN KEY(id_pokemao_catalogo) REFERENCES pokemao_catalogo(id_pokemao_catalogo),
FOREIGN KEY(id_treinador) REFERENCES treinador(id_treinador)
);

-- inserindo um registro dentro da tabela TREINADORES
INSERT INTO treinador VALUES(null, 'bonachao', '12345', 'Diogo', '2003-11-12 15:00:00');

INSERT INTO pokemao_catalogo VALUES(1, '🤔', 'Pensante', 10, 20, 5, 2, 'Ele pensa e isso o deixa sempre na defensiva. É um tanque.')