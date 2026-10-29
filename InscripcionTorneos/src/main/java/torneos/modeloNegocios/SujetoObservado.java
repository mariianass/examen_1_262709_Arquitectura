package torneos.modeloNegocios;

import java.util.ArrayList;
import java.util.List;

/**
 * Sujeto del patrón Observer: lleva la lista de observadores y les avisa.
 */
public abstract class SujetoObservado {

    private final List<IObservadorModeloNegocios> observadores = new ArrayList<>();

    public void agregarObservador(IObservadorModeloNegocios observador) {
        observadores.add(observador);
    }

    public void eliminarObservador(IObservadorModeloNegocios observador) {
        observadores.remove(observador);
    }

    protected void notificarObservadores(EventoModelo evento) {
        for (IObservadorModeloNegocios observador : observadores) {
            observador.actualizar(evento);
        }
    }
}
