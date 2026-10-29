package torneos.dtos;

/**
 * Datos del comprobante de inscripción.
 */
public class ComprobanteDTO {

    private final String nombreParticipante;
    private final String torneo;
    private final String categoria;
    private final String fecha;
    private final String lugar;
    private final double costoInscripcion;

    public ComprobanteDTO(String nombreParticipante, String torneo, String categoria, String fecha,
                          String lugar, double costoInscripcion) {
        this.nombreParticipante = nombreParticipante;
        this.torneo = torneo;
        this.categoria = categoria;
        this.fecha = fecha;
        this.lugar = lugar;
        this.costoInscripcion = costoInscripcion;
    }

    public String getNombreParticipante() { return nombreParticipante; }
    public String getTorneo() { return torneo; }
    public String getCategoria() { return categoria; }
    public String getFecha() { return fecha; }
    public String getLugar() { return lugar; }
    public double getCostoInscripcion() { return costoInscripcion; }
}
