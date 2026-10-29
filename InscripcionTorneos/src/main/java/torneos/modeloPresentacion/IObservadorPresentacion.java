package torneos.modeloPresentacion;

/**
 * Lo implementan las vistas para enterarse de los cambios del modelo de presentación.
 */
public interface IObservadorPresentacion {

    void actualizar(EventoPresentacion evento);
}
