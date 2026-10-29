package torneos.modeloPresentacion;

import java.util.List;
import torneos.dtos.ComprobanteDTO;
import torneos.dtos.ParticipanteDTO;
import torneos.dtos.TorneoDTO;

/**
 * Consultas que las vistas hacen al modelo de presentación.
 */
public interface IConsultaModeloPresentacion {

    List<TorneoDTO> getTorneos();

    TorneoDTO getTorneoSeleccionado();

    List<ParticipanteDTO> getCoincidencias();

    ParticipanteDTO getParticipanteSeleccionado();

    ComprobanteDTO getComprobante();

    String getMensaje();

    void agregarObservador(IObservadorPresentacion observador);

    void eliminarObservador(IObservadorPresentacion observador);
}
