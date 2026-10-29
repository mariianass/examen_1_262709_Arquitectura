package torneos.modeloNegocios.objetosNegocio;

import java.util.ArrayList;
import java.util.List;

/**
 * Conoce a los participantes dados de alta en el sistema.
 */
public class PadronParticipantes {

    private final List<Participante> participantes = new ArrayList<>();

    public void agregarParticipante(Participante participante) {
        participantes.add(participante);
    }

    public List<Participante> buscarPorIdParcial(String idParcial) {
        List<Participante> coincidencias = new ArrayList<>();
        for (Participante participante : participantes) {
            if (participante.coincideConId(idParcial)) {
                coincidencias.add(participante);
            }
        }
        return coincidencias;
    }

    public Participante buscarPorId(String idParticipante) {
        for (Participante participante : participantes) {
            if (participante.getId().equals(idParticipante)) {
                return participante;
            }
        }
        return null;
    }
}
