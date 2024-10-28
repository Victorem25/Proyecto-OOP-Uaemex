package punto_de_venta;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.awt.BorderLayout;


public class VentanaProveedores {

    private DefaultTableModel modeloTabla;
    private List<Object[]> proveedores;

    public VentanaProveedores() {
        modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("Proveedor");
        modeloTabla.addColumn("Próxima Entrega");
        modeloTabla.addColumn("Contacto");

        cargarProveedores();
        WindowProveedores();
    }

    private void cargarProveedores() {
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
        Ventana_de_Proveedores.setExtendedState(JFrame.MAXIMIZED_BOTH);

        JTextField campoBusqueda = new JTextField(40);
        campoBusqueda.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { actualizarTablaFiltrada(campoBusqueda.getText()); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { actualizarTablaFiltrada(campoBusqueda.getText()); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { actualizarTablaFiltrada(campoBusqueda.getText()); }
        });

        JButton botonBuscar = new JButton("Buscar");

        JPanel panelBuscador = new JPanel();
        panelBuscador.add(campoBusqueda);
        panelBuscador.add(botonBuscar);

        JTable tablaProveedores = new JTable(modeloTabla);
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(VentanaProveedores::new);
    }
}
