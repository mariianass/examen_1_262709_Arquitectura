package torneos.modeloNegocios.objetosNegocio;

/**
 * Participante dado de alta en el sistema.
 */
public class Participante {

    private final String id;
    private final String nombreCompleto;
    private final int edad;
    private final String categoria;
    private final String equipo;

    public Participante(String id, String nombreCompleto, int edad, String categoria, String equipo) {
        this.id = id;
        this.nombreCompleto = nombreCompleto;
        this.edad = edad;
        this.categoria = categoria;
        this.equipo = equipo;
    }

    /** Indica si el ID del participante empieza con lo que se lleva capturado. */
    public boolean coincideConId(String idParcial) {
        return idParcial != null && !idParcial.isBlank() && id.startsWith(idParcial.trim());
    }

    /** Indica si el participante pertenece a la categoría que exige el torneo. */
    public boolean cumpleCategoria(String categoriaTorneo) {
        return categoria.equalsIgnoreCase(categoriaTorneo);
    }

    public String getId() { return id; }
    public String getNombreCompleto() { return nombreCompleto; }
    public int getEdad() { return edad; }
    public String getCategoria() { return categoria; }
    public String getEquipo() { return equipo; }
}
