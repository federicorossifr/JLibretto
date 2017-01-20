DROP DATABASE IF EXISTS prg;
CREATE DATABASE prg;
USE prg;
DROP TABLE IF EXISTS esame;
DROP TABLE IF EXISTS esami;
CREATE TABLE IF NOT EXISTS esami (
	codiceEsame INTEGER PRIMARY KEY AUTO_INCREMENT,
	nome varchar(55),
	crediti integer,
	caratterizzante boolean DEFAULT 0
);

CREATE TABLE IF NOT EXISTS esame (
	id INTEGER PRIMARY KEY AUTO_INCREMENT,
	codiceEsame INTEGER UNIQUE,
	valutazione INTEGER,
	data DATE,
	FOREIGN KEY(codiceEsame) REFERENCES esami(codiceEsame)
);

INSERT INTO esami(nome,crediti,caratterizzante) VALUES 	
										("Reti Logiche",9,1),
										("Calcolatori Elettronici",9,1),
										("Fondamenti Informatica 2",12,1),
										("Progettazione WEB",6,1),
										("Fondamenti Informatica 1",12,1),
										("Analisi 1",12,0),
										("Analisi 2",12,0),
										("Fisica Generale",12,0);
										

