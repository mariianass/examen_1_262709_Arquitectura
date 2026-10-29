package torneos.modeloNegocios.objetosNegocio;

import java.util.ArrayList;
import java.util.List;

/**
 * Conoce los torneos registrados en el sistema.
 */
public class CatalogoTorneos {

    private final List<Torneo> torneos = new ArrayList<>();

    public void agregarTorneo(Torneo torneo) {
        torneos.add(torneo);
    }

    public List<Torneo> obtenerTorneos() {
        return torneos;
    }

    public Torneo buscarPorId(String idTorneo) {
        for (Torneo torneo : torneos) {
            if (torneo.getId().equals(idTorneo)) {
                return torneo;
            }
        }
        return null;
    }
}
