package punto_de_venta;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.BufferedReader;
import java.io.BufferedWriter; // Import necesario
import java.io.FileReader;
import java.io.FileWriter; // Import necesario
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class VentanaProveedores {

    private DefaultTableModel modeloTabla;
    private List<Object[]> proveedores;
    private JTable tablaProveedores; // Añadido para referencia

    public VentanaProveedores() {
        modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("Proveedor");
        modeloTabla.addColumn("Próxima Entrega");
        modeloTabla.addColumn("Contacto");

        cargarProveedores();
        WindowProveedores();
    }

    public void cargarProveedores() {
        proveedores = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("Proyecto-OOP-Uaemex/Punto_de_venta/src/proveedores.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                proveedores.add(datos);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Archivo 'proveedores.txt' no encontrado.");
        }
        actualizarTabla();
    }

    private void actualizarTabla() {
        modeloTabla.setRowCount(0);
        for (Object[] proveedor : proveedores) {
            modeloTabla.addRow(proveedor);
        }
    }

    private void WindowProveedores() {
        JFrame Ventana_de_Proveedores = new JFrame("Ventana de Proveedores");
        Ventana_de_Proveedores.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        Ventana_de_Proveedores.setExtendedState(JFrame.MAXIMIZED_BOTH);
        Ventana_de_Proveedores.setResizable(false);

        // Configura el menú para la ventana de venta
        ClassMenuWindVenta menu = new ClassMenuWindVenta();
        Ventana_de_Proveedores.setJMenuBar(menu.crearMenu());

        // Crear el campo de búsqueda
        JTextField campoBusqueda = new JTextField(40);
        campoBusqueda.setFont(new Font("Arial", Font.PLAIN, 16));
        campoBusqueda.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { actualizarTablaFiltrada(campoBusqueda.getText()); }
            public void removeUpdate(DocumentEvent e) { actualizarTablaFiltrada(campoBusqueda.getText()); }
            public void changedUpdate(DocumentEvent e) { actualizarTablaFiltrada(campoBusqueda.getText()); }
        });

        // Crear el botón de búsqueda
        JButton botonBuscar = new JButton("Buscar");
        botonBuscar.setFont(new Font("Arial", Font.PLAIN, 16));

        // Agregar ActionListener al botón de búsqueda
        botonBuscar.addActionListener(e -> {
            String busqueda = campoBusqueda.getText().trim();
            if (busqueda.isEmpty()) {
                JOptionPane.showMessageDialog(Ventana_de_Proveedores, "La barra de busqueda está vacía.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            } else {
                actualizarTablaFiltrada(busqueda);
            }
        });

        // Crear el botón de opciones
        JButton botonOpciones = new JButton("Opciones");
        botonOpciones.setFont(new Font("Arial", Font.PLAIN, 16));
        // Crear el menú emergente
        JPopupMenu menuOpciones = new JPopupMenu();

        // Opción de añadir
        JMenuItem opcionAnadir = new JMenuItem("Añadir");
        opcionAnadir.addActionListener(e -> {
            new AddProveedor(this); // Pasa la referencia a VentanaProveedores
        });

        // Opción de modificar
        JMenuItem opcionModificar = new JMenuItem("Modificar");
        opcionModificar.addActionListener(e -> {
            int filaSeleccionada = tablaProveedores.getSelectedRow(); // Obtener la fila seleccionada
            if (filaSeleccionada != -1) { // Verificar que se haya seleccionado una fila
                Object[] proveedorSeleccionado = proveedores.get(filaSeleccionada);
                new ModProveedor(this, proveedorSeleccionado, filaSeleccionada); // Crear nueva ventana de modificación
            } else {
                JOptionPane.showMessageDialog(Ventana_de_Proveedores, "Por favor, seleccione un proveedor para modificar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            }
        });

        // Agregar opciones al menú emergente
        menuOpciones.add(opcionAnadir);
        menuOpciones.add(opcionModificar);

        // Mostrar el menú emergente al hacer clic en el botón de opciones
        botonOpciones.addActionListener(e -> menuOpciones.show(botonOpciones, 0, botonOpciones.getHeight()));

        JPanel panelBuscador = new JPanel();
        panelBuscador.add(campoBusqueda);
        panelBuscador.add(botonBuscar);
        panelBuscador.add(botonOpciones); // Agregar el botón de opciones al panel

        tablaProveedores = new JTable(modeloTabla); // Inicializar tablaProveedores aquí
        tablaProveedores.setFont(new Font("Arial", Font.PLAIN, 16));
        tablaProveedores.setRowHeight(30);

        JScrollPane scrollPane = new JScrollPane(tablaProveedores);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(60, 60, 60, 60));

        Ventana_de_Proveedores.add(panelBuscador, BorderLayout.NORTH);
        Ventana_de_Proveedores.add(scrollPane, BorderLayout.CENTER);

        Ventana_de_Proveedores.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Ventana_de_Proveedores.setSize(800, 600);
        Ventana_de_Proveedores.setVisible(true);
    }

    private void actualizarTablaFiltrada(String busqueda) {
        modeloTabla.setRowCount(0);
        for (Object[] proveedor : proveedores) {
            if (proveedor[0].toString().toLowerCase().contains(busqueda.toLowerCase())) {
                modeloTabla.addRow(proveedor);
            }
        }
    }

    // Método para actualizar la tabla con el nuevo proveedor
    public void actualizarTablaConProveedor(String[] proveedor) {
        modeloTabla.addRow(proveedor);
    }

    // Método para actualizar un proveedor existente
    public void actualizarProveedor(int indice, String[] nuevoProveedor) {
        proveedores.set(indice, nuevoProveedor); // Actualizar el proveedor en la lista
        guardarProveedores(); // Guardar cambios en el archivo
        actualizarTabla(); // Actualizar la tabla
    }

    // Método para eliminar un proveedor
    public void eliminarProveedor(int indice) {
        proveedores.remove(indice); // Eliminar el proveedor de la lista
        guardarProveedores(); // Guardar cambios en el archivo
        actualizarTabla(); // Actualizar la tabla
    }

    private void guardarProveedores() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("Proyecto-OOP-Uaemex/Punto_de_venta/src/proveedores.txt"))) {
            for (Object[] proveedor : proveedores) {
                bw.write(String.join(",", (CharSequence[]) proveedor));
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error al guardar el archivo.");
        }
    }
}
