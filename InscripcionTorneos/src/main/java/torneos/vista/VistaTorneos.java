package torneos.vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import torneos.controlador.ControlInscripcion;
import torneos.dtos.TorneoDTO;
import torneos.modeloPresentacion.EventoPresentacion;
import torneos.modeloPresentacion.IConsultaModeloPresentacion;
import torneos.modeloPresentacion.IObservadorPresentacion;

/**
 * Vista superior: lista de torneos disponibles (izquierda) y detalles del torneo (derecha).
 */
public class VistaTorneos extends JPanel implements IObservadorPresentacion {

    private final IConsultaModeloPresentacion modelo;
    private final ControlInscripcion control;
    private List<TorneoDTO> torneosMostrados = List.of();

    private final DefaultTableModel modeloTabla = new DefaultTableModel(
            new Object[]{"Nombre", "Disciplina", "Fecha", "Lugares"}, 0) {
        @Override
        public boolean isCellEditable(int fila, int columna) {
            return false;
        }
    };
    private final JTable tablaTorneos = new JTable(modeloTabla);
    private final JLabel lblNombre = new JLabel();
    private final JLabel lblCategoria = new JLabel();
    private final JLabel lblLugar = new JLabel();
    private final JLabel lblFecha = new JLabel();
    private final JLabel lblCosto = new JLabel();
    private final JLabel lblLugares = new JLabel();
    private final JLabel lblMensaje = new JLabel(" ");
    private final JButton btnInscribirse = new JButton("Inscribirse");

    public VistaTorneos(IConsultaModeloPresentacion modelo, ControlInscripcion control) {
        this.modelo = modelo;
        this.control = control;
        construirInterfaz();
    }

    private void construirInterfaz() {
        setLayout(new GridLayout(1, 2, 12, 0));

        JPanel panelTorneos = new JPanel(new BorderLayout());
        panelTorneos.setBorder(BorderFactory.createTitledBorder("Torneos disponibles"));
        tablaTorneos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaTorneos.setRowHeight(24);
        tablaTorneos.setPreferredScrollableViewportSize(new Dimension(450, 130));
        tablaTorneos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                seleccionarTorneo(tablaTorneos.rowAtPoint(e.getPoint()));
            }
        });
        panelTorneos.add(new JScrollPane(tablaTorneos), BorderLayout.CENTER);

        JPanel panelDetalles = new JPanel(new BorderLayout());
        panelDetalles.setBorder(BorderFactory.createTitledBorder("Detalles del torneo"));
        JPanel datos = new JPanel(new GridBagLayout());
        agregarDato(datos, 0, "Nombre", lblNombre);
        agregarDato(datos, 1, "Categoría", lblCategoria);
        agregarDato(datos, 2, "Lugar", lblLugar);
        agregarDato(datos, 3, "Fecha", lblFecha);
        agregarDato(datos, 4, "Costo de inscripción", lblCosto);
        agregarDato(datos, 5, "Lugares disponibles", lblLugares);
        panelDetalles.add(datos, BorderLayout.NORTH);

        lblMensaje.setForeground(new Color(200, 30, 30));
        btnInscribirse.setEnabled(false);
        btnInscribirse.addActionListener(e -> control.iniciarInscripcion());
        JPanel pie = new JPanel(new BorderLayout());
        pie.add(lblMensaje, BorderLayout.CENTER);
        JPanel boton = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        boton.add(btnInscribirse);
        pie.add(boton, BorderLayout.EAST);
        panelDetalles.add(pie, BorderLayout.SOUTH);

        add(panelTorneos);
        add(panelDetalles);
    }

    @Override
    public void actualizar(EventoPresentacion evento) {
        switch (evento) {
            case INSCRIPCION_REINICIADA:
                mostrarTorneos(modelo.getTorneos());
                mostrarDetalles(null);
                lblMensaje.setText(" ");
                btnInscribirse.setEnabled(false);
                break;
            case DETALLES_ACTUALIZADOS:
                TorneoDTO torneo = modelo.getTorneoSeleccionado();
                mostrarDetalles(torneo);
                String mensaje = modelo.getMensaje();
                lblMensaje.setText(mensaje != null ? mensaje : " ");
                btnInscribirse.setEnabled(torneo.getLugaresDisponibles() > 0);
                break;
            case CAPTURA_INICIADA:
                btnInscribirse.setEnabled(false);
                break;
            case COMPROBANTE_GENERADO:
                mostrarTorneos(modelo.getTorneos());
                mostrarDetalles(modelo.getTorneoSeleccionado());
                btnInscribirse.setEnabled(false);
                break;
            default:
                break;
        }
    }

    private void seleccionarTorneo(int fila) {
        if (fila >= 0 && fila < torneosMostrados.size()) {
            control.seleccionarTorneo(torneosMostrados.get(fila).getId());
        }
    }

    private void mostrarTorneos(List<TorneoDTO> torneos) {
        torneosMostrados = torneos;
        modeloTabla.setRowCount(0);
        for (TorneoDTO t : torneos) {
            modeloTabla.addRow(new Object[]{t.getNombre(), t.getDisciplina(), t.getFecha(), t.getLugaresDisponibles()});
        }
    }

    private void mostrarDetalles(TorneoDTO torneo) {
        tablaTorneos.clearSelection();
        if (torneo == null) {
            for (JLabel lbl : new JLabel[]{lblNombre, lblCategoria, lblLugar, lblFecha, lblCosto, lblLugares}) {
                lbl.setText("");
            }
            return;
        }
        for (int i = 0; i < torneosMostrados.size(); i++) {
            if (torneosMostrados.get(i).getId().equals(torneo.getId())) {
                tablaTorneos.setRowSelectionInterval(i, i);
            }
        }
        lblNombre.setText(torneo.getNombre());
        lblCategoria.setText(torneo.getCategoria());
        lblLugar.setText(torneo.getLugar());
        lblFecha.setText(torneo.getFecha());
        lblCosto.setText(String.format("$%,.2f", torneo.getCostoInscripcion()));
        lblLugares.setText(String.valueOf(torneo.getLugaresDisponibles()));
    }

    private void agregarDato(JPanel panel, int fila, String etiqueta, JLabel valor) {
        GridBagConstraints c = new GridBagConstraints();
        c.gridy = fila;
        c.anchor = GridBagConstraints.WEST;
        c.insets = new Insets(3, 6, 3, 12);
        JLabel lbl = new JLabel(etiqueta);
        lbl.setForeground(Color.GRAY);
        panel.add(lbl, c);
        c.gridx = 1;
        c.weightx = 1;
        panel.add(valor, c);
    }
}
