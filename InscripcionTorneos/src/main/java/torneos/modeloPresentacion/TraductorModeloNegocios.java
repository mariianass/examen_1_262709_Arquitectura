package torneos.modeloPresentacion;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import torneos.dtos.ComprobanteDTO;
import torneos.dtos.ParticipanteDTO;
import torneos.dtos.TorneoDTO;
import torneos.modeloNegocios.objetosNegocio.Inscripcion;
import torneos.modeloNegocios.objetosNegocio.Participante;
import torneos.modeloNegocios.objetosNegocio.Torneo;

/**
 * Convierte los objetos de negocio en DTOs para la vista.
 */
public class TraductorModeloNegocios {

    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public TorneoDTO aTorneoDTO(Torneo torneo) {
        if (torneo == null) {
            return null;
        }
        return new TorneoDTO(torneo.getId(), torneo.getNombre(), torneo.getDisciplina(),
                torneo.getFecha().format(FORMATO_FECHA), torneo.getCategoria(), torneo.getLugar(),
                torneo.getCostoInscripcion(), torneo.getLugaresDisponibles());
    }

    public List<TorneoDTO> aTorneosDTO(List<Torneo> torneos) {
        List<TorneoDTO> dtos = new ArrayList<>();
        for (Torneo torneo : torneos) {
            dtos.add(aTorneoDTO(torneo));
        }
        return dtos;
    }

    public ParticipanteDTO aParticipanteDTO(Participante participante) {
        if (participante == null) {
            return null;
        }
        return new ParticipanteDTO(participante.getId(), participante.getNombreCompleto(),
                participante.getEdad(), participante.getCategoria(), participante.getEquipo());
    }

    public List<ParticipanteDTO> aParticipantesDTO(List<Participante> participantes) {
        List<ParticipanteDTO> dtos = new ArrayList<>();
        for (Participante participante : participantes) {
            dtos.add(aParticipanteDTO(participante));
        }
        return dtos;
    }

    public ComprobanteDTO aComprobanteDTO(Inscripcion inscripcion) {
        if (inscripcion == null) {
            return null;
        }
        Torneo torneo = inscripcion.getTorneo();
        return new ComprobanteDTO(inscripcion.getParticipante().getNombreCompleto(), torneo.getNombre(),
                torneo.getCategoria(), torneo.getFecha().format(FORMATO_FECHA), torneo.getLugar(),
                torneo.getCostoInscripcion());
    }
}
