package torneos.modeloPresentacion;

import torneos.modeloNegocios.IOperacionesModeloNegocios;

/**
 * Recibe lo que delega la fachada de presentación y lo solicita al modelo de negocios.
 */
public class ControlModeloPresentacion {

    private final IOperacionesModeloNegocios operacionesNegocio;

    public ControlModeloPresentacion(IOperacionesModeloNegocios operacionesNegocio) {
        this.operacionesNegocio = operacionesNegocio;
    }

    public void iniciar() {
        operacionesNegocio.iniciar();
    }

    public void seleccionarTorneo(String idTorneo) {
        operacionesNegocio.seleccionarTorneo(idTorneo);
    }

    public void iniciarInscripcion() {
        operacionesNegocio.iniciarInscripcion();
    }

    public void buscarParticipantes(String idParcial) {
        operacionesNegocio.buscarParticipantes(idParcial);
    }

    public void seleccionarParticipante(String idParticipante) {
        operacionesNegocio.seleccionarParticipante(idParticipante);
    }

    public void confirmarInscripcion() {
        operacionesNegocio.confirmarInscripcion();
    }

    public void cancelar() {
        operacionesNegocio.cancelar();
    }
}
