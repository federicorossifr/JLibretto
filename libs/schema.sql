DROP DATABASE IF EXISTS prg;
CREATE DATABASE prg;

USE prg;

CREATE TABLE esame (
	id INTEGER PRIMARY KEY AUTO_INCREMENT,
	nome VARCHAR(50) NOT NULL,
	crediti INT NOT NULL,
	valutazione INT NOT NULL,
	data DATE NOT NULL,
	codiceUtente VARCHAR(255) NOT NULL
);
