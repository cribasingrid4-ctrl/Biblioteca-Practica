package hn.uth.poo.biblioteca.app;

import hn.uth.poo.biblioteca.servicio.ServicioAutores;
import hn.uth.poo.biblioteca.servicio.ServicioLibros;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;

public class VentanaPrincipal extends JFrame {

    public VentanaPrincipal(ServicioAutores servicioAutores, ServicioLibros servicioLibros) {
        super("Biblioteca - Autores y Libros");
        PanelAutores panelAutores = new PanelAutores(servicioAutores);
        PanelLibros panelLibros = new PanelLibros(servicioLibros, servicioAutores);

        JTabbedPane pestanas = new JTabbedPane();
        pestanas.addTab("Autores", panelAutores);
        pestanas.addTab("Libros", panelLibros);
        pestanas.addChangeListener(e -> {
            if (pestanas.getSelectedComponent() == panelLibros) {
                panelLibros.recargarAutores();
            }
        });

        add(pestanas);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 560);
        setLocationRelativeTo(null);
    }
}
