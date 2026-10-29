# Registrar inscripción a un torneo deportivo

Examen 1 – Arquitectura de Software (ITSON).
Arquitectura MVC con Modelo de Presentación y Modelo de Negocios, con la misma
estructura del ejemplo visto en clase.

## Paquetes

| Paquete | Contenido |
|---|---|
| `torneos` | `Main` (arranque) y `Ensamblador` (crea y conecta los objetos) |
| `torneos.vista` | `VentanaPrincipal`, `VistaTorneos`, `VistaInscripcion` (Swing) |
| `torneos.controlador` | `ControlInscripcion` |
| `torneos.modeloPresentacion` | Fachada, control, receptor de eventos y traductor del modelo de presentación |
| `torneos.modeloNegocios` | Fachada del modelo de negocios, interfaces, `SujetoObservado` y `EventoModelo` |
| `torneos.modeloNegocios.objetosNegocio` | `ProcesoInscripcion`, `CatalogoTorneos`, `PadronParticipantes`, `Torneo`, `Participante`, `Inscripcion` |
| `torneos.dtos` | `TorneoDTO`, `ParticipanteDTO`, `ComprobanteDTO` |

## Ejecutar

Abrir el proyecto en NetBeans o IntelliJ (proyecto Maven, Java 17 o superior) y ejecutar `torneos.Main`.

## Datos de prueba

| ID | Participante | Categoría | Para probar |
|---|---|---|---|
| 102345 | Carlos Méndez Soto | Juvenil | Flujo básico en el Torneo Regional de Fútbol 7 |
| 102378 | Luis Ramírez Gil | Juvenil | 9A: ya está inscrito en Fútbol 7 |
| 102911 | Jorge Vega Ruiz | Veteranos | 9B: no cumple la categoría de Fútbol 7 |

- La Copa Básquetbol 3x3 no tiene lugares (flujo 3A).
- Escribir `999` en el ID muestra el flujo 7A.
