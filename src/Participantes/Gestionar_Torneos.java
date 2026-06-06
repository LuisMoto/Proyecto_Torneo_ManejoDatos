package Participantes;

import Entidades.Torneo;
import java.util.ArrayList;
import java.util.List;

public class Gestionar_Torneos {

    private List<Torneo> torneos;

    public Gestionar_Torneos() {
        torneos = new ArrayList<>();
    }

    public void agregarTorneo(Torneo torneo) {
        torneos.add(torneo);
    }

    public int cantidadTorneos() {
        return torneos.size();
    }

    public Torneo buscarTorneo(int id) {

        for (Torneo t : torneos) {

            if (t.getIdTorneo() == id) {
                return t;
            }
        }

        return null;
    }

    public Torneo buscarTorneo(String nombre) {

        for (Torneo t : torneos) {

            if (t.getNombreTorneo().equalsIgnoreCase(nombre)) {
                return t;
            }
        }

        return null;
    }

    public void mostrarTorneos() {

        if (torneos.isEmpty()) {

            System.out.println("No hay torneos registrados.");
            return;
            
        }

        for (Torneo t : torneos) {
            System.out.println(t);
        }
    }

}
