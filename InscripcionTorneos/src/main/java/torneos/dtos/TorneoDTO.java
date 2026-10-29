package torneos.dtos;

/**
 * Datos de un torneo listos para mostrarse en la vista.
 */
public class TorneoDTO {

    private final String id;
    private final String nombre;
    private final String disciplina;
    private final String fecha;
    private final String categoria;
    private final String lugar;
    private final double costoInscripcion;
    private final int lugaresDisponibles;

    public TorneoDTO(String id, String nombre, String disciplina, String fecha, String categoria,
                     String lugar, double costoInscripcion, int lugaresDisponibles) {
        this.id = id;
        this.nombre = nombre;
        this.disciplina = disciplina;
        this.fecha = fecha;
        this.categoria = categoria;
        this.lugar = lugar;
        this.costoInscripcion = costoInscripcion;
        this.lugaresDisponibles = lugaresDisponibles;
    }

    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public String getDisciplina() { return disciplina; }
    public String getFecha() { return fecha; }
    public String getCategoria() { return categoria; }
    public String getLugar() { return lugar; }
    public double getCostoInscripcion() { return costoInscripcion; }
    public int getLugaresDisponibles() { return lugaresDisponibles; }
}
