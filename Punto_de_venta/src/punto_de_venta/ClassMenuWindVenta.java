package punto_de_venta;

import javax.swing.*;

public class ClassMenuWindVenta {
    public JMenuBar crearMenu() {
        // Crear la barra de menú
        JMenuBar menuBar = new JMenuBar();

        // Crear las opciones de menú
        JMenu menuVenta = new JMenu("Venta");
        JMenu menuInventario = new JMenu("Inventario");
        JMenu menuProveedores = new JMenu("Proveedores");
        JMenu menuUsuario = new JMenu("Usuario");
        JMenu menuCorteCaja = new JMenu("Corte de Caja");

        // Agregar las opciones a la barra de menú
        menuBar.add(menuVenta);
        menuBar.add(menuInventario);
        menuBar.add(menuProveedores);
        menuBar.add(menuUsuario);
        menuBar.add(menuCorteCaja);

        return menuBar;
    }
}
