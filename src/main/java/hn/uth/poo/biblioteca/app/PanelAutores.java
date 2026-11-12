package hn.uth.poo.biblioteca.app;

import hn.uth.poo.biblioteca.modelo.Autor;
import hn.uth.poo.biblioteca.servicio.ServicioAutores;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
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
import javax.swing.table.DefaultTableModel;

public class PanelAutores extends JPanel {

    private final ServicioAutores servicio;

    private final JTextField txtId = new JTextField(6);
    private final JTextField txtNombre = new JTextField(20);
    private final JTextField txtNacionalidad = new JTextField(20);

    private final DefaultTableModel modelo = new DefaultTableModel(
            new Object[]{"ID", "Nombre", "Nacionalidad"}, 0) {
        @Override
        public boolean isCellEditable(int fila, int columna) {
            return false;
        }
    };
    private final JTable tabla = new JTable(modelo);

    public PanelAutores(ServicioAutores servicio) {
        this.servicio = servicio;
        setLayout(new BorderLayout(8, 8));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(crearFormulario(), BorderLayout.NORTH);
        add(new JScrollPane(tabla), BorderLayout.CENTER);
        add(crearBotones(), BorderLayout.SOUTH);

        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabla.getSelectionModel().addListSelectionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (!e.getValueIsAdjusting() && fila >= 0) {
                txtId.setText(String.valueOf(modelo.getValueAt(fila, 0)));
                txtNombre.setText(String.valueOf(modelo.getValueAt(fila, 1)));
                Object nac = modelo.getValueAt(fila, 2);
                txtNacionalidad.setText(nac == null ? "" : nac.toString());
            }
        });
        listar();
    }

    private JPanel crearFormulario() {
        JPanel p = new JPanel(new GridBagLayout());
        p.setBorder(BorderFactory.createTitledBorder("Datos del autor"));
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4, 6, 4, 6);
        c.anchor = GridBagConstraints.WEST;

        c.gridx = 0; c.gridy = 0; p.add(new JLabel("ID:"), c);
        c.gridx = 1; p.add(txtId, c);
        c.gridx = 0; c.gridy = 1; p.add(new JLabel("Nombre:"), c);
        c.gridx = 1; p.add(txtNombre, c);
        c.gridx = 0; c.gridy = 2; p.add(new JLabel("Nacionalidad:"), c);
        c.gridx = 1; p.add(txtNacionalidad, c);
        return p;
    }

    private JPanel crearBotones() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 4));
        JButton btnCrear = new JButton("Crear");
        JButton btnListar = new JButton("Listar");
        JButton btnBuscar = new JButton("Buscar por ID");
        JButton btnActualizar = new JButton("Actualizar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnLimpiar = new JButton("Limpiar");

        btnCrear.addActionListener(e -> crear());
        btnListar.addActionListener(e -> listar());
        btnBuscar.addActionListener(e -> buscar());
        btnActualizar.addActionListener(e -> actualizar());
        btnEliminar.addActionListener(e -> eliminar());
        btnLimpiar.addActionListener(e -> limpiar());

        p.add(btnCrear);
        p.add(btnListar);
        p.add(btnBuscar);
        p.add(btnActualizar);
        p.add(btnEliminar);
        p.add(btnLimpiar);
        return p;
    }

    private void crear() {
        try {
            Autor autor = new Autor(txtNombre.getText(), txtNacionalidad.getText());
            servicio.crear(autor);
            Mensajes.info(this, "Autor creado con id " + autor.getId() + ".");
            limpiar();
            listar();
        } catch (RuntimeException e) {
            Mensajes.error(this, e);
        }
    }

    private void listar() {
        try {
            mostrar(servicio.listar());
        } catch (RuntimeException e) {
            Mensajes.error(this, e);
        }
    }

    private void buscar() {
        try {
            Autor autor = servicio.buscarPorId(leerId());
            mostrar(List.of(autor));
            txtNombre.setText(autor.getNombre());
            txtNacionalidad.setText(autor.getNacionalidad());
        } catch (RuntimeException e) {
            Mensajes.error(this, e);
        }
    }

    private void actualizar() {
        try {
            Autor autor = new Autor(leerId(), txtNombre.getText(), txtNacionalidad.getText());
            servicio.actualizar(autor);
            Mensajes.info(this, "Autor actualizado.");
            listar();
        } catch (RuntimeException e) {
            Mensajes.error(this, e);
        }
    }

    private void eliminar() {
        try {
            int id = leerId();
            if (Mensajes.confirmar(this, "¿Eliminar el autor con id " + id + "?")) {
                servicio.eliminar(id);
                Mensajes.info(this, "Autor eliminado.");
                limpiar();
                listar();
            }
        } catch (RuntimeException e) {
            Mensajes.error(this, e);
        }
    }

    private int leerId() {
        String texto = txtId.getText().trim();
        if (texto.isEmpty()) {
            throw new hn.uth.poo.biblioteca.excepciones.DatoInvalidoException(
                    "Escriba el ID del autor o selecciónelo en la tabla.");
        }
        return Integer.parseInt(texto);
    }

    private void mostrar(List<Autor> autores) {
        modelo.setRowCount(0);
        for (Autor a : autores) {
            modelo.addRow(new Object[]{a.getId(), a.getNombre(), a.getNacionalidad()});
        }
    }

    private void limpiar() {
        txtId.setText("");
        txtNombre.setText("");
        txtNacionalidad.setText("");
        tabla.clearSelection();
    }
}
