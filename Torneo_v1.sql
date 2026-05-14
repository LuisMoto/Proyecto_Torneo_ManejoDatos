CREATE DATABASE IF NOT EXISTS torneo_ajedrez;
USE torneo_ajedrez;

DROP TABLE IF EXISTS Partida;
DROP TABLE IF EXISTS Jugador;
DROP TABLE IF EXISTS Torneo;
DROP TABLE IF EXISTS Nivel;
DROP VIEW IF EXISTS Vista_Ranking;

CREATE TABLE Nivel (
    id_nivel INT AUTO_INCREMENT PRIMARY KEY,
    nombre_nivel VARCHAR(100) NOT NULL,
    puntaje_minimo INT NOT NULL,
    puntaje_maximo INT NOT NULL
);

CREATE TABLE Torneo (
    id_torneo INT AUTO_INCREMENT PRIMARY KEY,
    nombre_torneo VARCHAR(100) NOT NULL,
    tipo VARCHAR(50) NOT NULL,
    fecha_inicio DATE NOT NULL,
    duracion INT NOT NULL
);

CREATE TABLE Jugador (
    id_jugador INT AUTO_INCREMENT PRIMARY KEY,
    nombre_jugador VARCHAR(100) NOT NULL,
    puntaje_acumulado INT NOT NULL,
    id_nivel INT NOT NULL,
    CONSTRAINT fk_nivel FOREIGN KEY (id_nivel) REFERENCES Nivel(id_nivel)
);

CREATE TABLE Partida (
    id_partida INT AUTO_INCREMENT PRIMARY KEY,
    fecha DATE NOT NULL,
    resultado VARCHAR(50),
    puntaje_obtenido INT,
    id_torneo INT NOT NULL,
    jugador_blancas INT NOT NULL,
    jugador_negras INT NOT NULL,
    CONSTRAINT fk_torneo FOREIGN KEY (id_torneo) REFERENCES Torneo(id_torneo),
    CONSTRAINT fk_blancas FOREIGN KEY (jugador_blancas) REFERENCES Jugador(id_jugador),
    CONSTRAINT fk_negras FOREIGN KEY (jugador_negras) REFERENCES Jugador(id_jugador)
);

CREATE VIEW Vista_Ranking AS
SELECT nombre_jugador, puntaje_acumulado
FROM Jugador
ORDER BY puntaje_acumulado DESC;