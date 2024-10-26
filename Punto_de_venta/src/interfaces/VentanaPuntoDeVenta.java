/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package interfaces;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 *
 * @author Viktor
 */
public class VentanaPuntoDeVenta {
    //Instancio una nueva ventana
    private JFrame puntoDeVenta = new JFrame("Pollo de montaña");
    public void VentanaPuntoDeVenta(String tipoUsuario){
        //Creando la ventana principal
        puntoDeVenta.setSize(800,600);
        
        //Desactivar el cierre de la ventana 
        puntoDeVenta.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        puntoDeVenta.setResizable(false);
        
        //Agregar funcionalidad a un boton para cerrar la ventana
        JPanel botonSalir = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton salir = new JButton("Salir");
        botonSalir.add(salir);
        salir.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent escucha){
                int resultado = JOptionPane.showConfirmDialog(puntoDeVenta, 
                    "¿Estás seguro de que quieres cerrar la ventana?", 
                    "Confirmar Cierre", 
                    JOptionPane.YES_NO_OPTION);
                if (resultado == JOptionPane.YES_OPTION) {
                    puntoDeVenta.dispose();
                }
            }
        });
        
        //Creando la barra de botones superior izquierda
        JPanel barraVentanas = new JPanel(new FlowLayout(FlowLayout.LEADING));
        barraVentanas.add(new JButton("Ventas"));
        barraVentanas.add(new JButton("Inventario"));
        barraVentanas.add(new JButton("Proveedores"));
        //Agregando opciones solamente visibles para un manager
        if(tipoUsuario.equals("manager")){
            barraVentanas.add(new JButton("Usuarios"));
            barraVentanas.add(new JButton("Corte de caja"));
            JMenuBar opciones = new JMenuBar();
            JMenu menu = new JMenu("Opciones");
            menu.add(new JMenuItem("Modificar datos"));
            menu.add(new JMenuItem("Agregar datos"));
            opciones.add(menu);
            barraVentanas.add(opciones);
        }
        
        puntoDeVenta.add(botonSalir,BorderLayout.SOUTH);
        puntoDeVenta.add(barraVentanas);
        puntoDeVenta.setVisible(true);
        
    }
    //Un getter para acceder al JFrame de la ventana
    public JFrame getFrame() {
        return puntoDeVenta;
    }
}
