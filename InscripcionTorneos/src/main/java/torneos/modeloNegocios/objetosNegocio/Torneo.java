package torneos.modeloNegocios.objetosNegocio;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Torneo deportivo con sus lugares disponibles y sus inscripciones.
 */
public class Torneo {

    private final String id;
    private final String nombre;
    private final String disciplina;
    private final LocalDate fecha;
    private final String categoria;
    private final String lugar;
    private final double costoInscripcion;
    private int lugaresDisponibles;
    private final List<Inscripcion> inscripciones = new ArrayList<>();

    public Torneo(String id, String nombre, String disciplina, LocalDate fecha, String categoria,
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

    public boolean hayLugares() {
        return lugaresDisponibles > 0;
    }

    public boolean tieneInscrito(Participante participante) {
        for (Inscripcion inscripcion : inscripciones) {
            if (inscripcion.getParticipante().getId().equals(participante.getId())) {
                return true;
            }
        }
        return false;
    }

    public void agregarInscripcion(Inscripcion inscripcion) {
        inscripciones.add(inscripcion);
    }

    public void descontarLugar() {
        if (lugaresDisponibles > 0) {
            lugaresDisponibles--;
        }
    }

    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public String getDisciplina() { return disciplina; }
    public LocalDate getFecha() { return fecha; }
    public String getCategoria() { return categoria; }
    public String getLugar() { return lugar; }
    public double getCostoInscripcion() { return costoInscripcion; }
    public int getLugaresDisponibles() { return lugaresDisponibles; }
}
