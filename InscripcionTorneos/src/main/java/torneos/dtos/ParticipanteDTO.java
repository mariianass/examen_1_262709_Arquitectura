package torneos.dtos;

/**
 * Datos de un participante listos para mostrarse en la vista.
 */
public class ParticipanteDTO {

    private final String id;
    private final String nombreCompleto;
    private final int edad;
    private final String categoria;
    private final String equipo;

    public ParticipanteDTO(String id, String nombreCompleto, int edad, String categoria, String equipo) {
        this.id = id;
        this.nombreCompleto = nombreCompleto;
        this.edad = edad;
        this.categoria = categoria;
        this.equipo = equipo;
    }

    public String getId() { return id; }
    public String getNombreCompleto() { return nombreCompleto; }
    public int getEdad() { return edad; }
    public String getCategoria() { return categoria; }
    public String getEquipo() { return equipo; }
}
