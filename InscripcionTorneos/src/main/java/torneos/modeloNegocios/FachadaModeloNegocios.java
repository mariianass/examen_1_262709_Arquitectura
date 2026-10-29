package torneos.modeloNegocios;

import java.util.List;
import torneos.modeloNegocios.objetosNegocio.Inscripcion;
import torneos.modeloNegocios.objetosNegocio.Participante;
import torneos.modeloNegocios.objetosNegocio.ProcesoInscripcion;
import torneos.modeloNegocios.objetosNegocio.Torneo;

/**
 * Punto de entrada al modelo de negocios. Delega en el proceso de inscripción
 * y avisa a los observadores con el evento que ocurrió.
 */
public class FachadaModeloNegocios extends SujetoObservado
        implements IOperacionesModeloNegocios, IConsultaModeloNegocios {

    private final ProcesoInscripcion proceso;

    public FachadaModeloNegocios(ProcesoInscripcion proceso) {
        this.proceso = proceso;
    }

    // ---------------------------------------------------------- operaciones

    @Override
    public void iniciar() {
        proceso.reiniciar();
        notificarObservadores(EventoModelo.PROCESO_REINICIADO);
    }

    @Override
    public void seleccionarTorneo(String idTorneo) {
        if (proceso.seleccionarTorneo(idTorneo)) {
            notificarObservadores(EventoModelo.TORNEO_SELECCIONADO);
        }
    }

    @Override
    public void iniciarInscripcion() {
        if (proceso.iniciarInscripcion()) {
            notificarObservadores(EventoModelo.CAPTURA_INICIADA);
        }
    }

    @Override
    public void buscarParticipantes(String idParcial) {
        if (proceso.buscarParticipantes(idParcial)) {
            notificarObservadores(EventoModelo.COINCIDENCIAS_ACTUALIZADAS);
        }
    }

    @Override
    public void seleccionarParticipante(String idParticipante) {
        if (proceso.seleccionarParticipante(idParticipante)) {
            notificarObservadores(EventoModelo.PARTICIPANTE_SELECCIONADO);
        }
    }

    @Override
    public void confirmarInscripcion() {
        if (proceso.confirmarInscripcion()) {
            notificarObservadores(EventoModelo.INSCRIPCION_REGISTRADA);
        }
    }

    @Override
    public void cancelar() {
        proceso.reiniciar();
        notificarObservadores(EventoModelo.PROCESO_REINICIADO);
    }

    // ---------------------------------------------------------- consultas

    @Override
    public List<Torneo> getTorneos() {
        return proceso.getTorneos();
    }

    @Override
    public Torneo getTorneoSeleccionado() {
        return proceso.getTorneoSeleccionado();
    }

    @Override
    public List<Participante> getCoincidencias() {
        return proceso.getCoincidencias();
    }

    @Override
    public Participante getParticipanteSeleccionado() {
        return proceso.getParticipanteSeleccionado();
    }

    @Override
    public Inscripcion getInscripcionRegistrada() {
        return proceso.getInscripcionRegistrada();
    }

    @Override
    public String getMensaje() {
        return proceso.getMensaje();
    }
}
