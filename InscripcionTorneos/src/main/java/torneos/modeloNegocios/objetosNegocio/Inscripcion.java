package torneos.modeloNegocios.objetosNegocio;

import java.time.LocalDateTime;

/**
 * Inscripción de un participante en un torneo.
 */
public class Inscripcion {

    private final LocalDateTime fecha;
    private final Participante participante;
    private final Torneo torneo;

    public Inscripcion(Participante participante, Torneo torneo) {
        this.fecha = LocalDateTime.now();
        this.participante = participante;
        this.torneo = torneo;
    }

    public LocalDateTime getFecha() { return fecha; }
    public Participante getParticipante() { return participante; }
    public Torneo getTorneo() { return torneo; }
}
