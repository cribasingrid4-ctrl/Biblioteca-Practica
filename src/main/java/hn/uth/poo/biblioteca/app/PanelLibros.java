package hn.uth.poo.biblioteca.app;

import hn.uth.poo.biblioteca.excepciones.DatoInvalidoException;
import hn.uth.poo.biblioteca.modelo.Autor;
import hn.uth.poo.biblioteca.modelo.Libro;
import hn.uth.poo.biblioteca.servicio.ServicioAutores;
import hn.uth.poo.biblioteca.servicio.ServicioLibros;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

public class PanelLibros extends JPanel {

    private final ServicioLibros servicio;
    private final ServicioAutores servicioAutores;

    private final JTextField txtId = new JTextField(6);
    private final JTextField txtIsbn = new JTextField(16);
    private final JTextField txtTitulo = new JTextField(24);
    private final JTextField txtAnio = new JTextField(6);
    private final JTextField txtPrecio = new JTextField(8);
    private final JComboBox<Autor> cmbAutor = new JComboBox<>();

    private final DefaultTableModel modelo = new DefaultTableModel(
            new Object[]{"ID", "ISBN", "Título", "Año", "Precio", "Autor ID", "Autor"}, 0) {
        @Override
        public boolean isCellEditable(int fila, int columna) {
            return false;
        }
    };
    private final JTable tabla = new JTable(modelo);

    public PanelLibros(ServicioLibros servicio, ServicioAutores servicioAutores) {
        this.servicio = servicio;
        this.servicioAutores = servicioAutores;
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
                txtIsbn.setText(String.valueOf(modelo.getValueAt(fila, 1)));
                txtTitulo.setText(String.valueOf(modelo.getValueAt(fila, 2)));
                txtAnio.setText(String.valueOf(modelo.getValueAt(fila, 3)));
                txtPrecio.setText(String.valueOf(modelo.getValueAt(fila, 4)));
                seleccionarAutor((Integer) modelo.getValueAt(fila, 5));
            }
        });
        recargarAutores();
        listar();
    }

    private JPanel crearFormulario() {
        JPanel p = new JPanel(new GridBagLayout());
        p.setBorder(BorderFactory.createTitledBorder("Datos del libro"));
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4, 6, 4, 6);
        c.anchor = GridBagConstraints.WEST;

        c.gridx = 0; c.gridy = 0; p.add(new JLabel("ID:"), c);
        c.gridx = 1; p.add(txtId, c);
        c.gridx = 2; p.add(new JLabel("ISBN:"), c);
        c.gridx = 3; p.add(txtIsbn, c);

        c.gridx = 0; c.gridy = 1; p.add(new JLabel("Título:"), c);
        c.gridx = 1; c.gridwidth = 3; p.add(txtTitulo, c);
        c.gridwidth = 1;

        c.gridx = 0; c.gridy = 2; p.add(new JLabel("Año:"), c);
        c.gridx = 1; p.add(txtAnio, c);
        c.gridx = 2; p.add(new JLabel("Precio:"), c);
        c.gridx = 3; p.add(txtPrecio, c);

        c.gridx = 0; c.gridy = 3; p.add(new JLabel("Autor:"), c);
        c.gridx = 1; c.gridwidth = 3; p.add(cmbAutor, c);
        return p;
    }

    private JPanel crearBotones() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 4));
        JButton btnCrear = new JButton("Crear");
        JButton btnListar = new JButton("Listar");
        JButton btnBuscar = new JButton("Buscar por ID");
        JButton btnActualizar = new JButton("Actualizar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnPorAutor = new JButton("Libros del autor");
        JButton btnLimpiar = new JButton("Limpiar");

        btnCrear.addActionListener(e -> crear());
        btnListar.addActionListener(e -> listar());
        btnBuscar.addActionListener(e -> buscar());
        btnActualizar.addActionListener(e -> actualizar());
        btnEliminar.addActionListener(e -> eliminar());
        btnPorAutor.addActionListener(e -> listarPorAutor());
        btnLimpiar.addActionListener(e -> limpiar());

        p.add(btnCrear);
        p.add(btnListar);
        p.add(btnBuscar);
        p.add(btnActualizar);
        p.add(btnEliminar);
        p.add(btnPorAutor);
        p.add(btnLimpiar);
        return p;
    }

    public void recargarAutores() {
        try {
            Autor seleccionado = (Autor) cmbAutor.getSelectedItem();
            cmbAutor.removeAllItems();
            for (Autor a : servicioAutores.listar()) {
                cmbAutor.addItem(a);
            }
            if (seleccionado != null) {
                seleccionarAutor(seleccionado.getId());
            }
        } catch (RuntimeException e) {
            Mensajes.error(this, e);
        }
    }

    private void crear() {
        try {
            Libro libro = leerFormulario(false);
            servicio.crear(libro);
            Mensajes.info(this, "Libro creado con id " + libro.getId() + ".");
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
            Libro libro = servicio.buscarPorId(leerId());
            mostrar(List.of(libro));
            txtIsbn.setText(libro.getIsbn());
            txtTitulo.setText(libro.getTitulo());
            txtAnio.setText(String.valueOf(libro.getAnio()));
            txtPrecio.setText(String.valueOf(libro.getPrecio()));
            seleccionarAutor(libro.getAutor().getId());
        } catch (RuntimeException e) {
            Mensajes.error(this, e);
        }
    }

    private void actualizar() {
        try {
            Libro libro = leerFormulario(true);
            servicio.actualizar(libro);
            Mensajes.info(this, "Libro actualizado.");
            listar();
        } catch (RuntimeException e) {
            Mensajes.error(this, e);
        }
    }

    private void eliminar() {
        try {
            int id = leerId();
            if (Mensajes.confirmar(this, "¿Eliminar el libro con id " + id + "?")) {
                servicio.eliminar(id);
                Mensajes.info(this, "Libro eliminado.");
                limpiar();
                listar();
            }
        } catch (RuntimeException e) {
            Mensajes.error(this, e);
        }
    }

    private void listarPorAutor() {
        try {
            Autor autor = (Autor) cmbAutor.getSelectedItem();
            if (autor == null) {
                throw new DatoInvalidoException("Seleccione un autor.");
            }
            List<Libro> libros = servicio.listarPorAutor(autor.getId());
            mostrar(libros);
            if (libros.isEmpty()) {
                Mensajes.info(this, autor.getNombre() + " no tiene libros registrados.");
            }
        } catch (RuntimeException e) {
            Mensajes.error(this, e);
        }
    }

    private Libro leerFormulario(boolean conId) {
        Autor autor = (Autor) cmbAutor.getSelectedItem();
        if (autor == null) {
            throw new DatoInvalidoException("Seleccione un autor (primero registre uno en la pestaña Autores).");
        }
        if (txtAnio.getText().isBlank() || txtPrecio.getText().isBlank()) {
            throw new DatoInvalidoException("El año y el precio son obligatorios.");
        }
        int anio = Integer.parseInt(txtAnio.getText().trim());
        double precio = Double.parseDouble(txtPrecio.getText().trim());
        Libro libro = new Libro(txtIsbn.getText(), txtTitulo.getText(), anio, precio, autor);
        if (conId) {
            libro.setId(leerId());
        }
        return libro;
    }

    private int leerId() {
        String texto = txtId.getText().trim();
        if (texto.isEmpty()) {
            throw new DatoInvalidoException("Escriba el ID del libro o selecciónelo en la tabla.");
        }
        return Integer.parseInt(texto);
    }

    private void seleccionarAutor(int autorId) {
        for (int i = 0; i < cmbAutor.getItemCount(); i++) {
            if (cmbAutor.getItemAt(i).getId() == autorId) {
                cmbAutor.setSelectedIndex(i);
                return;
            }
        }
    }

    private void mostrar(List<Libro> libros) {
        modelo.setRowCount(0);
        for (Libro l : libros) {
            modelo.addRow(new Object[]{l.getId(), l.getIsbn(), l.getTitulo(), l.getAnio(),
                l.getPrecio(), l.getAutor().getId(), l.getAutor().getNombre()});
        }
    }

    private void limpiar() {
        txtId.setText("");
        txtIsbn.setText("");
        txtTitulo.setText("");
        txtAnio.setText("");
        txtPrecio.setText("");
        tabla.clearSelection();
    }
}
