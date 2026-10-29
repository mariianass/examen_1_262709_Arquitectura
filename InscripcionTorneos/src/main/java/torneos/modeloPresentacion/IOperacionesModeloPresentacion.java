package torneos.modeloPresentacion;

/**
 * Operaciones que el controlador solicita al modelo de presentación.
 */
public interface IOperacionesModeloPresentacion {

    void iniciar();

    void seleccionarTorneo(String idTorneo);

    void iniciarInscripcion();

    void buscarParticipantes(String idParcial);

    void seleccionarParticipante(String idParticipante);

    void confirmarInscripcion();

    void cancelar();
}
