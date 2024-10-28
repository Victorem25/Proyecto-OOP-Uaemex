package punto_de_venta;

import java.awt.Color;
import java.awt.Font;
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
        
        // Crear JLabel para el total
        AreaMostrarTotal totalArea = new AreaMostrarTotal();
        ventanaPagar.add(totalArea.crearAreaTotal());
        
        // JTextField para anotar lo recibido
        AreaDeRecibido recibidoArea = new AreaDeRecibido();
        ventanaPagar.add(recibidoArea.crearAreaRecibido());
        
        // Crear JLabel para el cambio
        AreaDeCambio cambioArea = new AreaDeCambio();
        ventanaPagar.add(cambioArea.crearAreaCambio());
        
        // Label para "Total"
        JLabel totalLabel = new JLabel("Total");
        totalLabel.setBounds(645, 50, 200, 30);
        totalLabel.setFont(new Font("Arial", Font.BOLD, 25));
        ventanaPagar.add(totalLabel);
        
        // Label para "Recibido"
        JLabel recibidoLabel = new JLabel("Recibido");
        recibidoLabel.setBounds(625, 190, 200, 30);
        recibidoLabel.setFont(new Font("Arial", Font.BOLD, 25));
        ventanaPagar.add(recibidoLabel);
        
        // Label para "Cambio"
        JLabel cambioLabel = new JLabel("Cambio");
        cambioLabel.setBounds(620, 270, 200, 30);
        cambioLabel.setFont(new Font("Arial", Font.BOLD, 25));
        ventanaPagar.add(cambioLabel);
        
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
    public static class BotonTerminar {
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
    
    // JLabel para mostrar el total
    public static class AreaMostrarTotal {
        public JLabel crearAreaTotal() {
            JLabel areaTotal = new JLabel("0.00");
            areaTotal.setBounds(600, 80, 150, 100);
            areaTotal.setOpaque(true);
            areaTotal.setBackground(Color.WHITE);
            areaTotal.setHorizontalAlignment(SwingConstants.CENTER);
            Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
            areaTotal.setBorder(border);
            areaTotal.setFont(new Font("Arial", Font.PLAIN, 20));
            return areaTotal;
        }
    }
    
    // JTextField para el campo de recibido
    public static class AreaDeRecibido {
        public JTextField crearAreaRecibido() {
            JTextField areaRecibido = new JTextField();
            areaRecibido.setBounds(600, 220, 150, 30);
            areaRecibido.setBackground(Color.WHITE);
            Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
            areaRecibido.setBorder(border);
            return areaRecibido;
        }
    }
    
    // JLabel para mostrar el cambio
    public static class AreaDeCambio {
        public JLabel crearAreaCambio() {
            JLabel areaCambio = new JLabel("0.00");
            areaCambio.setBounds(600, 300, 150, 100);
            areaCambio.setOpaque(true);
            areaCambio.setBackground(Color.WHITE);
            areaCambio.setHorizontalAlignment(SwingConstants.CENTER);
            Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
            areaCambio.setBorder(border);
            areaCambio.setFont(new Font("Arial", Font.PLAIN, 20));
            return areaCambio;
        }
    }
}
