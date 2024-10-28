package punto_de_venta;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class ClassMenuWindVenta {

    public JMenuBar crearMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menuVenta = new JMenu("Venta");
        JMenu menuInventario = new JMenu("Inventario");
        JMenu menuProveedores = new JMenu("Proveedores");
        JMenu menuCorteCaja = new JMenu("Corte de Caja");
        JMenu menuUsuario = new JMenu("Usuario");
        JMenu menuOpciones = new JMenu("Opciones");
       
        // Items para el menú de las opciones
        // Agregar un JMenuItem para abrir la ventana de "Añadir"
        JMenuItem añadirItem = new JMenuItem("Añadir");
        menuOpciones.add(añadirItem);
        
        // Agregar un JMenuItem para llamar a la ventana "Modificar"
        JMenuItem modificarItem = new JMenuItem("Modificar");
        menuOpciones.add(modificarItem);
        
        // Añadir un JMenuItem para abrir la ventana de usuarios
        JMenuItem usuarioItem = new JMenuItem("Ventana Usuario");
        usuarioItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VentanaUsuario.WindowUsuarios(); // Llama a la ventana de usuario
            }
        });
        menuUsuario.add(usuarioItem); // Agregar el item al menú de usuario

        // Agregar las opciones al menú de barra
        menuBar.add(menuVenta);
        menuBar.add(menuInventario);
        menuBar.add(menuProveedores);
        menuBar.add(menuCorteCaja);
        menuBar.add(menuUsuario);
        menuBar.add(menuOpciones);

        return menuBar;
    }
}
