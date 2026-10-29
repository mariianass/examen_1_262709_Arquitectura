package torneos.modeloPresentacion;

import java.util.ArrayList;
import java.util.List;
import torneos.dtos.ComprobanteDTO;
import torneos.dtos.ParticipanteDTO;
import torneos.dtos.TorneoDTO;
import torneos.modeloNegocios.EventoModelo;
import torneos.modeloNegocios.IConsultaModeloNegocios;
import torneos.modeloNegocios.IObservadorModeloNegocios;

/**
 * Observa el modelo de negocios. Cuando ocurre un evento, consulta lo que cambió,
 * lo traduce a DTOs, lo guarda y avisa a las vistas con el evento de presentación.
 */
public class ReceptorEventosNegocio implements IObservadorModeloNegocios {

    private final IConsultaModeloNegocios consultaNegocio;
    private final TraductorModeloNegocios traductor;
    private final List<IObservadorPresentacion> observadores = new ArrayList<>();

    private List<TorneoDTO> torneos = new ArrayList<>();
    private TorneoDTO torneoSeleccionado;
    private List<ParticipanteDTO> coincidencias = new ArrayList<>();
    private ParticipanteDTO participanteSeleccionado;
    private ComprobanteDTO comprobante;
    private String mensaje;

    public ReceptorEventosNegocio(IConsultaModeloNegocios consultaNegocio, TraductorModeloNegocios traductor) {
        this.consultaNegocio = consultaNegocio;
        this.traductor = traductor;
    }

    @Override
    public void actualizar(EventoModelo evento) {
        switch (evento) {
            case PROCESO_REINICIADO:
                torneos = traductor.aTorneosDTO(consultaNegocio.getTorneos());
                torneoSeleccionado = null;
                coincidencias = new ArrayList<>();
                participanteSeleccionado = null;
                comprobante = null;
                mensaje = null;
                notificarObservadores(EventoPresentacion.INSCRIPCION_REINICIADA);
                break;
            case TORNEO_SELECCIONADO:
                torneoSeleccionado = traductor.aTorneoDTO(consultaNegocio.getTorneoSeleccionado());
                mensaje = consultaNegocio.getMensaje();
                notificarObservadores(EventoPresentacion.DETALLES_ACTUALIZADOS);
                break;
            case CAPTURA_INICIADA:
                coincidencias = new ArrayList<>();
                mensaje = null;
                notificarObservadores(EventoPresentacion.CAPTURA_INICIADA);
                break;
            case COINCIDENCIAS_ACTUALIZADAS:
                coincidencias = traductor.aParticipantesDTO(consultaNegocio.getCoincidencias());
                mensaje = consultaNegocio.getMensaje();
                notificarObservadores(EventoPresentacion.COINCIDENCIAS_ACTUALIZADAS);
                break;
            case PARTICIPANTE_SELECCIONADO:
                participanteSeleccionado = traductor.aParticipanteDTO(consultaNegocio.getParticipanteSeleccionado());
                mensaje = consultaNegocio.getMensaje();
                notificarObservadores(EventoPresentacion.CONFIRMACION_SOLICITADA);
                break;
            case INSCRIPCION_REGISTRADA:
                comprobante = traductor.aComprobanteDTO(consultaNegocio.getInscripcionRegistrada());
                torneos = traductor.aTorneosDTO(consultaNegocio.getTorneos());
                torneoSeleccionado = traductor.aTorneoDTO(consultaNegocio.getTorneoSeleccionado());
                mensaje = null;
                notificarObservadores(EventoPresentacion.COMPROBANTE_GENERADO);
                break;
            default:
                break;
        }
    }

    public void agregarObservador(IObservadorPresentacion observador) {
        observadores.add(observador);
    }

    public void eliminarObservador(IObservadorPresentacion observador) {
        observadores.remove(observador);
    }

    private void notificarObservadores(EventoPresentacion evento) {
        for (IObservadorPresentacion observador : observadores) {
            observador.actualizar(evento);
        }
    }

    public List<TorneoDTO> getTorneos() { return torneos; }
    public TorneoDTO getTorneoSeleccionado() { return torneoSeleccionado; }
    public List<ParticipanteDTO> getCoincidencias() { return coincidencias; }
    public ParticipanteDTO getParticipanteSeleccionado() { return participanteSeleccionado; }
    public ComprobanteDTO getComprobante() { return comprobante; }
    public String getMensaje() { return mensaje; }
}
