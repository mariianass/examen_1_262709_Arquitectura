package torneos.controlador;

import torneos.modeloPresentacion.IOperacionesModeloPresentacion;

/**
 * Controlador del MVC: recibe las acciones del participante desde las vistas
 * y las solicita al modelo de presentación.
 */
public class ControlInscripcion {

    private final IOperacionesModeloPresentacion modelo;

    public ControlInscripcion(IOperacionesModeloPresentacion modelo) {
        this.modelo = modelo;
    }

    public void iniciar() {
        modelo.iniciar();
    }

    public void seleccionarTorneo(String idTorneo) {
        modelo.seleccionarTorneo(idTorneo);
    }

    public void iniciarInscripcion() {
        modelo.iniciarInscripcion();
    }

    public void buscarParticipantes(String idParcial) {
        modelo.buscarParticipantes(idParcial);
    }

    public void seleccionarParticipante(String idParticipante) {
        modelo.seleccionarParticipante(idParticipante);
    }

    public void confirmarInscripcion() {
        modelo.confirmarInscripcion();
    }

    public void cancelar() {
        modelo.cancelar();
    }
}
