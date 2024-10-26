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
public class Ventas {
    public void Ventas(VentanaPuntoDeVenta venta){
        JPanel barraDeBusqueda = new JPanel();
        barraDeBusqueda.add(new JLabel("Busqueda: "));
        barraDeBusqueda.add(new JTextField());
        barraDeBusqueda.setSize(300,50);
        //barraDeBusqueda.setAlignmentX(venta.getFrame().);
        venta.getFrame().add(barraDeBusqueda,BorderLayout.SOUTH);
        
        venta.getFrame().revalidate();
        venta.getFrame().repaint();
    }
}
