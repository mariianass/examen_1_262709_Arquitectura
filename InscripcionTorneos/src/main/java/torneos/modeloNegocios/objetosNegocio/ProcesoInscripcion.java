package torneos.modeloNegocios.objetosNegocio;

import java.util.ArrayList;
import java.util.List;

/**
 * Lleva el estado de la inscripción en curso y aplica las reglas del negocio:
 * lugares disponibles, participante ya inscrito y categoría.
 */
public class ProcesoInscripcion {

    private final CatalogoTorneos catalogo;
    private final PadronParticipantes padron;

    private EstadoProceso estado = EstadoProceso.SIN_TORNEO;
    private Torneo torneoSeleccionado;
    private Participante participanteSeleccionado;
    private List<Participante> coincidencias = new ArrayList<>();
    private Inscripcion inscripcionRegistrada;
    private String mensaje;

    public ProcesoInscripcion(CatalogoTorneos catalogo, PadronParticipantes padron) {
        this.catalogo = catalogo;
        this.padron = padron;
    }

    public void reiniciar() {
        estado = EstadoProceso.SIN_TORNEO;
        torneoSeleccionado = null;
        participanteSeleccionado = null;
        coincidencias = new ArrayList<>();
        inscripcionRegistrada = null;
        mensaje = null;
    }

    public boolean seleccionarTorneo(String idTorneo) {
        reiniciar();
        torneoSeleccionado = catalogo.buscarPorId(idTorneo);
        if (torneoSeleccionado == null) {
            return false;
        }
        estado = EstadoProceso.TORNEO_SELECCIONADO;
        if (!torneoSeleccionado.hayLugares()) {
            mensaje = "El torneo está lleno";                                   // flujo alterno 3A
        }
        return true;
    }

    public boolean iniciarInscripcion() {
        if (estado != EstadoProceso.TORNEO_SELECCIONADO || !torneoSeleccionado.hayLugares()) {
            return false;
        }
        estado = EstadoProceso.CAPTURANDO_ID;
        coincidencias = new ArrayList<>();
        mensaje = null;
        return true;
    }

    public boolean buscarParticipantes(String idParcial) {
        if (estado != EstadoProceso.CAPTURANDO_ID) {
            return false;
        }
        coincidencias = padron.buscarPorIdParcial(idParcial);
        boolean seEscribioAlgo = idParcial != null && !idParcial.isBlank();
        mensaje = (seEscribioAlgo && coincidencias.isEmpty())
                ? "No se encontraron participantes con ese ID" : null;            // flujo alterno 7A
        return true;
    }

    public boolean seleccionarParticipante(String idParticipante) {
        Participante participante = padron.buscarPorId(idParticipante);
        if (estado != EstadoProceso.CAPTURANDO_ID || participante == null) {
            return false;
        }
        participanteSeleccionado = participante;
        estado = EstadoProceso.CONFIRMANDO;
        validarInscripcion();
        return true;
    }

    public boolean confirmarInscripcion() {
        if (estado != EstadoProceso.CONFIRMANDO || !validarInscripcion()) {
            return false;
        }
        inscripcionRegistrada = new Inscripcion(participanteSeleccionado, torneoSeleccionado);
        torneoSeleccionado.agregarInscripcion(inscripcionRegistrada);
        torneoSeleccionado.descontarLugar();
        estado = EstadoProceso.INSCRITO;
        return true;
    }

    private boolean validarInscripcion() {
        mensaje = null;
        if (torneoSeleccionado.tieneInscrito(participanteSeleccionado)) {
            mensaje = "El participante ya está inscrito en este torneo";          // flujo alterno 9A
        } else if (!participanteSeleccionado.cumpleCategoria(torneoSeleccionado.getCategoria())) {
            mensaje = "No cumple con la categoría requerida ("
                    + torneoSeleccionado.getCategoria() + ")";                    // flujo alterno 9B
        }
        return mensaje == null;
    }

    public List<Torneo> getTorneos() { return catalogo.obtenerTorneos(); }
    public EstadoProceso getEstado() { return estado; }
    public Torneo getTorneoSeleccionado() { return torneoSeleccionado; }
    public Participante getParticipanteSeleccionado() { return participanteSeleccionado; }
    public List<Participante> getCoincidencias() { return coincidencias; }
    public Inscripcion getInscripcionRegistrada() { return inscripcionRegistrada; }
    public String getMensaje() { return mensaje; }
}
