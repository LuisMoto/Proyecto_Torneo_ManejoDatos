CREATE DATABASE IF NOT EXISTS torneo_ajedrez;
USE torneo_ajedrez;

-- L.M. | Elimino Nivel del orden de borrado porque decidimos quitar esa tabla
--        ya que el emparejamiento será libre vs libre y no me acordaba.
DROP TABLE IF EXISTS Partida;
DROP TABLE IF EXISTS Jugador;
DROP TABLE IF EXISTS Torneo;
DROP VIEW  IF EXISTS Vista_Ranking;

-- L.M. | Cambié fecha_inicio de DATE a DATETIME porque antes duraba días
-- 			no días, así que necesito registrar hora de inicio también.
-- L.M. | Renombré duracion a duracion_minutos para que no haya más problemas en las 
--    	  Por ende, se necesita revisar el Calendar add de las validaciones.
CREATE TABLE Torneo (
    id_torneo        INT          AUTO_INCREMENT PRIMARY KEY,
    nombre_torneo    VARCHAR(100) NOT NULL,
    tipo             VARCHAR(50)  NOT NULL,
    fecha_inicio     DATETIME     NOT NULL,
    duracion_minutos INT          NOT NULL
);

-- L.M. | Quité id_nivel y la FK a Nivel
--        El emparejamiento es libre.
-- L.M. | Cambié puntaje_acumulado de INT a DECIMAL(4,1) para poder 
--        almacenar medios puntos (0.5) porque no me acordé de las Tablas.
CREATE TABLE Jugador (
    id_jugador        INT          AUTO_INCREMENT PRIMARY KEY,
    nombre_jugador    VARCHAR(100) NOT NULL,
    puntaje_acumulado DECIMAL(4,1) NOT NULL DEFAULT 0
);

-- L.M. | Cambié fecha de DATE a DATETIME, para el cambio de tiempo en Torneo.
--        Así puedo validar correctamente si la partida cae dentro del rango
--        horario del torneo (lógica que usa Nathalia en validarFechas()).
-- L.M. | Cambié resultado a ENUM para restringir los únicos tres resultados
--        válidos: ganan blancas, ganan negras o tablas.
-- L.M. | Cambié puntaje_obtenido de INT a DECIMAL(4,1) por la misma razón
--        que puntaje_acumulado: las tablas otorgan 0.5 puntos.
-- L.M. | Agregué el CHECK chk_jugadores
CREATE TABLE Partida (
    id_partida          INT          AUTO_INCREMENT PRIMARY KEY,
    fecha               DATETIME     NOT NULL,
    resultado           ENUM('1-0', '0-1', '1/2-1/2'),
    puntaje_blancas     DECIMAL(4,1),
    puntaje_negras      DECIMAL(4,1),
    id_torneo           INT          NOT NULL,
    jugador_blancas     INT          NOT NULL,
    jugador_negras      INT          NOT NULL,
    CONSTRAINT fk_torneo   FOREIGN KEY (id_torneo)       REFERENCES Torneo(id_torneo),
    CONSTRAINT fk_blancas  FOREIGN KEY (jugador_blancas) REFERENCES Jugador(id_jugador),
    CONSTRAINT fk_negras   FOREIGN KEY (jugador_negras)  REFERENCES Jugador(id_jugador),
    CONSTRAINT chk_jugadores CHECK (jugador_blancas <> jugador_negras)
);

-- L.M. | La vista se mantiene igual en lógica, pero ahora puntaje_acumulado
--        es DECIMAL, así que el ranking puede mostrar valores como 3.5, 2.0, etc.
-- L.M. | No me di cuenta que la el puntaje quedaba ambiguo, ya también lo corregí.
CREATE VIEW Vista_Ranking AS
SELECT nombre_jugador, puntaje_acumulado
FROM Jugador
ORDER BY puntaje_acumulado DESC;