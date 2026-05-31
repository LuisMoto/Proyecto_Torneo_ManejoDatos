CREATE DATABASE IF NOT EXISTS torneo_ajedrez;
USE torneo_ajedrez;

-- L.M. | Elimino Nivel del orden de borrado porque decidimos quitar esa tabla
--        ya que el emparejamiento será libre vs libre y no me acordaba.
DROP TABLE IF EXISTS Partida;
DROP TABLE IF EXISTS Jugador;
DROP TABLE IF EXISTS Torneo;
DROP VIEW  IF EXISTS Vista_Ranking;

-- L.M. | Cambié fecha_inicio de DATE a DATETIME porque un Torneo Arena
--        dura minutos/horas, no días, así que necesito registrar hora de inicio también.
-- L.M. | Renombré duracion a duracion_minutos para que no haya más ambigüedad.
--        Por ende, se necesita revisar el Calendar.add() en ValidadorArena.
CREATE TABLE Torneo (
    id_torneo        INT          AUTO_INCREMENT PRIMARY KEY,
    nombre_torneo    VARCHAR(100) NOT NULL,
    tipo             VARCHAR(50)  NOT NULL,
    fecha_inicio     DATETIME     NOT NULL,
    duracion_minutos INT          NOT NULL
);

-- L.M. | Quité id_nivel y la FK a Nivel porque acordamos con Nathalia
--        eliminar el sistema de niveles. El emparejamiento es libre.
-- L.M. | Cambié puntaje_acumulado de INT a DECIMAL(4,1) para poder
--        almacenar medios puntos (0.5) porque no me acordé de las Tablas.
-- L.M. | En Workbench tuve que quitar manualmente la FK fk_nivel y la columna
--        id_nivel de jugador antes de poder eliminar la tabla Nivel.
CREATE TABLE Jugador (
    id_jugador        INT          AUTO_INCREMENT PRIMARY KEY,
    nombre_jugador    VARCHAR(100) NOT NULL,
    puntaje_acumulado DECIMAL(4,1) NOT NULL DEFAULT 0
);

-- L.M. | Cambié fecha de DATE a DATETIME, consistente con el cambio en Torneo.
--        Así puedo validar correctamente si la partida cae dentro del rango
--        horario del torneo (lógica que usa Nathalia en validarFechas()).
-- L.M. | Cambié resultado a ENUM para restringir los únicos tres resultados
--        válidos en ajedrez: ganan blancas, ganan negras o tablas.
-- L.M. | Separé puntaje_obtenido en puntaje_blancas y puntaje_negras para
--        eliminar la ambigüedad de a quién pertenece el puntaje.
--        Ambos son DECIMAL(4,1) para soportar medios puntos (tablas = 0.5).
-- L.M. | Agregué el CHECK chk_jugadores para reforzar a nivel de base de datos
--        la validación que Nathalia hace en ValidadorArena.validarJugadorClonado().
-- L.M. | La actualización de puntaje_acumulado se hará en Java según lo que
--        acuerde el equipo con Nathalia (Semana 3). Por ahora la columna
--        queda lista con el tipo correcto (DECIMAL).
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
--        es DECIMAL, así que el ranking muestra valores como 3.5, 2.0, etc.
-- L.M. | El puntaje antes quedaba ambiguo (un solo campo puntaje_obtenido),
--        ya lo corregí separándolo en puntaje_blancas y puntaje_negras en Partida.
CREATE VIEW Vista_Ranking AS
SELECT nombre_jugador, puntaje_acumulado
FROM Jugador
ORDER BY puntaje_acumulado DESC;