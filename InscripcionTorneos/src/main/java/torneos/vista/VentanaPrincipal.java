package torneos.vista;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Ventana que contiene las dos vistas del caso de uso.
 */
public class VentanaPrincipal extends JFrame {

    public VentanaPrincipal(VistaTorneos vistaTorneos, VistaInscripcion vistaInscripcion) {
        super("Torneos - Registrar inscripción a un torneo deportivo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel titulo = new JLabel("Torneos");
        titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 22f));
        JPanel encabezado = new JPanel(new BorderLayout());
        encabezado.setBorder(BorderFactory.createEmptyBorder(10, 14, 4, 14));
        encabezado.add(titulo, BorderLayout.WEST);

        JPanel contenido = new JPanel(new BorderLayout(0, 10));
        contenido.setBorder(BorderFactory.createEmptyBorder(6, 14, 10, 14));
        contenido.add(vistaTorneos, BorderLayout.NORTH);
        contenido.add(vistaInscripcion, BorderLayout.CENTER);

        add(encabezado, BorderLayout.NORTH);
        add(contenido, BorderLayout.CENTER);
        setMinimumSize(new Dimension(980, 640));
        setLocationRelativeTo(null);
    }
}
