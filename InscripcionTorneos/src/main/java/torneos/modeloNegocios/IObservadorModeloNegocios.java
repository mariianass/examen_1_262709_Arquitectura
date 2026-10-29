package torneos.modeloNegocios;

/**
 * Lo implementa quien quiera enterarse de los cambios del modelo de negocios.
 */
public interface IObservadorModeloNegocios {

    void actualizar(EventoModelo evento);
}
