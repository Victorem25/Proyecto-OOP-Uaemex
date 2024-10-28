package punto_de_venta;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ModProveedor {

    private VentanaProveedores ventanaProveedores; // Instancia de VentanaProveedores
    private JTextField campoNombre;
    private JTextField campoContacto;
    private JTextField campoFrecuencia;
    private int indiceProveedor; // Índice del proveedor a modificar

    public ModProveedor(VentanaProveedores ventanaProveedores, Object[] proveedorSeleccionado, int indice) {
        this.ventanaProveedores = ventanaProveedores;
        this.indiceProveedor = indice;

        // Crear la ventana
        JFrame ventana = new JFrame("Modificar Proveedor");
        ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ventana.setSize(400, 300);
        ventana.setLayout(new GridLayout(4, 2));

        // Campos de texto
        campoNombre = new JTextField(proveedorSeleccionado[0].toString());
        campoContacto = new JTextField(proveedorSeleccionado[1].toString());
        campoFrecuencia = new JTextField(proveedorSeleccionado[2].toString());

        // Botones
        JButton botonGuardar = new JButton("Guardar");
        JButton botonCancelar = new JButton("Cancelar");
        JButton botonEliminar = new JButton("Eliminar");

        // ActionListener para guardar
        botonGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] proveedorModificado = {
                        campoNombre.getText(),
                        campoContacto.getText(),
                        campoFrecuencia.getText()
                };
                ventanaProveedores.actualizarProveedor(indiceProveedor, proveedorModificado);
                ventana.dispose();
            }
        });

        // ActionListener para cancelar
        botonCancelar.addActionListener(e -> ventana.dispose());

        // ActionListener para eliminar
        botonEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(ventana, "¿Estás seguro de que deseas eliminar este proveedor?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    ventanaProveedores.eliminarProveedor(indiceProveedor);
                    ventana.dispose();
                }
            }
        });

        // Añadir componentes a la ventana
        ventana.add(new JLabel("Nombre:"));
        ventana.add(campoNombre);
        ventana.add(new JLabel("Contacto:"));
        ventana.add(campoContacto);
        ventana.add(new JLabel("Frecuencia de Entrega:"));
        ventana.add(campoFrecuencia);
        ventana.add(botonEliminar);
        ventana.add(botonGuardar);
        ventana.add(botonCancelar);

        ventana.setVisible(true);
    }
}
