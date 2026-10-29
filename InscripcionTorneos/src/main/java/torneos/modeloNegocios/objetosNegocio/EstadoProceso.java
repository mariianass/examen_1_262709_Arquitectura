package torneos.modeloNegocios.objetosNegocio;

/**
 * Etapas por las que pasa el proceso de inscripción.
 */
public enum EstadoProceso {
    SIN_TORNEO,
    TORNEO_SELECCIONADO,
    CAPTURANDO_ID,
    CONFIRMANDO,
    INSCRITO
}
