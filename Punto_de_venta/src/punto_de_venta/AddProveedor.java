package punto_de_venta;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;


public class AddProveedor extends JFrame {
    private VentanaProveedores ventanaProveedores; // Referencia a VentanaProveedores
    private JTextField campoNombre;
    private JTextField campoContacto;
    private JTextField campoNumero;
    private JComboBox<String> comboLapso;

    public AddProveedor(VentanaProveedores ventanaProveedores) {
        this.ventanaProveedores = ventanaProveedores; // Guarda la referencia
        setTitle("Registrar Nuevo Proveedor");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(5, 2));

        // Crear componentes
        JLabel etiquetaNombre = new JLabel("Ingresar Nombre:");
        campoNombre = new JTextField();
        JLabel etiquetaContacto = new JLabel("Ingresar Contacto:");
        campoContacto = new JTextField();
        JLabel etiquetaFrecuencia = new JLabel("Frecuencia de Entregas:");

        // ComboBox para la frecuencia de entrega
        comboLapso = new JComboBox<>(new String[]{"Días", "Semanas", "Meses"});
        campoNumero = new JTextField();

        // Crear botones
        JButton botonGuardar = new JButton("Guardar");
        JButton botonCancelar = new JButton("Cancelar");

        // Agregar componentes a la ventana
        add(etiquetaNombre);
        add(campoNombre);
        add(etiquetaContacto);
        add(campoContacto);
        add(etiquetaFrecuencia);
        add(comboLapso);
        add(campoNumero);
        add(botonGuardar);
        add(botonCancelar);

        // Acción del botón de guardar
        botonGuardar.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro de guardar?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                guardarProveedor();
            }
        });

        // Acción del botón de cancelar
        botonCancelar.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro de cancelar?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                dispose();
            }
        });

        // Agregar WindowListener para actualizar la tabla al cerrar
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                ventanaProveedores.cargarProveedores(); // Recargar proveedores
            }
        });

        setVisible(true);
    }

    private void guardarProveedor() {
        String nombre = campoNombre.getText().trim();
        String contacto = campoContacto.getText().trim();
        String lapso = comboLapso.getSelectedItem().toString();
        String frecuencia = campoNumero.getText().trim();

        if (nombre.isEmpty() || contacto.isEmpty() || frecuencia.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Calcular la próxima fecha de entrega
        LocalDate fechaActual = LocalDate.now();
        LocalDate proximaEntrega = null;

        try {
            int cantidad = Integer.parseInt(frecuencia);
            switch (lapso) {
                case "Días":
                    proximaEntrega = fechaActual.plusDays(cantidad);
                    break;
                case "Semanas":
                    proximaEntrega = fechaActual.plusWeeks(cantidad);
                    break;
                case "Meses":
                    proximaEntrega = fechaActual.plusMonths(cantidad);
                    break;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese un número válido para la frecuencia.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Formatear la fecha a String
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String fechaEntregaStr = proximaEntrega.format(formatter);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Proyecto-OOP-Uaemex/Punto_de_venta/src/proveedores.txt", true))) {
            writer.write(nombre + "," + fechaEntregaStr + "," + contacto); // Escribir la fecha de entrega en lugar del lapso
            writer.newLine();
            JOptionPane.showMessageDialog(this, "Nuevo proveedor añadido.");
            ventanaProveedores.actualizarTablaConProveedor(new String[]{nombre, fechaEntregaStr, contacto}); // Actualiza la tabla
            dispose(); // Cerrar la ventana después de guardar
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al guardar el proveedor.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
