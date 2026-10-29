package torneos.modeloNegocios;

import java.util.List;
import torneos.modeloNegocios.objetosNegocio.Inscripcion;
import torneos.modeloNegocios.objetosNegocio.Participante;
import torneos.modeloNegocios.objetosNegocio.Torneo;

/**
 * Consultas que el modelo de negocios ofrece al modelo de presentación.
 */
public interface IConsultaModeloNegocios {

    List<Torneo> getTorneos();

    Torneo getTorneoSeleccionado();

    List<Participante> getCoincidencias();

    Participante getParticipanteSeleccionado();

    Inscripcion getInscripcionRegistrada();

    String getMensaje();
}
