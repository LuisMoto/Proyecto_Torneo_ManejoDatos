CREATE DATABASE IF NOT EXISTS torneo_ajedrez;
USE torneo_ajedrez;

DROP TABLE IF EXISTS Partida;
DROP TABLE IF EXISTS Jugador;
DROP TABLE IF EXISTS Torneo;
DROP VIEW  IF EXISTS Vista_Ranking;

CREATE TABLE Torneo (
    id_torneo        INT          AUTO_INCREMENT PRIMARY KEY,
    nombre_torneo    VARCHAR(100) NOT NULL,
    tipo             VARCHAR(50)  NOT NULL,
    fecha_inicio     DATETIME     NOT NULL,
    duracion_minutos INT          NOT NULL
);

CREATE TABLE Jugador (
    id_jugador        INT          AUTO_INCREMENT PRIMARY KEY,
    nombre_jugador    VARCHAR(100) NOT NULL,
    puntaje_acumulado DECIMAL(4,1) NOT NULL DEFAULT 0
);

CREATE TABLE Partida (
    id_partida INT AUTO_INCREMENT PRIMARY KEY,
    fecha DATETIME NOT NULL,
    resultado ENUM('1-0','0-1','1/2-1/2'),
    puntaje_blancas DECIMAL(4,1),
    puntaje_negras DECIMAL(4,1),
    estado VARCHAR(20) NOT NULL DEFAULT 'PENDIENTE',
    id_torneo INT NOT NULL,
    jugador_blancas INT NOT NULL,
    jugador_negras INT NOT NULL,
    CONSTRAINT fk_torneo FOREIGN KEY (id_torneo) REFERENCES Torneo(id_torneo),
    CONSTRAINT fk_blancas FOREIGN KEY (jugador_blancas) REFERENCES Jugador(id_jugador),
    CONSTRAINT fk_negras FOREIGN KEY (jugador_negras) REFERENCES Jugador(id_jugador),
    CONSTRAINT chk_jugadores CHECK (jugador_blancas <> jugador_negras)
);

CREATE VIEW Vista_Ranking AS
SELECT nombre_jugador, puntaje_acumulado
FROM Jugador
ORDER BY puntaje_acumulado DESC;
