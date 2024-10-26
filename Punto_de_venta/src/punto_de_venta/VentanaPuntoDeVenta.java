package punto_de_venta;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.Border;

public class VentanaPuntoDeVenta {
    // Modelo compartido para la lista de productos, accesible desde cualquier parte de la aplicación
    static DefaultListModel<String> modeloListaProductos = new DefaultListModel<>(); 

    // Método para inicializar y mostrar la ventana principal de venta
    public static void WindowPuntoDeVenta() {
        JFrame ventanaVenta = new JFrame("Ventana de venta");
        ventanaVenta.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE); // No cerrar la ventana cuando abres la de pagar
        ventanaVenta.setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximiza la ventana en pantalla completa
        ventanaVenta.setResizable(false); // Evita que el usuario redimensione la ventana

        // Configura el menú para la ventana de venta
        ClassMenuWindVenta menu = new ClassMenuWindVenta();
        ventanaVenta.setJMenuBar(menu.crearMenu());

        // Agrega el panel donde se mostrarán los productos y cantidades seleccionadas
        JScrollPane panelProductos = new PanelProductosCantidad();
        ventanaVenta.add(panelProductos);

        // Agrega el panel que mostrará el total por cada producto
        JScrollPane panelTotalProductoIndiv = new PanelCantProductos();
        ventanaVenta.add(panelTotalProductoIndiv);

        // Etiqueta "Busqueda" junto a la barra de búsqueda de productos
        JLabel busquedaLabel = new JLabel("Busqueda");
        busquedaLabel.setBounds(540, 20, 200, 30); // Posición y tamaño
        busquedaLabel.setFont(new Font("Arial", Font.BOLD, 20));
        ventanaVenta.add(busquedaLabel);

        // Barra de búsqueda para ingresar productos
        BarraBusqueda barraBusqueda = new BarraBusqueda();
        ventanaVenta.add(barraBusqueda.crearBarra());

        // Botón "Pagar" para proceder con la compra, enviando referencia de la ventana de venta
        ButtonPagar botonPagar = new ButtonPagar(ventanaVenta);
        ventanaVenta.add(botonPagar.crearBotonPagar());

        // Panel para mostrar el logo de la empresa
        JPanel panelLogo = new ImagenPanel("src/imagenes/Logo_Pollo de montaña.jpg");
        panelLogo.setBounds(1400, -10, 100, 130);
        panelLogo.setLayout(null);
        ventanaVenta.add(panelLogo);
        
        // Spinner para seleccionar la cantidad de productos
        CantidadSpinner cantidadSpinner = new CantidadSpinner();
        ventanaVenta.add(cantidadSpinner.crearSpinnerCantidad());
        
        // Botón "Cancelar" para limpiar la lista de productos
        ButtonCancelar botonCancelar = new ButtonCancelar();
        ventanaVenta.add(botonCancelar.crearBotonCancelar());
        
        //Crear TextArea para el total
        AreaMostrarTotal totalArea = new AreaMostrarTotal();
        ventanaVenta.add(totalArea.crearAreaTotal());

        // Layout manual para posicionar componentes
        ventanaVenta.setLayout(null);

        // Muestra la ventana en pantalla
        ventanaVenta.setVisible(true);
    }

    // Clase interna que define el panel de la lista de productos
    public static class PanelProductosCantidad extends JScrollPane {
        private final JList<String> listaProductos;

        public PanelProductosCantidad() {
            listaProductos = new JList<>(modeloListaProductos); // Asigna el modelo compartido
            listaProductos.setFont(new Font("Arial", Font.BOLD, 30)); // Fuente para la lista de productos
            this.setViewportView(listaProductos); // Agrega la lista al panel de desplazamiento
            this.setBounds(20, 100, 1200, 600); // Posición y tamaño del panel
        }
    }

    // Clase interna para el panel que muestra el total de cada producto
    public static class PanelCantProductos extends JScrollPane {
        private final JPanel panelCantTotalproducto;

        public PanelCantProductos() {
            panelCantTotalproducto = new JPanel();
            panelCantTotalproducto.setLayout(new BorderLayout());
            panelCantTotalproducto.setBackground(Color.LIGHT_GRAY); // Fondo del panel en color gris claro
            this.setViewportView(panelCantTotalproducto); // Agrega el panel al JScrollPane
            this.setBounds(1230, 100, 300, 600); // Posición y tamaño del panel
        }
    }

    // Clase interna para la barra de búsqueda que permite ingresar productos
    public static class BarraBusqueda {
        public JTextField crearBarra() {
            JTextField barraBusqueda = new JTextField();
            barraBusqueda.setColumns(20); // Configura el ancho de la barra de búsqueda
            barraBusqueda.setBounds(650, 20, 200, 30); // Posición y tamaño

            // Listener para añadir el producto al presionar Enter
            barraBusqueda.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String producto = barraBusqueda.getText(); // Obtiene el texto ingresado
                    if (!producto.isEmpty()) { // Verifica que no esté vacío
                        modeloListaProductos.addElement(producto); // Agrega el producto al modelo
                        barraBusqueda.setText(""); // Limpia la barra de búsqueda
                    }
                }
            });

            return barraBusqueda;
        }
    }

    // Clase interna para el botón de pagar
    public static class ButtonPagar {
        private final JFrame ventanaVenta;

        public ButtonPagar(JFrame ventanaVenta) {
            this.ventanaVenta = ventanaVenta;
        }

        public JButton crearBotonPagar() {
            JButton pagarButton = new JButton("Pagar");
            pagarButton.setBounds(1120, 720, 100, 30); // Posición y tamaño del botón

            // Listener para abrir la ventana de pago al presionar el botón
            pagarButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    VentanaPagar.WindowPagar(ventanaVenta); // Llama a la ventana de pago con referencia de venta
                }
            });

            return pagarButton;
        }
    }

    // Clase para mostrar una imagen (logo) en un JPanel
    static class ImagenPanel extends JPanel {
        private final Image imagen;

        public ImagenPanel(String rutaImagen) {
            this.imagen = new ImageIcon(rutaImagen).getImage(); // Carga la imagen desde la ruta
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this); // Dibuja la imagen en el panel
        }
    }

    // Clase interna para el spinner de selección de cantidad
    public static class CantidadSpinner {
        public JSpinner crearSpinnerCantidad() {
            SpinnerModel model = new SpinnerNumberModel(1, 1, 100, 1); // Configura valores de cantidad
            JSpinner spinnerCantidad = new JSpinner(model);
            spinnerCantidad.setBounds(900, 20, 50, 30); // Posición y tamaño del spinner

            return spinnerCantidad;
        }
    }

    // Clase interna para el botón de cancelar
    public static class ButtonCancelar {
        public JButton crearBotonCancelar() {
            JButton cancelarButton = new JButton("Cancelar");
            cancelarButton.setBounds(1000, 720, 100, 30); // Posición y tamaño

            // Listener para limpiar la lista de productos al presionar el botón
            cancelarButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    modeloListaProductos.clear(); // Limpia la lista de productos
                }
            });

            return cancelarButton;
        }
    }
    
    public static class AreaMostrarTotal{
        public JTextArea crearAreaTotal(){
            JTextArea areaTotal = new JTextArea();
            areaTotal.setBounds(1300, 705, 150, 100);
            areaTotal.setBackground(Color.WHITE);
            Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
            areaTotal.setBorder(border);
            return areaTotal;
        }
    }
}
