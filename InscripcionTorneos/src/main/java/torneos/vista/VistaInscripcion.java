package torneos.vista;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import torneos.controlador.ControlInscripcion;
import torneos.dtos.ComprobanteDTO;
import torneos.dtos.ParticipanteDTO;
import torneos.modeloPresentacion.EventoPresentacion;
import torneos.modeloPresentacion.IConsultaModeloPresentacion;
import torneos.modeloPresentacion.IObservadorPresentacion;

/**
 * Vista inferior: búsqueda del participante, datos de confirmación y comprobante,
 * que se muestran en la misma zona según el evento recibido.
 */
public class VistaInscripcion extends JPanel implements IObservadorPresentacion {

    private static final String VACIO = "vacio";
    private static final String BUSQUEDA = "busqueda";
    private static final String CONFIRMACION = "confirmacion";
    private static final String COMPROBANTE = "comprobante";

    private final IConsultaModeloPresentacion modelo;
    private final ControlInscripcion control;
    private List<ParticipanteDTO> coincidenciasMostradas = List.of();
    private boolean actualizando = false;

    private final CardLayout tarjetas = new CardLayout();
    private final JPanel zona = new JPanel(tarjetas);

    private final JTextField txtId = new JTextField(18);
    private final DefaultTableModel modeloCoincidencias = new DefaultTableModel(new Object[]{"ID", "Nombre"}, 0) {
        @Override
        public boolean isCellEditable(int fila, int columna) {
            return false;
        }
    };
    private final JTable tablaCoincidencias = new JTable(modeloCoincidencias);
    private final JLabel lblMensajeBusqueda = new JLabel(" ");

    private final JLabel lblId = new JLabel();
    private final JLabel lblNombre = new JLabel();
    private final JLabel lblEdad = new JLabel();
    private final JLabel lblCategoria = new JLabel();
    private final JLabel lblEquipo = new JLabel();
    private final JLabel lblMensajeConfirmacion = new JLabel(" ");
    private final JButton btnConfirmar = new JButton("Confirmar inscripción");

    private final JLabel cParticipante = new JLabel();
    private final JLabel cTorneo = new JLabel();
    private final JLabel cCategoria = new JLabel();
    private final JLabel cFecha = new JLabel();
    private final JLabel cLugar = new JLabel();
    private final JLabel cCosto = new JLabel();

    private final JButton btnCancelar = new JButton("Cancelar");

    public VistaInscripcion(IConsultaModeloPresentacion modelo, ControlInscripcion control) {
        this.modelo = modelo;
        this.control = control;
        construirInterfaz();
    }

    private void construirInterfaz() {
        setLayout(new BorderLayout(0, 8));
        zona.setBorder(BorderFactory.createEtchedBorder());
        zona.add(new JPanel(), VACIO);
        zona.add(crearPanelBusqueda(), BUSQUEDA);
        zona.add(crearPanelConfirmacion(), CONFIRMACION);
        zona.add(crearPanelComprobante(), COMPROBANTE);
        add(zona, BorderLayout.CENTER);

        btnCancelar.setEnabled(false);
        btnCancelar.addActionListener(e -> control.cancelar());
        JPanel pie = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pie.add(btnCancelar);
        add(pie, BorderLayout.SOUTH);
    }

    @Override
    public void actualizar(EventoPresentacion evento) {
        switch (evento) {
            case INSCRIPCION_REINICIADA:
                tarjetas.show(zona, VACIO);
                btnCancelar.setEnabled(false);
                break;
            case DETALLES_ACTUALIZADOS:
                tarjetas.show(zona, VACIO);
                btnCancelar.setEnabled(true);
                break;
            case CAPTURA_INICIADA:
                actualizando = true;
                txtId.setText("");
                mostrarCoincidencias(List.of());
                lblMensajeBusqueda.setText(" ");
                actualizando = false;
                tarjetas.show(zona, BUSQUEDA);
                txtId.requestFocusInWindow();
                break;
            case COINCIDENCIAS_ACTUALIZADAS:
                mostrarCoincidencias(modelo.getCoincidencias());
                mostrarMensaje(lblMensajeBusqueda, modelo.getMensaje());
                break;
            case CONFIRMACION_SOLICITADA:
                mostrarDatosConfirmacion(modelo.getParticipanteSeleccionado());
                String mensaje = modelo.getMensaje();
                mostrarMensaje(lblMensajeConfirmacion, mensaje);
                btnConfirmar.setEnabled(mensaje == null);
                tarjetas.show(zona, CONFIRMACION);
                break;
            case COMPROBANTE_GENERADO:
                mostrarComprobante(modelo.getComprobante());
                tarjetas.show(zona, COMPROBANTE);
                btnCancelar.setEnabled(false);
                break;
            default:
                break;
        }
    }

    private void buscar() {
        if (!actualizando) {
            control.buscarParticipantes(txtId.getText());
        }
    }

    private void mostrarCoincidencias(List<ParticipanteDTO> coincidencias) {
        actualizando = true;
        coincidenciasMostradas = coincidencias;
        modeloCoincidencias.setRowCount(0);
        for (ParticipanteDTO p : coincidencias) {
            modeloCoincidencias.addRow(new Object[]{p.getId(), p.getNombreCompleto()});
        }
        actualizando = false;
    }

    private void mostrarDatosConfirmacion(ParticipanteDTO participante) {
        lblId.setText(participante.getId());
        lblNombre.setText(participante.getNombreCompleto());
        lblEdad.setText(String.valueOf(participante.getEdad()));
        lblCategoria.setText(participante.getCategoria());
        lblEquipo.setText(participante.getEquipo());
    }

    private void mostrarComprobante(ComprobanteDTO comprobante) {
        cParticipante.setText(comprobante.getNombreParticipante());
        cTorneo.setText(comprobante.getTorneo());
        cCategoria.setText(comprobante.getCategoria());
        cFecha.setText(comprobante.getFecha());
        cLugar.setText(comprobante.getLugar());
        cCosto.setText(String.format("$%,.2f", comprobante.getCostoInscripcion()));
    }

    private void mostrarMensaje(JLabel etiqueta, String mensaje) {
        etiqueta.setText(mensaje != null ? mensaje : " ");
    }

    // ------------------------------------------------------- construcción de paneles

    private JPanel crearPanelBusqueda() {
        JPanel panel = new JPanel(new BorderLayout(0, 6));
        panel.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));
        JPanel captura = new JPanel(new FlowLayout(FlowLayout.LEFT));
        captura.add(new JLabel("ID del participante"));
        captura.add(txtId);
        panel.add(captura, BorderLayout.NORTH);

        txtId.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { buscar(); }
            @Override
            public void removeUpdate(DocumentEvent e) { buscar(); }
            @Override
            public void changedUpdate(DocumentEvent e) { buscar(); }
        });

        tablaCoincidencias.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaCoincidencias.setRowHeight(22);
        tablaCoincidencias.getSelectionModel().addListSelectionListener(e -> {
            int fila = tablaCoincidencias.getSelectedRow();
            if (!actualizando && !e.getValueIsAdjusting() && fila >= 0) {
                control.seleccionarParticipante(coincidenciasMostradas.get(fila).getId());
            }
        });
        panel.add(new JScrollPane(tablaCoincidencias), BorderLayout.CENTER);
        lblMensajeBusqueda.setForeground(new Color(200, 30, 30));
        panel.add(lblMensajeBusqueda, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel crearPanelConfirmacion() {
        JPanel panel = crearPanelConTitulo("Datos de confirmación");
        JPanel datos = new JPanel(new GridBagLayout());
        agregarDato(datos, 0, "ID", lblId);
        agregarDato(datos, 1, "Nombre", lblNombre);
        agregarDato(datos, 2, "Edad", lblEdad);
        agregarDato(datos, 3, "Categoría", lblCategoria);
        agregarDato(datos, 4, "Equipo", lblEquipo);
        agregarArriba(panel, datos);

        lblMensajeConfirmacion.setForeground(new Color(200, 30, 30));
        btnConfirmar.addActionListener(e -> control.confirmarInscripcion());
        JPanel pie = new JPanel(new BorderLayout());
        pie.add(lblMensajeConfirmacion, BorderLayout.CENTER);
        pie.add(btnConfirmar, BorderLayout.EAST);
        panel.add(pie, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel crearPanelComprobante() {
        JPanel panel = crearPanelConTitulo("Comprobante de inscripción");
        JPanel datos = new JPanel(new GridBagLayout());
        agregarDato(datos, 0, "Participante", cParticipante);
        agregarDato(datos, 1, "Torneo", cTorneo);
        agregarDato(datos, 2, "Categoría", cCategoria);
        agregarDato(datos, 3, "Fecha", cFecha);
        agregarDato(datos, 4, "Lugar", cLugar);
        agregarDato(datos, 5, "Costo", cCosto);
        agregarArriba(panel, datos);
        return panel;
    }

    private JPanel crearPanelConTitulo(String texto) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));
        JLabel titulo = new JLabel(texto);
        titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 14f));
        panel.add(titulo, BorderLayout.NORTH);
        return panel;
    }

    private void agregarArriba(JPanel panel, JPanel datos) {
        JPanel arriba = new JPanel(new BorderLayout());
        arriba.add(datos, BorderLayout.NORTH);
        panel.add(arriba, BorderLayout.CENTER);
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
