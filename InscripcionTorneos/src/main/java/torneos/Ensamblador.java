package torneos;

import java.time.LocalDate;
import torneos.controlador.ControlInscripcion;
import torneos.modeloNegocios.FachadaModeloNegocios;
import torneos.modeloNegocios.objetosNegocio.CatalogoTorneos;
import torneos.modeloNegocios.objetosNegocio.Inscripcion;
import torneos.modeloNegocios.objetosNegocio.PadronParticipantes;
import torneos.modeloNegocios.objetosNegocio.Participante;
import torneos.modeloNegocios.objetosNegocio.ProcesoInscripcion;
import torneos.modeloNegocios.objetosNegocio.Torneo;
import torneos.modeloPresentacion.ControlModeloPresentacion;
import torneos.modeloPresentacion.FachadaModeloPresentacion;
import torneos.modeloPresentacion.ReceptorEventosNegocio;
import torneos.modeloPresentacion.TraductorModeloNegocios;
import torneos.vista.VentanaPrincipal;
import torneos.vista.VistaInscripcion;
import torneos.vista.VistaTorneos;

/**
 * Crea todos los objetos de la arquitectura y los conecta entre sí.
 */
public class Ensamblador {

    public void ensamblar() {
        // Modelo de negocios (con datos de prueba en memoria)
        CatalogoTorneos catalogo = crearCatalogo();
        PadronParticipantes padron = crearPadron();
        cargarInscripcionesPrevias(catalogo, padron);
        FachadaModeloNegocios modeloNegocios = new FachadaModeloNegocios(new ProcesoInscripcion(catalogo, padron));

        // Modelo de presentación
        ReceptorEventosNegocio receptor = new ReceptorEventosNegocio(modeloNegocios, new TraductorModeloNegocios());
        modeloNegocios.agregarObservador(receptor);
        ControlModeloPresentacion controlPresentacion = new ControlModeloPresentacion(modeloNegocios);
        FachadaModeloPresentacion modeloPresentacion = new FachadaModeloPresentacion(controlPresentacion, receptor);

        // Controlador y vistas
        ControlInscripcion control = new ControlInscripcion(modeloPresentacion);
        VistaTorneos vistaTorneos = new VistaTorneos(modeloPresentacion, control);
        VistaInscripcion vistaInscripcion = new VistaInscripcion(modeloPresentacion, control);
        modeloPresentacion.agregarObservador(vistaTorneos);
        modeloPresentacion.agregarObservador(vistaInscripcion);

        new VentanaPrincipal(vistaTorneos, vistaInscripcion).setVisible(true);
        control.iniciar();
    }

    private CatalogoTorneos crearCatalogo() {
        CatalogoTorneos catalogo = new CatalogoTorneos();
        catalogo.agregarTorneo(new Torneo("T01", "Torneo Regional de Fútbol 7", "Fútbol", LocalDate.of(2026, 11, 15),
                "Juvenil", "Unidad Deportiva Álvaro Obregón", 350, 12));
        catalogo.agregarTorneo(new Torneo("T02", "Copa Básquetbol 3x3", "Básquetbol", LocalDate.of(2026, 11, 22),
                "Juvenil", "Gimnasio del Estado", 200, 0));
        catalogo.agregarTorneo(new Torneo("T03", "Torneo de Voleibol de Playa", "Voleibol", LocalDate.of(2026, 11, 29),
                "Juvenil", "Playa San Carlos", 250, 8));
        catalogo.agregarTorneo(new Torneo("T04", "Carrera 10K", "Atletismo", LocalDate.of(2026, 12, 6),
                "Veteranos", "Parque Madero", 180, 40));
        return catalogo;
    }

    private PadronParticipantes crearPadron() {
        PadronParticipantes padron = new PadronParticipantes();
        padron.agregarParticipante(new Participante("102345", "Carlos Méndez Soto", 20, "Juvenil", "Halcones FC"));
        padron.agregarParticipante(new Participante("102378", "Luis Ramírez Gil", 22, "Juvenil", "Tigres FC"));
        padron.agregarParticipante(new Participante("102911", "Jorge Vega Ruiz", 38, "Veteranos", "Águilas FC"));
        padron.agregarParticipante(new Participante("205410", "Ana Torres Lugo", 19, "Juvenil", "Halcones FC"));
        return padron;
    }

    /** Luis Ramírez ya estaba inscrito en el torneo de fútbol (para probar el flujo alterno 9A). */
    private void cargarInscripcionesPrevias(CatalogoTorneos catalogo, PadronParticipantes padron) {
        Torneo futbol = catalogo.buscarPorId("T01");
        futbol.agregarInscripcion(new Inscripcion(padron.buscarPorId("102378"), futbol));
    }
}
