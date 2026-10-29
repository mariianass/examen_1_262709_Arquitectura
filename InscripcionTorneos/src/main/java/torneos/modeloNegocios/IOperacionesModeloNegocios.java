package torneos.modeloNegocios;

/**
 * Operaciones que cambian el estado del modelo de negocios.
 */
public interface IOperacionesModeloNegocios {

    void iniciar();

    void seleccionarTorneo(String idTorneo);

    void iniciarInscripcion();

    void buscarParticipantes(String idParcial);

    void seleccionarParticipante(String idParticipante);

    void confirmarInscripcion();

    void cancelar();
}
