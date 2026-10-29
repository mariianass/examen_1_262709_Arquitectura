package torneos.modeloPresentacion;

import java.util.List;
import torneos.dtos.ComprobanteDTO;
import torneos.dtos.ParticipanteDTO;
import torneos.dtos.TorneoDTO;

/**
 * Punto de entrada al modelo de presentación: las operaciones las delega al control
 * y las consultas al receptor, que es quien guarda el estado que ven las vistas.
 */
public class FachadaModeloPresentacion implements IOperacionesModeloPresentacion, IConsultaModeloPresentacion {

    private final ControlModeloPresentacion control;
    private final ReceptorEventosNegocio receptor;

    public FachadaModeloPresentacion(ControlModeloPresentacion control, ReceptorEventosNegocio receptor) {
        this.control = control;
        this.receptor = receptor;
    }

    // ---------------------------------------------------------- operaciones

    @Override
    public void iniciar() {
        control.iniciar();
    }

    @Override
    public void seleccionarTorneo(String idTorneo) {
        control.seleccionarTorneo(idTorneo);
    }

    @Override
    public void iniciarInscripcion() {
        control.iniciarInscripcion();
    }

    @Override
    public void buscarParticipantes(String idParcial) {
        control.buscarParticipantes(idParcial);
    }

    @Override
    public void seleccionarParticipante(String idParticipante) {
        control.seleccionarParticipante(idParticipante);
    }

    @Override
    public void confirmarInscripcion() {
        control.confirmarInscripcion();
    }

    @Override
    public void cancelar() {
        control.cancelar();
    }

    // ---------------------------------------------------------- consultas

    @Override
    public List<TorneoDTO> getTorneos() {
        return receptor.getTorneos();
    }

    @Override
    public TorneoDTO getTorneoSeleccionado() {
        return receptor.getTorneoSeleccionado();
    }

    @Override
    public List<ParticipanteDTO> getCoincidencias() {
        return receptor.getCoincidencias();
    }

    @Override
    public ParticipanteDTO getParticipanteSeleccionado() {
        return receptor.getParticipanteSeleccionado();
    }

    @Override
    public ComprobanteDTO getComprobante() {
        return receptor.getComprobante();
    }

    @Override
    public String getMensaje() {
        return receptor.getMensaje();
    }

    @Override
    public void agregarObservador(IObservadorPresentacion observador) {
        receptor.agregarObservador(observador);
    }

    @Override
    public void eliminarObservador(IObservadorPresentacion observador) {
        receptor.eliminarObservador(observador);
    }
}
