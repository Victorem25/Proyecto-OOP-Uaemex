package punto_de_venta;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.*;
import javax.swing.border.Border;

public class VentanaPagar {
    // Método para mostrar la ventana de pago y recibir la ventana de venta
    public static void WindowPagar(JFrame ventanaVenta) {
        JFrame ventanaPagar = new JFrame("Pago");
        ventanaPagar.setSize(800, 500); // Establece el tamaño de la ventana de pago
        ventanaPagar.setLayout(null); // Usa un layout manual
        ventanaPagar.setLocationRelativeTo(null); // Centra la ventana en la pantalla
        ventanaPagar.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Cierra solo esta ventana al salir

        // Panel para mostrar los productos agregados desde la lista compartida
        JScrollPane panelProductos = new PanelMostrarProductos();
        panelProductos.setBounds(20, 40, 550, 300); // Posición y tamaño del panel
        ventanaPagar.add(panelProductos);
        
        // Botón "Terminar" para generar el PDF con el ticket
        BotonTerminar botonPagar = new BotonTerminar();
        ventanaPagar.add(botonPagar.creaBotonTerminar());
        
        // Botón "Volver" para regresar a la pantalla de venta
        BotonVolver botonVolver = new BotonVolver(ventanaVenta, ventanaPagar);
        ventanaPagar.add(botonVolver.crearBotonVolver());
        
        //Crear TextArea para el total
        AreaMostrarTotal totalArea = new AreaMostrarTotal();
        ventanaPagar.add(totalArea.crearAreaTotal());
        
        ventanaPagar.setVisible(true);
    }

    // Clase interna para mostrar los productos en la ventana de pago
    public static class PanelMostrarProductos extends JScrollPane {
        private final JList<String> listaProducts;

        public PanelMostrarProductos() {
            // Usa el modelo compartido de lista de productos
            listaProducts = new JList<>(VentanaPuntoDeVenta.modeloListaProductos);
            this.setViewportView(listaProducts); // Agrega la lista al JScrollPane
            this.setBounds(20, 100, 200, 200); // Posición y tamaño
        }
    }
    
    // Clase interna para el botón "Terminar" que generará el ticket en PDF
    public static class BotonTerminar{
        public JButton creaBotonTerminar(){
            JButton terminarButton = new JButton("Terminar");
            terminarButton.setBounds(470, 350, 100, 30);
            
            // Listener para generar el PDF
            terminarButton.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e){
                    // Código para generar el PDF con el ticket
                }
            });
            return terminarButton;
        }
    }

    // Clase interna para el botón "Volver" que cierra la ventana de pago y muestra la de venta
    public static class BotonVolver {
        private final JFrame ventanaVenta;
        private final JFrame ventanaPagar;

        // Constructor que recibe las ventanas de venta y de pago
        public BotonVolver(JFrame ventanaVenta, JFrame ventanaPagar) {
            this.ventanaVenta = ventanaVenta;
            this.ventanaPagar = ventanaPagar;
        }

        public JButton crearBotonVolver(){
            JButton volverButton = new JButton("Volver");
            volverButton.setBounds(350, 350, 100, 30);
            
            volverButton.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                    ventanaPagar.dispose(); // Cierra la ventana de pago
                    ventanaVenta.setVisible(true); // Muestra la ventana de venta
                }
            });
            return volverButton;
        }
    }
    
    public static class AreaMostrarTotal{
        public JTextArea crearAreaTotal(){
            JTextArea areaTotal = new JTextArea();
            areaTotal.setBounds(600, 300, 150, 100);
            areaTotal.setBackground(Color.WHITE);
            Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
            areaTotal.setBorder(border);
            return areaTotal;
        }
    }
}
